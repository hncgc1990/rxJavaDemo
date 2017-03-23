
package com.hncgc1990.rxjavademo.data;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class CategoryDetail {

    @SerializedName("contents")
    private Contents mContents;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private Long mStatus;
    @SerializedName("total")
    private Long mTotal;

    public Contents getContents() {
        return mContents;
    }

    public void setContents(Contents contents) {
        mContents = contents;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }

    public Long getTotal() {
        return mTotal;
    }

    public void setTotal(Long total) {
        mTotal = total;
    }

    @Override
    public String toString() {
        return "CategoryDetail{" +
                "mContents=" + mContents +
                ", mMessage='" + mMessage + '\'' +
                ", mStatus=" + mStatus +
                ", mTotal=" + mTotal +
                '}';
    }
}
