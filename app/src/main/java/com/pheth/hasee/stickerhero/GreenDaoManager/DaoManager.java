package com.pheth.hasee.stickerhero.GreenDaoManager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.pheth.hasee.stickerhero.greendao.DaoMaster;
import com.pheth.hasee.stickerhero.greendao.DaoSession;
import com.pheth.hasee.stickerhero.greendao.FavoriteCategoryDao;
import com.pheth.hasee.stickerhero.greendao.HistoryDao;

/**
 * Created by allengotstuff on 10/22/2016.
 */
public class DaoManager {

    public static DaoManager manager;

    private DaoMaster daoMaster;
    private DaoSession daoSessionHistory;
    private DaoSession daoSessionFavorite;


    private HistoryDao historyDao;
    private FavoriteCategoryDao favoriteCategoryDao;

    private DaoManager(){
    }

    public static DaoManager getManager(){
        if (manager==null){
            manager = new DaoManager();
        }
        return manager;
    }

    public void init(Context context){
        //初始化数据库·
        try {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "stickerhero-db", null);
            SQLiteDatabase database = helper.getWritableDatabase();
            this.daoMaster = new DaoMaster(database);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initHistoryDao(){
        if (daoMaster != null) {
            daoSessionHistory = daoMaster.newSession();
            historyDao = daoSessionHistory.getHistoryDao();
        }
    }

    public HistoryDao getHistoryDao()
    {
        return historyDao;
    }

    public void initFavoriteDao(){
        if (daoMaster != null) {
            daoSessionFavorite = daoMaster.newSession();
            favoriteCategoryDao = daoSessionFavorite.getFavoriteCategoryDao();
        }
    }

    public FavoriteCategoryDao getFavoriteCategoryDao(){
        return favoriteCategoryDao;
    }
}
