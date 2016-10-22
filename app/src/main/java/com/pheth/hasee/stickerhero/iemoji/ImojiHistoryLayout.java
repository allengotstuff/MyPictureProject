package com.pheth.hasee.stickerhero.iemoji;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;

import com.pheth.hasee.stickerhero.GreenDaoManager.DaoManager;
import com.pheth.hasee.stickerhero.MyApplication;
import com.pheth.hasee.stickerhero.R;
import com.pheth.hasee.stickerhero.greendao.DaoMaster;
import com.pheth.hasee.stickerhero.greendao.DaoSession;
import com.pheth.hasee.stickerhero.greendao.History;
import com.pheth.hasee.stickerhero.greendao.HistoryDao;
import com.pheth.hasee.stickerhero.utils.Constants;

import java.util.ArrayList;

/**
 * Created by allengotstuff on 9/13/2016.
 */
public class ImojiHistoryLayout extends FrameLayout {

    private static final String TAG = ImojiHistoryLayout.class.getName();

    private GridView gridView;
    private HistoryImojiAdapter myAdapter;
    private Context mContext;
    private ArrayList<History> mHistoryList;
    private FrameLayout main_container;

    private DaoManager daoManager;

    public ImojiHistoryLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initdataBase();
        initView(context);
    }

    public ImojiHistoryLayout(Context context) {
        super(context);
        initdataBase();
        initView(context);
    }

    private void initView(Context context){
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View rootView = inflater.inflate(R.layout.layout_imoji_category, this);
        main_container = (FrameLayout)rootView.findViewById(R.id.fl_category_container);

        gridView = (GridView) View.inflate(getContext(), R.layout.foto_gif_gridview, null);
        myAdapter = new HistoryImojiAdapter(mContext, mHistoryList);
        gridView.setAdapter(myAdapter);
        gridView.setOnItemClickListener(myAdapter);
        main_container.addView(gridView);
    }

    private void initdataBase()
    {
        daoManager = DaoManager.getManager();
        daoManager.initHistoryDao();

        if(mHistoryList==null){
            mHistoryList = new ArrayList<History>();
            mHistoryList.addAll(daoManager.getHistoryDao().loadAll());
            Log.e("History",mHistoryList.size()+"");
        }
    }

    public void notifyDataChange() {
        if(mHistoryList==null)
            return;

        mHistoryList.clear();
        mHistoryList.addAll(daoManager.getHistoryDao().loadAll());
        myAdapter.notifyDataSetChanged();
    }

    private UpdateReceiver updateReceiver;
    public void registerReceiver(){
        if(updateReceiver==null){
            updateReceiver = new UpdateReceiver();
        }
        IntentFilter intentFilter = new IntentFilter(Constants.UPDATE_HISTORY_LIST);
        mContext.registerReceiver(updateReceiver, intentFilter);
    }

    public void destoryView()
    {
        if(updateReceiver!=null){
            mContext.unregisterReceiver(updateReceiver);
        }

        if(gridView!=null){
            gridView.setAdapter(null);
            myAdapter = null;
            gridView = null;
            main_container.removeAllViews();
        }
    }

    public class UpdateReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getBooleanExtra(Constants.UPDATE_HISTORY_LIST, false) && intent.getAction() == Constants.UPDATE_HISTORY_LIST) {
                //update the favorite list adapter
                notifyDataChange();

                Log.e(TAG,"onreceiver");
            }
        }
    }
}
