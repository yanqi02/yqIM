package com.yq.yqim.model;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.yq.yqim.model.bean.InvationInfo;
import com.yq.yqim.model.bean.UserInfo;
import com.yq.yqim.utils.Constant;
import com.yq.yqim.utils.SpUtils;

//全局事件监听
public class EventListener {
    private Context mcontext;
    private final LocalBroadcastManager mLBM;

    public EventListener(Context context) {
        mcontext = context;
        //创建发送广播的管理者对象
        mLBM = LocalBroadcastManager.getInstance(mcontext);
        //注册一个联系人变化的监听
        EMClient.getInstance().contactManager().setContactListener(emContactListener);
    }

    private final EMContactListener emContactListener = new EMContactListener() {
        @Override
        public void onContactAdded(String hxid) {
            //数据库更新
            Model.getInstance().getDbManager().getContactTableDao().saveContact(new UserInfo(hxid), true);
            //发送联系人变化的广播
            mLBM.sendBroadcast(new Intent(Constant.CONTACT_CHANGED));
        }

        @Override
        public void onContactDeleted(String s) {
            Model.getInstance().getDbManager().getContactTableDao().deleteContactByid(s);
//            Model.getInstance().getDbManager().getInviteTableDao().removeInvitation(s);
            mLBM.sendBroadcast(new Intent(Constant.CONTACT_CHANGED));

        }

        @Override
        public void onContactInvited(String hxid, String reason) {
            //邀请信息表更新
            InvationInfo invitation = new InvationInfo();
//            invitation.setUser(new UserInfo(hxid));
            invitation.setReason(reason);
            invitation.setStatus(InvationInfo.InvationStatus.NEW_INVITE);//新邀请

//            Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invitation);
            //新邀请的处理
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, true);//是否有新邀请信息
            //发送邀请信息变化的广播
            mLBM.sendBroadcast(new Intent(Constant.CONTACT_INVITE_CHANGED));

        }

        @Override
        public void onFriendRequestAccepted(String s) {
            InvationInfo invitationInfo = new InvationInfo();
//            invitationInfo.setUser(new UserInfo(s));
            invitationInfo.setStatus(InvationInfo.InvationStatus.INVITE_ACCEPT_BY_PEER);
//            Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invitationInfo);
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, true);
            mLBM.sendBroadcast(new Intent(Constant.CONTACT_INVITE_CHANGED));
        }

        @Override
        public void onFriendRequestDeclined(String s) {
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, true);
            mLBM.sendBroadcast(new Intent(Constant.CONTACT_INVITE_CHANGED));
        }
    };
}
