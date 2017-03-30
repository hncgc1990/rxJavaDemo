package com.hncgc1990.rxjavademo.operator.conditionOperator;

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
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

public class ConditionMainActivity extends AppCompatActivity {

    @BindView(R.id.btn_amb)
    Button btnAmb;
    @BindView(R.id.btn_defaultifempty)
    Button btnDefaultifempty;
    @BindView(R.id.btn_skipwhile)
    Button btnSkipwhile;
    @BindView(R.id.btn_skipuntil)
    Button btnSkipuntil;
    @BindView(R.id.btn_takeuntil)
    Button btnTakeuntil;
    @BindView(R.id.btn_takewhile)
    Button btnTakewhile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition_main);
        ButterKnife.bind(this);

        initEvent();
    }

    private void initEvent() {

        RxView.clicks(btnAmb).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doAmb();
            }
        });
        RxView.clicks(btnDefaultifempty).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doDefaultifEmpty();
            }
        });
        RxView.clicks(btnSkipwhile).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doSkipWhile();
            }
        });

        RxView.clicks(btnSkipuntil).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doSkipUntil();
            }
        });

        RxView.clicks(btnTakeuntil).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doTakeUntil();
            }
        });
        RxView.clicks(btnTakewhile).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doTakeWhile();
            }
        });

    }




    private void doAmb() {

        Observable<Integer> integerObservable = Observable.just(1, 3).delay(1, TimeUnit.SECONDS);


        Observable<Integer> integerObservable1 = Observable.just(4, 4);


        Observable.ambArray(integerObservable, integerObservable1).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.d("#######" + integer);
            }
        });


    }


    private void doDefaultifEmpty() {

        Observable.empty().defaultIfEmpty(2).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                Logger.d("______" + o);
            }
        });


    }


    private void doSkipWhile() {

        Observable.just(1, 23, 3).skipWhile(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {

                if (integer == 3) {
                    return true;
                }

                return false;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {

            }
        });


    }

    private void doSkipUntil() {

        Observable.interval(1, TimeUnit.SECONDS).skipUntil(Observable.just(1).delay(3, TimeUnit.SECONDS)).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long integer) throws Exception {
                Logger.d("value____" + integer);
            }
        });

    }


    private void doTakeUntil() {

        Observable.interval(1, TimeUnit.SECONDS).takeUntil(Observable.just(4, 5).delay(3, TimeUnit.SECONDS)).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long integer) throws Exception {
                Logger.d("_____" + integer);
            }
        });

    }


    private void doTakeWhile() {

        Observable.just(1,2,3,4,5,6).takeWhile(new Predicate<Integer>() {
            @Override
            public boolean test(Integer aLong) throws Exception {

                if(aLong>4){
                    return false;
                }

                return true;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.d("accpet"+integer);
            }
        });


    }
}
