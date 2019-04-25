package com.yq.yqim.model.db;

import android.content.Context;

import com.yq.yqim.model.dao.ContactTableDao;
//import com.yq.yqim.model.dao.InviteTableDao;

public class DBManager {
    private final DBHelper dbHelper;
    private final ContactTableDao contactTableDao;
//    private final InviteTableDao inviteTableDao;

    public DBManager(Context context, String name) {
        //创建数据库
        dbHelper = new DBHelper(context, name);
        //创建表的操作类
        contactTableDao = new ContactTableDao(dbHelper);
//        inviteTableDao = new InviteTableDao(dbHelper);
    }

    //获取联系人表的操作类对象
    public ContactTableDao getContactTableDao() {
        return contactTableDao;
    }

    //
//    public InviteTableDao getInviteTableDao() {
//        return inviteTableDao;
//    }

    public void close() {
        dbHelper.close();
    }
}
