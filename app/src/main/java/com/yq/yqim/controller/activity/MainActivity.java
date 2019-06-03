package com.yq.yqim.controller.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nightonke.boommenu.BoomButtons.BoomButtonWithTextBuilder;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.yq.yqim.R;
import com.yq.yqim.controller.fragment.ChatFragment;
import com.yq.yqim.controller.fragment.ContactFragment;
import com.yq.yqim.controller.fragment.SettingFragment;
import com.yq.yqim.model.Model;
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
    private SwipeRefreshLayout swipeRefreshLayout;
    private BoomMenuButton bmb;
    private TextView tv;
    Fragment fragment=null;
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
swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.refresh);
tv=(TextView)findViewById(R.id.tv);
swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
    @Override
    public void onRefresh() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                swipeRefreshLayout.setRefreshing(false);
                if(fragment==contactFragment){
switchFragment(new ContactFragment());}
                else if(fragment==chatFragment)
                {
                    switchFragment(new ChatFragment());
                }
            }
        },2000);

    }

});
    }
    private  void  initData(){
        //创建Fragment对象

        chatFragment=new ChatFragment();
        contactFragment=new ContactFragment();
        settingFragment=new SettingFragment();
    }
    private  void initListener()
    {

        String[] ms={"消息","好友","设置","更多","退出"  };

        bmb=(BoomMenuButton)findViewById(R.id.bmb);
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {

                            //默认选择布局
                            fragment=chatFragment;
                            // When the boom-button corresponding this builder is clicked.
                            switch (index){

                                case 0:
                                    fragment=chatFragment;
                                    break;
                                case 1:
                                    fragment=contactFragment;
                                    break;
                                case 2:
                                    fragment=settingFragment;
                                    break;
                                case 3:
                                    Intent intent = new Intent(MainActivity.this, mbookActivity.class);
                                    startActivity(intent);
                                    break;
                                case 4:
//                                    //关闭DBHelper
//                                    Model.getInstance().getDbManager().close();
                                    //回到登录页面
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(MainActivity.this,"退出成功",Toast.LENGTH_SHORT).show();
                                            Intent intent =new Intent(MainActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                    break;
                            }
                            //切换布局
                            switchFragment(fragment);
                        }
                        //默认选择布局

                    }) .isRound(false)
                    .normalText(ms[i]);

            ;
            bmb.addBuilder(builder);
        }
    }private void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.f1_main,fragment).commit();
    }



}
