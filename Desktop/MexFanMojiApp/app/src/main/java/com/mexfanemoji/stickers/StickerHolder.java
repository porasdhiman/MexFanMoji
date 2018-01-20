package com.mexfanemoji.stickers;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.mexfanemoji.R;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by mist on 12.12.16.
 */

public class StickerHolder  extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public GifImageView gif;

    public StickerHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.imageItem);
        gif = (GifImageView) itemView.findViewById(R.id.img);
    }
}
