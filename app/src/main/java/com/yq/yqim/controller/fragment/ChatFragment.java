//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yq.yqim.controller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yq.yqim.R;
import com.yq.yqim.controller.activity.ChatingActivity;
import com.yq.yqim.controller.adapter.FriendAdapter;
import com.yq.yqim.controller.adapter.FriendAdapter.OnItemClickListener;
import com.yq.yqim.model.bean.testbean;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;

public class ChatFragment extends Fragment {
    public static ArrayList<testbean> friendList = new ArrayList();
    private RecyclerView chatlist;
    private ArrayList<testbean> mIn = new ArrayList();

    public ChatFragment() {
    }

    private void initRecyclerView(View var1) {
        PrintStream var2 = System.out;
        StringBuilder var3 = new StringBuilder();
        var3.append("Friend:");
        var3.append(friendList);
        var2.println(var3.toString());
        RecyclerView var4 = (RecyclerView)var1.findViewById(R.id.chats);
        FriendAdapter var5 = new FriendAdapter(this.getActivity(), friendList);
        var4.setLayoutManager(new LinearLayoutManager(this.getActivity(), 1, false));
        var5.setOnItemClickListener(new OnItemClickListener() {
            public void OnItemClick(View var1, testbean var2) {
                Intent var3 = new Intent(ChatFragment.this.getActivity(), ChatingActivity.class);
                ChatFragment.this.startActivity(var3);
            }
        });
        var4.setAdapter(var5);
    }

    private void initView(View var1) {
        Collections.reverse(friendList);
        this.initRecyclerView(var1);
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater var1, @Nullable ViewGroup var2, @Nullable Bundle var3) {
        View var4 = View.inflate(this.getActivity(), R.layout.activity_chat, (ViewGroup)null);
        this.initView(var4);
        return var4;
    }
}
