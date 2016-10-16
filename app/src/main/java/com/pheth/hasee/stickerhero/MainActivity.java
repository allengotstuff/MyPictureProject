package com.pheth.hasee.stickerhero;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;
import com.pheth.hasee.stickerhero.Adapter.FragmentPageAdapter;
import com.pheth.hasee.stickerhero.headchat.ChatHeadService;
import com.pheth.hasee.stickerhero.headchat.Utils;
import com.pheth.hasee.stickerhero.utils.CommonUtils;
import com.pheth.hasee.stickerhero.utils.StaticConstant;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    private RelativeLayout main_container;
    private Spinner socialSpinner;
    private ImageView imageView_HeroMode;

    public static int OVERLAY_PERMISSION_REQ_CODE_CHATHEAD = 1234;

    private TabLayout tabLayout;
    private ViewPager myViewPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        socialSpinner = (Spinner) findViewById(R.id.spinner);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        myViewPage = (ViewPager) findViewById(R.id.viewpager);

        imageView_HeroMode = (ImageView) findViewById(R.id.iv_hero_mode);
        imageView_HeroMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.canDrawOverlays(MainActivity.this)) {
                    Intent intent = new Intent(MainActivity.this, ChatHeadService.class);
                    intent.putExtra(Utils.START_HEADCHAT, true);
                    startService(intent);
                } else {
                    requestPermission(OVERLAY_PERMISSION_REQ_CODE_CHATHEAD);
                }

                FlurryAgent.logEvent(StaticConstant.OPEN_HERO_MODE);
            }
        });


        FragmentPageAdapter myAdapter = new FragmentPageAdapter(getSupportFragmentManager(), this);
        myViewPage.setAdapter(myAdapter);
        myViewPage.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(myViewPage);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        setupSpinner();
    }


    private void setupSpinner() {

        SpinnerAdapter adapter = new SpinnerAdapter(getBaseContext());
        socialSpinner.setAdapter(adapter);
        socialSpinner.setOnItemSelectedListener(adapter);

        //set the default display icon on top of the spinner
        String[] socialPackage = getResources().getStringArray(R.array.social_package_id);
        for (int i = 0; i < socialPackage.length; i++) {
            if (MyApplication.sharedPackage.equals(socialPackage[i])) {
                socialSpinner.setSelection(i);
                break;
            }
        }


    }

    private class SpinnerAdapter extends BaseAdapter implements AdapterView.OnItemSelectedListener {

        private Context mContext;
        private LayoutInflater inflater;
        private ArrayList<IconHolder> mList;

        private String[] socialArray = getResources().getStringArray(R.array.social_package_title);
        private int[] socialIconArray = {R.drawable.whatsapp_icon, R.drawable.messenger_icon};

        private boolean firstTime = true;

        public SpinnerAdapter(Context context) {
            mContext = context;
            mList = new ArrayList<>(socialArray.length);
            for (int i = 0; i < socialArray.length; i++) {

//                if (CommonUtils.isPackageExist(context, socialArray[i])) {
                IconHolder iconHolder = new IconHolder(socialArray[i], socialIconArray[i]);
                mList.add(iconHolder);
//                }
            }
            inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = inflater.inflate(R.layout.single_spinner, parent, false);
            TextView title = (TextView) convertView.findViewById(R.id.tv_title);
            ImageView icon = (ImageView) convertView.findViewById(R.id.iv_icon);

            IconHolder iconHolder = mList.get(position);
            title.setText(iconHolder.getTitle());
            icon.setImageResource(iconHolder.getIcon());
            return convertView;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//            Log.e("onItemSelected",position+"");
            IconHolder iconHolder = mList.get(position);
            CommonUtils.saveSocialSetting(mContext, iconHolder.getTitle());

            if (!firstTime) {
                Toast.makeText(getBaseContext(), "you have swith to " + iconHolder.getTitle() + " shareing", Toast.LENGTH_SHORT).show();

                HashMap<String, String> social_platfor = new HashMap<>();
                social_platfor.put("Social_Platform", iconHolder.getTitle());
                FlurryAgent.logEvent(StaticConstant.SELECT_SOCIAL_PLATFORM, social_platfor);
            }
            firstTime = false;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class IconHolder {
        private String title;
        private int icon;

        public IconHolder(String title, int icon) {
            this.title = title;
            this.icon = icon;
        }

        public int getIcon() {
            return icon;
        }

        public String getTitle() {
            return title;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void requestPermission(int requestCode) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, requestCode);
    }
}
