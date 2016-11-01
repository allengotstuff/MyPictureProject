package com.pheth.hasee.stickerhero.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
    private UpdateReceiver updateReceiver;

    abstract void clearReference();

    public abstract void updateFragment();


    abstract void setActionName();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionName();
        updateReceiver = new UpdateReceiver();
        IntentFilter filer = new IntentFilter();
        filer.addAction(actionName);
        getContext().registerReceiver(updateReceiver, filer);
    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.page_fragment, container, false);
//        mainView = (ViewGroup)view.findViewById(R.id.swipe_container);
//        return view;
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clearReference();

        if (updateReceiver != null)
            getContext().unregisterReceiver(updateReceiver);
    }

    public class UpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction()== actionName) {
                updateFragment();
            }
        }
    }
}
