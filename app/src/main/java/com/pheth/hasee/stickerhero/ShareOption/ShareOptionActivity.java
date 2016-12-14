package com.pheth.hasee.stickerhero.ShareOption;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.flurry.android.FlurryAgent;
import com.pheth.hasee.stickerhero.BaseData.Data.BaseData;
import com.pheth.hasee.stickerhero.BaseData.Data.DataContainer;
import com.pheth.hasee.stickerhero.R;
import com.pheth.hasee.stickerhero.iemoji.IemojiUtil;
import com.pheth.hasee.stickerhero.utils.CommonUtils;
import com.pheth.hasee.stickerhero.utils.Constants;


import java.util.HashMap;



/**
 * Created by hasee on 2016/8/26.
 */
public class ShareOptionActivity extends AppCompatActivity implements View.OnClickListener {

    private DataContainer imoji;
    private DraweeView shareDrawee;
    public static final String PASS_BASE_DATA = "pass_base_data";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_option);
        init();

        if (getIntent() != null) {
            imoji = getIntent().getParcelableExtra(PASS_BASE_DATA);

            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setUri(imoji.getOnlineThumbUrl())
                    .setAutoPlayAnimations(true)
                    .build();
            shareDrawee.setController(controller);
        }
    }

    private void init() {
        shareDrawee = (DraweeView) findViewById(R.id.iv_shared_sticker);
        findViewById(R.id.dv_sms).setOnClickListener(this);
        findViewById(R.id.dv_fb_messenger).setOnClickListener(this);
        findViewById(R.id.dv_whatsapp).setOnClickListener(this);
        findViewById(R.id.drawable_intragram).setOnClickListener(this);

        findViewById(R.id.dv_facebook).setOnClickListener(this);
        findViewById(R.id.dv_twitter).setOnClickListener(this);
        findViewById(R.id.dv_snapchat).setOnClickListener(this);
        findViewById(R.id.dv_more).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (imoji == null)
            return;

        String urlToString = imoji.getOnlineFullUrl();

        HashMap<String, String> map = new HashMap<>();
        switch (v.getId()) {

            //TODO: need to implementing share on default sms
            case R.id.dv_sms:
                IemojiUtil.shareGif(getApplicationContext(), urlToString, Constants.SMS, null, null);
                map.put("share_app", Constants.SMS);
                break;

            case R.id.dv_fb_messenger:
                if (CommonUtils.isPackageExist(Constants.WHATSAPP, getApplicationContext())) {
                    IemojiUtil.shareGif(getApplicationContext(), urlToString, Constants.MESSENGER_FB,null,null);
                    map.put("share_app", Constants.MESSENGER_FB);
                } else {
                    Toast.makeText(getBaseContext(), "you don't have"+ getResources().getString(R.string.facebook_messenger), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.dv_whatsapp:
                if (CommonUtils.isPackageExist(Constants.WHATSAPP, getApplicationContext())) {
                    IemojiUtil.shareGif(getApplicationContext(), urlToString, Constants.WHATSAPP, null, null);
                    map.put("share_app", Constants.WHATSAPP);
                } else {
                    Toast.makeText(getBaseContext(),"you don't have"+ getResources().getString(R.string.whatsapp_share), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.drawable_intragram:
                if (CommonUtils.isPackageExist(Constants.INSTAGRAM, getApplicationContext())) {
                    IemojiUtil.shareGif(getApplicationContext(), urlToString, Constants.INSTAGRAM,null,null);
                    map.put("share_app", Constants.INSTAGRAM);
                } else {
                    Toast.makeText(getBaseContext(),"you don't have"+ getResources().getString(R.string.instagram_share), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.dv_facebook:
                if (CommonUtils.isPackageExist(Constants.FACEBOOK, getApplicationContext())) {
                    IemojiUtil.shareGif(getApplicationContext(), urlToString, Constants.FACEBOOK,null,null);
                    map.put("share_app", Constants.FACEBOOK);
                } else {
                    Toast.makeText(getBaseContext(),"you don't have"+ getResources().getString(R.string.facebook), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.dv_twitter:
                if (CommonUtils.isPackageExist(Constants.TWITTER, getApplicationContext())) {
                    IemojiUtil.shareGif(getApplicationContext(), urlToString, Constants.TWITTER,null,null);
                    map.put("share_app", Constants.TWITTER);
                } else {
                    Toast.makeText(getBaseContext(), "you don't have"+getResources().getString(R.string.twitter), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.dv_snapchat:
                if (CommonUtils.isPackageExist(Constants.SNAPCHAT, getApplicationContext())) {
                    IemojiUtil.shareGif(getApplicationContext(), urlToString, Constants.SNAPCHAT,null,null);
                    map.put("share_app", Constants.SNAPCHAT);
                } else {
                    Toast.makeText(getBaseContext(),"you don't have"+ getResources().getString(R.string.snapchat), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.dv_more:
                IemojiUtil.shareGif(getApplicationContext(), urlToString, null,null,null);
                map.put("share_app", Constants.MORE);
                break;
        }
//        // 统计当前emoji package的点击
//        if (map != null && map.size() > 0) {
//            FlurryAgent.logEvent(Constants.IMOJI_SHARE, map);
//        }
//        finish();
    }
}
