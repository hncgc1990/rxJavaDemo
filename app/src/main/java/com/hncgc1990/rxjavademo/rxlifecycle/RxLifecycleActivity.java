package com.hncgc1990.rxjavademo.rxlifecycle;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hncgc1990.rxjavademo.util.Logger;
import com.trello.rxlifecycle2.components.RxActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by cgc on 17-4-5.
 */

public class RxLifecycleActivity extends RxActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


       /* Observable.interval(2, TimeUnit.SECONDS)
                .doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {
                        Logger.d("取消了訂閱");
                    }
                }).compose(this.<Long>bindUntilEvent(ActivityEvent.PAUSE))
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Logger.d("正在發射事件:_____"+aLong);
                    }
                });*/


//        Observable.interval(2, TimeUnit.SECONDS)
//                .doOnDispose(new Action() {
//                    @Override
//                    public void run() throws Exception {
//                        Logger.d("取消了訂閱");
//                    }
//                }).compose(this.<Long>bindToLifecycle())
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        Logger.d("正在發射事件:_____"+aLong);
//                    }
//                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d("onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.d("onPause");

        Observable.interval(2, TimeUnit.SECONDS)
                .doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {
                        Logger.d("取消了訂閱");
                    }
                }).compose(this.<Long>bindToLifecycle())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Logger.d("正在發射事件:_____"+aLong);
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.d("onResume");


    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.d("onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.d("onStop");
    }
}
