package com.hncgc1990.rxjavademo.retrofit;

import android.app.ProgressDialog;
import android.content.Context;

import com.hncgc1990.rxjavademo.util.Logger;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * UI界面的处理
 * Created by Administrator on 2017/4/6.
 */
public class DialogHelper {

   private  ProgressDialog mDialog;

   private  Context mContext;

    public DialogHelper(Context context){
        this.mContext=context;
    }


    <T> ObservableTransformer<T,T> applyDialog(){
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {

               return  upstream.doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        //在这里进行弹窗
                        Logger.d("doOnSubscribe");
                        mDialog=new ProgressDialog(mContext);
                        mDialog.show();
                    }
                }).doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        //在这里进行取消弹窗
                        Logger.d("doOnComplete");
                        if(mDialog!=null &&mDialog.isShowing()){
                            mDialog.dismiss();
                        }
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //在这里进行取消弹窗
                        Logger.d("doOnError");
                        if(mDialog!=null &&mDialog.isShowing()){
                            mDialog.dismiss();
                        }
                    }
                });

            }
        };


    }



}
