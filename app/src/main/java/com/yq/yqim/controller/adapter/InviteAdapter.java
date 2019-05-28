package com.yq.yqim.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yq.yqim.R;
import com.yq.yqim.model.bean.InvationInfo;
import com.yq.yqim.utils.IPtools;


import java.util.ArrayList;
import java.util.List;



public class InviteAdapter extends BaseAdapter {
    private OnInviteListener monInviteListener;
    private Context mcontext;
    private InvationInfo invationInfo;
    private List<InvationInfo> mInvitationInfos = new ArrayList<>();
    String ip= IPtools.getIp();
    public InviteAdapter(Context context, OnInviteListener onInviteListener) {
        mcontext = context;
        monInviteListener = onInviteListener;
    }

    //刷新数据
    public void refresh(List<InvationInfo> invationInfos) {

        for (InvationInfo invationInfo : invationInfos) {
            System.out.println("刷新适配器");
            System.out.println(invationInfo.getFriend());
            System.out.println(invationInfo.getReason());
        }
        if (invationInfos != null && invationInfos.size() >= 0) {
           mInvitationInfos.clear();
            mInvitationInfos.addAll(invationInfos);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mInvitationInfos == null ? 0 : mInvitationInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return mInvitationInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //1获取或创建viewHolder
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mcontext, R.layout.item_invite, null);
            holder.name = (TextView) convertView.findViewById(R.id.tv_invite_name);
            holder.reason = (TextView) convertView.findViewById(R.id.tv_invite_reason);
            holder.accept = (Button) convertView.findViewById(R.id.bt_invite_accept);
            holder.reject = (Button) convertView.findViewById(R.id.bt_invite_reject);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //2获取item数据
        invationInfo = mInvitationInfos.get(position);

       // 3 显示当前item数据
        String friend = invationInfo.getFriend();
        if (friend != null) {//联系人
            //名称
       holder.name.setText(friend);
            holder.accept.setVisibility(View.GONE);
            holder.reject.setVisibility(View.GONE);
            holder.reason.setText(invationInfo.getReason());

        //按钮处理
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monInviteListener.onAccept(invationInfo);
            }
        });
        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monInviteListener.onReject(invationInfo);
            }
        });}

    else{//群组
            }

            //4返回view
            return convertView;

        }


    private class ViewHolder {
        private TextView name;
        private TextView reason;
        private Button accept;
        private Button reject;

    }

    public interface OnInviteListener {
        //联系人接受按钮点击事件
        void onAccept(InvationInfo invationInfo);

        //拒绝
        void onReject(InvationInfo invationInfo);
    }






















}

