package com.ghz.flow.exception;

/**
 * 异常扩展类
 * Created by admi on 2017/10/12.
 */
public class CustomException extends Exception {

    public String message;

    public CustomException(){

    }
    public  CustomException(String message){
      super(message);

    }

    @Override
    public String toString() {
        return "CustomException{" +
                "message='" + message + '\'' +
                '}';
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
