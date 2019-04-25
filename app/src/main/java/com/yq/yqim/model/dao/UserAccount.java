package com.yq.yqim.model.dao;

import android.content.Context;


//SQL语句
public class UserAccount {
    public  static final String TAB_NAME="User";
    public static final  String NAME="name";
    public static final  String NICK="nick";
    public static final  String HXID="hxid";
    public static final  String PHOTO="photo";
    public  static final String CREATE_TAB ="create table "
            +TAB_NAME+" ("
            +HXID+ " text primary key,"
            +NAME+" text,"
            +NICK+" text,"
            +PHOTO+" text);";

public UserAccount(Context context){

}

}
