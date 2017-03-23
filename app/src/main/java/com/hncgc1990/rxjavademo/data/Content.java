
package com.hncgc1990.rxjavademo.data;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Content {

    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("describe")
    private String mDescribe;
    @SerializedName("id")
    private Long mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("sequence")
    private Long mSequence;
    @SerializedName("updated_at")
    private String mUpdatedAt;

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getDescribe() {
        return mDescribe;
    }

    public void setDescribe(String describe) {
        mDescribe = describe;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Long getSequence() {
        return mSequence;
    }

    public void setSequence(Long sequence) {
        mSequence = sequence;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Content{" +
                "mCreatedAt='" + mCreatedAt + '\'' +
                ", mDescribe='" + mDescribe + '\'' +
                ", mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mSequence=" + mSequence +
                ", mUpdatedAt='" + mUpdatedAt + '\'' +
                '}';
    }
}
