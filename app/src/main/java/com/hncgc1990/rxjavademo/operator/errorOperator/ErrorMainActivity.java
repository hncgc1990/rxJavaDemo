package com.hncgc1990.rxjavademo.operator.errorOperator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.hncgc1990.rxjavademo.R;
import com.hncgc1990.rxjavademo.util.Logger;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class ErrorMainActivity extends AppCompatActivity {

    @BindView(R.id.btn_errordeal)
    Button btnErrordeal;
    @BindView(R.id.btn_errorresume)
    Button btnErrorresume;
    @BindView(R.id.btn_exception)
    Button btnException;
    @BindView(R.id.btn_retry)
    Button btnRetry;
    @BindView(R.id.btn_retrywhen)
    Button btnRetrywhen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_main);
        ButterKnife.bind(this);
        initEvent();
    }

    private void initEvent() {

        RxView.clicks(btnErrordeal).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doOnErrorResumeError();
            }
        });

        RxView.clicks(btnErrorresume).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doOnErrorReturn();
            }
        });

        RxView.clicks(btnException).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doOnExceptionResumeNext();
            }
        });

        RxView.clicks(btnRetry).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doOnRetry();
            }
        });
        RxView.clicks(btnRetrywhen).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doonRetryWhen();
            }
        });


    }




    private void doOnErrorResumeError() {
        Observable.just(1)
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        if (integer == 1) throw new RuntimeException("I am error");
                        return 0;
                    }
                })
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends Integer>>() {
                    @Override
                    public ObservableSource<? extends Integer> apply(Throwable throwable) throws Exception {
                        return Observable.just(2, 3);
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.d("发生异常了" + integer);
            }
        });


        Observable.just(1)
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        if (integer == 1) throw new RuntimeException("I am error");
                        return 0;
                    }
                })
                .onErrorResumeNext(new ObservableSource<Integer>() {
                    @Override
                    public void subscribe(Observer<? super Integer> observer) {
                        observer.onNext(4);
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.d("发生异常了____" + integer);
            }
        });

    }

    private void doOnErrorReturn() {

        Observable.just(1, 2)
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        if (integer == 1) throw new RuntimeException("I am error");
                        return 0;
                    }
                }).onErrorReturn(new Function<Throwable, Integer>() {
            @Override
            public Integer apply(Throwable throwable) throws Exception {
                return 3;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.d("天真" + integer);
            }
        });


    }

    private void doOnExceptionResumeNext() {


        Observable.just(1, 2)
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        if (integer == 1) throw new RuntimeException("I am error");
                        return 0;
                    }
                }).onExceptionResumeNext(new Observable<Integer>() {
            @Override
            protected void subscribeActual(Observer<? super Integer> observer) {
                observer.onNext(5);
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.d("难道真的有人会这么瞎" + integer);
            }
        });


    }

    int i = 0;

    private void doOnRetry() {


        Observable.just(1, 3).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                Logger.d("map 了一次");

                if (i == 0) {
                    i++;
                    throw new RuntimeException("I am error");
                }
                return "naive";
            }
        }).retry().subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Logger.d("我是不相信的" + s);
            }
        });


        Observable.just(1, 3).retry().map(new Function<Integer, Integer>() {//要进行重试的操作在retry操作符之前
            @Override
            public Integer apply(Integer integer) throws Exception {

                if (integer == 1) {
                    throw new RuntimeException("I am error");
                }

                return integer;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.d("最坏后果猜想:" + integer);
            }
        });


    }

    private void doonRetryWhen() {

        Observable.just(1, 3).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                Logger.d("map 了一次");

                if (integer ==1) {
                    i++;
                    throw new RuntimeException("I am error");
                }
                return "naive";
            }
        }).retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {



                return throwableObservable;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Logger.d("我是不相信的" + s);
            }
        });

    }


}
