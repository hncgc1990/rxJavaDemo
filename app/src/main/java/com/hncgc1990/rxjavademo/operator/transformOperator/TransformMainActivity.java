package com.hncgc1990.rxjavademo.operator.transformOperator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.hncgc1990.rxjavademo.R;
import com.hncgc1990.rxjavademo.RetrofitSingle;
import com.hncgc1990.rxjavademo.data.Category;
import com.hncgc1990.rxjavademo.data.CategoryDetail;
import com.hncgc1990.rxjavademo.data.Content;
import com.hncgc1990.rxjavademo.request.CategoryInter;
import com.hncgc1990.rxjavademo.util.Logger;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.schedulers.Schedulers;

public class TransformMainActivity extends AppCompatActivity {

    @BindView(R.id.btn_map)
    Button btnMap;
    @BindView(R.id.btn_flatmap)
    Button btnFlatmap;
    @BindView(R.id.btn_concatmap)
    Button btnConcatmap;
    @BindView(R.id.btn_buffer)
    Button btnBuffer;
    @BindView(R.id.btn_groupby)
    Button btnGroupby;
    @BindView(R.id.btn_scan)
    Button btnScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transform_main);
        ButterKnife.bind(this);
        initEvent();
    }

    private void initEvent() {

        RxView.clicks(btnMap).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doMap();
            }
        });

        RxView.clicks(btnFlatmap).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doFlatMap();
            }
        });
        RxView.clicks(btnConcatmap).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doConcatmap();
            }
        });
//        RxView.clicks(btnBuffer).map(new Function<Object, Integer>() {
//            @Override
//            public Integer apply(Object o) throws Exception {
//                return 1;
//            }
//        }).buffer(2).subscribe(new Consumer<List<Integer>>() {
//            @Override
//            public void accept(List<Integer> integers) throws Exception {
//                Logger.d(integers.toString());
//            }
//        });

        RxView.clicks(btnBuffer).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doBuffer();
            }
        });

        RxView.clicks(btnGroupby).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doGroupBy();
            }
        });

        RxView.clicks(btnScan).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doScan();
            }
        });


    }



    private void doMap() {

        CategoryInter categoryInter = RetrofitSingle.getInstance().create(CategoryInter.class);
        categoryInter.getCategories()
                .map(new Function<Category, List<Content>>() {
                    @Override
                    public List<Content> apply(Category category) throws Exception {

                        if (category.getStatus() == 0) {
                            return category.getContents();
                        } else {
                            throw new RuntimeException(category.getMessage());

                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Content>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Content> value) {
                        Logger.d(value.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("onError" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Logger.d("onComplete");
                    }
                });


    }

    private void doFlatMap() {


        final CategoryInter categoryInter = RetrofitSingle.getInstance().create(CategoryInter.class);
        categoryInter.getCategories()
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<Category, ObservableSource<Content>>() {
                    @Override
                    public ObservableSource<Content> apply(Category category) throws Exception {
                        return Observable.fromIterable(category.getContents());
                    }
                })
                .flatMap(new Function<Content, ObservableSource<CategoryDetail>>() {
                    @Override
                    public ObservableSource<CategoryDetail> apply(Content content) throws Exception {
                        return categoryInter.getCategoryDetail(content.getId()).map(new Function<CategoryDetail, CategoryDetail>() {
                            @Override
                            public CategoryDetail apply(CategoryDetail categoryDetail) throws Exception {
                                if (categoryDetail.getStatus() == 0) {
                                    return categoryDetail;
                                } else {
                                    throw new RuntimeException(categoryDetail.getMessage());

                                }
                            }
                        });
                    }
                })
                .buffer(Integer.MAX_VALUE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<CategoryDetail>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<CategoryDetail> value) {
                        Logger.d("获取到的列表大小是" + value.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Logger.d("onComplete");
                    }
                });

    }

    private void doConcatmap() {


        final CategoryInter categoryInter = RetrofitSingle.getInstance().create(CategoryInter.class);
        categoryInter.getCategories()
                .subscribeOn(Schedulers.io())
                .concatMap(new Function<Category, ObservableSource<Content>>() {
                    @Override
                    public ObservableSource<Content> apply(Category category) throws Exception {
                        return Observable.fromIterable(category.getContents());
                    }
                })
                .concatMap(new Function<Content, ObservableSource<CategoryDetail>>() {
                    @Override
                    public ObservableSource<CategoryDetail> apply(Content content) throws Exception {
                        return categoryInter.getCategoryDetail(content.getId());
                    }
                })
                .buffer(Integer.MAX_VALUE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<CategoryDetail>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<CategoryDetail> value) {
                        Logger.d("获取到的列表大小是" + value.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Logger.d("onComplete");
                    }
                });

    }


    private void doBuffer() {

//        Observable.interval(2, TimeUnit.SECONDS).buffer(2,4,TimeUnit.SECONDS).subscribe(new Consumer<List<Long>>() {
//            @Override
//            public void accept(List<Long> longs) throws Exception {
//                Logger.d(longs.toString());
//            }
//        });

//        Observable.interval(2,TimeUnit.SECONDS).buffer(new ObservableSource<Object>() {
//            @Override
//            public void subscribe(Observer<? super Object> observer) {
//
//            }
//        }).subscribe(new Consumer<List<Long>>() {
//            @Override
//            public void accept(List<Long> longs) throws Exception {
//
//            }
//        });

        Observable.interval(2, TimeUnit.SECONDS).buffer(new Callable<ObservableSource<Object>>() {
            @Override
            public ObservableSource<Object> call() throws Exception {
                return null;
            }
        }).subscribe(new Consumer<List<Long>>() {
            @Override
            public void accept(List<Long> longs) throws Exception {
                Logger.d(longs.toString());
            }
        });

    }

    private void doGroupBy() {

        Observable.fromArray(1, 2, 12, 34, 23, 32, 31, 12, 412, 4, 12, 176, 12, 12, 12, 4).groupBy(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return (integer + "").substring(0, 1);
            }
        }).subscribe(new Consumer<GroupedObservable<String, Integer>>() {
            @Override
            public void accept(final GroupedObservable<String, Integer> objectIntegerGroupedObservable) throws Exception {
                Logger.d(objectIntegerGroupedObservable.getKey());
                objectIntegerGroupedObservable.toList().subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) throws Exception {
                        Logger.d("分组" + objectIntegerGroupedObservable.getKey() + "______" + integers.toString());
                    }
                });
            }
        });

        Observable.fromArray(1, 2, 12, 34, 23, 32, 31, 12, 412, 4, 12, 176, 12, 12, 12, 4).groupBy(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return (integer + "").substring(0, 1);
            }
        }, new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return integer + "________________";
            }
        }).subscribe(new Consumer<GroupedObservable<String, String>>() {
            @Override
            public void accept(final GroupedObservable<String, String> objectIntegerGroupedObservable) throws Exception {
                Logger.d(objectIntegerGroupedObservable.getKey());
                objectIntegerGroupedObservable.toList().subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> integers) throws Exception {
                        Logger.d("分组" + objectIntegerGroupedObservable.getKey() + "______" + integers.toString());
                    }
                });
            }
        });

    }

    private void doScan() {

        Observable.just(1,2,3,4,5).scan(new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer sum, Integer current) throws Exception {

                Logger.d(sum+"______"+current);

                return sum*current;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.d(integer+"______结果");
            }
        });


        Observable.just(1,2,3,4,5).scan(8,new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer sum, Integer current) throws Exception {

                Logger.d(sum+"______"+current);

                return sum+current;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.d(integer+"______结果");
            }
        });


    }

}
