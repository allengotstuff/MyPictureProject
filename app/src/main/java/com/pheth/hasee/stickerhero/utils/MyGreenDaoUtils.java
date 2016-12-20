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


    /**
     * adding individual imoji to favorite list;
     * @param favoriteDao
     * @param mfavorite
     * @return
     */
    public static boolean addSingleImojiFavorite(FavoriteDao favoriteDao, Favorite mfavorite){
        if (favoriteDao == null || mfavorite==null )
            return false;
        QueryBuilder qb = favoriteDao.queryBuilder();
        Object result = qb.where(FavoriteDao.Properties.Identifier.eq(mfavorite.getIdentifier())).unique();


        if(result==null)
        {
            favoriteDao.insert(mfavorite);
            return true;
        }else
        {
            return false;
        }
    }

    /**
     *  delete the favorite imoji from favorite list.
     * @param favoriteDao
     * @param mfavorite
     * @return
     */
    public static boolean deleteSingleImojiFavorite(FavoriteDao favoriteDao, Favorite mfavorite){
        if (favoriteDao == null || mfavorite==null )
            return false;

        QueryBuilder qb = favoriteDao.queryBuilder();
        Object result = qb.where(FavoriteDao.Properties.Identifier.eq(mfavorite.getIdentifier())).unique();


        if(result==null)
        {
            return false;
        }else
        {
            favoriteDao.delete((Favorite)result);
            return true;
        }
    }


}
