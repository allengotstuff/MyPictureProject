package com.pheth.hasee.stickerhero.utils;

import com.pheth.hasee.stickerhero.greendao.Favorite;
import com.pheth.hasee.stickerhero.greendao.FavoriteCategory;
import com.pheth.hasee.stickerhero.greendao.FavoriteCategoryDao;
import com.pheth.hasee.stickerhero.greendao.FavoriteDao;
import com.pheth.hasee.stickerhero.greendao.History;
import com.pheth.hasee.stickerhero.greendao.HistoryDao;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by allengotstuff on 8/30/2016.
 */
public class MyGreenDaoUtils {

    /**
     *
     * @param favoriteCategoryDao
     * @param mFavoriteCategory
     * @return if no duplicate result, insert the row and return true. If there is duplicate result in db, return false instead.
     */
    public static boolean AddToFavoriteCategory(FavoriteCategoryDao favoriteCategoryDao, FavoriteCategory mFavoriteCategory) {
        if (favoriteCategoryDao == null || mFavoriteCategory==null )
            return false;

        QueryBuilder qb = favoriteCategoryDao.queryBuilder();
        Object result = qb.where(FavoriteCategoryDao.Properties.Identifier.eq(mFavoriteCategory.getIdentifier())).unique();

        if(result==null)
        {
            favoriteCategoryDao.insert(mFavoriteCategory);
            return true;
        }else
        {
            return false;
        }
    }

    public static boolean deleteFavoriteCategory(FavoriteCategoryDao favoriteCategoryDao, FavoriteCategory favoriteCategory){
        if (favoriteCategoryDao == null || favoriteCategoryDao==null )
            return false;

        QueryBuilder qb = favoriteCategoryDao.queryBuilder();
        Object result = qb.where(FavoriteCategoryDao.Properties.Identifier.eq(favoriteCategory.getIdentifier())).unique();

        if(result==null)
        {
            //no result in the list
            return false;
        }else
        {
            //delete successfully.
            favoriteCategoryDao.delete((FavoriteCategory)result);
            return true;
        }
    }


    /**
     *
     * @param historyDao
     * @param mHistory
     * @return if no duplicate result, insert the row and return true. If there is duplicate result in db, return false instead.
     */
    public static boolean AddToHistory(HistoryDao historyDao, History mHistory) {
        if (historyDao == null || mHistory==null )
            return false;

        QueryBuilder qb = historyDao.queryBuilder();
        Object result = qb.where(HistoryDao.Properties.Identifier.eq(mHistory.getIdentifier())).unique();

        if(result==null)
        {
            historyDao.insert(mHistory);
            return true;
        }else
        {
            return false;
        }
    }

//    public static boolean updateHistory(HistoryDao historyDao, String identifier,String local_url){
//        if (historyDao == null )
//            return false;
//
//        QueryBuilder qb = historyDao.queryBuilder();
//        Object result = qb.where(HistoryDao.Properties.Identifier.eq(identifier)).unique();
//
//        if(result==null){
//            return false;
//        }else{
//            History history = (History)result;
//            history.setUrl_send_local(local_url);
//            historyDao.update(history);
//            return true;
//        }
//    }


}
