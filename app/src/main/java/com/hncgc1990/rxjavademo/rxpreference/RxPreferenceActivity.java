package com.hncgc1990.rxjavademo.rxpreference;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;

import com.f2prateek.rx.preferences2.Preference;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.hncgc1990.rxjavademo.R;
import com.hncgc1990.rxjavademo.util.Logger;
import com.jakewharton.rxbinding2.widget.RxCompoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class RxPreferenceActivity extends AppCompatActivity {

    @BindView(R.id.checkbox1)
    AppCompatCheckBox checkbox1;
    @BindView(R.id.checkbox2)
    AppCompatCheckBox checkbox2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_preference);
        ButterKnife.bind(this);

        SharedPreferences defaultSharedPreferences = getDefaultSharedPreferences(this);

        RxSharedPreferences rxSharedPreferences = RxSharedPreferences.create(defaultSharedPreferences);


        final Preference<Boolean> xixi = rxSharedPreferences.getBoolean("xixi",true);
        xixi.asObservable().subscribe(new Observer<Boolean>() {//监听sp的值的变动
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean aBoolean) {


                Logger.d("结果是"+aBoolean);
                    checkbox1.setChecked(aBoolean);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        RxCompoundButton.checkedChanges(checkbox2).subscribe(xixi.asConsumer());//

        Boolean aBoolean = xixi.get();//获取sp的值


    }
}
