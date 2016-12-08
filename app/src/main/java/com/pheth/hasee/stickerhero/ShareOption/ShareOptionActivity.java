//package com.pheth.hasee.stickerhero.ShareOption;
//
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.Toast;
//
//import com.facebook.drawee.backends.pipeline.Fresco;
//import com.facebook.drawee.interfaces.DraweeController;
//import com.facebook.drawee.view.DraweeView;
//import com.flurry.android.FlurryAgent;
//import com.pheth.hasee.stickerhero.R;
//import com.pheth.hasee.stickerhero.iemoji.IemojiUtil;
//import com.pheth.hasee.stickerhero.utils.Constants;
//
//
//import java.util.HashMap;
//
//import io.imoji.sdk.objects.Imoji;
//
//
///**
// * Created by hasee on 2016/8/26.
// */
//public class ShareOptionActivity extends AppCompatActivity implements View.OnClickListener {
//
//    private Imoji imoji;
//    private DraweeView shareDrawee;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_share_option);
//        init();
//
//        if (getIntent() != null) {
//            imoji = getIntent().getParcelableExtra(Constants.SHAREE_IMOJI);
//
//            DraweeController controller = Fresco.newDraweeControllerBuilder()
//                    .setUri(imoji.getStandardThumbnailUri(true))
//                    .setAutoPlayAnimations(true)
//                    .build();
//            shareDrawee.setController(controller);
//        }
//    }
//
//    private void init() {
//        shareDrawee = (DraweeView) findViewById(R.id.iv_shared_sticker);
//        findViewById(R.id.dv_sms).setOnClickListener(this);
//        findViewById(R.id.dv_fb_messenger).setOnClickListener(this);
//        findViewById(R.id.dv_whatsapp).setOnClickListener(this);
//        findViewById(R.id.drawable_intragram).setOnClickListener(this);
//
//        findViewById(R.id.dv_facebook).setOnClickListener(this);
//        findViewById(R.id.dv_twitter).setOnClickListener(this);
//        findViewById(R.id.dv_snapchat).setOnClickListener(this);
//        findViewById(R.id.dv_more).setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//
//        if (imoji == null)
//            return;
//
//        String urlToString = imoji.getStandardThumbnailUri().toString();
//        HashMap<String, String> map = new HashMap<>();
//        switch (v.getId()) {
//            case R.id.dv_sms:
//                IemojiUtil.shareGif(getApplicationContext(), urlToString, Constants.SMS);
//                map.put("share_app",Constants.SMS_SHARE );
//                break;
//
//            case R.id.dv_fb_messenger:
//                IemojiUtil.shareSticerkFBm(ShareOptionActivity.this, urlToString);
//                map.put("share_app",Constants.MESSENGER_SHARE );
//                break;
//
//            case R.id.dv_whatsapp:
//                if (MobileUtil.isPackageInstalled(Constants.WHATSAPP, getApplicationContext())) {
//                    IemojiUtil.shareGif(getApplicationContext(), urlToString, Constants.WHATSAPP);
//                    map.put("share_app",Constants.WHATSAPP_SHARE );
//                } else {
//                    Toast.makeText(getBaseContext(), getResources().getString(R.string.whatsapp), Toast.LENGTH_SHORT).show();
//                }
//                break;
//
//            case R.id.drawable_intragram:
//                if (MobileUtil.isPackageInstalled(Constants.INSTAGRAM, getApplicationContext())) {
//                    IemojiUtil.shareGif(getApplicationContext(), urlToString, Constants.INSTAGRAM);
//                    map.put("share_app",Constants.INSTAGRAM_SHARE );
//                } else {
//                    Toast.makeText(getBaseContext(), getResources().getString(R.string.Instagram), Toast.LENGTH_SHORT).show();
//                }
//                break;
//
//            case R.id.dv_facebook:
//                if (MobileUtil.isPackageInstalled(Constants.FACEBOOK, getApplicationContext())) {
//                    IemojiUtil.shareGif(getApplicationContext(), urlToString, Constants.FACEBOOK);
//                    map.put("share_app",Constants.FACEBOOK_SHARE );
//                } else {
//                    Toast.makeText(getBaseContext(), getResources().getString(R.string.facebook), Toast.LENGTH_SHORT).show();
//                }
//                break;
//
//            case R.id.dv_twitter:
//                if (MobileUtil.isPackageInstalled(Constants.TWITTER, getApplicationContext())) {
//                    IemojiUtil.shareGif(getApplicationContext(), urlToString, Constants.TWITTER);
//                    map.put("share_app",Constants.TWITTER_SHARE );
//                } else {
//                    Toast.makeText(getBaseContext(), getResources().getString(R.string.twitter), Toast.LENGTH_SHORT).show();
//                }
//                break;
//
//            case R.id.dv_snapchat:
//                if (MobileUtil.isPackageInstalled(Constants.SNAPCHAT, getApplicationContext())) {
//                    IemojiUtil.shareGif(getApplicationContext(), urlToString, Constants.SNAPCHAT);
//                    map.put("share_app",Constants.SNAPCHAT_SHARE );
//                } else {
//                    Toast.makeText(getBaseContext(), getResources().getString(R.string.snapchat), Toast.LENGTH_SHORT).show();
//                }
//                break;
//
//            case R.id.dv_more:
//                IemojiUtil.shareGif(getApplicationContext(), urlToString, Constants.MORE);
//                map.put("share_app",Constants.MORE_SHARE );
//                break;
//        }
//        // 统计当前emoji package的点击
//        if(map!=null&&map.size()>0){
//            FlurryAgent.logEvent(Constants.IMOJI_SHARE, map);
//        }
//        finish();
//    }
//}
