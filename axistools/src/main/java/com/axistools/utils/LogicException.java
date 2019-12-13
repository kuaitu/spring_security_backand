package com.axistools.utils;

/**
 * <br>Description: 系统逻辑异常类，一般指编码时可以直接判定的异常
 * <br>Copyright: Copyright (c) 2012    
 * <pre>
 * 修改记录：
 * 修改日期       修改人     修改原因     版本
 * </pre>
 */
public class LogicException extends RuntimeException {
    /** serialVersionUID */
    private static final long serialVersionUID = 1900229357925849028L;

    /** 异常编码 */
    private String excepCode;

    public String getExcepCode() {
        return excepCode;
    }

    public void setExcepCode(String excepCode) {
        this.excepCode = excepCode;
    }

    /**
     * Constructs a new exception with <code>null</code> as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public LogicException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message. The cause
     * is not initialized, and may subsequently be initialized by a call to
     * {@link #initCause}.
     * 
     * @param message the detail message. The detail message is saved for later
     * retrieval by the {@link #getMessage()} method.
     */
    public LogicException(String message) {
        super(message);
    }

    public LogicException(String excepCode, String message) {
        super(message);
        this.excepCode = excepCode;
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     * <p>Note that the detail message associated with <code>cause</code> is
     * <i>not</i> automatically incorporated in this exception's detail message.
     * 
     * @param message the detail message (which is saved for later retrieval by
     * the {@link #getMessage()} method).
     * @param cause the cause (which is saved for later retrieval by the
     * {@link #getCause()} method). (A <tt>null</tt> value is permitted, and
     * indicates that the cause is nonexistent or unknown.)
     * @since 1.4
     */
    public LogicException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause and a detail message
     * of <tt>(cause==null ? null : cause.toString())</tt> (which typically
     * contains the class and detail message of <tt>cause</tt>). This
     * constructor is useful for exceptions that are little more than wrappers
     * for other throwables (for example,
     * {@link java.security.PrivilegedActionException}).
     * 
     * @param cause the cause (which is saved for later retrieval by the
     * {@link #getCause()} method). (A <tt>null</tt> value is permitted, and
     * indicates that the cause is nonexistent or unknown.)
     * @since 1.4
     */
    public LogicException(Throwable cause) {
        super(cause);
    }

    public LogicException(String excepCode, String message, Throwable cause) {
        super(message);
        this.excepCode = excepCode;
    }
}
