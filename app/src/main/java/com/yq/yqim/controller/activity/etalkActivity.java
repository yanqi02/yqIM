package com.yq.yqim.controller.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yq.yqim.R;
import com.yq.yqim.controller.adapter.etalkAdapter;
import com.yq.yqim.model.bean.etalkBean;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class etalkActivity extends AppCompatActivity {
private RecyclerView recyclerView;
private ArrayList<etalkBean> mtalks;
private etalkAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etalk);
        initView();
    }

    private void initView() {

        recyclerView=(RecyclerView)findViewById(R.id.mtalk);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter=new etalkAdapter());
    }
}
