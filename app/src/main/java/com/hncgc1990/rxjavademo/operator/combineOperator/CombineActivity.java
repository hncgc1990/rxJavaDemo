package com.hncgc1990.rxjavademo.operator.combineOperator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.hncgc1990.rxjavademo.R;
import com.hncgc1990.rxjavademo.util.ActivityUtil;
import com.hncgc1990.rxjavademo.util.Logger;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;

public class CombineActivity extends AppCompatActivity {

    @BindView(R.id.btn_startwith)
    Button btnStartwith;
    @BindView(R.id.btn_merge)
    Button btnMerge;
    @BindView(R.id.btn_zip)
    Button btnZip;
    @BindView(R.id.btn_combineLatest)
    Button btnCombineLatest;
    @BindView(R.id.btn_switch)
    Button btnSwitch;
    @BindView(R.id.btn_combineLatest_demo)
    Button btnCombineLatestDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combine);
        ButterKnife.bind(this);
        initEvent();
    }

    private void initEvent() {

        RxView.clicks(btnStartwith).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doStartWith();
            }
        });

        RxView.clicks(btnMerge).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doMerge();
            }
        });
        RxView.clicks(btnZip).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                dozip();
            }
        });


        RxView.clicks(btnCombineLatest).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doCombineLatest();
            }
        });

        RxView.clicks(btnCombineLatestDemo).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                ActivityUtil.startActivity(CombineActivity.this,CombineLatestActivity.class);
            }
        });




        RxView.clicks(btnSwitch).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doSwitch();
            }
        });

    }


    private void doStartWith() {
        int[] items = new int[]{3, 4, 5, 6, 7, 8, 98, 0};
        Observable.just(1, 2, 3, 4, 5, 6).startWith(90).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.d("添加了数据" + integer);
            }
        });
    }

    private void doMerge() {

        Observable<Integer> just = Observable.just(1, 2, 3, 4, 5, 6);

        Observable<Integer> just1 = Observable.just(12, 24, 35, 43, 57, 63).flatMap(new Function<Integer, ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> apply(Integer integer) throws Exception {
                return Observable.error(new RuntimeException("dddddd"));
            }
        });

        Observable<Integer> just2 = Observable.just(12, 24, 35, 43, 57, 63).flatMap(new Function<Integer, ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> apply(Integer integer) throws Exception {
                return Observable.error(new RuntimeException("ssssssssss"));
            }
        });


        Observable.merge(just1, just2, just).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer value) {
                Logger.d("next___" + value);
            }

            @Override
            public void onError(Throwable e) {
                Logger.d("onError___" + e.toString());
            }

            @Override
            public void onComplete() {
                Logger.d("onComplete___");
            }
        });

        Observable.mergeDelayError(just1, just).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer value) {
                Logger.d("next___" + value);
            }

            @Override
            public void onError(Throwable e) {
                Logger.d("onError___" + e.toString());
            }

            @Override
            public void onComplete() {
                Logger.d("onComplete___");
            }
        });


    }

    private void dozip() {

        Observable<Integer> just = Observable.just(1, 2, 3, 4, 5, 6);
        Observable<Integer> just2 = Observable.just(11, 12, 13, 14, 15, 16, 17);
        Observable<Integer> just3 = Observable.just(111, 112, 113, 114, 115, 116, 117, 118);

        Observable.zip(just, just2, new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) throws Exception {

                Logger.d(integer + "__________" + integer2);

                return integer + integer2;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.d("结果是_______" + integer);

            }
        });


        Observable.zip(just, just2, just3, new Function3<Integer, Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2, Integer integer3) throws Exception {

                Logger.d(integer + "__________" + integer2 + "________" + integer3);

                return integer + integer2 + integer3;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.d("结果是_______" + integer);
            }
        });

    }


    private void doCombineLatest() {
        Observable<Integer> just = Observable.just(1, 2, 3, 4, 5, 6);
        Observable<Integer> just2 = Observable.just(11, 12, 13, 14, 15, 16, 17);
        Observable<Integer> just3 = Observable.just(111, 112, 113, 114, 115, 116, 117, 118);


        Observable.combineLatest(just, just2, just3, new Function3<Integer, Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2, Integer integer3) throws Exception {
                Logger.d(integer + "__________" + integer2 + "________" + integer3);
                return integer + integer2 + integer3;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.d("结果是_______" + integer);
            }
        });


    }


    private void doSwitch() {

        Observable<Integer> just = Observable.just(1, 2, 3, 4, 5, 6);
        Observable<Integer> just2 = Observable.just(11, 12, 13, 14, 15, 16, 17);

        Observable.switchOnNext(new Observable<ObservableSource<?>>() {
            @Override
            protected void subscribeActual(Observer<? super ObservableSource<?>> observer) {
                for (int i = 0; i < 10; i++) {
                    observer.onNext(craete(i));
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                observer.onComplete();
            }
        }).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object value) {
                Logger.d("onNext____"+value);
            }

            @Override
            public void onError(Throwable e) {
                Logger.e("onError___"+e.toString());
            }

            @Override
            public void onComplete() {
                Logger.d("onComplete");
            }
        });

    }


    private static Observable<String> craete(final int index) {


        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> subscriber) throws Exception {
                for (int i = 0; i < 5; i++) {
                    subscriber.onNext("index: " + index + ", i: " + i);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        subscriber.onComplete();
                    }
                }
                subscriber.onComplete();
            }
        }).subscribeOn(Schedulers.newThread());

    }
}
