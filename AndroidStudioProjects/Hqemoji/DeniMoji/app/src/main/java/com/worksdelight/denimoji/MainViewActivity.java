package emoji.worksdelight.emoji;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import emoji.worksdelight.emoji.stickers.MarginDecoration;
import emoji.worksdelight.emoji.stickers.StickerAdapter;

/**
 * Created by worksdelight on 16/08/17.
 */

public class MainViewActivity extends Activity {
    ImageView recent_img, setting_img;

    private RecyclerView stickerView;
    FullViewAdapter stickerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view_layout);
        recent_img = (ImageView) findViewById(R.id.recent_img);
        setting_img = (ImageView) findViewById(R.id.setting_img);
        stickerView = (RecyclerView) findViewById(R.id.gif_view);
        stickerView.addItemDecoration(new MarginDecoration(this));
        stickerView.setHasFixedSize(true);
        stickerView.setLayoutManager(new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false));
        stickerAdapter = new FullViewAdapter(this, GlobalConstant.firstArrayImage);
        stickerView.setAdapter(stickerAdapter);
        recent_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainViewActivity.this, RecentActivity.class);
                startActivity(i);
            }
        });
        setting_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainViewActivity.this, SettingActivity.class);
                startActivity(i);
            }
        });
    }
}
