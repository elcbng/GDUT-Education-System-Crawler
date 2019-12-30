package club.cerbur.gdutcrawler.result;

/**
 * 统一返回值的工具类
 *
 * @author Cerbur
 */
public class ResultUtil {
    /**
     * 成功时带data返回的json
     * @param data 返回给前端的object
     * @return obj
     */
    public static GdutResult<Object> success(Object data) {
        GdutResult<Object> result = new GdutResult<>();
        result.setError(ResultStatus.SUCCESS.getCode());
        result.setMsg(ResultStatus.SUCCESS.getMsg());
        result.setData(data);
        return result;
    }

    /**
     * 成功时无data返回的json
     * @return error 1 msg
     */
    public static GdutResult<Object> success() {
        return success(null);
    }

    /**
     * 失败时返回的json
     *
     *
     * @return json
     */
    public static GdutResult<Object> error(ResultStatus status) {
        GdutResult<Object> result = new GdutResult<>();
        result.setError(status.getCode());
        result.setMsg(status.getMsg());
        return result;
    }
}
