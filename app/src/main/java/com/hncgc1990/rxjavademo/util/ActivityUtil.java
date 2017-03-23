package com.hncgc1990.rxjavademo.util;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by Administrator on 2017/3/22.
 */
public class ActivityUtil {

    public static void startActivity(Activity activity,Class calss){
        Intent intent=new Intent(activity,calss);
        activity.startActivity(intent);

    }
}
