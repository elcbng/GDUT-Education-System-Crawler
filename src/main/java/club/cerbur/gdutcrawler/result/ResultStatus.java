package club.cerbur.gdutcrawler.result;

/**
 * @author Cerbur
 * 这是一个用来存各种返回码和对应的信息的枚举类
 */
public enum ResultStatus {
    /**
     * 我们对errorCode做如下规定
     * 1. 所有error为了避免和http的error码发生冲突采用负数
     * 2. 1         代表成功
     * 3. -200~-299 为用户通过正常前端操作造成的错误
     * 4. -300~-399 为用户通过正常前端操作造成的因为我们自身服务器造成的错误
     * 5. -400~-499 并非用户通过正常前端操作造成的错误（这里不需要写入api文档
     * 6. -500~-599 上游服务器照成的错误，如教务系统
     */

    LOGINERROR(-201,"用户名或密码错误"),

    MAXFREQUENCYERROR(-301, "爬虫请求超过了最大的请求次数，可能是服务器"),

    PARAMMISSERROR(-401,"必要参数缺失"),
    JSOUPERROR(-402,"jsoup组件异常,这里出现了业务逻辑上的bug,请联系开发者"),

    EDUCATIONSYSTEMERROR(-501,"教务系统异常"),
//    ERRORNAME(ERRORCODE, ERRORMSG),
    SUCCESS(1, "成功");

    private int code;

    private String msg;

    ResultStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
