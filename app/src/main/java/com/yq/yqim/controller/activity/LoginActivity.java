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
import com.google.gson.reflect.TypeToken;
import com.yq.yqim.R;
import com.yq.yqim.controller.fragment.ContactFragment;
import com.yq.yqim.model.Model;
import com.yq.yqim.model.bean.Gbooks;
import com.yq.yqim.model.bean.testbean;
import com.yq.yqim.utils.IPtools;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText login_edit_account;
    private EditText login_edit_pwd;
    private Button login_btn_register;
    private Button login_btn_login;
    private TextView login_text_chengepwd;
    public static String cuser = null;

    public final static String user = null;
    String ip = IPtools.getIp();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //初始化控件
        initView();
        //初始化监听
        initListener();
        login_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Login();
//                Intent  intent=new Intent(LoginActivity.this,FindFriendActivity.class);
//                startActivity(intent);


            }
        });

        login_text_chengepwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(LoginActivity.this, ChengePwdActivity.class);
                startActivity(intent);


            }
        });
        login_btn_register = (Button) findViewById(R.id.login_btn_register);

        login_btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);

                startActivity(intent);
            }
        });
    }


    private void Login() {
        final String loginName = login_edit_account.getText().toString();
        final String loginPwd = login_edit_pwd.getText().toString();

        if (TextUtils.isEmpty(loginName) || TextUtils.isEmpty(loginPwd)) {
            Toast.makeText(LoginActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
        } else {

            Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                @Override
                public void run() {

                    OkHttpClient client = new OkHttpClient();
                    final testbean user = new testbean();
                    user.setUsername(login_edit_account.getText().toString().trim());
                    user.setPassword(login_edit_pwd.getText().toString().trim());
                    final Gson gson = new Gson();
                    //使用Gson将对象转换为json字符串
                    String json = gson.toJson(user);
                    RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
                    String Url = ip + "login";
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
                                    Toast.makeText(LoginActivity.this, "登陆失败！", Toast.LENGTH_SHORT).show();
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
                                        cuser = Res.getUsername();
                                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                        //跳转主界面
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "用户名或密码错误！", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });


                        }
                    });
                }

            });
        }
    }

    private void initView() {
        login_edit_account = (EditText) findViewById(R.id.login_edit_account);
        login_edit_pwd = (EditText) findViewById(R.id.login_edit_pwd);
        login_btn_login = (Button) findViewById(R.id.login_btn_login);
        login_btn_register = (Button) findViewById(R.id.login_btn_register);
        login_text_chengepwd = (TextView) findViewById(R.id.login_text_change_pwd);
    }

    private void initListener() {//注册
//        login_btn_register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                regist();
//            }
//        });
//        //登录
//        login_btn_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                login();
//            }
//        });
    }
//
//    private void login() {
//
//
//            Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
//                @Override
//                public void run() {
//
//
//
//    }
//
//    private void regist() {
//        final String registName = login_edit_account.getText().toString();
//        final String registPwd = login_edit_pwd.getText().toString();
//        if (TextUtils.isEmpty(registName) || TextUtils.isEmpty(registPwd)) {
//            Toast.makeText(LoginActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
//
//        }
//        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
//            @Override
//            public void run() {
//
////    注册
//                try {
//                    EMClient.getInstance().createAccount(registName, registPwd);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                } catch (final HyphenateException e1) {
//                    e1.printStackTrace();
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(LoginActivity.this, "注册失败" + e1.toString(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//                //更新页面显示
//
//            }
//        });
//    }
}










