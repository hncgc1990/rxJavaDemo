package com.hncgc1990.rxjavademo.retrofit;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.Toast;

import com.hncgc1990.rxjavademo.R;
import com.hncgc1990.rxjavademo.RetrofitSingle;
import com.hncgc1990.rxjavademo.data.PostData;
import com.hncgc1990.rxjavademo.data.Result;
import com.hncgc1990.rxjavademo.request.PostListInter;
import com.hncgc1990.rxjavademo.util.Logger;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/4/5.
 */
public class RetrofitMainActivity extends FragmentActivity implements ItemFragment.OnListFragmentInteractionListener {


    @BindView(R.id.btn_request)
    Button btnRequest;

    ItemFragment fragment;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_retrofit);
        ButterKnife.bind(this);
        initEvent();

    }

    private void initEvent() {

        RxView.clicks(btnRequest).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                getListData();
            }
        });

        fragment = (ItemFragment) getSupportFragmentManager().findFragmentById(R.id.framgment);

    }

    private void getListData() {

        Retrofit instance = RetrofitSingle.getInstance();
        PostListInter postListInter = instance.create(PostListInter.class);
        postListInter
                .getPostList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<PostData<List<Result>>, PostData<List<Result>>>() {
                    @Override
                    public PostData apply(PostData postData) throws Exception {

                        if(!postData.getError()){
                            return postData;
                        }else{
                          throw new RuntimeException("wrong response");
                        }
                    }
                })
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        //在这里进行弹窗
                        dialog=new ProgressDialog(RetrofitMainActivity.this);
                        dialog.show();
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        //在这里进行取消弹窗
                        if(dialog!=null &&dialog.isShowing()){
                            dialog.dismiss();
                        }
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //在这里进行取消弹窗
                        if(dialog!=null &&dialog.isShowing()){
                            dialog.dismiss();
                        }
                    }
                })
                .subscribe(new Observer<PostData<List<Result>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PostData<List<Result>> value) {

                        Logger.d("列表的大小"+value.getResults().size());
                    fragment.setAdapter(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(RetrofitMainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    @Override
    public void onListFragmentInteraction(Result item) {

    }
}
