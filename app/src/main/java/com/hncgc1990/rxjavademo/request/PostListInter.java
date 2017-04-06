package com.hncgc1990.rxjavademo.request;

import com.hncgc1990.rxjavademo.data.PostData;
import com.hncgc1990.rxjavademo.data.Result;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2017/4/5.
 */
public interface PostListInter {


    @GET("data/Android/13/1")
    public Observable<PostData<List<Result>>> getPostList();
}
