package com.pheth.hasee.stickerhero.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.pheth.hasee.stickerhero.R;


/**
 * Created by allengotstuff on 10/30/2016.
 */
public abstract class BaseFragment extends Fragment {

    protected String actionName;
    protected ViewGroup mainView;

    abstract void clearReference();

    public abstract void updateFragment();

    abstract void initView();

    abstract void setActionName();



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_fragment, container, false);
        mainView = (ViewGroup)view;
        initView();
        setActionName();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clearReference();
    }

   public class UpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getBooleanExtra(actionName, false) && intent.getAction() == actionName) {
                updateFragment();
            }
        }
    }
}
