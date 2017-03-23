package com.hncgc1990.rxjavademo;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/3/21.
 */
public class RetrofitSingle {


    private static Retrofit retrofit;


    public static  Retrofit getInstance(){

        synchronized (RetrofitSingle.class){
            if(retrofit==null){
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);//设置查看日志的等级
                OkHttpClient client=new OkHttpClient.Builder().addInterceptor(logging ).build();

                retrofit = new Retrofit.Builder()
                        .client(client)
                        .baseUrl("https://guangzhouudesk.udesk.cn/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();

            }
        }
        return retrofit;
    }


}
