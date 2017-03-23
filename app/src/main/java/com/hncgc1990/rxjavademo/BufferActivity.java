package com.hncgc1990.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.hncgc1990.rxjavademo.util.Logger;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;

public class BufferActivity extends AppCompatActivity {


    @BindView(R.id.btn_buffer)
    Button btn_buffer;

    @BindView(R.id.btn_two)
    Button btn_two;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buffer);
        ButterKnife.bind(this);
        init();
    }



    private void init(){

        RxView.clicks(btn_buffer)
                .map(new Function<Object,Integer>(){
            @Override
            public Integer apply(Object s) throws Exception {

                Logger.d("点击了一次");
                return 1;
            }
        }).buffer(4, TimeUnit.SECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableObserver<List<Integer>>() {
            @Override
            public void onNext(List<Integer> value) {
                if (value.size() > 0) {
                    Logger.d(String.format("%d taps", value.size()));
                } else {
                   Logger.d("--------- No taps received ");
                }
            }

            @Override
            public void onError(Throwable e) {
                Logger.d("onError");
            }

            @Override
            public void onComplete() {
                Logger.d("onComplete");
            }
        });


        RxView.clicks(btn_two)
                .map(new Function<Object, Integer>() {
                    @Override
                    public Integer apply(Object o) throws Exception {

                        Logger.d("click once");


                        return 1;
                    }
                }).buffer(4,TimeUnit.SECONDS,3)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Integer>>() {
                    @Override
                    public void onNext(List<Integer> value) {
                        if (value.size() > 0) {
                            Logger.d(String.format("%d taps", value.size()));
                        } else {
                            Logger.d("--------- No taps received ");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("onError");
                    }

                    @Override
                    public void onComplete() {
                        Logger.d("onComplete");
                    }
                });





    }





    public void buffer(){

        Observable.just(1,2,3,4,5,6,7,8,9).buffer(2,4).subscribe(new Observer<List<Integer>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Integer> value) {
                Logger.d(value.toString());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Logger.d("onComplete()");
            }
        });

    }
}
