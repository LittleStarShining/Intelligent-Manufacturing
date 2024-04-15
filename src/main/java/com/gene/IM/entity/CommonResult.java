package com.gene.IM.entity;

public final class CommonResult<T> {

    private int code = 1;
    private T resultBody;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    // 响应消息
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }



    public CommonResult() {
    }




    public T getResultBody() {
        return resultBody;
    }

    public void setResultBody(T resultBody) {
        this.resultBody = resultBody;
    }

    public CommonResult<T> success(T resultBody, String msg) {
        this.code = 1;
        this.resultBody = resultBody;
        this.msg = msg + "成功";
        return this;
    }

    public  CommonResult<T> failed(T resultBody,String msg) {
        this.code = 0;
        this.resultBody = resultBody;
        this.msg = msg + "失败";
        return this;
    }

    public CommonResult(T resultBody) {
        if(resultBody!=null) {
            this.resultBody = resultBody;
            this.msg = "成功";
            this.code = 1;
        }
        else{
                this.msg = "失败";
                this.code = 0;
        }
    }

    public CommonResult(T resultBody, String msg) {
        if(resultBody!=null) {

            this.resultBody = resultBody;
            this.msg = msg + ",成功";
            this.code = 1;
        }
        else{
                this.msg = msg + ",失败";
                this.code = 0;
        }
    }
}

