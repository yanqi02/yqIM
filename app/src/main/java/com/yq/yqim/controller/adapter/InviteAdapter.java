//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yq.yqim.controller.adapter;

import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.yq.yqim.R;
import com.yq.yqim.controller.activity.InviteActivity;
import com.yq.yqim.model.bean.InvationInfo;
import com.yq.yqim.utils.IPtools;
import java.util.ArrayList;
import java.util.List;

public class InviteAdapter extends BaseAdapter {
    public String cfriend = null;
    private InvationInfo invationInfo;
    String ip = IPtools.getIp();
    private List<InvationInfo> mInvitationInfos = new ArrayList();
    private LocalBroadcastManager mLBM;
    private Context mcontext;
    private InviteAdapter.OnInviteListener monInviteListener;

    public InviteAdapter(Context var1, InviteAdapter.OnInviteListener var2) {
        this.mcontext = var1;
        InviteActivity.localBroadcastManager = LocalBroadcastManager.getInstance(var1);
        this.monInviteListener = var2;
    }

    public int getCount() {
        List var1 = this.mInvitationInfos;
        return var1 == null ? 0 : var1.size();
    }

    public Object getItem(int var1) {
        return this.mInvitationInfos.get(var1);
    }

    public long getItemId(int var1) {
        return (long)var1;
    }

    public View getView(int var1, View var2, ViewGroup var3) {
        final InviteAdapter.ViewHolder var5;
        View var6;
        if (var2 == null) {
            var5 = new InviteAdapter.ViewHolder();
            var6 = View.inflate(this.mcontext, R.layout.item_invite, (ViewGroup)null);
            var5.name = (TextView)var6.findViewById(R.id.tv_invite_name);
            var5.reason = (TextView)var6.findViewById(R.id.tv_invite_reason);
            var5.accept = (Button)var6.findViewById(R.id.bt_invite_accept);
            var5.reject = (Button)var6.findViewById(R.id.bt_invite_reject);
            var6.setTag(var5);
        } else {
            InviteAdapter.ViewHolder var4 = (InviteAdapter.ViewHolder)var2.getTag();
            var6 = var2;
            var5 = var4;
        }

        this.invationInfo = (InvationInfo)this.mInvitationInfos.get(var1);
        String var7 = this.invationInfo.getFriend();
        var1 = this.invationInfo.getStatus();
        if (var7 != null) {
            var5.name.setText(var7);
            if (var1 == 1 || var1 == 2) {
                var5.accept.setVisibility(View.VISIBLE);
                var5.reject.setVisibility(View.VISIBLE);
            }

            var5.reason.setText(this.invationInfo.getReason());
            var5.accept.setOnClickListener(new OnClickListener() {
                public void onClick(View var1) {
                    InviteAdapter.this.monInviteListener.onAccept(InviteAdapter.this.invationInfo);
                    InviteAdapter var2 = InviteAdapter.this;
                    var2.cfriend = var2.invationInfo.getFriend();
                    var5.reason.setText("接受邀请");
                    var5.accept.setVisibility(View.GONE);
                    var5.reject.setVisibility(View.GONE);
                }
            });
            var5.reject.setOnClickListener(new OnClickListener() {
                public void onClick(View var1) {
                    InviteAdapter.this.monInviteListener.onReject(InviteAdapter.this.invationInfo);
                    InviteAdapter var2 = InviteAdapter.this;
                    var2.cfriend = var2.invationInfo.getFriend();
                    var5.reason.setText("拒绝邀请");
                    var5.accept.setVisibility(View.GONE);
                    var5.reject.setVisibility(View.GONE);
                }
            });
        }

        return var6;
    }

    public void refresh(List<InvationInfo> var1) {
        if (var1 != null && var1.size() >= 0) {
            this.mInvitationInfos.clear();
            this.mInvitationInfos.addAll(var1);
            this.notifyDataSetChanged();
        }

    }

    public interface OnInviteListener {
        void onAccept(InvationInfo var1);

        void onReject(InvationInfo var1);
    }

    private class ViewHolder {
        private Button accept;
        private TextView name;
        private TextView reason;
        private Button reject;

        private ViewHolder() {
        }
    }
}
