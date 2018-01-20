package com.mexfanemoji;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.kobakei.ratethisapp.RateThisApp;
import com.mexfanemoji.stickers.MarginDecoration;
import com.rampo.updatechecker.UpdateChecker;


/**
 * Created by worksdelight on 16/08/17.
 */

public class MainViewActivity extends Activity {
    ImageView recent_img, setting_img;

    private RecyclerView stickerView;
    FullViewAdapter stickerAdapter;
    SharedPreferences sp;
    int[] imageType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view_layout);

        UpdateChecker checker = new UpdateChecker(this); // If you are in a Activity or a FragmentActivity
        checker.setSuccessfulChecksRequired(5);
        checker.start();
        RateThisApp.init(new RateThisApp.Config(3, 5));

        // Set callback (optional)

        // Monitor launch times and interval from installation



        setting_img = (ImageView) findViewById(R.id.setting_img);
        stickerView = (RecyclerView) findViewById(R.id.gif_view);
        stickerView.addItemDecoration(new MarginDecoration(this));
        stickerView.setHasFixedSize(true);

        stickerView.setLayoutManager(new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false));

        stickerAdapter = new FullViewAdapter(this, GlobalConstants.completeArray);
        stickerView.setAdapter(stickerAdapter);
       // setting_img.setVisibility(View.GONE);
        setting_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainViewActivity.this,SettingActivity.class);
                startActivity(i);
            }
        });
        setting_img.setColorFilter(setting_img.getContext().getResources().getColor(R.color.app_color), PorterDuff.Mode.SRC_ATOP);
        //recent_img.setColorFilter(setting_img.getContext().getResources().getColor(R.color.app_color), PorterDuff.Mode.SRC_ATOP);
        RateThisApp.onStart(this);
        // Show a dialog if criteria is satisfied
        RateThisApp.showRateDialogIfNeeded(this);
    }


}
