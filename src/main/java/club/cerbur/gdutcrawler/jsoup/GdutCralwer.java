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
public class GdutCralwer implements Crawler {

    private String username;

    private String password;

    private int outTime;

    private int maxFrequency;

    /**
     *
     */
    public static final String LOGIN_URL = "http://authserver.gdut.edu.cn/authserver/login?service=http://jxfw.gdut.edu.cn/new/ssoLogin";


    @Override
    public Response connect() throws ParameterIsNullException, EducationSystemException, LoginException, IOException, MaxFrequencyException {
        if (username.isBlank()) {
            throw new ParameterIsNullException("username");
        } else if (password.isBlank()) {
            throw new ParameterIsNullException("password");
        }
        Connection.Response response = null;
        var i = 0;
        while (i++ < maxFrequency && response == null) {
            response = Jsoup.connect(LOGIN_URL)
                    .timeout(this.outTime)
                    .execute();
        }
        if (response == null) {
            throw new EducationSystemException();
        }
        Document doc = response.parse();
        Element content = doc.getElementById("casLoginForm");
        Elements links = content.getElementsByTag("input");
        Map<String, String> dataMap = new HashMap<>(10);
        for (Element link : links) {
            if ("hidden".equals(link.attr("type"))) {
                dataMap.put(link.attr("name"), link.attr("value"));
            }
        }
        if (dataMap.isEmpty()) {
            throw new ParameterIsNullException("Hidden dataMap");
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
        if (res == null) {
            throw new EducationSystemException();
        }
        Map<String, String> cookies = res.cookies();
        if (cookies.isEmpty()) {
            throw new LoginException("LoginFail...May be caused by your wrong password");
        }
        return new GdutCralwer.Response(cookies);
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
         * @return string
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

        public String getGrade() throws MaxFrequencyException {
            Map<String, String> dataMap = new HashMap<>(6);
            dataMap.put("xnxqdm", "");
            dataMap.put("jhlxdm", "");
            dataMap.put("page", "1");
            dataMap.put("rows", "1000");
            dataMap.put("sort", "xnxqdm");
            return getString(dataMap, GRADE_URL);
        }

        public String getExam() throws MaxFrequencyException {
            Map<String, String> dataMap = new HashMap<>(6);
            dataMap.put("xnxqdm", "201901");
            dataMap.put("ksaplxdm", "");
            dataMap.put("page", "1");
            dataMap.put("rows", "1000");
            dataMap.put("sort", "zc,xq,jcdm2");
            return getString(dataMap, EXAM_URL);
        }

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
    public GdutCralwer timeout(int timeout) {
        this.outTime = timeout;
        return this;
    }

    public GdutCralwer frequencyOut(int count) {
        this.maxFrequency = count;
        return this;
    }

    public GdutCralwer(String username, String password) {
        this.username = username;
        this.password = password;
        this.outTime = DEFAULT_OUT_TIME;
        this.maxFrequency = DEFAULT_MAX_FREQUENCY;
    }


}
