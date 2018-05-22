package group.rober.runtime.lang;

import java.text.MessageFormat;

/**
 * 定义格式化之后的RuntimeException
 *
 * @author tisir<yangsong158@qq.com>
 * @date 2017年2月18日
 */
public class RoberException extends RuntimeException {

    private static final long serialVersionUID = -2049467256019982005L;
    private String code = "0";


    public RoberException() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @param message
     */
    public RoberException(String message) {
        super(message);
    }

    /**
     *
     * @param messageFormat
     * @param objects
     */
    public RoberException(String messageFormat, Object ...objects) {
        this(MessageFormat.format(messageFormat, objects));
    }    /**
     *
     * @param messageFormat
     * @param objects
     */
    public RoberException(Throwable cause, String messageFormat, Object ...objects) {
        this(MessageFormat.format(messageFormat, objects),cause);
    }

    /**
     * @param cause
     * @param message
     */
    public RoberException(Throwable cause, String message) {
        super(message, cause);
    }

    /**
     * @param cause
     */
    public RoberException(Throwable cause) {
        super(cause);
    }
    

    /**
     * @param message
     * @param cause
     */
    public RoberException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public RoberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
