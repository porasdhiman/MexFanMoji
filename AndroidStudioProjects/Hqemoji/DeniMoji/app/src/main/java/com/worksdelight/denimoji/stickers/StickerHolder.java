package com.worksdelight.denimoji.stickers;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;


import com.worksdelight.denimoji.R;

import pl.droidsonroids.gif.GifImageView;


public class StickerHolder  extends RecyclerView.ViewHolder {
    public ImageView imageView,lock;
    public GifImageView gif;

    public StickerHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.imageItem);
        lock = (ImageView) itemView.findViewById(R.id.lock);
        gif = (GifImageView) itemView.findViewById(R.id.img);
    }
}
