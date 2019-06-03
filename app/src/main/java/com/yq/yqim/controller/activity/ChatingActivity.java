//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yq.yqim.controller.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.gson.Gson;
import com.yq.yqim.R;
import com.yq.yqim.controller.adapter.FriendAdapter;
import com.yq.yqim.controller.adapter.MessageAdapter;
import com.yq.yqim.model.Model;
import com.yq.yqim.model.bean.MsgEntity;
import com.yq.yqim.utils.IPtools;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Request.Builder;

public class ChatingActivity<RecycleView> extends AppCompatActivity {
    private static String mess;
    private Button btn_send;
    private EditText edt_msg;
    private TextView friendname;
    private final Handler handler = new Handler();
    private List<MsgEntity> list = new ArrayList();
    private RecyclerView recycleView;
    private boolean run = false;
    private final Runnable task = new Runnable() {
        public void run() {
            if (ChatingActivity.this.run) {
                ChatingActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        ChatingActivity.this.initMsg();
                    }
                });
                ChatingActivity.this.handler.postDelayed(this, 2000L);
            }

        }
    };

    public ChatingActivity() {
    }

    private String getMsgFromserver() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    OkHttpClient var1 = new OkHttpClient();
                    MsgEntity var2 = new MsgEntity(LoginActivity.cuser, FriendAdapter.friendName1);
                    String var5 = (new Gson()).toJson(var2);
                    RequestBody var6 = FormBody.create(MediaType.parse("application/json; charset=utf-8"), var5);
                    StringBuilder var3 = new StringBuilder();
                    var3.append(IPtools.getIp());
                    var3.append("chatTran");
                    String var7 = var3.toString();
                    var1.newCall((new Builder()).url(var7).post(var6).build()).enqueue(new Callback() {
                        public void onFailure(Call var1, IOException var2) {
                            System.out.println("获取失败");
                        }

                        public void onResponse(Call var1, Response var2) throws IOException {
                            MsgEntity var3 = (MsgEntity)(new Gson()).fromJson(var2.body().string(), MsgEntity.class);
                            if (var3 != null) {
                                ChatingActivity.mess = var3.getContent();
                            }

                        }
                    });
                } catch (Exception var4) {
                    var4.printStackTrace();
                }
            }
        });
        return mess;
    }

    private void initMsg() {
        this.friendname.setText(FriendAdapter.friendName1);
        mess = this.getMsgFromserver();
        PrintStream var1 = System.out;
        StringBuilder var2 = new StringBuilder();
        var2.append("消息：");
        var2.append(mess);
        var1.println(var2.toString());
        if (mess != null) {
            System.out.println(mess);
            MsgEntity var3 = new MsgEntity(FriendAdapter.friendName1, LoginActivity.cuser, mess, 2);
            this.list.add(var3);
            this.recycleView.scrollToPosition(this.list.size() - 1);
            this.edt_msg.setText("");
        }

    }

    private void initView() {
        this.recycleView = (RecyclerView)this.findViewById(R.id.list);
        this.edt_msg = (EditText)this.findViewById(R.id.msg);
        this.btn_send = (Button)this.findViewById(R.id.send);
        this.friendname = (TextView)this.findViewById(R.id.friend);
    }

    private void sendMsg() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    String var2 = ChatingActivity.this.edt_msg.getText().toString();
                    if (!TextUtils.isEmpty(var2)) {
                        OkHttpClient var1 = new OkHttpClient();
                        MsgEntity var5 = new MsgEntity(LoginActivity.cuser, FriendAdapter.friendName1, var2, 1);
                        var2 = (new Gson()).toJson(var5);
                        RequestBody var6 = FormBody.create(MediaType.parse("application/json; charset=utf-8"), var2);
                        StringBuilder var3 = new StringBuilder();
                        var3.append(IPtools.getIp());
                        var3.append("Chatmsg");
                        String var7 = var3.toString();
                        var1.newCall((new Builder()).url(var7).post(var6).build()).enqueue(new Callback() {
                            public void onFailure(Call var1, IOException var2) {
                                System.out.println("获取失败");
                            }

                            public void onResponse(Call var1, Response var2) throws IOException {
                                new Gson();
                                var2.body().string();
                            }
                        });
                    }

                } catch (Exception var4) {
                    var4.printStackTrace();
                }
            }
        });
    }

    private String trygetMsgFromserver() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    OkHttpClient var1 = new OkHttpClient();
                    RequestBody var2 = FormBody.create(MediaType.parse("application/json; charset=utf-8"), ".");
                    StringBuilder var3 = new StringBuilder();
                    var3.append(IPtools.getIp());
                    var3.append("chatTran");
                    String var5 = var3.toString();
                    var1.newCall((new Builder()).url(var5).post(var2).build()).enqueue(new Callback() {
                        public void onFailure(Call var1, IOException var2) {
                            System.out.println("获取失败");
                        }

                        public void onResponse(Call var1, Response var2) throws IOException {
                            var2.body().string();
                        }
                    });
                } catch (Exception var4) {
                    var4.printStackTrace();
                }
            }
        });
        return mess;
    }

    protected void onCreate(Bundle var1) {
        super.onCreate(var1);
        this.setContentView(R.layout.activity_chating);
        this.initView();
        this.initMsg();
        this.run = true;
        this.handler.postDelayed(this.task, 1000L);
        final MessageAdapter var3 = new MessageAdapter(this, this.list);
        LinearLayoutManager var2 = new LinearLayoutManager(this);
        this.recycleView.setLayoutManager(var2);
        this.recycleView.setAdapter(var3);
        this.btn_send.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {
                ChatingActivity.this.sendMsg();
                String var2 = ChatingActivity.this.edt_msg.getText().toString().trim();
                if (!TextUtils.isEmpty(var2)) {
                    MsgEntity var3x = new MsgEntity(LoginActivity.cuser, FriendAdapter.friendName1, var2, 1);
                    ChatingActivity.this.list.add(var3x);
                    var3.notifyItemInserted(ChatingActivity.this.list.size() - 1);
                    ChatingActivity.this.recycleView.scrollToPosition(ChatingActivity.this.list.size() - 1);
                    ChatingActivity.this.edt_msg.setText("");
                }

            }
        });
    }
}
