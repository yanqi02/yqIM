package com.yq.yqim.controller.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yq.yqim.R;
import com.yq.yqim.model.Model;
import com.yq.yqim.model.bean.InvationInfo;
import com.yq.yqim.model.bean.testbean;
import com.yq.yqim.utils.IPtools;
import com.yq.yqim.utils.Register;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
private Button register;
private EditText username;
private  EditText password;
public static String uu;
    String ip= IPtools.getIp();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//初始化控件
        initView();

register.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Register();
    }
});

    }

    private  void  initView()
    {
        register=(Button)findViewById(R.id.register_bt_register);
        username=(EditText)findViewById(R.id.register_et_username);
        password=(EditText)findViewById(R.id.register_et_pwd);

    }


    private void Register() {
        final String userName = username.getText().toString();
        final String userPwd = password.getText().toString();
uu=userName;
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userPwd)) {
            Toast.makeText(RegisterActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
        } else {

            Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                @Override
                public void run() {

                    OkHttpClient client = new OkHttpClient();
                    final testbean user = new testbean();
                    user.setUsername(userName);
                    user.setPassword(userPwd);
                    final Gson gson = new Gson();
                    //使用Gson将对象转换为json字符串
                    String json = gson.toJson(user);
                    RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
                    String Url = ip+"Register" ;
                    Request request = new Request.Builder().url(Url)//请求的url
                            .post(requestBody).build();

                    Call call = client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, final IOException e) {
                            //进行更新UI操作
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this, "注册失败！", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            final String res = response.body().string();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //返回信息不为空，则表示登录验证成功
                                    if (res != null) {

dialog();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });


                        }
                    });
                }

            });
        }
    }

    public  void  dialog(){

        AlertDialog dialog=  new AlertDialog.Builder(this)
                .setTitle("完善信息")
                .setMessage("是否完善信息")
                .setIcon(R.drawable.heada)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //跳转更多注册信息
                        Intent intent = new Intent(RegisterActivity.this, MoreMegActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();

                    }
                })
                .create();
        dialog.show();

    }


}



