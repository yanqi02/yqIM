package com.yq.yqim.model;

import android.content.Context;

import com.yq.yqim.model.bean.UserInfo;
import com.yq.yqim.model.dao.UserAccount;
import com.yq.yqim.model.dao.UserDao;
import com.yq.yqim.model.db.DBHelper;
import com.yq.yqim.model.db.DBManager;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Model全局类
public class Model {
    private DBManager dbManager;
    private Context mcontext;
    private ExecutorService executors = Executors.newCachedThreadPool();//线程池
    private static Model model = new Model();
    UserDao userDao;

    private Model() {
    }

    public static Model getInstance() {
        return model;
    }

    public void init(Context context) {
        mcontext = context;
        userDao = new UserDao(mcontext);
        //开启全局监听
        EventListener eventListener = new EventListener(mcontext);
    }

    //获取全局线程池对象
    public ExecutorService getGlobalThreadPool() {

        return executors;
    }

    public void loginSuccess(UserInfo account) {//登陆成功后处理
        if (account == null) {
            return;
        }
        if (dbManager != null) {
            dbManager.close();
        }
        dbManager = new DBManager(mcontext, account.getName());


    }

    public DBManager getDbManager() {
        return dbManager;
    }

    //获取用户账号数据库操作类对象
    public UserDao getUserDao() {
        return userDao;
    }
}
