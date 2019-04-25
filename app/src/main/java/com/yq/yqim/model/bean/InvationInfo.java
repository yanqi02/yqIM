package com.yq.yqim.model.bean;

public class InvationInfo {
    private String friend;
    private String reason;
    private InvationStatus status;//邀请的状态
private  String  user;
    public InvationInfo() {
    }

    public InvationInfo(String friend, String reason, InvationStatus status, String user) {
        this.friend = friend;
        this.reason = reason;
        this.status = status;
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    public enum InvationStatus                         //枚举
    {
//联系人邀请信息状态
        NEW_INVITE,//新邀请
        INVITE_ACCEPT,//接受邀请
        INVITE_ACCEPT_BY_PEER,//邀请被接受
        NEW_GROUP_INVITE,
        NEW_GROUP_APPLICATION,
        GROUP_INVITE_ACCEPTED,
        GROUP_APPLICATION_ACCEPTED,
       GROUP_INVITE_DECLINED,
       GROUP_APPLICATION_DECLINED,
        GROUP_ACCEPT_INVITE,
        GROUP_ACCEPT_APPLICATION,
        GROUP_REJECT_APPLICATION,
        GROUP_REJECT_INVITE
    }



    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public InvationStatus getStatus() {
        return status;
    }

    public void setStatus(InvationStatus status) {
        this.status = status;
    }
}