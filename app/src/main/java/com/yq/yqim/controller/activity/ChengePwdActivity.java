package com.yq.yqim.controller.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yq.yqim.R;
import com.yq.yqim.model.Model;
import com.yq.yqim.model.bean.testbean;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.yq.yqim.R.layout.activity_chenge_pwd;
import static com.yq.yqim.controller.activity.LoginActivity.cuser;
import static com.yq.yqim.utils.IPtools.ip;

public class ChengePwdActivity extends AppCompatActivity {
private TextView user;
private EditText pwd;
private EditText pwd1;
private Button  chengepwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_chenge_pwd);
        initView();
        initListener();
    }

    private void initListener() {
chengepwd.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        user.setText(cuser);
        chenge();
    }
});

    }

    private  void chenge(){

        final String userName=cuser;
        final String password = pwd.getText().toString();
        final String password1 = pwd1.getText().toString();

        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)|| TextUtils.isEmpty(password1)) {
            Toast.makeText(ChengePwdActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
        } else {
            if (password.equals(password1)) {



            Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                @Override
                public void run() {

                    OkHttpClient client = new OkHttpClient();
                    final testbean user = new testbean();
                    user.setUsername(userName);
                    user.setPassword(password);
                    final Gson gson = new Gson();
                    //使用Gson将对象转换为json字符串
                    String json = gson.toJson(user);
                    RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
                    String Url = ip+"WebService/Loging";
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
                                    Toast.makeText(ChengePwdActivity.this, "修改失败！", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            final String res = response.body().string();
                            final testbean Res = gson.fromJson(res, testbean.class);//注意，将Json对象转化为User对象必须在子线程中进行，不能放在主线程中，
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //返回信息不为空，则表示登录验证成功
                                    if (Res != null) {

                                        Toast.makeText(ChengePwdActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                        //跳转主界面
                                        Intent intent = new Intent(ChengePwdActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(ChengePwdActivity.this, "用户名或密码错误！", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });


                        }
                    });
                }

            });}
            else {
                Toast.makeText(ChengePwdActivity.this, "密码不一致", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void initView() {
        user=(TextView) findViewById(R.id.edit_account);
        pwd=(EditText)findViewById(R.id.edit_pwd);
        pwd1=(EditText)findViewById(R.id.edit_pwd1);
        chengepwd=(Button)findViewById(R.id.btn_chengepwd);
    }
}
