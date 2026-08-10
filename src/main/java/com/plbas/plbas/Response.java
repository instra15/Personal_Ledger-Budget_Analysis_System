package com.plbas.plbas;

import lombok.Data;

@Data
public class Response<T> {
    private T data;

    private boolean success;

    private String errorMsg;

    public static <K> Response<K> success(K data)
    {
        Response<K> response=new Response<>();
        response.setData(data);
        response.setSuccess(true);
        response.setErrorMsg(null);
        return response;
    }

    public static <K> Response<K> fail(Exception e)
    {
        Response<K> response=new Response<>();
        response.setSuccess(false);
        response.setErrorMsg(e.getMessage());
        return response;
    }

}
