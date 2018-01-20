package com.worksdelight.denimoji;

import android.app.Application;

/**
 * Created by worksdelight on 27/07/17.
 */

public class Global extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FontsOverride.setDefaultFont(this, "DEFAULT", "fonts/mixstitch.ttf");

        /*FontsOverride.setDefaultFont(this, "MONOSPACE", "MyFontAsset2.ttf");
        FontsOverride.setDefaultFont(this, "SERIF", "MyFontAsset3.ttf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "MyFontAsset4.ttf");*/
    }
}
