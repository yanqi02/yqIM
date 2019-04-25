package com.yq.yqim.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yq.yqim.model.bean.UserInfo;
import com.yq.yqim.model.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

//联系人表的操作类
public class ContactTableDao {
    private DBHelper mHelper;

    public ContactTableDao(DBHelper helper) {
        mHelper = helper;
    }

    //获取所有联系人
    public List<UserInfo> getContacts() {//获取数据库连接
        SQLiteDatabase db = mHelper.getReadableDatabase();
        //执行查询语句
        String sql = "select * from " + ContactTable.TAB_NAME + " where " + ContactTable.IS_CONTACT + " =1";
        Cursor cursor = db.rawQuery(sql, null);
        List<UserInfo> users = new ArrayList<>();
        while (cursor.moveToNext()) {
            UserInfo userinfo = new UserInfo();
            userinfo.setHxid(cursor.getString(cursor.getColumnIndex(UserAccount.HXID)));
            userinfo.setName(cursor.getString(cursor.getColumnIndex(UserAccount.NAME)));
            userinfo.setNick(cursor.getString(cursor.getColumnIndex(UserAccount.NICK)));
            userinfo.setPhoto(cursor.getString(cursor.getColumnIndex(UserAccount.PHOTO)));
            users.add(userinfo);
        }
        //关闭资源
        cursor.close();
        //返回数据
        return users;


    }

    //通过id获取信息
    public UserInfo getContactByid(String hxid)
    {
        if(hxid==null)
        {return null;}
        SQLiteDatabase db = mHelper.getReadableDatabase();
        //执行查询语句
        String sql = "select * from " + ContactTable.TAB_NAME + " where " + ContactTable.HXID + " =?";
        Cursor cursor = db.rawQuery(sql, new String[]{hxid});
        UserInfo userInfo=null;
        if(cursor.moveToNext())
        {
            UserInfo userinfo = new UserInfo();
        userinfo.setHxid(cursor.getString(cursor.getColumnIndex(UserAccount.HXID)));
        userinfo.setName(cursor.getString(cursor.getColumnIndex(UserAccount.NAME)));
        userinfo.setNick(cursor.getString(cursor.getColumnIndex(UserAccount.NICK)));
        userinfo.setPhoto(cursor.getString(cursor.getColumnIndex(UserAccount.PHOTO)));

        }
cursor.close();
        return userInfo;
    }

    //通过id获取用户联系人信息
    public List<UserInfo> getContactsByid(List<String> hxids)
    {
        if(hxids==null||hxids.size()<=0)
        {return null;}
//遍历hxids
        List<UserInfo> contacts =new ArrayList<>();
         for(String hxid:hxids)
        {
            UserInfo contact =getContactByid(hxid);
            contacts.add(contact);
        }
        return contacts;

    }
    //保存单个联系人
    public void saveContact(UserInfo user, boolean isMyContact)
    {
        if(user==null)
        {return;}
        //获取数据库连接
SQLiteDatabase db=mHelper.getReadableDatabase();
        //执行保存语句
        ContentValues values=new ContentValues();
        values.put(ContactTable.HXID,user.getHxid());
        values.put(ContactTable.NAME,user.getName());
        values.put(ContactTable.NAME,user.getNick());
        values.put(ContactTable.PHOTO,user.getPhoto());
        values.put(ContactTable.IS_CONTACT,isMyContact ? 1 : 0 );
        db.replace(ContactTable.TAB_NAME,null,values);




    }
    //保存联系人信息
    public void saveContacts(List<UserInfo> contacts, boolean isMyContact)
    {
        if(contacts==null||contacts.size()<=0)
        {return;}
        for(UserInfo contact:contacts){
            saveContact(contact,isMyContact) ;
        }


    }
    //删除联系人信息
    public void deleteContactByid(String hxid)

    {
        if(hxid==null)
        {return;}
        SQLiteDatabase db=mHelper.getReadableDatabase();
        db.delete(ContactTable.TAB_NAME,ContactTable.HXID+" =?",new String[]{hxid});

    }
}
