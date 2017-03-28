package com.hncgc1990.rxjavademo.operator.combineOperator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.hncgc1990.rxjavademo.R;
import com.hncgc1990.rxjavademo.util.Logger;
import com.jakewharton.rxbinding2.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;

public class CombineLatestActivity extends AppCompatActivity {

    @BindView(R.id.et_user)
    EditText etUser;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combine_latest);
        ButterKnife.bind(this);


        initEvent();
    }

    private void initEvent() {
        Observable<CharSequence> userObservable = RxTextView.textChanges(etUser).skip(1);
        Observable<CharSequence> passObservable = RxTextView.textChanges(etPass).skip(1);


        Observable.combineLatest(userObservable, passObservable, new BiFunction<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(CharSequence charSequence, CharSequence charSequence2) throws Exception {

                boolean userEmpty=true;
                if(TextUtils.isEmpty(charSequence)){
                    userEmpty=false;
                    etUser.setError("用户名不能为空");
                }

                boolean passEmpty=true;
                if(TextUtils.isEmpty(charSequence2)){
                    passEmpty=false;
                    etPass.setError("密码不能为空");
                }

                Logger.d(charSequence+"________"+charSequence2);

                return userEmpty&&passEmpty;
            }
        }).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean value) {
                Logger.d("结果是"+value);
            }

            @Override
            public void onError(Throwable e) {
                Logger.e(e.toString());
            }

            @Override
            public void onComplete() {

            }
        });




    }
}
