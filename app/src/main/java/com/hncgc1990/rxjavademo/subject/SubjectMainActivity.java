package com.hncgc1990.rxjavademo.subject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.hncgc1990.rxjavademo.R;
import com.hncgc1990.rxjavademo.util.Logger;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.UnicastSubject;

public class SubjectMainActivity extends AppCompatActivity {

    @BindView(R.id.btn_asyncsubject)
    Button btnAsyncsubject;
    @BindView(R.id.btn_behaviorsubject)
    Button btnBehaviorsubject;
    @BindView(R.id.btn_publishsubject)
    Button btnPublishsubject;
    @BindView(R.id.btn_replaysubject)
    Button btnReplaysubject;
    @BindView(R.id.btn_UnicastSubject)
    Button btnUnicastSubject;
    @BindView(R.id.btn_SerializedSubject)
    Button btnSerializedSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_main);
        ButterKnife.bind(this);

        initEvent();

    }

    private void initEvent() {


        RxView.clicks(btnAsyncsubject).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doAsyncSubject();
            }
        });

        RxView.clicks(btnBehaviorsubject).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doBehaviorSubject();
            }
        });
        RxView.clicks(btnPublishsubject).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doPublishSubject();
            }
        });
        RxView.clicks(btnReplaysubject).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doReplaysubject();
            }
        });
        RxView.clicks(btnUnicastSubject).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doUnicastSubject();
            }
        });

        RxView.clicks(btnSerializedSubject).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doSerializedSubject();
            }
        });
    }



    private void doAsyncSubject() {
        AsyncSubject<Integer> asyncSubject = AsyncSubject.create(); //在执行onComplete方法之后,只输出了最后一个值.
        asyncSubject.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.d("值:" + integer);
            }
        });
        asyncSubject.onNext(1);
        asyncSubject.onNext(2);
        asyncSubject.onNext(3);
        asyncSubject.onNext(4);

        asyncSubject.onComplete();


    }

    private void doBehaviorSubject() {
        BehaviorSubject<Integer> behaviorSubject = BehaviorSubject.create();//会收到里订阅前一个事件之后的所有事件.在onComplete之后订阅,会收不到任何onNext，仅仅收到onComplete或者onError
        //在这里订阅,所有的事件都可以收到
        behaviorSubject.onNext(1);
        //在这里订阅，同样可以收到所有的事件
        behaviorSubject.onNext(2);

        //在这里订阅，收不到1

        behaviorSubject.onNext(3);
        behaviorSubject.onNext(4);
        behaviorSubject.onNext(5);
        behaviorSubject.onNext(6);


        behaviorSubject.onComplete();
        behaviorSubject.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Logger.d("值:" + integer);
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


    private void doPublishSubject() {


        PublishSubject<Integer> publishSubject = PublishSubject.create();//在订阅之后发送的事件才会接受到
        publishSubject.onNext(1);
        publishSubject.onNext(2);
        publishSubject.onNext(3);
        publishSubject.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.d("publish1:" + integer);
            }
        });
        publishSubject.onNext(4);
        publishSubject.onNext(5);


        publishSubject.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Logger.d("publish2:" + integer);
            }

            @Override
            public void onError(Throwable e) {
                Logger.d("publish2:onError");
            }

            @Override
            public void onComplete() {
                Logger.d("publish2:onComplete");
            }
        });
        publishSubject.onComplete();

    }

    private void doReplaysubject() {//


        ReplaySubject<Integer> replaySubject = ReplaySubject.create();
        replaySubject.onNext(1);
        replaySubject.onNext(21);
        replaySubject.onNext(13);

        replaySubject.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.d("replay" + integer);
            }
        });

        replaySubject.onNext(12);
        replaySubject.onNext(51);
        replaySubject.onNext(16);
        replaySubject.onNext(18);
        replaySubject.onNext(18);
        replaySubject.onComplete();
        replaySubject.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.d("replay2___" + integer);
            }
        });
    }


    private void doUnicastSubject() {

        UnicastSubject<Integer> objectUnicastSubject = UnicastSubject.create();//只允许一个订阅者，订阅行为类似ReplaySubject

        objectUnicastSubject.onNext(1);
        objectUnicastSubject.onNext(2);
        objectUnicastSubject.onNext(3);

        objectUnicastSubject.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.d("unicast" + integer);
            }
        });

        objectUnicastSubject.onNext(4);
        objectUnicastSubject.onNext(5);
        objectUnicastSubject.onNext(6);


        objectUnicastSubject.onComplete();


        objectUnicastSubject.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Logger.d("onNext" + integer);
            }

            @Override
            public void onError(Throwable e) {
                Logger.e(e.toString());
            }

            @Override
            public void onComplete() {
                Logger.d("oncomplete");
            }
        });


    }

    int pos=0;

    private void doSerializedSubject() {
            final PublishSubject<Integer> publishSubject=PublishSubject.create();
        publishSubject.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
            Logger.d("==onNext==>value:" + pos +" ,threadId: "+ Thread.currentThread().getId());
                pos++;
            }
        });





        for (int i = 0; i < 15; i++) {
            final int finalI = i;
            new Thread() {
                public void run() {
                    publishSubject.onNext(finalI);
                }
            }.start();
        }

    }


}
