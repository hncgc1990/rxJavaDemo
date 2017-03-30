package com.hncgc1990.rxjavademo.operator.connectOperator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.hncgc1990.rxjavademo.R;
import com.hncgc1990.rxjavademo.util.Logger;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observables.ConnectableObservable;

public class ConnectMainActivity extends AppCompatActivity {

    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.btn_cancel)
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_main);
        ButterKnife.bind(this);

        initEvent();

    }

    Disposable disposable;

    private void initEvent() {
        final ConnectableObservable<Long> publish = Observable.interval(1, TimeUnit.SECONDS).replay(2);
        publish.subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long integer) throws Exception {
                Logger.d("取值" + integer);

                if(integer==3){
                    publish.subscribe(newConsumer());
                }

                if(integer==8){
                    publish.subscribe(newConsumerTwo());
                }


            }
        });

        RxView.clicks(btnStart).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                disposable = publish.connect();
            }
        });


        RxView.clicks(btnCancel).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                if(disposable!=null && !disposable.isDisposed()){
                    disposable.dispose();
                    Logger.d("取消了订阅");
                }
            }
        });


    }



    private Consumer newConsumer(){

       return  new Consumer<Long>() {
            @Override
            public void accept(Long integer) throws Exception {
                Logger.d("取值_____" + integer);
            }
        };
    }

    private Consumer newConsumerTwo(){

        return  new Consumer<Long>() {
            @Override
            public void accept(Long integer) throws Exception {
                Logger.d("取值*******" + integer);
            }
        };
    }
}
