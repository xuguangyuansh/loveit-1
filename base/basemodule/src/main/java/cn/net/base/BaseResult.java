package cn.net.base;

import java.io.Serializable;
import org.json.JSONObject;


public class BaseResult<T> implements Serializable {

    //用户misscall
    private String token;

    private int code;

    private String msg;

    private boolean success;

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getJsonString(){
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code",code);
            jsonObject.put("msg",msg);
            jsonObject.put("success",success);
            return jsonObject.toString();
        }catch(Exception e){}
        return null;
    }
}
