package com.yq.yqim.controller.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yq.yqim.R;
import com.yq.yqim.controller.adapter.BookAdapter;
import com.yq.yqim.controller.adapter.FriendAdapter;
import com.yq.yqim.controller.adapter.etalkAdapter;
import com.yq.yqim.controller.fragment.ContactFragment;
import com.yq.yqim.model.Model;
import com.yq.yqim.model.bean.Gbooks;
import com.yq.yqim.model.bean.testbean;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


import static com.yq.yqim.controller.adapter.BookAdapter.bhref;

public class mbookActivity extends AppCompatActivity {
private ArrayList<Gbooks> gbooks=new ArrayList<>();
private RecyclerView booklist;
private BookAdapter bookAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mbook);
        listbook();
//        String utl = null;
//        getbook(utl);
    }

public void  listbook(){
booklist=(RecyclerView)findViewById(R.id.booklist);

    Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
        @Override
        public void run() {
            try {
                OkHttpClient client = new OkHttpClient();
                String path = "http://172.24.234.147:8081/gbooks";
                final Gson gson = new Gson();
                Request request = new Request.Builder().url(path)//请求的url
                        .get().build();

                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {
                        //进行更新UI操作
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mbookActivity.this, "清检查网络连接！", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        final String res = response.body().string();


                        final List<Gbooks> mIn = gson.fromJson(res, new TypeToken<List<Gbooks>>() {
                        }.getType());
System.out.println(mIn.toString());

                        runOnUiThread(new Runnable() {
                            public void run() {

                                Toast.makeText(mbookActivity.this, "清检连接！", Toast.LENGTH_SHORT).show();

                                if ((gbooks != null) || (gbooks.size() > 0)) {
                                    gbooks.clear();
                                }


                                for (Gbooks book : mIn) {
                              System.out.println(book.getBookhref());
                                    gbooks.add(book);

                                }
                                initView();
                            }
                        });

                    }
                });
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    });





}

    private void initView() {

        booklist=(RecyclerView)findViewById(R.id.booklist);
        booklist.setLayoutManager(new LinearLayoutManager(this));
        booklist.setAdapter(bookAdapter=new BookAdapter(mbookActivity.this,gbooks));
        bookAdapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View paramView, Gbooks gbooks) {
                Intent intent1 = new Intent(mbookActivity.this,webviewActivity.class);
System.out.println("onitemclicker");
                   startActivity(intent1);
            }
        });



    }



//    //获取指定url的图片
//    public void getbook(final String ur) {
//        final ImageView iv;
//        iv = (ImageView) findViewById(R.id.iv_book);
//
//        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
//            @Override
//            public void run() {
//                HttpURLConnection connection = null;
//                final Bitmap bitmap;
//                try {
//                    URL url = new URL(ur);
//                    connection = (HttpURLConnection) url.openConnection();
//                    //设置请求方式
//                    connection.setRequestMethod(GET);
//                    //设置超时时间
//                    connection.setConnectTimeout(5000);
//                    int code = connection.getResponseCode();
//                    if (code == 200) {
//                        InputStream inputStream = connection.getInputStream();
//                        bitmap = BitmapFactory.decodeStream(inputStream);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(mbookActivity.this, "sddd", Toast.LENGTH_SHORT).show();
//                                iv.setImageBitmap(bitmap);
//                            }
//                        });
//
//                    }
//                } catch (Exception e) {
//                }
//                connection.disconnect();
//            }
//        });
//
//
//    }
}
