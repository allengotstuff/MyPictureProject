package com.pheth.hasee.stickerhero.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.pheth.hasee.stickerhero.fragments.PageFragment;

/**
 * Created by allengotstuff on 9/5/2016.
 */
public class FragmentPageAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"Category","Favorite","History"};
    private Context context;

    public FragmentPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
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
