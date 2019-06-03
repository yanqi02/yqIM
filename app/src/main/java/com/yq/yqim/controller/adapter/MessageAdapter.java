//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yq.yqim.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yq.yqim.R;
import com.yq.yqim.controller.activity.LoginActivity;
import com.yq.yqim.model.bean.MsgEntity;

import java.util.List;

public class MessageAdapter extends Adapter<MessageAdapter.ViewHolder> {
    private Context mContext;
    private List<MsgEntity> mMsg;

    public MessageAdapter(Context var1, List<MsgEntity> var2) {
        this.mMsg = var2;
        this.mContext = var1;
    }

    public int getItemCount() {
        return this.mMsg.size();
    }

    public void onBindViewHolder(MessageAdapter.ViewHolder var1, int var2) {
        MsgEntity var3 = (MsgEntity)this.mMsg.get(var2);
        if (var3.getType() == 2) {
            var1.send_layout.setVisibility(View.VISIBLE);
            var1.rev_layout.setVisibility(View.GONE);
            var1.rev_tv.setText(var3.getContent());
            var1.friend.setText(var3.getUsername());
        } else {
            if (var3.getType() == 1) {
                var1.rev_layout.setVisibility(View.VISIBLE);
                var1.send_layout.setVisibility(View.GONE);
                var1.send_tv.setText(var3.getContent());
                var1.my.setText(LoginActivity.cuser);
            }

        }
    }

    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup var1, int var2) {
        return new MessageAdapter.ViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.item_chat, var1, false));
    }

    public static class ViewHolder<LinearLayout> extends android.support.v7.widget.RecyclerView.ViewHolder {
        TextView friend;
        TextView my;
        View rev_layout;
        TextView rev_tv;
        View send_layout;
        TextView send_tv;

        public ViewHolder(View var1) {
            super(var1);
            this.rev_layout = var1.findViewById(R.id.left_layout);
            this.send_layout = var1.findViewById(R.id.right_layout);
            this.rev_tv = (TextView)var1.findViewById(R.id.left_msg);
            this.send_tv = (TextView)var1.findViewById(R.id.right_msg);
            this.my = (TextView)var1.findViewById(R.id._my);
            this.friend = (TextView)var1.findViewById(R.id._friend);
        }
    }
}
