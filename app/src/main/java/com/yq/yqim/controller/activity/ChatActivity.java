package com.yq.yqim.controller.activity;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yq.yqim.R;
import com.yq.yqim.utils.IPtools;

public class ChatActivity extends FragmentActivity {
   private String mHxid;
    String ip= IPtools.getIp();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initData();
    }

    private void initData() {
        //创建Fragment

    }
}
