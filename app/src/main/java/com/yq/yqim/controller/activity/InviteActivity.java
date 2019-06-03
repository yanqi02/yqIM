//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yq.yqim.controller.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yq.yqim.R;
import com.yq.yqim.controller.adapter.InviteAdapter;
import com.yq.yqim.controller.adapter.InviteAdapter.OnInviteListener;
import com.yq.yqim.model.Model;
import com.yq.yqim.model.bean.InvationInfo;
import com.yq.yqim.model.bean.friendInfo;
import com.yq.yqim.utils.IPtools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Request.Builder;

public class InviteActivity extends AppCompatActivity {
    public static LocalBroadcastManager localBroadcastManager;
    private InviteAdapter inviteAdapter;
    String ip = IPtools.getIp();
    private ListView lv_invite;
    private List<InvationInfo> mInvitationInfos = new ArrayList();
    private OnInviteListener monInviteListener = new OnInviteListener() {
        public void onAccept(InvationInfo var1) {
            InviteActivity.LocalReceiver var2 = InviteActivity.this.new LocalReceiver();
            InviteActivity.localBroadcastManager.registerReceiver(var2, new IntentFilter("newb"));
            Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                public void run() {
                    String var2 = InviteActivity.this.inviteAdapter.cfriend;

                    try {
                        System.out.println(var2);
                        OkHttpClient var1 = new OkHttpClient();
                        friendInfo var3 = new friendInfo();
                        var3.setUsername(LoginActivity.cuser);
                        var3.setFriend(var2);
                        var2 = (new Gson()).toJson(var3);
                        RequestBody var5 = FormBody.create(MediaType.parse("application/json; charset=utf-8"), var2);
                        StringBuilder var6 = new StringBuilder();
                        var6.append(InviteActivity.this.ip);
                        var6.append("AgreeAdd");
                        String var7 = var6.toString();
                        var1.newCall((new Builder()).url(var7).post(var5).build()).enqueue(new Callback() {
                            public void onFailure(Call var1, IOException var2) {
                                InviteActivity.this.runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(InviteActivity.this, "  ", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            public void onResponse(Call var1, Response var2) throws IOException {
                                final String var3 = var2.body().string();
                                InviteActivity.this.runOnUiThread(new Runnable() {
                                    public void run() {
                                        if (var3 != null) {
                                            InviteActivity.this.runOnUiThread(new Runnable() {
                                                public void run() {
                                                    Toast.makeText(InviteActivity.this, "接受邀请", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        } else {
                                            InviteActivity.this.runOnUiThread(new Runnable() {
                                                public void run() {
                                                    Toast.makeText(InviteActivity.this, "接受邀请失败", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        });
                    } catch (Exception var4) {
                        var4.printStackTrace();
                    }
                }
            });
        }

        public void onReject(InvationInfo var1) {
            InviteActivity.LocalReceiver var2 = InviteActivity.this.new LocalReceiver();
            InviteActivity.localBroadcastManager.registerReceiver(var2, new IntentFilter("newb"));
            Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                public void run() {
                    String var2 = InviteActivity.this.inviteAdapter.cfriend;

                    try {
                        System.out.println(var2);
                        OkHttpClient var1 = new OkHttpClient();
                        friendInfo var3 = new friendInfo();
                        var3.setUsername(LoginActivity.cuser);
                        var3.setFriend(var2);
                        var2 = (new Gson()).toJson(var3);
                        RequestBody var5 = FormBody.create(MediaType.parse("application/json; charset=utf-8"), var2);
                        StringBuilder var6 = new StringBuilder();
                        var6.append(InviteActivity.this.ip);
                        var6.append("Refuse");
                        String var7 = var6.toString();
                        var1.newCall((new Builder()).url(var7).post(var5).build()).enqueue(new Callback() {
                            public void onFailure(Call var1, IOException var2) {
                                InviteActivity.this.runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(InviteActivity.this, "  ", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            public void onResponse(Call var1, Response var2) throws IOException {
                                final String var3 = var2.body().string();
                                InviteActivity.this.runOnUiThread(new Runnable() {
                                    public void run() {
                                        if (var3 != null) {
                                            InviteActivity.this.runOnUiThread(new Runnable() {
                                                public void run() {
                                                    Toast.makeText(InviteActivity.this, "拒绝邀请", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        } else {
                                            InviteActivity.this.runOnUiThread(new Runnable() {
                                                public void run() {
                                                    Toast.makeText(InviteActivity.this, "拒绝邀请失败", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        });
                    } catch (Exception var4) {
                        var4.printStackTrace();
                    }
                }
            });
        }
    };

    public InviteActivity() {
    }

    private void initData() {
        this.inviteAdapter = new InviteAdapter(this, this.monInviteListener);
        this.lv_invite.setAdapter(this.inviteAdapter);
        this.refresh();
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
    }

    private void initUser(final String var1) {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    OkHttpClient var1x = new OkHttpClient();
                    RequestBody var2 = FormBody.create(MediaType.parse("application/json; charset=utf-8"), var1);
                    StringBuilder var3 = new StringBuilder();
                    var3.append(InviteActivity.this.ip);
                    var3.append("Invite");
                    String var5 = var3.toString();
                    var1x.newCall((new Builder()).url(var5).post(var2).build()).enqueue(new Callback() {
                        public void onFailure(Call var1x, IOException var2) {
                            System.out.println("获取失败");
                        }

                        public void onResponse(Call var1x, Response var2) throws IOException {
                            String var5 = var2.body().string();

                            InvationInfo var7;
                            for (Iterator var6 = ((List) (new Gson()).fromJson(var5, (new TypeToken<List<InvationInfo>>() {
                            }).getType())).iterator(); var6.hasNext(); InviteActivity.this.mInvitationInfos.add(var7)) {
                                var7 = (InvationInfo) var6.next();
                                if (var7.getStatus() == 0) {
                                    try {
                                        Intent var3 = new Intent("newinvite");
                                        InviteActivity.localBroadcastManager.sendBroadcast(var3);
                                        System.out.println("发送新邀请信息的广播");
                                    } catch (Exception var4) {
                                    }
                                }
                            }

                            InviteActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    InviteActivity.this.initData();
                                }
                            });
                        }
                    });
                } catch (Exception var4) {
                    var4.printStackTrace();
                }
            }
        });
    }

    private void initView() {
        this.lv_invite = (ListView) this.findViewById(R.id.lv_invite);
    }

    private void refresh() {
        this.inviteAdapter.refresh(this.mInvitationInfos);
    }

    protected void onCreate(Bundle var1) {
        super.onCreate(var1);
        this.setContentView(R.layout.activity_invite);
        this.initView();
        this.initUser(LoginActivity.cuser);
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    class LocalReceiver extends BroadcastReceiver {
        LocalReceiver() {
        }

        public void onReceive(Context var1, Intent var2) {
            InviteActivity.this.refresh();
            System.out.println("收到广播，刷新页面");
        }
    }
}
