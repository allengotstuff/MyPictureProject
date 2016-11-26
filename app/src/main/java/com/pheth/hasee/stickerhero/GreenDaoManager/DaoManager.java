package com.pheth.hasee.stickerhero.GreenDaoManager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.pheth.hasee.stickerhero.greendao.DaoMaster;
import com.pheth.hasee.stickerhero.greendao.DaoSession;
import com.pheth.hasee.stickerhero.greendao.Favorite;
import com.pheth.hasee.stickerhero.greendao.FavoriteCategoryDao;
import com.pheth.hasee.stickerhero.greendao.FavoriteDao;
import com.pheth.hasee.stickerhero.greendao.HistoryDao;

/**
 * Created by allengotstuff on 10/22/2016.
 */
public class DaoManager {

    public static DaoManager manager;

    private DaoMaster daoMaster;
    private DaoSession daoSessionHistory;
    private DaoSession daoSessionFavoriteCategory;
    private DaoSession daoSessionFavoriteIndividual;


    private HistoryDao historyDao;
    private FavoriteCategoryDao favoriteCategoryDao;
    private FavoriteDao favoriteDao;

    private DaoManager(){
    }

    public static DaoManager getManager(){

        if (manager==null){
            synchronized (DaoManager.class) {
                if(manager==null) {
                    manager = new DaoManager();
                }
            }
        }
        return manager;
    }

    /**
     * This is a application scope, should only use once in application scope
     * @param context
     */
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


    /**
     * should call this every time client try to make a operation with db,
     */
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

    public void initFavoriteCategoryDao(){
        if (daoMaster != null) {
            daoSessionFavoriteCategory = daoMaster.newSession();
            favoriteCategoryDao = daoSessionFavoriteCategory.getFavoriteCategoryDao();
        }
    }

    public FavoriteCategoryDao getFavoriteCategoryDao(){
        return favoriteCategoryDao;
    }


    public void initFavoriteIndividualDao(){
        if (daoMaster != null) {
            daoSessionFavoriteIndividual = daoMaster.newSession();
            favoriteDao = daoSessionFavoriteIndividual.getFavoriteDao();
        }
    }

    public FavoriteDao getFavoriteIndividualDao(){
        return favoriteDao;
    }
}
