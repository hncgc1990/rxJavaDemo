package com.hncgc1990.rxjavademo.operator.assistantOperator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.hncgc1990.rxjavademo.R;
import com.hncgc1990.rxjavademo.util.Logger;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Timed;

public class AssistantMainActivity extends AppCompatActivity {

    @BindView(R.id.btn_delay)
    Button btnDelay;
    @BindView(R.id.btn_delaySubscription)
    Button btnDelaySubscription;
    @BindView(R.id.btn_dooneach)
    Button btnDooneach;
    @BindView(R.id.btn_doonnext)
    Button btnDoonnext;
    @BindView(R.id.btn_doonsubscribe)
    Button btnDoonsubscribe;
    @BindView(R.id.btn_doonerror)
    Button btnDoonerror;
    @BindView(R.id.btn_dooncomplete)
    Button btnDooncomplete;
    @BindView(R.id.btn_doonterminate)
    Button btnDoonterminate;
    @BindView(R.id.btn_materialize)
    Button btnMaterialize;
    @BindView(R.id.btn_dematerialize)
    Button btnDematerialize;
    @BindView(R.id.btn_timeinterval)
    Button btnTimeinterval;
    @BindView(R.id.btn_timestamp)
    Button btnTimestamp;
    @BindView(R.id.btn_using)
    Button btnUsing;
    @BindView(R.id.btn_tolist)
    Button btnTolist;
    @BindView(R.id.btn_tomap)
    Button btnTomap;
    @BindView(R.id.btn_tosortedlist)
    Button btnTosortedlist;
    @BindView(R.id.btn_tomultimap)
    Button btnTomultimap;
    @BindView(R.id.btn_nest)
    Button btnNest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistant_main);
        ButterKnife.bind(this);

        initEvent();
    }

    private void initEvent() {

        RxView.clicks(btnDelay).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doDelay();
            }
        });


        RxView.clicks(btnDelaySubscription).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doDelaySucription();
            }
        });

        RxView.clicks(btnDooneach).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doonEach();
            }
        });

        RxView.clicks(btnDoonnext).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doonNext();
            }
        });

        RxView.clicks(btnDoonsubscribe).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doonsubscribe();
            }
        });
        RxView.clicks(btnDoonerror).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doonError();
            }
        });

        RxView.clicks(btnDooncomplete).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                dooncomplete();
            }
        });


        RxView.clicks(btnDematerialize).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                dodeMaterialize();
            }
        });

        RxView.clicks(btnTimeinterval).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doTimeinterval();
            }
        });

        RxView.clicks(btnTimestamp).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doTimestamp();
            }
        });

        RxView.clicks(btnUsing).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doUsing();
            }
        });

        RxView.clicks(btnTolist).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                dotoList();
            }
        });

        RxView.clicks(btnTosortedlist).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                dotoSortedList();
            }
        });


        RxView.clicks(btnTomap).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                dotomap();
            }
        });


        RxView.clicks(btnTomultimap).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                dotomultimap();
            }
        });

        RxView.clicks(btnNest).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                donest();
            }
        });

    }




    private void doDelay() {


        Observable.just(1).delay(4, TimeUnit.SECONDS).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Logger.d("onSubscribe");
            }

            @Override
            public void onNext(Integer value) {
                Logger.d("onNext");
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


        Observable.just(1)
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        if (integer == 1) throw new RuntimeException("ddd");
                        return 1;
                    }
                }).delay(4, TimeUnit.SECONDS)
                .subscribe(createIntegerObserver());

    }

    private void doDelaySucription() {

        Observable.just(1)
                .delaySubscription(4, TimeUnit.SECONDS)
                .subscribe(createIntegerObserver());


    }


    private void doonEach() {


        Observable.just(1, 2, 3, 4).map(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) throws Exception {
                if (integer == 4) throw new RuntimeException("ddd");
                return integer;
            }
        }).doOnEach(new Consumer<Notification<Integer>>() {
            @Override
            public void accept(Notification<Integer> integerNotification) throws Exception {
                Integer value = integerNotification.getValue();

                Logger.d("获取的值" + value + "______" + integerNotification.toString());
            }
        }).subscribe(createIntegerObserver());

    }


    private Observer createIntegerObserver() {
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Logger.d("onSubscribe");
            }

            @Override
            public void onNext(Integer value) {
                Logger.d("onNext");
            }

            @Override
            public void onError(Throwable e) {
                Logger.d("onError");
            }

            @Override
            public void onComplete() {
                Logger.d("onComplete");
            }
        };
    }

    private void doonNext() {

        Observable.just(1, 2, 3, 4).map(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) throws Exception {
                if (integer == 4) throw new RuntimeException("ddd");
                return integer;
            }
        }).doOnNext(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.d("doonnext____" + integer);
            }
        }).subscribe(createIntegerObserver());
    }

    private void doonsubscribe() {//在订阅之后回调
        Observable.just(1, 2, 3, 4).map(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) throws Exception {
                if (integer == 4) throw new RuntimeException("ddd");
                return integer;
            }
        }).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                Logger.d("doOnSubscribe");
            }
        }).subscribe(createIntegerObserver());
    }


    private void doonError() {

        Observable.just(1, 2, 3, 4).map(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) throws Exception {
                if (integer == 4) throw new RuntimeException("ddd");
                return integer;
            }
        }).doOnError(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Logger.e("doOnError__" + throwable);
            }
        }).subscribe(createIntegerObserver());
    }

    private void dooncomplete() {
        Observable.just(1, 2, 3, 4).map(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) throws Exception {
//                if (integer == 4) throw new RuntimeException("ddd");
                return integer;
            }
        }).doOnComplete(new Action() {
            @Override
            public void run() throws Exception {
                Logger.d("doOnComplete");
            }
        }).subscribe(createIntegerObserver());


    }

    private void doonterminate() {

        Observable.just(1, 2, 3, 4).map(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) throws Exception {
//                if (integer == 4) throw new RuntimeException("ddd");
                return integer;
            }
        }).doOnTerminate(new Action() {
            @Override
            public void run() throws Exception {
                Logger.d("doOnTerminate");
            }
        }).subscribe(createIntegerObserver());

        Observable.just(1, 2, 3, 4).map(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) throws Exception {
                if (integer == 4) throw new RuntimeException("ddd");
                return integer;
            }
        }).doOnTerminate(new Action() {
            @Override
            public void run() throws Exception {
                Logger.d("doOnTerminate");
            }
        }).subscribe(createIntegerObserver());

    }


    private void doMaterialize() {

        Observable.just(1, 2, 3).materialize().subscribe(new Consumer<Notification<Integer>>() {
            @Override
            public void accept(Notification<Integer> integerNotification) throws Exception {
                Logger.d(integerNotification.toString());
            }
        });


    }

    private void dodeMaterialize() {
        Observable.just(1, 2, 3).materialize().dematerialize().subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                Logger.d("value_______" + o);
            }
        });

    }


    private void doTimeinterval() {

        Observable.interval(1, TimeUnit.SECONDS).timeInterval().subscribe(new Consumer<Timed<Long>>() {
            @Override
            public void accept(Timed<Long> longTimed) throws Exception {
                Logger.d(longTimed.toString());
            }
        });

    }

    private void doTimestamp() {
        Observable.just(1, 2, 3).timestamp().subscribe(new Consumer<Timed<Integer>>() {
            @Override
            public void accept(Timed<Integer> integerTimed) throws Exception {
                Logger.d(integerTimed.toString());
            }
        });


    }


    private void doUsing() {
        Observable.using(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Logger.d("Callable call");

                return 1;
            }
        }, new Function<Object, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Object o) throws Exception {

                Logger.d("Function apply");
                return Observable.just(o);
            }
        }, new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                Logger.d("Consumer accept");
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                Logger.d("onComplete");
            }
        });


    }


    private void dotoList() {


        Observable.just(1, 2, 3, 4).toList().subscribe(new Consumer<List<Integer>>() {
            @Override
            public void accept(List<Integer> integers) throws Exception {
                Logger.d(integers.toString());
            }
        });
    }

    private void dotoSortedList() {

        Observable.just(1, 5, 2, 3, 7, 4).toSortedList().subscribe(new Consumer<List<Integer>>() {
            @Override
            public void accept(List<Integer> integers) throws Exception {
                Logger.d(integers.toString());
            }
        });


    }

    private void dotomap() {

        Observable.just(1, 2, 3, 4).toMap(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                if (integer < 3) {
                    return "key2";
                } else {
                    return "key1";
                }

            }
        }).subscribe(new Consumer<Map<String, Integer>>() {
            @Override
            public void accept(Map<String, Integer> integerIntegerMap) throws Exception {
                Logger.d(integerIntegerMap.toString());
            }
        });


    }

    private void dotomultimap() {
        Observable.just(1, 2, 3, 4).toMultimap(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                if (integer < 3) {
                    return "key2";
                } else {
                    return "key1";
                }
            }
        }).subscribe(new Consumer<Map<String, Collection<Integer>>>() {
            @Override
            public void accept(Map<String, Collection<Integer>> stringCollectionMap) throws Exception {
                Logger.d(stringCollectionMap.toString());
            }
        });


    }

    private void donest() {
    }
}
