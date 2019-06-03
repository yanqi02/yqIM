package com.yq.yqim.controller.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yq.yqim.R;
import com.yq.yqim.model.Model;
import com.yq.yqim.model.bean.Gbooks;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.yq.yqim.controller.activity.RegisterActivity.uu;

//更多信息
public class MoreMegActivity extends AppCompatActivity {

    private CheckBox checkBox;
    private Button sure;
    private LinearLayout lin;
    private EditText introduce;
    public  static  String mes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_msg);

        initView();
        initListener();
    }
    private void initView()
    {introduce=(EditText)findViewById(R.id.introduce);
        sure=(Button)findViewById(R.id.sure);
        lin=(LinearLayout)findViewById(R.id.lin);


    }

    private void initListener(){

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final StringBuffer sb=new StringBuffer();
                //拿到所有的子类长度
                int cNum = lin.getChildCount();
                for (int i = 0; i < cNum; i++) {
                    //根据i 拿到每一个CheckBox
                    CheckBox cb= (CheckBox) lin.getChildAt(i);
                    //判断CheckBox是否被选中
                    if(cb.isChecked()){
                        //把被选中的文字添加到StringBuffer中
                        sb.append(cb.getText().toString());
                    }
                }

                sb.append(introduce.getText().toString().trim());
                Toast.makeText(MoreMegActivity.this, sb.toString(), Toast.LENGTH_SHORT).show();

mes=sb.toString();



                Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OkHttpClient client = new OkHttpClient();
                            String path = "http://172.24.234.147:8081/ins/"+uu+"/"+sb;
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
                                            Toast.makeText(MoreMegActivity.this, "清检查网络连接！", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {



                                    runOnUiThread(new Runnable() {
                                        public void run() {

                                            Toast.makeText(MoreMegActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();


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
        });

    }

}
