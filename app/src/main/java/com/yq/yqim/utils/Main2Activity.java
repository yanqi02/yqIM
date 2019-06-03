package com.yq.yqim.utils;

import android.app.Application;
import android.content.Context;


import com.yq.yqim.model.Model;

public class Main2Activity extends Application {
private static  Context mcontext;
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化


        Model.getInstance().init(this);
//初始化全局上下文
        mcontext=this;
    }
    //获取全局上下文对像
    public  static Context getGlobalApplication(){
        return  mcontext;
    }
}
