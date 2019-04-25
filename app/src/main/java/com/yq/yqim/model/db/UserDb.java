package com.yq.yqim.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yq.yqim.model.dao.UserAccount;

public class UserDb extends SQLiteOpenHelper
 {
     public UserDb(Context context)
     {
         super(context,"account.db",null,1);
     }
     @Override
     public void onCreate(SQLiteDatabase db) {
db.execSQL(UserAccount.CREATE_TAB);
     }

     @Override
     public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

     }
 }
