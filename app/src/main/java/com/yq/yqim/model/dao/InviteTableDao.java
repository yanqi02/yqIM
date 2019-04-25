//package com.yq.yqim.model.dao;
//
//import android.content.ContentValues;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.widget.LinearLayout;
//
//import com.yq.yqim.model.bean.GroupInfo;
//import com.yq.yqim.model.bean.InvationInfo;
//import com.yq.yqim.model.bean.UserInfo;
//import com.yq.yqim.model.db.DBHelper;
//
//import java.util.ArrayList;
//import java.util.List;
//
////邀请信息操作类
//public class InviteTableDao {
//    private DBHelper mhelper;
//
//    public InviteTableDao(DBHelper helper) {
//        mhelper = helper;
//    }
//
//    //添加邀请
//    public void addInvitation(InvationInfo invationInfo) {
//        //获取数据库连接
//        SQLiteDatabase db = mhelper.getReadableDatabase();
//        //执行添加语句
//        ContentValues values = new ContentValues();
//        values.put(InviteTable.REASON, invationInfo.getReason());
//        values.put(InviteTable.STSTUS, invationInfo.getStatus().ordinal());
//        UserInfo user = invationInfo.getUser();
//        if (user != null) {//联系人
//            values.put(InviteTable.USER_HXID, invationInfo.getUser().getHxid());
//            values.put(InviteTable.USER_NAME, invationInfo.getUser().getName());
//        } else {//群组
//
//        }
//        db.replace(InviteTable.TAB_NAME, null, values);
//    }
//
//    //获取所有邀请信息
//    public List<InvationInfo> getInvitations() {
//        //获取数据库连接
//        SQLiteDatabase db = mhelper.getReadableDatabase();
//        String sql = "select * from " + InviteTable.TAB_NAME;
//        Cursor cursor = db.rawQuery(sql, null);
//        List<InvationInfo> invationInfos = new ArrayList<>();
//        while ((cursor.moveToNext())) {
//            InvationInfo invationInfo = new InvationInfo();
//            invationInfo.setReason(cursor.getString(cursor.getColumnIndex(InviteTable.REASON)));
//            invationInfo.setStatus(int2InviteStatus(cursor.getInt(cursor.getColumnIndex(InviteTable.STSTUS))));
//            String groupId = cursor.getString(cursor.getColumnIndex(InviteTable.GROUP_HXID));
//            if (groupId == null) {//联系人的邀请信息
//                UserInfo userInfo = new UserInfo();
//                userInfo.setHxid(cursor.getString(cursor.getColumnIndex(InviteTable.USER_HXID)));
//                userInfo.setName(cursor.getString(cursor.getColumnIndex(InviteTable.USER_NAME)));
//                userInfo.setNick(cursor.getString(cursor.getColumnIndex(InviteTable.USER_NAME)));
//                invationInfo.setUser(userInfo);
//
//            } else {//群的邀请信息
//
//            }
//            //添加本次循环的邀请信息到总集合
//            invationInfos.add(invationInfo);
//        }
//        cursor.close();
//        return invationInfos;
//    }
//
//    //j将int状态转换为邀请的状态
//    private InvationInfo.InvationStatus int2InviteStatus(int intStatus) {
//        if (intStatus == InvationInfo.InvationStatus.NEW_INVITE.ordinal()) {
//            return InvationInfo.InvationStatus.NEW_INVITE;
//        }
//        if (intStatus == InvationInfo.InvationStatus.INVITE_ACCEPT.ordinal()) {
//            return InvationInfo.InvationStatus.INVITE_ACCEPT;
//        }
//        if (intStatus == InvationInfo.InvationStatus.INVITE_ACCEPT_BY_PEER.ordinal()) {
//            return InvationInfo.InvationStatus.INVITE_ACCEPT_BY_PEER;
//        }
//        if (intStatus == InvationInfo.InvationStatus.NEW_GROUP_INVITE.ordinal()) {
//            return InvationInfo.InvationStatus.NEW_GROUP_INVITE;
//        }
//        if (intStatus == InvationInfo.InvationStatus.NEW_GROUP_APPLICATION.ordinal()) {
//            return InvationInfo.InvationStatus.NEW_GROUP_APPLICATION;
//        }
//        if (intStatus == InvationInfo.InvationStatus.GROUP_INVITE_ACCEPTED.ordinal()) {
//            return InvationInfo.InvationStatus.GROUP_INVITE_ACCEPTED;
//        }
//        if (intStatus == InvationInfo.InvationStatus.GROUP_APPLICATION_ACCEPTED.ordinal()) {
//            return InvationInfo.InvationStatus.GROUP_APPLICATION_ACCEPTED;
//        }
//        if (intStatus == InvationInfo.InvationStatus.GROUP_INVITE_DECLINED.ordinal()) {
//            return InvationInfo.InvationStatus.GROUP_INVITE_DECLINED;
//        }
//        if (intStatus == InvationInfo.InvationStatus.GROUP_APPLICATION_DECLINED.ordinal()) {
//            return InvationInfo.InvationStatus.GROUP_APPLICATION_DECLINED;
//        }
//        if (intStatus == InvationInfo.InvationStatus.GROUP_ACCEPT_INVITE.ordinal()) {
//            return InvationInfo.InvationStatus.GROUP_ACCEPT_INVITE;
//        }
//        if (intStatus == InvationInfo.InvationStatus.GROUP_ACCEPT_APPLICATION.ordinal()) {
//            return InvationInfo.InvationStatus.GROUP_ACCEPT_APPLICATION;
//        }
//        if (intStatus == InvationInfo.InvationStatus.GROUP_REJECT_APPLICATION.ordinal()) {
//            return InvationInfo.InvationStatus.GROUP_REJECT_APPLICATION;
//        }
//        if (intStatus == InvationInfo.InvationStatus.GROUP_REJECT_INVITE.ordinal()) {
//            return InvationInfo.InvationStatus.GROUP_REJECT_INVITE;
//        }
//        return null;
//
//    }
//    //删除邀请
//
//
//    public  void removeInvitation(String hxid) {
//        if (hxid == null) {
//            return;
//        }
//        SQLiteDatabase db = mhelper.getReadableDatabase();
//        db.delete(InviteTable.TAB_NAME, InviteTable.USER_HXID + "=?", new String[]{hxid});
//    }
//
//    //更新邀请状态
//    public void updateInvitationStatus(InvationInfo.InvationStatus invationStatus, String hxid){
//
//        if (hxid == null) {
//            return;
//        }
//        SQLiteDatabase db = mhelper.getReadableDatabase();
//        ContentValues values =new ContentValues();
//        values.put(InviteTable.STSTUS,invationStatus.ordinal());
//        db.update(InviteTable.TAB_NAME,values,InviteTable.USER_HXID+" =?",new String[]{hxid});
//
//}
//}
