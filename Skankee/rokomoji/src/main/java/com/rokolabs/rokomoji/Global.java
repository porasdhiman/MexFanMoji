package com.rokolabs.rokomoji;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by worksdelight on 28/07/17.
 */

public class Global extends Application {
    public ArrayList<String> getListImages() {
        return listImages;
    }

    public void setListImages(ArrayList<String> listImages) {
        this.listImages = listImages;
    }

    ArrayList<String> listImages = new ArrayList<String>();
}
