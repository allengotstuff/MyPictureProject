package com.pheth.hasee.stickerhero.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.pheth.hasee.stickerhero.fragments.CategoryFragment;
import com.pheth.hasee.stickerhero.fragments.FavoritImojiFragment;
import com.pheth.hasee.stickerhero.fragments.PageFragment;
import com.pheth.hasee.stickerhero.fragments.TrendingFragment;
import com.pheth.hasee.stickerhero.greendao.Favorite;

/**
 * Created by allengotstuff on 9/5/2016.
 */
public class FragmentPageAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"Category","Trending"};
    private Context context;

    public FragmentPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            CategoryFragment fragment = new CategoryFragment();
            return fragment;
        }

        if(position==1){
            TrendingFragment fragment = new TrendingFragment();
            return fragment;
        }

//        if(position==2){
//            FavoritImojiFragment favoritImojiFragment = new FavoritImojiFragment();
//            return  favoritImojiFragment;
//        }
        return PageFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }


//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//
//        PageFragment fragment = (PageFragment) super.instantiateItem(container,
//                position);
//
//        if(isNeedToUpdate)
//        {
//            if(fragment.getArguments().getInt(PageFragment.ARG_PAGE,999)==1)
//            {
//                FragmentTransaction ft = mFragmentManager.beginTransaction();
//                ft.remove(fragment);
//
//                fragment = PageFragment.newInstance(position);
//                //添加新fragment时必须用前面获得的tag，这点很重要
//                ft.add(container.getId(), fragment, "");
//                ft.attach(fragment);
//                ft.commit();
//
//                isNeedToUpdate = false;
//            }
//        }
//        return fragment;
//    }
}
