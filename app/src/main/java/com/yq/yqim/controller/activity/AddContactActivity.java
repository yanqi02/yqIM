package com.yq.yqim.controller.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import com.yq.yqim.R;
import com.yq.yqim.model.Model;
import com.yq.yqim.model.bean.InvationInfo;
import com.yq.yqim.model.bean.UserInfo;
import com.yq.yqim.model.bean.testbean;
import com.yq.yqim.utils.IPtools;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//添加联系人
public class AddContactActivity extends AppCompatActivity {
    private TextView tv_add_find;
    private EditText et_add_name;
    private RelativeLayout rl_add;
    private TextView tv_add_name;
    private Button bt_add_add;
    private UserInfo userInfo;
String ip= IPtools.getIp();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        initView();
        initListener();
    }

    private void initListener() {
        //查找按钮点击事件
        tv_add_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                find();
            }
        });

        //添加按钮点击事件
        bt_add_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFriend();
            }
        });

    }

    private void find() {
        final String name = et_add_name.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(AddContactActivity.this, "输入用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        // 去服务器判断用户名是否存在
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                final String name = et_add_name.getText().toString();
                OkHttpClient client = new OkHttpClient();
                final testbean user = new testbean();
                user.setUsername(name);

                final Gson gson = new Gson();
                //使用Gson将对象转换为json字符串
                String json = gson.toJson(user);
                RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
                String Url = ip+"FindUser";
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
                                Toast.makeText(AddContactActivity.this, "无该用户", Toast.LENGTH_SHORT).show();
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
                                    final String newFriend=Res.getUsername();

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            rl_add.setVisibility(View.VISIBLE);
                                            tv_add_name.setText(newFriend);
                                        }
                                    });
                                    Toast.makeText(AddContactActivity.this, newFriend, Toast.LENGTH_SHORT).show();


                                } else {
                                    Toast.makeText(AddContactActivity.this, "无该用户！", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });


                    }
                });
            }

        });





    }




    private void AddFriend() {
        final String name = et_add_name.getText().toString();
        bt_add_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                @Override
                public void run() {

                    OkHttpClient client = new OkHttpClient();
                    final InvationInfo invationInfo = new InvationInfo();
                    invationInfo.setFriend(name);
                    invationInfo.setUser(LoginActivity.cuser);
                    invationInfo.setReason("添加好友");
                    final Gson gson = new Gson();
                    //使用Gson将对象转换为json字符串
                    String json = gson.toJson(invationInfo);
                    RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
                    String Url = ip+"addFriend";
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
                                    Toast.makeText(AddContactActivity.this, "发送失败！", Toast.LENGTH_SHORT).show();
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

                                        Toast.makeText(AddContactActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(AddContactActivity.this, "清检查网络连接！", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });


                        }
                    });
                }




//                    OkHttpClient client = new OkHttpClient();
//                    final String friend = name;
//                    final Gson gson = new Gson();
//                    //使用Gson将对象转换为json字符串
//                    String json = gson.toJson(name);
//                    RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
//                    String Url = ip+"WebService/addFriend";
//                    Request request = new Request.Builder().url(Url)//请求的url
//                            .post(requestBody).build();
//
//                    Call call = client.newCall(request);
//                    call.enqueue(new Callback() {
//                        @Override
//                        public void onFailure(Call call, final IOException e) {
//                            //进行更新UI操作
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(AddContactActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
//
//                        @Override
//                        public void onResponse(Call call, Response response) throws IOException {
//
//                            final String res = response.body().string();
//
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    //返回信息不为空，则表示登录验证成功
//                                    if (res != null) {
//                                        Toast.makeText(AddContactActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
//
//                                    } else {
//                                        Toast.makeText(AddContactActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
//
//                                    }
//                                }
//                            });
//
//
//                        }
//                    });
//                }

            });
        }

        });
    }



    private void initView(){
        tv_add_find = (TextView) findViewById(R.id.tv_add_find);
        et_add_name = (EditText) findViewById(R.id.et_add_name);
        rl_add = (RelativeLayout) findViewById(R.id.rl_add);
        tv_add_name = (TextView) findViewById(R.id.tv_add_name);
        bt_add_add = (Button) findViewById(R.id.bt_add_add);

    }
}
