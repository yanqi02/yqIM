package com.yq.yqim.controller.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yq.yqim.R;
import com.yq.yqim.controller.fragment.ChatFragment;
import com.yq.yqim.controller.fragment.ContactFragment;
import com.yq.yqim.controller.fragment.SettingFragment;
import com.yq.yqim.model.bean.testbean;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.yq.yqim.utils.IPtools.ip;

public class MainActivity extends FragmentActivity {
private RadioGroup rg_main;
private ChatFragment chatFragment;
private   ContactFragment contactFragment;
private   SettingFragment settingFragment;
List<String> friend=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    initView();

initData();
initListener();
    }
    private void initView(){
        rg_main=(RadioGroup)findViewById(R.id.rg_main);
    }
    private  void  initData(){
        //创建Fragment对象
       chatFragment=new ChatFragment();
         contactFragment=new ContactFragment();
        settingFragment=new SettingFragment();
    }
    private  void initListener()
    {
        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Fragment fragment=null;
                switch (checkedId)
                {
                    case R.id.rb_main_chat:
                        fragment=chatFragment;
                        break;
                    case  R.id.rb_main_contact:
                        fragment=contactFragment;
                        break;
                    case R.id.rb_main_setting:
                        fragment=settingFragment;
                        break;
                }
                //切换布局
                switchFragment(fragment);
            }


        });
        //默认选择布局
        rg_main.check(R.id.rb_main_chat);
    }private void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.f1_main,fragment).commit();
    }
    //准备数据，把数据添加到List中
    private void initUser() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String Url = ip+"WebService/Friend";
                    Request request = new Request.Builder()
                            .url(Url)//请求的url
                            .get()
                            .build();
                    Response response = client.newCall(request).execute();

                    //接受服务端传回来的数据
                    String responseData = response.body().string();//将服务端传来的Json数据转化为String类型数据
                    Gson gson = new Gson();
                    final List<testbean> userList = gson.fromJson(responseData, new TypeToken<List<testbean>>() {
                    }.getType());
runOnUiThread(new Runnable() {
    @Override
    public void run() {


                    for (testbean u : userList) {
                        friend.add(u.getUsername());
                        System.out.println(u.getUsername());
                    }
    }
});
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



}
