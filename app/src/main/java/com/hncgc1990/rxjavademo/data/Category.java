
package com.hncgc1990.rxjavademo.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class Category {

    @SerializedName("contents")
    private List<Content> mContents;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private Long mStatus;
    @SerializedName("total")
    private Long mTotal;

    public List<Content> getContents() {
        return mContents;
    }

    public void setContents(List<Content> contents) {
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
        return "Category{" +
                "mContents=" + mContents +
                ", mMessage='" + mMessage + '\'' +
                ", mStatus=" + mStatus +
                ", mTotal=" + mTotal +
                '}';
    }
}
