package com.hncgc1990.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.hncgc1990.rxjavademo.retrofit.FlatMapActivity;
import com.hncgc1990.rxjavademo.util.ActivityUtil;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_buffer)
    Button btnBuffer;
    @BindView(R.id.btn_flatmap)
    Button btnFlatmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initEvent();
    }

    private void initEvent() {

        RxView.clicks(btnBuffer).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                ActivityUtil.startActivity(MainActivity.this,BufferActivity.class);
            }
        });



        RxView.clicks(btnFlatmap).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                ActivityUtil.startActivity(MainActivity.this,FlatMapActivity.class);
            }
        });
    }
}
