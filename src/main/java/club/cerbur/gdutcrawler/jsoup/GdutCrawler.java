package club.cerbur.gdutcrawler.jsoup;

import club.cerbur.gdutcrawler.exception.EducationSystemException;
import club.cerbur.gdutcrawler.exception.LoginException;
import club.cerbur.gdutcrawler.exception.MaxFrequencyException;
import club.cerbur.gdutcrawler.exception.ParameterIsNullException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cerbur
 */
public class GdutCrawler implements Crawler {

    /**
     * 这里是schoolId但为了和统一身份认证系统的表单统一所以取名username
     */
    private String username;

    /**
     * 统一身份认证系统的密码
     */
    private String password;

    /**
     * 最大超时时间
     */
    private int outTime;

    /**
     * 最大请求次数
     */
    private int maxFrequency;

    public GdutCrawler(String username, String password) throws ParameterIsNullException {
        //如果username或者password为空则此类不能被创建并抛出异常
        if (username.isBlank()) {
            throw new ParameterIsNullException("username");
        } else if (password.isBlank()) {
            throw new ParameterIsNullException("password");
        }
        this.username = username;
        this.password = password;
        this.outTime = DEFAULT_OUT_TIME;
        this.maxFrequency = DEFAULT_MAX_FREQUENCY;
    }

    /**
     * 登录的URL
     * [GET]:获取页面参数和cookie
     * [POST]:提交登录请求获取教务系统Cookie
     */
    public static final String LOGIN_URL = "http://authserver.gdut.edu.cn/authserver/login?service=http://jxfw.gdut.edu.cn/new/ssoLogin";

    /**
     * 类成功被创建后用于登录获取cookies
     *
     * @return 带有cookie的下一个请求体
     * @throws ParameterIsNullException 必要参数为空异常
     * @throws EducationSystemException 教务系统异常
     * @throws LoginException           登录异常/密码错误
     * @throws IOException              IO异常/不太可能出现
     */
    @Override
    public Response connect() throws ParameterIsNullException, EducationSystemException, LoginException, IOException {
        Connection.Response response = null;
        var i = 0;
        while (i++ < maxFrequency && response == null) {
            response = Jsoup.connect(LOGIN_URL)
                    .timeout(this.outTime)
                    .execute();
        }
        //多次请求失败则认为是教务系统发生异常
        if (response == null) {
            throw new EducationSystemException();
        }

        //处理文本内容提取隐藏的参数
        Document doc = response.parse();
        Element content = doc.getElementById("casLoginForm");
        Elements links = content.getElementsByTag("input");

        //构建form表单提交的参数
        Map<String, String> dataMap = new HashMap<>(10);
        for (Element link : links) {
            if ("hidden".equals(link.attr("type"))) {
                dataMap.put(link.attr("name"), link.attr("value"));
            }
        }
        //判断是不是会出现没有这个隐藏数据的可能
        if (dataMap.isEmpty()) {
            throw new ParameterIsNullException("Hidden dataMap Not found");
        }
        dataMap.put("username", this.username);
        dataMap.put("password", this.password);


        var j = 0;
        Connection.Response res = null;
        while (j++ < maxFrequency && res == null) {
            res = Jsoup.connect(LOGIN_URL)
                    .timeout(this.outTime)
                    .validateTLSCertificates(false)
                    .cookies(response.cookies())
                    .data(dataMap)
                    .method(Connection.Method.POST)
                    .execute();
        }
        //当超过请求次数，认为教务系统发生异常
        if (res == null) {
            throw new EducationSystemException();
        }
        //获取结果中的cookies
        Map<String, String> cookies = res.cookies();

        //如果cookies为空说明密码错误抛出异常
        if (cookies.isEmpty()) {
            throw new LoginException("LoginFail...May be caused by your wrong password");
        }
        //成功返回一个带cookies的新请求体
        return new GdutCrawler.Response(cookies);
    }

    public static class Response implements Crawler.Response {
        private Map<String, String> cookies;
        private int outTime;
        private int maxFrequency;

        public static final String SCHEDULE_URL = "https://jxfw.gdut.edu.cn/xsgrkbcx!getDataList.action";

        public static final String GRADE_URL = "https://jxfw.gdut.edu.cn/xskccjxx!getDataList.action";

        public static final String EXAM_URL = "https://jxfw.gdut.edu.cn/xsksap!getDataList.action";

        public static final String CAMPUS_URL = "https://jxfw.gdut.edu.cn/xjkpxx!xjkpxx.action";

        private Response(Map<String, String> cookies) {
            this.cookies = cookies;
            this.outTime = DEFAULT_OUT_TIME;
            this.maxFrequency = DEFAULT_MAX_FREQUENCY;
        }

        /**
         * 爬虫仅仅获取目标json字符串不做任何处理，可通过service层自行提取所需字符
         *
         * @return 不做任何处理的课表json字符串
         * @throws MaxFrequencyException 抛出连接超过最大上限异常/多次请求未成功
         */
        public String getSchedule() throws MaxFrequencyException {
            Map<String, String> dataMap = new HashMap<>(6);
            dataMap.put("zc", "");
            dataMap.put("xnxqdm", "201901");
            dataMap.put("page", "1");
            dataMap.put("rows", "1000");
            dataMap.put("sort", "kxh");
            return getString(dataMap, SCHEDULE_URL);
        }

        /**
         * 爬虫仅仅获取目标json字符串不做任何处理，可通过service层自行提取所需字符
         *
         * @return 不做任何处理的课表json字符串
         * @throws MaxFrequencyException 抛出最大请求异常，服务器超时
         */
        public String getGrade() throws MaxFrequencyException {
            Map<String, String> dataMap = new HashMap<>(6);
            dataMap.put("xnxqdm", "");
            dataMap.put("jhlxdm", "");
            dataMap.put("page", "1");
            dataMap.put("rows", "1000");
            dataMap.put("sort", "xnxqdm");
            return getString(dataMap, GRADE_URL);
        }

        /**
         * 爬虫仅仅获取目标json字符串不做任何处理，可通过service层自行提取所需字符
         *
         * @return 不做任何处理的成绩json字符串
         * @throws MaxFrequencyException 抛出连接超过最大上限异常/多次请求未成功
         */
        public String getExam() throws MaxFrequencyException {
            Map<String, String> dataMap = new HashMap<>(6);
            dataMap.put("xnxqdm", "201901");
            dataMap.put("ksaplxdm", "");
            dataMap.put("page", "1");
            dataMap.put("rows", "1000");
            dataMap.put("sort", "zc,xq,jcdm2");
            return getString(dataMap, EXAM_URL);
        }

        /**
         * 爬虫仅仅获取目标json字符串不做任何处理，可通过service层自行提取所需字符
         *
         * @return 校区字符串/如：大学城校区
         * @throws MaxFrequencyException 抛出连接超过最大上限异常/多次请求未成功
         */
        public String getCampus() throws MaxFrequencyException {
            String res = null;
            var i = 0;
            while (i++ < this.maxFrequency && res == null) {
                try {
                    res = Jsoup.connect(CAMPUS_URL)
                            .cookies(this.cookies)
                            .timeout(outTime)
                            .validateTLSCertificates(false)
                            .method(Connection.Method.GET)
                            .execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (res == null) {
                throw new MaxFrequencyException();
            }
            res = Jsoup.parse(res).body().text();
            return res.substring(res.indexOf("校区： ") + 4, res.indexOf(" 学生状态： "));
        }

        private String getString(Map<String, String> dataMap, String url) throws MaxFrequencyException {
            dataMap.put("order", "asc");
            String res = null;
            var i = 0;
            while (i++ < this.maxFrequency && res == null) {
                try {
                    res = Jsoup.connect(url)
                            .cookies(this.cookies)
                            .timeout(outTime)
                            .data(dataMap)
                            .validateTLSCertificates(false)
                            .method(Connection.Method.POST)
                            .execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (res == null) {
                throw new MaxFrequencyException();
            }
            return res.substring(res.indexOf("["), res.lastIndexOf("]") + 1);
        }

        public Response frequencyOut(int count) {
            this.maxFrequency = count;
            return this;
        }

        public Response timeout(int outTime) {
            this.outTime = outTime;
            return this;
        }

        @Override
        public String toString() {
            return "Response{" +
                    "cookies=" + cookies +
                    ", outTime=" + outTime +
                    ", maxFrequency=" + maxFrequency +
                    '}';
        }
    }

    @Override
    public GdutCrawler timeout(int timeout) {
        this.outTime = timeout;
        return this;
    }

    public GdutCrawler frequencyOut(int count) {
        this.maxFrequency = count;
        return this;
    }



}
