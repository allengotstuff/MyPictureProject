package com.pheth.hasee.stickerhero.ClickHandler;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;
import com.pheth.hasee.stickerhero.Animation.AdapterSelector;
import com.pheth.hasee.stickerhero.Animation.FavoriteAdapterAnimation;
import com.pheth.hasee.stickerhero.GreenDaoManager.DaoManager;
import com.pheth.hasee.stickerhero.R;
import com.pheth.hasee.stickerhero.fragments.FavoritImojiFragment;
import com.pheth.hasee.stickerhero.greendao.Favorite;
import com.pheth.hasee.stickerhero.greendao.FavoriteDao;
import com.pheth.hasee.stickerhero.utils.DataCollectionConstant;
import com.pheth.hasee.stickerhero.utils.MyGreenDaoUtils;

/**
 * Created by allengotstuff on 12/20/2016.
 */
public class FavoriteImojiClickHandler extends TrendingClickHandler {

    private static final String TAG = "FavoriteClickHandler";
    private AdapterSelector animationHandler;

    public FavoriteImojiClickHandler(DaoManager daoManager, Context context) {
        super(daoManager, context);
    }

    public void setAnimationHandler(AdapterSelector animationHandler){
        this.animationHandler = animationHandler;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            //delete favorite collecton.
            case R.id.iamgeview_1:
                deleteFavorite();
                return;
        }
        super.onClick(v);
    }

    private void deleteFavorite(){
        Log.e(TAG, "deFavorite");

        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setTitle("Delete Favorite");
        alertDialog.setMessage("Are you sure you want to delete this sticker");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteStickerFromDB();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancle",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void deleteStickerFromDB(){
        daoManager.initFavoriteIndividualDao();
        FavoriteDao favoriteDao = daoManager.getFavoriteIndividualDao();

        Favorite favorite = mapFavoriteItem();

        boolean isSuccess = MyGreenDaoUtils.deleteSingleImojiFavorite(favoriteDao, favorite);

        if (isSuccess) {
            Toast.makeText(mContext, "Delete Sticker Successfully" , Toast.LENGTH_SHORT).show();

            // restore the current referenced holder animation property
            FavoriteAdapterAnimation adapterAnimation = (FavoriteAdapterAnimation)animationHandler;
            adapterAnimation.deleteFavoriteViewHolderRestore();

            // update the favoriteImojiFragment
            Intent intent = new Intent(FavoritImojiFragment.TAG);
            mContext.sendBroadcast(intent);
        } else {
            Toast.makeText(mContext, "Error: Unable to Delete Sticker", Toast.LENGTH_SHORT).show();
        }

        FlurryAgent.logEvent(DataCollectionConstant.DELETE_COLLECTED_STICKER);
    }
}
