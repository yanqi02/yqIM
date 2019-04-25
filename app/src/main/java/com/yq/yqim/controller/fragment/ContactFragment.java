package com.yq.yqim.controller.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.exceptions.HyphenateException;
import com.yq.yqim.R;
import com.yq.yqim.controller.activity.AddContactActivity;
import com.yq.yqim.controller.activity.ChatActivity;
//import com.yq.yqim.controller.activity.InviteActivity;
import com.yq.yqim.controller.activity.InviteActivity;
import com.yq.yqim.model.Model;
import com.yq.yqim.model.bean.UserInfo;
import com.yq.yqim.model.bean.testbean;
import com.yq.yqim.utils.Constant;
import com.yq.yqim.utils.IPtools;
import com.yq.yqim.utils.SpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//联系人
public class ContactFragment extends EaseContactListFragment {

    private BroadcastReceiver ContactInviteChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //更新红点显示
            iv_contact_red.setVisibility(View.VISIBLE);
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, true);
        }
    };
    private ImageView iv_contact_red;
    private LocalBroadcastManager mLBM;
    private LinearLayout ll_contact_invite;
    String ip= IPtools.getIp();
    private BroadcastReceiver ContactChangeReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //刷新页面
//            refreshcontact();
        }
    };

    @Override
    protected void initView() {
        super.initView();
        titleBar.setRightImageResource(R.drawable.ease_blue_add);
        //添加头布局
        View headView = View.inflate(getActivity(), R.layout.header_fragment_contact, null);
        listView.addHeaderView(headView);
        //获取红点对象
        iv_contact_red = (ImageView) headView.findViewById(R.id.iv_contact_red);
        //g获取邀请信息
        ll_contact_invite = (LinearLayout) headView.findViewById(R.id.ll_contact_invite);

        //listView的点击事件
        setContactListItemClickListener(new EaseContactListItemClickListener() {
            @Override
            public void onListItemClicked(EaseUser user) {
                if(user==null)
                {return;}

               Intent intent= new Intent(getActivity(), ChatActivity.class);
               //传递参数    （聊天方式）
                intent.putExtra(EaseConstant.EXTRA_USER_ID,user.getUsername());
               startActivity(intent);
            }
        });
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        //添加按钮点击事件
        titleBar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddContactActivity.class);
                startActivity(intent);
            }
        });
        //初始化红点显示
        boolean isNewInvite = SpUtils.getInstance().getBoolean(SpUtils.IS_NEW_INVITE, false);
        iv_contact_red.setVisibility(isNewInvite ? View.VISIBLE : View.GONE);
//邀请信息条目的点击事件
        ll_contact_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //红点处理
                iv_contact_red.setVisibility(View.GONE);
                SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, false);
                //跳转到邀请信息列表页面
              Intent intent = new Intent(getActivity(), InviteActivity.class);
              startActivity(intent);
            }
        });


        //注册广播
        mLBM = LocalBroadcastManager.getInstance(getActivity());
        //联系人邀请信息变化的广播
        mLBM.registerReceiver(ContactInviteChangeReceiver, new IntentFilter(Constant.CONTACT_INVITE_CHANGED));
        //联系人变化的广播
        mLBM.registerReceiver(ContactChangeReceiver,new IntentFilter(Constant.CONTACT_CHANGED));

        //从服务器获取联系人信息
        getContactFromserver();

    }




//    private void showResponse(final String response) {
//
//        Gson gson = new Gson();
//        final List<testbean> userList = gson.fromJson(response, new TypeToken<List<testbean>>() {
//        }.getType());
//        //切换到主线程
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//
//                for (testbean u : userList) {
//                    System.out.println(u.getPassword());
//                }
//
//            }
//        });
//
//    }


    public void getContactFromserver() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {

                    //获取所有好友



//                    if (hxids != null && hxids.size() >= 0) {
//                        List<UserInfo> contacts = new ArrayList<UserInfo>();
//                        for (String hxid : hxids) {
//                            UserInfo userInfo = new UserInfo(hxid);
//                            contacts.add(userInfo);
//                        }

//                        //保存好友信息到本地数据库
//                        Model.getInstance().getDbManager().getContactTableDao().saveContacts(contacts,true);
                        if (getActivity() == null) {
                            return;
                        }
                        //刷新页面
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //刷新页面
//                                refreshcontact();
                            }

                        });



                } catch (Exception e) {
                    e.printStackTrace();
                }

            } 
});
    }
//    private void refreshcontact(){
////获取数据
//        List<UserInfo> contacts = Model.getInstance().getDbManager().getContactTableDao().getContacts();
//        if (contacts != null && contacts.size() >= 0) {
//            //设置数据
//            Map<String, EaseUser> contactMap = new HashMap<>();
//            //转换
//            for (UserInfo contact : contacts) {
//                EaseUser easeUser = new EaseUser(contact.getHxid());
//                contactMap.put(contact.getHxid(), easeUser);
//            }
//            setContactsMap(contactMap);
//            refresh();
//        }
//    }




    //页面关闭时
    @Override
    public void onDestroy() {
        super.onDestroy();

        //关闭广播
        mLBM.unregisterReceiver(ContactInviteChangeReceiver);
        mLBM.unregisterReceiver(ContactChangeReceiver);

    }










}


