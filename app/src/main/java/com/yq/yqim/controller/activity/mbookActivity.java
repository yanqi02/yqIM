package com.yq.yqim.controller.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.yq.yqim.R;
import com.yq.yqim.model.Model;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.hyphenate.cloud.EMHttpClient.GET;

public class mbookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mbook);
      String utl = null;
        getbook(utl);
    }


    //获取指定url的图片
    public void getbook(final String ur)
    {
        final ImageView iv;
        iv=(ImageView)findViewById(R.id.iv_book);

        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                final Bitmap bitmap;
                try {
                    URL url=new URL(ur);
                  connection=(HttpURLConnection)url.openConnection();
                  //设置请求方式
                  connection.setRequestMethod(GET);
                  //设置超时时间
                  connection.setConnectTimeout(5000);
                  int code =connection.getResponseCode();
                  if(code==200){
                      InputStream inputStream=connection.getInputStream();
                      bitmap= BitmapFactory.decodeStream(inputStream);
                      runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              Toast.makeText(mbookActivity.this,"sddd",Toast.LENGTH_SHORT).show();
                              iv.setImageBitmap(bitmap);
                          }
                      });

                                        }
                }catch (Exception e)
                {}
connection.disconnect();
            }
        });


    }
}
