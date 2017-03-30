package com.hncgc1990.rxjavademo.operator.mathOperator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.hncgc1990.rxjavademo.R;
import com.hncgc1990.rxjavademo.util.Logger;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

public class MathMainActivity extends AppCompatActivity {

    @BindView(R.id.btn_average)
    Button btnAverage;
    @BindView(R.id.btn_concatwith)
    Button btnConcatwith;
    @BindView(R.id.btn_concat)
    Button btnConcat;
    @BindView(R.id.btn_reduce)
    Button btnReduce;
    @BindView(R.id.btn_collect)
    Button btnCollect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_main);
        ButterKnife.bind(this);

        initEvent();
    }

    private void initEvent() {


        RxView.clicks(btnAverage).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doCount();
            }
        });

        RxView.clicks(btnConcat).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doConcat();
            }
        });

        RxView.clicks(btnConcatwith).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doConcatWith();
            }
        });

        RxView.clicks(btnReduce).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doReduce();
            }
        });

        RxView.clicks(btnCollect).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doCollect();
            }
        });
    }




    private void doCount() {

        Observable.just(23333, 33433, 3, 3, 3, 4, 4, 4, 2, 3).count().subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                Logger.d("发射了____" + aLong + "项数据");
            }
        });
    }

    private void doConcat() {

        Observable<Integer> just = Observable.just(1, 2, 3);
        Observable<Integer> just1 = Observable.just(6, 7, 8);
        Observable.concat(just, just1).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.d("发射的数据____" + integer);
            }
        });
    }

    private void doConcatWith() {

        Observable.just(1, 2, 3, 4).concatWith(Observable.fromArray(5, 6, 7, 8)).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.d("发射了这些数据" + integer);
            }
        });


    }


    private void doReduce() {

        Observable.just(1, 2, 3, 4).reduce(new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) throws Exception {

                Logger.d("reduce__" + integer + "___" + integer2);

                return integer + integer2;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.d("reduce__记过" + integer);
            }
        });

        Observable.just(1, 2, 3, 4).scan(new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) throws Exception {

                Logger.d("scan___" + integer + "___" + integer2);

                return integer + integer2;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.d("scan___记过" + integer);
            }
        });


    }


    private void doCollect() {

        Observable.just(1,2,3).collect(new Callable<ArrayList<Integer>>() {
            @Override
            public ArrayList<Integer> call() throws Exception {
                Logger.d("Callable");
                return new ArrayList<Integer>();
            }
        }, new BiConsumer<ArrayList<Integer>, Integer>() {
            @Override
            public void accept(ArrayList<Integer> s, Integer integer) throws Exception {
                Logger.d(s+"____"+integer);
                s.add(integer);

            }
        }).subscribe(new Consumer<ArrayList<Integer>>() {
            @Override
            public void accept(ArrayList<Integer> s) throws Exception {
                Logger.d("最后的结果:"+s.toString());
            }
        });


    }

}
