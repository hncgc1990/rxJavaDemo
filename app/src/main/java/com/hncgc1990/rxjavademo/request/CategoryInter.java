package com.hncgc1990.rxjavademo.request;

import com.hncgc1990.rxjavademo.data.Category;
import com.hncgc1990.rxjavademo.data.CategoryDetail;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2017/3/21.
 */
public interface CategoryInter {


    @GET("/api/v1/categories.json?app_id=eec3d31c2c1d34a3&sign=5eff089ca98eed84a3a65a8505b9a6b4")
    public Observable<Category> getCategories();


    @GET("api/v1/categories/{category}.json?app_id=eec3d31c2c1d34a3&sign=5eff089ca98eed84a3a65a8505b9a6b")
    public Observable<CategoryDetail> getCategoryDetail(@Path("category") long categoryid);

}
