package com.hncgc1990.rxjavademo.operator.filterOperator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hncgc1990.rxjavademo.R;
import com.hncgc1990.rxjavademo.util.Logger;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

public class FilterMainActivity extends AppCompatActivity {

    @BindView(R.id.btn_debounce)
    Button btn_debounce;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.btn_throttleWithTimeout)
    Button btnThrottleWithTimeout;
    @BindView(R.id.btn_distinct)
    Button btnDistinct;
    @BindView(R.id.btn_distinctUtilChange)
    Button btnDistinctUtilChange;
    @BindView(R.id.btn_elementAt)
    Button btnElementAt;
    @BindView(R.id.btn_elementAtOrDefault)
    Button btnElementAtOrDefault;
    @BindView(R.id.btn_filter)
    Button btnFilter;
    @BindView(R.id.btn_first)
    Button btnFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_main);
        ButterKnife.bind(this);
        initEvent();
    }

    private void initEvent() {

        RxTextView.textChanges(etSearch)
                .debounce(800, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence charSequence) throws Exception {

                        Logger.d("current thread" + Thread.currentThread().getName());
                        Toast.makeText(FilterMainActivity.this, "进行一次联想", Toast.LENGTH_SHORT).show();
                    }
                });


        RxView.clicks(btn_debounce).map(new Function<Object, Integer>() {
            @Override
            public Integer apply(Object o) throws Exception {

                Logger.d("click once");

                return 1;
            }
        }).debounce(2, TimeUnit.SECONDS).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer value) {
                Logger.d("过滤最后一次");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Logger.d("onComplete");
            }
        });


        RxView.clicks(btnThrottleWithTimeout)
                .map(new Function<Object, Object>() {
                    @Override
                    public Object apply(Object o) throws Exception {

                        Logger.d("点击了一次");


                        return null;
                    }
                })
                .throttleWithTimeout(1, TimeUnit.SECONDS)
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object value) {
                        Logger.d("过滤最后一次");
                    }

                    @Override
                    public void onError(Throwable 哦) {
                        Logger.e(哦.toString());
                    }

                    @Override
                    public void onComplete() {
                        Logger.d("onComplete");
                    }
                });


        RxView.clicks(btnDistinct).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doDistinct();
            }
        });
        RxView.clicks(btnDistinctUtilChange).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doDistinctUtilChange();
            }
        });

        RxView.clicks(btnElementAt).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doElementAt();
            }
        });

        RxView.clicks(btnElementAtOrDefault).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doElementAtOrDefault();
            }
        });

        RxView.clicks(btnFilter).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doFilter();
            }
        });


        RxView.clicks(btnFirst).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doFirst();
            }
        });

    }




    private void doDistinct() {

        Observable.fromArray(1, 3, 4, 1, 2, 3, 4, 4, 4, 23, 5).distinct().subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.d("过滤的结果" + integer);
            }
        });
    }

    private void doDistinctUtilChange() {

        Observable.fromArray(1, 3, 4, 1, 2, 3, 4, 4, 4, 23, 5).distinctUntilChanged().subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.d("过滤的结果" + integer);
            }
        });
    }

    private void doElementAt() {

        Observable.fromArray(1, 2, 3, 4, 5, 6, 7, 8).elementAt(14).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.d("选中的结果" + integer);
            }
        });
    }


    private void doElementAtOrDefault() {

        Observable.fromArray(1, 2, 3).elementAt(9, 1).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.d("默认的值" + integer);
            }
        });


        Observable.fromArray(1, 2, 3).elementAtOrError(9).subscribe(new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Integer value) {
                Logger.d("结果是" + value);
            }

            @Override
            public void onError(Throwable e) {
                Logger.e("异常:" + e.getMessage());
            }
        });


    }


    private void doFilter() {

        Observable.fromArray(1, 2, 3, 4, 5).filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {

                if (integer > 3) {
                    return true;
                }


                return false;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.d("过滤后的结果：" + integer);
            }
        });


        Observable.fromArray(1, 2, "3", 4, 5, "——————6").ofType(String.class).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Logger.d("过滤后的结果————————" + s);
            }
        });


    }


    private void doFirst() {

        Observable.empty().first(2).subscribe(new SingleObserver<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Object value) {
                Logger.d("默认的值"+value);
            }

            @Override
            public void onError(Throwable e) {

            }
        });


        Observable.fromArray(2,2,3,4,1,1).filter(new Predicate<Integer>() {//此处并不是发送第一项数据，而是发送所有的通过的数据
            @Override
            public boolean test(Integer integer) throws Exception {
                if(integer<3){
                    return true;
                }

                return false;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                    Logger.d("first后的值"+integer);
            }
        });

    }

}
