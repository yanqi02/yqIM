package com.yq.yqim.controller.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.yq.yqim.R;
import com.yq.yqim.model.Model;
import com.yq.yqim.model.bean.UserInfo;

import static com.yq.yqim.controller.activity.LoginActivity.cuser;

public class WelcomeActivity extends AppCompatActivity {

    private Handler handler =new Handler(){
        public void handleMessage(Message msg) {
//如果退出，不处理
            if (isFinishing()) {
                return;
            }
            toMainOrLogin();
        } };
    private void toMainOrLogin(){
;
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                if(cuser==null) {


                    Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
else {
    //登陆成功后的方法
//                        Model.getInstance().loginSuccess(user);

                        Intent intent=new Intent(WelcomeActivity.this, MainActivity.class);
                        startActivity(intent);

                    }
//
                finish();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //延时2s
        handler.sendMessageDelayed(Message.obtain(),2000);
    }
    //销毁

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
