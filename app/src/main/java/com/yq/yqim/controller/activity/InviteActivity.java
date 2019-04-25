package com.yq.yqim.controller.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.yq.yqim.R;
//import com.yq.yqim.controller.adapter.InviteAdapter;
import com.yq.yqim.controller.adapter.InviteAdapter;
import com.yq.yqim.model.Model;
import com.yq.yqim.model.bean.InvationInfo;
import com.yq.yqim.utils.Constant;
import com.yq.yqim.utils.IPtools;

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

import static com.yq.yqim.controller.activity.LoginActivity.cuser;

//邀请信息页面
public class InviteActivity extends AppCompatActivity {

    private ListView lv_invite;
  static   String a=null;
    private InviteAdapter inviteAdapter;
    private  LocalBroadcastManager mLBM;
    private List<InvationInfo> mInvitationInfos = new ArrayList<>();
    String ip= IPtools.getIp();
    private BroadcastReceiver ContactInviteChangedReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //接受邀请信息变化 刷新 页面
            refresh();
        }
    };

     private InviteAdapter.OnInviteListener monInviteListener=new InviteAdapter.OnInviteListener() {
        @Override
        public void onAccept(final InvationInfo invationInfo) {
Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
    //通知服务器点击了接受按钮
    @Override
    public void run() {
        try{
//        EMClient.getInstance().contactManager().acceptInvitation(invationInfo.getUser().getHxid());
//        Model.getInstance().getDbManager().getInviteTableDao().updateInvitationStatus(InvationInfo.InvationStatus.INVITE_ACCEPT,invationInfo.getUser().getHxid());
   runOnUiThread(new Runnable() {
       @Override
       public void run() {
           Toast.makeText(InviteActivity.this,"接受邀请",Toast.LENGTH_SHORT).show();
           refresh();





       }
   });

    } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(InviteActivity.this,"接受邀请失败",Toast.LENGTH_SHORT).show();

                }
            });
        }}
});}


        @Override
        public void onReject(final InvationInfo invationInfo) {
Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
    @Override
    public void run() {
        try{
//            EMClient.getInstance().contactManager().declineInvitation(invationInfo.getUser().getHxid());
//        Model.getInstance().getDbManager().getInviteTableDao().removeInvitation(invationInfo.getUser().getHxid());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(InviteActivity.this,"拒绝邀请",Toast.LENGTH_SHORT).show();
refresh();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(InviteActivity.this,"拒绝失败",Toast.LENGTH_SHORT).show();

                }
            });

        }
    }
});
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        initView();
        initUser(cuser);

System.out.println(a+"vvv");
System.out.println(mInvitationInfos);

    }



    private void initData() {
        //初始化ListView
         inviteAdapter = new InviteAdapter(this,monInviteListener);
        lv_invite.setAdapter(inviteAdapter);
        //刷新方法
        refresh();
        //注册邀请信息变化的广播
        mLBM= LocalBroadcastManager.getInstance(this);
        mLBM.registerReceiver(ContactInviteChangedReceiver,new IntentFilter(Constant.CONTACT_INVITE_CHANGED));
    }

    private void refresh() {
        //获取数据库中所有邀请信息
        //刷新适配器


System.out.println("refesh");
        for (InvationInfo invationInfo : mInvitationInfos) {

            System.out.println("add1");
            System.out.println(invationInfo.getFriend());
            System.out.println(invationInfo.getReason());
        }
       inviteAdapter.refresh(mInvitationInfos);

    }

    private void initView() {
        lv_invite=(ListView)findViewById(R.id.lv_invite);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLBM.unregisterReceiver(ContactInviteChangedReceiver);
    }


    //准备数据，把数据添加到List中
    private void initUser(final String user) {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();


                    RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), user);
                    String Url = ip + "WebService/Invite";
                    Request request = new Request.Builder().url(Url)//请求的url
                            .post(requestBody).build();

                    Call call = client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, final IOException e) {
                            System.out.println("获取失败");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            //接受服务端传回来的数据
                            String responseData = response.body().string();//将服务端传来的Json数据转化为String类型数据

                            Gson gson = new Gson();
                  List<InvationInfo>  mIn = gson.fromJson(responseData, new TypeToken<List<InvationInfo>>() {
                            }.getType());
a="sss";
                            for (InvationInfo invationInfo : mIn) {
                                mInvitationInfos.add(invationInfo);

                                System.out.println(a+"add");
                                System.out.println(invationInfo.getFriend());
                                System.out.println(invationInfo.getReason());
                            }
                    System.out.println(a+"in");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            for (InvationInfo invationInfo : mInvitationInfos) {



                                System.out.println(invationInfo.getFriend());
                                System.out.println(invationInfo.getReason());
                                mInvitationInfos.add(invationInfo);
                                System.out.println(mInvitationInfos);
                            }
                            initData();
                        }
                    });
                        }
                    });
                }


                catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }



}
