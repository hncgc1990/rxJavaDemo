package com.hncgc1990.rxjavademo.operator.blockOperator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.hncgc1990.rxjavademo.R;
import com.hncgc1990.rxjavademo.util.Logger;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class BlockMainActivity extends AppCompatActivity {

    @BindView(R.id.btn_block)
    Button btnBlock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_main);
        ButterKnife.bind(this);

        initEvent();


    }

    private void initEvent() {


        RxView.clicks(btnBlock).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {

                Observable.just(1,2,3).blockingForEach(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Logger.d(integer+"");
                    }
                });





            }
        });


    }
}
