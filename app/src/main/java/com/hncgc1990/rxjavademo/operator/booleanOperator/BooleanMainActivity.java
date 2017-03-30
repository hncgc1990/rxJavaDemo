package com.hncgc1990.rxjavademo.operator.booleanOperator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.hncgc1990.rxjavademo.R;
import com.hncgc1990.rxjavademo.util.Logger;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

public class BooleanMainActivity extends AppCompatActivity {

    @BindView(R.id.btn_all)
    Button btnAll;
    @BindView(R.id.btn_contains)
    Button btnContains;
    @BindView(R.id.btn_isempty)
    Button btnIsempty;
    @BindView(R.id.btn_sequenceequal)
    Button btnSequenceequal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boolean_main);
        ButterKnife.bind(this);


        initEvent();

    }

    private void initEvent() {


        RxView.clicks(btnAll).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doAll();
            }
        });

        RxView.clicks(btnContains).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doContains();
            }
        });

        RxView.clicks(btnIsempty).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doIsEmpty();
            }
        });

        RxView.clicks(btnSequenceequal).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doSequenceEqual();
            }
        });


    }




    private void doAll() {

        Observable.just(1, 3, 3, -4).all(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {
                if (integer > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                Logger.d("最后的结果是" + aBoolean);
            }
        });
    }

    private void doContains() {

        Observable.just(1, 2, 3).contains(4).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                Logger.d("发送的数据是否包括了4" + aBoolean);
            }
        });

    }

    private void doIsEmpty() {
        Observable.empty().isEmpty().subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                Logger.d("是否没有发射任何数据____" + aBoolean);
            }
        });

        Observable.just(1).isEmpty().subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                Logger.d("是否没有发射任何数据____" + aBoolean);
            }
        });

    }
    private void doSequenceEqual() {

        Observable<Integer> just = Observable.just(1, 3333);
        Observable<Integer> just2 = Observable.just(1);
        Observable.sequenceEqual(just, just2).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                Logger.d("accept____" + aBoolean);
            }
        });

    }
}
