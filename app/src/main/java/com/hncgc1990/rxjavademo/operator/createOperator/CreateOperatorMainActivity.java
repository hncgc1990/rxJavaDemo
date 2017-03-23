package com.hncgc1990.rxjavademo.operator.createOperator;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.hncgc1990.rxjavademo.R;
import com.hncgc1990.rxjavademo.util.Logger;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class CreateOperatorMainActivity extends AppCompatActivity {

    @BindView(R.id.btn_create)
    Button btnCreate;
    @BindView(R.id.btn_just)
    Button btnJust;
    @BindView(R.id.btn_from)
    Button btnFrom;
    @BindView(R.id.btn_defer)
    Button btnDefer;
    @BindView(R.id.btn_repeat)
    Button btnRepeat;
    @BindView(R.id.btn_repeatwhen)
    Button btnRepeatwhen;
    @BindView(R.id.btn_range)
    Button btnRange;
    @BindView(R.id.btn_interval)
    Button btnInterval;
    @BindView(R.id.btn_timer)
    Button btntimer;
    @BindView(R.id.btn_error)
    Button btnError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_operator_main);
        ButterKnife.bind(this);
        initEvent();
    }

    private void initEvent() {

        RxView.clicks(btnCreate).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doCreate();
            }
        });

        RxView.clicks(btnJust).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doJust();
            }
        });
        RxView.clicks(btnFrom).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doFrom();
            }
        });

        RxView.clicks(btnDefer).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doDefer();
            }
        });

        RxView.clicks(btnRepeat).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doRepeat();
            }
        });
        RxView.clicks(btnRepeatwhen).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doRepeatWhen();
            }
        });

        RxView.clicks(btnRange).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doRepeatRange();
            }
        });

        RxView.clicks(btnInterval).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doInterval();
            }
        });

        RxView.clicks(btntimer).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                dotimer();
            }
        });
        RxView.clicks(btnError).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doError();
            }
        });
    }




    private void doCreate() {

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {


                for (int i = 0; i < 4; i++) {
                    e.onNext(i);
                }
                e.onComplete();

            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Logger.d("onSubscribe");
//                d.dispose();TODO 这里的Disposable对象如何去利用来取消事件的发射
            }

            @Override
            public void onNext(Integer value) {
                Logger.d("onNext" + value);
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


    private void doJust() {

        //2.0版本的just方法不允许发射null事件,使用empty代替 onSubscribe->onComplete
        Observable.empty().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                Logger.d("onSubscribe");
            }

            @Override
            public void onNext(Object value) {
                Logger.d("onNext" + value);
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

        //并不会调用这里的accept
        Observable.empty().subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                Logger.d("accept");
            }
        });

    }

    private void doFrom() {
        Observable.fromArray(1, 2, 4, 5, 5, 6, 6).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.d("accept________" + integer);
            }
        });


        Future<String> future = new Future<String>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public String get() throws InterruptedException, ExecutionException {
                return "get()";
            }

            @Override
            public String get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return "get(long timeout, TimeUnit unit)";
            }
        };


        Observable.fromFuture(future).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Logger.d("onSubscribe");
            }

            @Override
            public void onNext(String value) {
                Logger.d("onNext_____" + value);
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

        Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Logger.d("current Thread is " + Thread.currentThread().getName());
                return "call method invoked";
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Logger.d(s);
            }
        });


    }


    private void doDefer() {
        Observable.defer(new Callable<ObservableSource<String>>() {
            @Override
            public ObservableSource<String> call() throws Exception {
                return Observable.empty();
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Logger.d("accept____" + s);
            }
        });


    }

    private void doRepeat() {
        Observable.just(1).repeat(10).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.d("accept____" + integer);
            }
        });


    }


    private void doRepeatWhen() {
        //TODO repeatWhen触发repeat的条件,很难理解
        Observable.just(1).repeatWhen(new Function<Observable<Object>, ObservableSource<Object>>() {
            @Override
            public ObservableSource<Object> apply(Observable<Object> objectObservable) throws Exception {
                return objectObservable.delay(5, TimeUnit.SECONDS);
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.d("accept____" + integer);
            }
        });


    }

    private void doRepeatRange() {
        Observable.range(0, 100).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.d("accept____" + integer);
            }
        });


    }


    private void doInterval() {

        Observable.interval(2, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                Logger.d(aLong + "_____" + SystemClock.uptimeMillis());
            }
        });


    }


    private void dotimer() {
        Observable.timer(2, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                Logger.d(aLong + "_____" + SystemClock.uptimeMillis());
            }
        });


    }

    private void doError() {

        Observable.just(1,2,3,4).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {

                if(integer==3){

                    return Observable.error(new Throwable("Can nont be 3"));
               }else{

                    return Observable.just(integer+"");
                }
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String value) {
                Logger.d("onNext "+value);
            }

            @Override
            public void onError(Throwable e) {
                Logger.d("onError "+e.getMessage());
            }

            @Override
            public void onComplete() {
                Logger.d("onComplete ");
            }
        });



    }

}
