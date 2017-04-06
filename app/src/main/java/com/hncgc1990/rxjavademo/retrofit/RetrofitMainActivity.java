package com.hncgc1990.rxjavademo.retrofit;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.hncgc1990.rxjavademo.R;
import com.hncgc1990.rxjavademo.RetrofitSingle;
import com.hncgc1990.rxjavademo.data.PostData;
import com.hncgc1990.rxjavademo.data.Result;
import com.hncgc1990.rxjavademo.request.PostListInter;
import com.hncgc1990.rxjavademo.util.Logger;
import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxFragmentActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/4/5.
 */
public class RetrofitMainActivity extends RxFragmentActivity implements ItemFragment.OnListFragmentInteractionListener {


    @BindView(R.id.btn_request)
    Button btnRequest;

    ItemFragment fragment;



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


    Disposable disposable;

    private void getListData() {

        Retrofit instance = RetrofitSingle.getInstance();
        PostListInter postListInter = instance.create(PostListInter.class);
        postListInter
                .getPostList()
                .compose(this.<PostData<List<Result>>>bindUntilEvent(ActivityEvent.DESTROY))
                .compose(SchedulerHelper.<PostData<List<Result>>>applySchedulers())
                .compose(ProtocolHelper.applyProtocolHandler())
                .compose(new DialogHelper(RetrofitMainActivity.this).<PostData<List<Result>>>applyDialog())
                .subscribe(new Observer<PostData<List<Result>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable=d;
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
                        Logger.d("onComplete");
                    }
                });


    }

    @Override
    public void onListFragmentInteraction(Result item) {

    }


    @Override
    protected void onStart() {
        super.onStart();
        Logger.d("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.d("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.d("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.d("onStop");
    }

    @Override
    protected void onDestroy() {
        if(!disposable.isDisposed()){
            disposable.dispose();
            Logger.d("disposable is called");
        }
        super.onDestroy();
        Logger.d("onDestroy");

    }
}
