package com.mexfanemoji;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;


/**
 * Created by worksdelight on 17/08/17.
 */

public class SettingActivity extends Activity {
    RelativeLayout install_layout,rate_layout,share_layout,set_layout,terms_layout,privacy_layout,contact_layout,twitter_layout;
    ImageView cancel_img;
    Dialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        install_layout=(RelativeLayout) findViewById(R.id.install_layout);
       // set_layout=(RelativeLayout) findViewById(R.id.set_layout);
        rate_layout=(RelativeLayout) findViewById(R.id.rate_layout);
        share_layout=(RelativeLayout) findViewById(R.id.share_layout);
        terms_layout=(RelativeLayout) findViewById(R.id.terms_layout);
        privacy_layout=(RelativeLayout) findViewById(R.id.privacy_layout);
        contact_layout=(RelativeLayout) findViewById(R.id.contact_layout);
        twitter_layout=(RelativeLayout) findViewById(R.id.twitter_layout);
        cancel_img=(ImageView)findViewById(R.id.cancel_img);
        install_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (KeyboardService.rokomojiEnabled(SettingActivity.this)) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showInputMethodPicker();
                } else {
                    startActivity(new Intent("android.settings.INPUT_METHOD_SETTINGS"));
                }
            }
        });
        cancel_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        cancel_img.setColorFilter(cancel_img.getContext().getResources().getColor(R.color.app_color), PorterDuff.Mode.SRC_ATOP);

        rate_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                //Try Google play
                intent.setData(Uri.parse("market://details?id="+getPackageName()));
                if (!MyStartActivity(intent)) {
                    //Market (Google play) app seems not installed, let's try to open a webbrowser
                    intent.setData(Uri.parse("https://play.google.com/store/apps/developer?id="+getPackageName()));
                    if (!MyStartActivity(intent)) {
                        //Well if this also fails, we have run out of options, inform the user.
                       // Toast.makeText(this, "Could not open Android market, please install the market app.", Toast.LENGTH_SHORT).show();
                    }
                }

            }


        });
        share_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "MexFanMoji app \n" + "https://play.google.com/store/apps/details?id="+getPackageName()+"&hl=ens");
                startActivity(shareIntent);
            }
        });
      /*  set_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
        privacy_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.h-emoji.com/privacy-policy/"));
                startActivity(browserIntent);
            }
        });
        terms_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.h-emoji.com/terms-of-service/"));
                startActivity(browserIntent);
            }
        });
        contact_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mailto = "mailto:hello@h-emoji.com?subject=Contact us&body=" + Uri.encode("");

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse(mailto));
                startActivity(Intent.createChooser(emailIntent, "Send Email"));
            }
        });
        twitter_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/hemojiapp"));
                startActivity(browserIntent);
                dialog.dismiss();
            }
        });
    }
    private boolean MyStartActivity(Intent aIntent) {
        try
        {
            startActivity(aIntent);
            return true;
        }
        catch (ActivityNotFoundException e)
        {
            return false;
        }
    }
   /* public void dailog() {
        dialog = new Dialog(SettingActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.follow_us_dialog);
        RelativeLayout facebook_layout=(RelativeLayout)dialog.findViewById(R.id.facebook_layout);
        RelativeLayout instagram_layout=(RelativeLayout)dialog.findViewById(R.id.instagram_layout);

        RelativeLayout twitter_layout=(RelativeLayout)dialog.findViewById(R.id.twitter_layout);

        facebook_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/H-Emoji-1814587665520025/"));
                startActivity(browserIntent);
                dialog.dismiss();
            }
        });
        instagram_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/h_emojiapp/"));
                startActivity(browserIntent);
                dialog.dismiss();
            }
        });
        twitter_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/hemojiapp"));
                startActivity(browserIntent);
                dialog.dismiss();
            }
        });
        dialog.show();



    }*/
}
