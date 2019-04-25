package com.yq.yqim.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yq.yqim.model.bean.UserInfo;
import com.yq.yqim.model.db.UserDb;
//用户数据库操作类

public class UserDao {
    private final UserDb mHelper;

    public UserDao(Context context) {

        mHelper = new UserDb(context);
    }

    //添加
    public void addUser(UserInfo user) {//获取数据库对象
        SQLiteDatabase db = mHelper.getReadableDatabase();
        //执行添加操作
        ContentValues values = new ContentValues();
        values.put(UserAccount.HXID, user.getHxid());
        values.put(UserAccount.NAME, user.getName());
        values.put(UserAccount.NAME, user.getNick());
        values.put(UserAccount.PHOTO, user.getPhoto());
        //有替换，没有添加
        ((SQLiteDatabase) db).replace(UserAccount.TAB_NAME, null, values);
    }

    //获取
    public UserInfo getUserByHxid(String hxid) {//获取数据库对象
        SQLiteDatabase db = mHelper.getReadableDatabase();
        //执行查询语句
        String sql = "select * from " + UserAccount.TAB_NAME + " where " + UserAccount.HXID + " =?";
        Cursor cursor = db.rawQuery(sql, new String[]{hxid});
        UserInfo userinfo = null;
        if (cursor.moveToNext()) {
            //封装资源
            userinfo = new UserInfo();
            userinfo.setHxid(cursor.getString(cursor.getColumnIndex(UserAccount.HXID)));
            userinfo.setName(cursor.getString(cursor.getColumnIndex(UserAccount.NAME)));
            userinfo.setNick(cursor.getString(cursor.getColumnIndex(UserAccount.NICK)));
            userinfo.setPhoto(cursor.getString(cursor.getColumnIndex(UserAccount.PHOTO)));
        }

        cursor.close();
        return userinfo;
    }

}

