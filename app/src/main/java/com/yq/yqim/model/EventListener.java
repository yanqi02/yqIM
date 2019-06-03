package com.yq.yqim.model;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.yq.yqim.model.bean.InvationInfo;
import com.yq.yqim.model.bean.UserInfo;
import com.yq.yqim.utils.Constant;
import com.yq.yqim.utils.SpUtils;

//全局事件监听
public class EventListener {
    private Context mcontext;
    private final LocalBroadcastManager mLBM;

    public EventListener(Context context) {
        mcontext = context;
        //创建发送广播的管理者对象
        mLBM = LocalBroadcastManager.getInstance(mcontext);
        //注册一个联系人变化的监听

    }


}
