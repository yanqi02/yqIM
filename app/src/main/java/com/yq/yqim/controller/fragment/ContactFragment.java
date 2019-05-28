package com.yq.yqim.controller.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yq.yqim.R;
import com.yq.yqim.controller.activity.AddContactActivity;
import com.yq.yqim.controller.activity.InviteActivity;
import com.yq.yqim.controller.activity.LoginActivity;
import com.yq.yqim.controller.adapter.FriendAdapter;
import com.yq.yqim.model.Model;
import com.yq.yqim.model.bean.testbean;
import com.yq.yqim.utils.IPtools;
import com.yq.yqim.utils.SpUtils;

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

//import com.yq.yqim.controller.activity.InviteActivity;


public class ContactFragment extends Fragment {
    private ArrayList<testbean> BList = new ArrayList();
    private ImageView add;
    private ArrayList<testbean> friendList = new ArrayList();
    String ip = IPtools.getIp();
    private ImageView iv_contact_red;
    private RecyclerView listView;
    private LinearLayout ll_contact_invite;
    private LocalBroadcastManager mLBM;
    private List<testbean> mfriend = new ArrayList();
    private Button newFriend;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.header_fragment_contact, null);
        initView(view);
        return view;

    }

    private void initView(View view) {

        listView = ((RecyclerView) view.findViewById(R.id.contact_list));
        iv_contact_red = ((ImageView) view.findViewById(R.id.iv_contact_red));
        iv_contact_red.setVisibility(View.GONE);
        ll_contact_invite = ((LinearLayout) view.findViewById(R.id.new_friend));
        ll_contact_invite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                iv_contact_red.setVisibility(View.GONE);
                Intent intent = new Intent(getActivity(), InviteActivity.class);
             startActivity(intent);
////                try {
////                    paramAnonymousView = new ContactFragment.LocalReceiver(ContactFragment.this);
////                    InviteActivity.localBroadcastManager.registerReceiver(paramAnonymousView, new IntentFilter("newinvite"));
////                    return;
////                } catch (Exception paramAnonymousView) {
////                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddContactActivity.class);
                ContactFragment.this.startActivity(intent);
            }
        });
       ll_contact_invite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                Intent intent = new Intent(getActivity(), InviteActivity.class);
                startActivity(intent);
            }
        });
    }


    private void getContactFromserver(final View paramView) {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    OkHttpClient localOkHttpClient = new OkHttpClient();
                    final testbean testbean = new testbean();
                    testbean.setUsername(LoginActivity.cuser);
                    final Gson gson = new Gson();
                    //使用Gson将对象转换为json字符串
                    String json = gson.toJson(testbean);
                    RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
                    String Url = ip + "FriendList";
                    Request request = new Request.Builder().url(Url)//请求的url
                            .post(requestBody).build();

                    Call call = localOkHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        public void onFailure(Call paramAnonymous2Call, IOException paramAnonymous2IOException) {
                            System.out.println("»ñÈ¡Ê§°Ü");
                        }

                        public void onResponse(final Call paramAnonymous2Call, Response response)
                                throws IOException {
                            final String responseData = response.body().string();

                            final List<testbean> mIn = gson.fromJson(responseData, new TypeToken<List<testbean>>() {
                            }.getType());


                            ContactFragment.this.getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    if ((ContactFragment.this.friendList != null) || (ContactFragment.this.friendList.size() > 0)) {
                                        ContactFragment.this.friendList.clear();
                                    }


                                    for (testbean testbean : mIn) {

                                        System.out.println(testbean.getUsername());
                                        ContactFragment.this.friendList.add(testbean);
                                    }
                                    ContactFragment.this.initRecyclerView(paramView);
                                }
                            });
                        }
                    });
                    return;
                } catch (Exception localException) {
                    localException.printStackTrace();
                }
            }
        });
    }

    private void initRecyclerView(View paramView) {
        paramView = (RecyclerView) paramView.findViewById(R.id.contact_list);
        FriendAdapter localFriendAdapter = new FriendAdapter(getActivity(), this.friendList);
        ((RecyclerView) paramView).setLayoutManager(new LinearLayoutManager(getActivity(), 1, true));
        localFriendAdapter.setOnItemClickListener(new FriendAdapter.OnItemClickListener() {
            public void OnItemClick(View paramAnonymousView, testbean paramAnonymoustestbean) {
////                paramAnonymousView = new Intent(ContactFragment.this.getActivity(), ChatingActivity.class);
////                ContactFragment.this.startActivity(paramAnonymousView);
            }
        });
        ((RecyclerView) paramView).setAdapter(localFriendAdapter);
    }




    class LocalReceiver extends BroadcastReceiver {
        LocalReceiver() {
        }

        public void onReceive(Context paramContext, Intent paramIntent) {
            ContactFragment.this.iv_contact_red.setVisibility(View.GONE);
            System.out.println("Ë¢ÐÂÁªÏµÈËÖ÷Ò³Ãæ");
        }
    }





    private BroadcastReceiver ContactInviteChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //更新红点显示
            iv_contact_red.setVisibility(View.VISIBLE);
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, true);
        }
    };


    private LinearLayout newfriend;

    private BroadcastReceiver ContactChangeReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //刷新页面
//            refreshcontact();
        }
    };







////    private void showResponse(final String response) {
////
////        Gson gson = new Gson();
////        final List<testbean> userList = gson.fromJson(response, new TypeToken<List<testbean>>() {
////        }.getType());
////        //切换到主线程
////        runOnUiThread(new Runnable() {
////            @Override
////            public void run() {
////
////                for (testbean u : userList) {
////                    System.out.println(u.getPassword());
////                }
////
////            }
////        });
////
////    }


    public void getContactFromserver() {

    }}

