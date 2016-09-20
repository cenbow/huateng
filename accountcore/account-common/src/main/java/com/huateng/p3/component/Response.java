package com.huateng.p3.component;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * RPC 璋冪敤杩斿洖
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-07-23
 */
public class Response<T> implements Serializable {
    private static final long serialVersionUID = 8350327877975282483L;

    private boolean success;  //琛ㄧず璋冪敤鏄惁鎴愬姛 ,濡傛灉涓簍rue,鍒欏彲浠ヨ皟鐢╣etResult,濡傛灉涓篺alse,鍒欒皟鐢╡rrorCode鏉ヨ幏鍙栧嚭閿欎俊鎭�

    private T result;  //鑾峰彇璋冪敤杩斿洖鍊�

    private String errorCode; //鑾峰彇閿欒鐮�

    private String errorMsg;

    public Response() {
    }

    public Response(T result) {
        this.success = true;
        this.result = result;
    }


    /**
     * 鏋勯�鏂规硶锛屾牴鎹甪lag杩斿洖涓嶅悓缁撴灉
     *
     * @param flag   true|false
     * @param result   鑻lag=true锛屽垯杩斿洖result瀵硅薄,鑻lag=false鍒欒繑鍥瀍rrorCode
     */
    public Response(boolean flag, T result) {
        if (flag) {
            this.success = true;
            this.result = result;
        } else {
            this.success = false;
            this.errorCode = (String) result;
        }
    }

    public Response(String errorCode) {
        this.success = false;
        this.errorCode = errorCode;
    }

    public Response(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        success = true;
        this.result = result;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.success = false;
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Response response = (Response) o;

        if (success != response.success) return false;
        if (!errorCode.equals(response.errorCode)) return false;
        if (!result.equals(response.result)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result1 = (success ? 1 : 0);
        result1 = 31 * result1 + result.hashCode();
        result1 = 31 * result1 + errorCode.hashCode();
        return result1;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("success", success)
                .add("result", result)
                .add("errorCode", errorCode)
                .omitNullValues()
                .toString();
    }
}
