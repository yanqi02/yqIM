package com.yq.yqim.utils;

import android.content.Context;
import android.content.SharedPreferences;

//保存
//获取数据
public class SpUtils {
    public static final String IS_NEW_INVITE = "is_new_invite";
    private static SharedPreferences mSp;
    private static SpUtils instance = new SpUtils();

    private SpUtils() {
    }

    public static SpUtils getInstance() {
        if (mSp == null) {
            mSp = Main2Activity.getGlobalApplication().getSharedPreferences("im", Context.MODE_PRIVATE);
        }
        return instance;

    }

    //保存
    public void save(String key, Object value) {
        if (value instanceof String) {
            mSp.edit().putString(key, (String) value).commit();
        } else if (value instanceof Boolean) {
            mSp.edit().putBoolean(key, (Boolean) value).commit();

        } else if (value instanceof Integer) {
            mSp.edit().putInt(key, (Integer) value).commit();
        }

    }

    //获取数据的方法
    public String getString(String key, String defValue) {
        return mSp.getString(key, defValue);
    }

    //boolean
    public boolean getBoolean(String key, boolean defValue) {
        return mSp.getBoolean(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return mSp.getInt(key, defValue);
    }
}
