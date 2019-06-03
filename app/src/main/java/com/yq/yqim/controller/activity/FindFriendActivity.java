package com.yq.yqim.controller.activity;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yq.yqim.R;
import com.yq.yqim.controller.adapter.FriendAdapter;
import com.yq.yqim.controller.fragment.ContactFragment;
import com.yq.yqim.model.Model;
import com.yq.yqim.model.bean.Gbooks;
import com.yq.yqim.model.bean.testbean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.yq.yqim.controller.activity.LoginActivity.cuser;
import static com.yq.yqim.controller.activity.MoreMegActivity.mes;

public class FindFriendActivity extends AppCompatActivity {
    private ArrayList<testbean> friendList = new ArrayList();
    private RecyclerView listfriend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friend);
        System.out.println(mes);
        list1();
    }

    public void list1()
    {

        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                String uu=cuser;
                try {
                    OkHttpClient client = new OkHttpClient();
                    String path = "http://172.24.234.147:8081/inte/"+mes;
                    final Gson gson = new Gson();
                    Request request = new Request.Builder().url(path)//请求的url
                            .get().build();

                    Call call = client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call paramAnonymous2Call, IOException paramAnonymous2IOException) {
                            System.out.println("请检查网络连接");
                            Toast.makeText(FindFriendActivity.this,"清检查网络连接",Toast.LENGTH_SHORT).show();
                        }

                        public void onResponse(final Call paramAnonymous2Call, Response response)
                                throws IOException {
                            final String responseData = response.body().string();

                            final List<testbean> mIn = gson.fromJson(responseData, new TypeToken<List<testbean>>() {
                            }.getType());


                       runOnUiThread(new Runnable() {
                                public void run() {
                                    if ((friendList != null) ||friendList.size() > 0) {
                                        friendList.clear();
                                    }


                                    for (testbean testbean : mIn) {
                                       int count=0;
                                        if(count==0)
                                        {
                                            System.out.println(cuser+"第一");
                                            count++;
                                        }
                                        else {
                                            System.out.println(testbean.getUsername());
                                            friendList.add(testbean);

                                        }
                                        if(friendList.size()>3){break;}
                                    }
                                    initRecyclerView();
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


    private void initRecyclerView() {

        listfriend=(RecyclerView)findViewById(R.id.listfriend);
        FriendAdapter adapter = new FriendAdapter(FindFriendActivity.this, friendList);
        listfriend.setAdapter(adapter);
        listfriend.setLayoutManager(new LinearLayoutManager(FindFriendActivity.this, 1, true));


    }
}
