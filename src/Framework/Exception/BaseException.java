/*
 * copyright_1
 * copyright_2
 * Copyright (c) 2010 Sun , Inc. All Rights Reserved.
 */

package Framework.Exception;

/**
 *
 * @author Administrator
 */
public class BaseException extends Exception {
        private String _code;
        private String _message;

    /**
     * Creates a new instance of <code>BaseException</code> without detail message.
     */
    public BaseException() {
    }


    /**
     * Constructs an instance of <code>BaseException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public BaseException(String msg) {
        super(msg);
    }

    public String getCode() {
        return _code;
    }

    public void setCode(String Code) {
        this._code = Code;
    }



    public String getUserMessage() {
        return _message;
    }

    public void setUserMessage(String string) {
        this._message = string;
    }




}
