package com.yq.yqim.model.dao;
//邀请信息的表
public class InviteTable {
    public  static final String TAB_NAME="tab_invite";
    public static final String USER_HXID="user_hxid";
    public static final  String USER_NAME="user_name";
    public static final  String GROUP_NAME="group_name";
    public static final  String GROUP_HXID="group_hxid";
    public static final  String REASON="reason";
    public static final String STSTUS="status";//是否是联系人
    public  static final String CREATE_TAB ="create table "
            +TAB_NAME+" ("
            +USER_HXID+ " text primary key,"
            +USER_NAME+" text,"
            +GROUP_HXID+" text,"
            +GROUP_NAME+" text,"
            +REASON+" text,"
            + STSTUS+" integer);";

}
