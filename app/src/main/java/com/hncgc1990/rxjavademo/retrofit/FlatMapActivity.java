package com.hncgc1990.rxjavademo.retrofit;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class FlatMapActivity extends AppCompatActivity {


    @BindView(R.id.btn_two)
    Button btnTwo;
    @BindView(R.id.btn_three)
    Button btnThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat_map);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        RxView.clicks(btnTwo).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doflatmap();
            }
        });

        RxView.clicks(btnThree).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                doflatmapTwo();
            }
        });


    }

    private void doflatmapTwo() {






    }

    private void doflatmap() {

//        Observable.just(1,2,3,4,5,8).flatMap(new Function<Integer, ObservableSource<String>>() {
//            @Override
//            public ObservableSource<String> apply(Integer integer) throws Exception {
//                return Observable.fromArray(integer+"");
//            }
//        }).subscribe(new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(String value) {
//                Logger.d(value);
//            }
//
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//                Logger.d("onComplete");
//            }
//        });


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
                        Logger.d("获取到的列表大小是" + value.size());
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


}
