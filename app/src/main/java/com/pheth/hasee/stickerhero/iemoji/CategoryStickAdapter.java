package com.pheth.hasee.stickerhero.iemoji;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.flurry.android.FlurryAgent;
import com.pheth.hasee.stickerhero.GreenDaoManager.DaoManager;
import com.pheth.hasee.stickerhero.MyApplication;
import com.pheth.hasee.stickerhero.R;
import com.pheth.hasee.stickerhero.greendao.DaoMaster;
import com.pheth.hasee.stickerhero.greendao.DaoSession;
import com.pheth.hasee.stickerhero.greendao.Favorite;
import com.pheth.hasee.stickerhero.greendao.FavoriteCategory;
import com.pheth.hasee.stickerhero.greendao.FavoriteCategoryDao;
import com.pheth.hasee.stickerhero.greendao.FavoriteDao;
import com.pheth.hasee.stickerhero.utils.Constants;
import com.pheth.hasee.stickerhero.utils.MyGreenDaoUtils;
import com.pheth.hasee.stickerhero.utils.StaticConstant;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import io.imoji.sdk.ApiTask;
import io.imoji.sdk.ImojiSDK;
import io.imoji.sdk.objects.Category;
import io.imoji.sdk.objects.CategoryFetchOptions;
import io.imoji.sdk.objects.Imoji;
import io.imoji.sdk.objects.RenderingOptions;
import io.imoji.sdk.response.CategoriesResponse;


/**
 * Created by hasee on 2016/8/2.
 */
public class CategoryStickAdapter extends BaseAdapter implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private ArrayList<Category> mList;
    private Context mContext;
    private float dpSizeSmall;
    private float dpSize62;
    private LayouController mLayoutController;

    private DaoManager daoManager;

    public CategoryStickAdapter(Context context, ArrayList<Category> imojis) {
        mContext = context;
        mList = imojis;
        dpSizeSmall = ScreenUtils.convertDpToPixel(30, mContext);
        dpSize62 = ScreenUtils.convertDpToPixel(65, mContext);
        daoManager = DaoManager.getManager();
        daoManager.initFavoriteDao();
    }


    public void setLayoutController(LayouController ml) {
        mLayoutController = ml;
    }

    @Override
    public int getCount() {
        return mList.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        if (position == mList.size()) {
            return null;
        } else {
            return mList.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyHolder myHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.category_imoji_single_item, parent, false);
            myHolder = new MyHolder(convertView);
        } else {
            myHolder = (MyHolder) convertView.getTag();
            myHolder.draweeView.setImageDrawable(null);
            myHolder.textView_category_title.setText("");
        }

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((int) dpSize62, (int) dpSize62);
        myHolder.draweeView.setLayoutParams(layoutParams);
        myHolder.textView_category_title.setLayoutParams(layoutParams);

        if (position == mList.size()) {
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams((int) dpSizeSmall, (int) dpSizeSmall);
            myHolder.draweeView.setLayoutParams(lp);
            myHolder.textView_category_title.setLayoutParams(lp);
            Drawable mDrawable = mContext.getResources().getDrawable(R.drawable.refresh_icon);
            myHolder.draweeView.setImageDrawable(mDrawable);
        } else {
            Imoji imoji = mList.get(position).getPreviewImoji();
            RenderingOptions options = IemojiUtil.getRenderOption(imoji, RenderingOptions.Size.Thumbnail);
            Uri uri = imoji.urlForRenderingOption(options);
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setUri(uri)
                    .setAutoPlayAnimations(true)
                    .build();
            myHolder.draweeView.setController(controller);
            myHolder.textView_category_title.setText("#" + mList.get(position).getTitle() + "#");
        }

        convertView.setTag(myHolder);
        return convertView;
    }

    private long lastClick = 0;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // this is the refresh button to refresh the trending view
        if (position == parent.getAdapter().getCount() - 1) {
            //三秒内才能点击
            if (System.currentTimeMillis() - lastClick > 5000) {
                lastClick = System.currentTimeMillis();
                refreshData();
                initAnimation(view);
                IemojiUtil.cleanCache();
            } else {
                Toast.makeText(mContext, R.string.toast_wait, Toast.LENGTH_SHORT).show();
            }
            return;
        }

        //get the single item's category id, and query the search for those category items
        Category category = (Category) parent.getAdapter().getItem(position);
        GridView gridView = mLayoutController.initFullEachCategoryGridView(category.getIdentifier());
        mLayoutController.displaySingleCatgoryGridview(gridView);

        mLayoutController.setCategoryTitle(category.getTitle());

        FlurryAgent.logEvent(StaticConstant.CATEGORY_CLICK_INTO_SUB);
    }

    // setting animation for the view
    private void initAnimation(final View view) {
        view.clearAnimation();

        RotateAnimation rotate = new RotateAnimation(0f, -360f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        // prevents View from restoring to original direction.
        rotate.setFillAfter(true);
        rotate.setDuration(1000);
        rotate.setInterpolator(new DecelerateInterpolator(3));
        view.startAnimation(rotate);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        Log.e("LongItemClick", position + "");

        final Category category = mList.get(position);
        final Imoji imoji = mList.get(position).getPreviewImoji();


        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Add to Favorite");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button

                FlurryAgent.logEvent(StaticConstant.ADD_TO_FAVORITE);

                Date currentDate = new Date();

                // get the render option for save thumbnail, animation, and no board
                RenderingOptions options = IemojiUtil.getRenderOption(imoji, RenderingOptions.Size.Thumbnail);
                Uri thumbail_uri = imoji.urlForRenderingOption(options);

                FavoriteCategory favoriteCategory = new FavoriteCategory(null,category.getTitle(),imoji.getStandardFullSizeUri(true).toString(),thumbail_uri.toString(),category.getIdentifier(),null,null,currentDate);
                boolean result = MyGreenDaoUtils.AddToFavoriteCategory(daoManager.getFavoriteCategoryDao(), favoriteCategory);
                if(result){
                    Log.e("favoriteCategoryDao","successful");
                    Intent intent = new Intent(Constants.UPDATE_FAVORITE_LIST);
                    intent.putExtra(Constants.UPDATE_FAVORITE_LIST,true);
                    mContext.sendBroadcast(intent);
                }else{
                    Log.e("favoriteCategoryDao","not - succeed");
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        AlertDialog dialog = builder.create();
        builder.create();
        dialog.show();

        return true;
    }

    class MyHolder {
        SimpleDraweeView draweeView;

        TextView textView_category_title;

        MyHolder(View v) {
            draweeView = (SimpleDraweeView) v.findViewById(R.id.iv_single_sticker);
            textView_category_title = (TextView) v.findViewById(R.id.tv_category_title);
        }
    }

    private void refreshData() {
        ImojiSDK.getInstance()
                .createSession(mContext.getApplicationContext())
                .getImojiCategories(new CategoryFetchOptions(Category.Classification.Trending))
                .executeAsyncTaskOnExecutor(new ApiTask.WrappedAsyncTask<CategoriesResponse>() {
                    @Override
                    protected void onPostExecute(CategoriesResponse imojisResponse) {
                        //Bind the results to an adapter of sorts


                        Log.e("Refreshdate", imojisResponse.getCategories().size() + "");
//                        if (categoryList == null)
//                            return;

                        mList.clear();
                        List<Category> tempList = imojisResponse.getCategories();
                        for (int i = 0; i < 21; i++) {
                            mList.add(tempList.remove(new Random().nextInt(tempList.size() - 20)));
                        }

                        if (this != null) {
                            CategoryStickAdapter.this.notifyDataSetChanged();
                        }
                    }
                }, MyApplication.getGlobelExector());
    }
}
