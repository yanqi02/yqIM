package com.yq.yqim.model.dao;

public class ContactTable {

    public  static final String TAB_NAME="tab_contact";
    public static final  String NAME="name";
    public static final  String NICK="nick";
    public static final  String HXID="hxid";
    public static final  String PHOTO="photo";
    public static final String IS_CONTACT="is_contact";//是否是联系人
    public  static final String CREATE_TAB ="create table "
            +TAB_NAME+" ("
            +HXID+ " text primary key,"
            +NAME+" text,"
            +NICK+" text,"
            +PHOTO+" text,"
            + IS_CONTACT+" integer);";
}
