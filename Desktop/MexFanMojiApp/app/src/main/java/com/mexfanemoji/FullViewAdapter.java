package com.mexfanemoji;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mexfanemoji.stickers.StickerHolder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;

/**
 * Created by worksdelight on 16/08/17.
 */

public class FullViewAdapter extends RecyclerView.Adapter<StickerHolder> {
    private final String TAG = "StickerAdapter";

    private Context c;
    private List<String> listImg = new ArrayList<String>();

    int img[];

    int imageType;
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    File f;
    Bitmap resizedbitmap,bm;
    public FullViewAdapter(Context c, int img[]) {
        this.c = c;
        this.img = img;


    }

    @Override
    public StickerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.full_view_item, parent, false);
        return new StickerHolder(view);
    }

    @Override
    public void onBindViewHolder(final StickerHolder holder, final int position) {

        try {


            TypedValue value = new TypedValue();
            c.getResources().getValue(img[position], value, true);
            String resname = value.string.toString();

            if(resname.contains(".gif")){
                holder.imageView.setVisibility(View.GONE);
                holder.gif.setVisibility(View.VISIBLE);
                try {
                    GifDrawable gifFromResource = new GifDrawable(c.getResources(), img[position] );
                    holder.gif.setImageDrawable(gifFromResource);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                Drawable d = ContextCompat.getDrawable(c.getApplicationContext(), img[position]);
                Bitmap bitmap = ((BitmapDrawable)resize(d)).getBitmap();
                holder.imageView.setVisibility(View.VISIBLE);
                holder.gif.setVisibility(View.GONE);
                holder.imageView.setImageResource(img[position]);

            }
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                   Intent i=new Intent(c,ShareActivity.class);
                    i.putExtra("pos",String.valueOf(position));
                    i.putExtra("image_type","png");

                    c.startActivity(i);
                }
            });
            holder.gif.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i=new Intent(c,ShareActivity.class);
                    i.putExtra("pos",String.valueOf(position));
                    i.putExtra("image_type","gif");

                    c.startActivity(i);
                }
            });
            // holder.gif.setVisibility(View.GONE);
            // }

        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    @Override
    public int getItemCount() {
        return img.length;
    }


    private Drawable resize(Drawable image) {

        Bitmap b = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 200, 140, false);
        return new BitmapDrawable(c.getResources(), bitmapResized);
    }


    public void GifFileMethod(int postion) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File file = new File(extStorageDirectory, "Trumoji.gif");



        try {
            byte[] readData = new byte[1024 * 500];


            InputStream fis = c.getResources().openRawResource(img[postion]);

            FileOutputStream fos = new FileOutputStream(file);
            int i = fis.read(readData);

            while (i != -1) {
                fos.write(readData, 0, i);
                i = fis.read(readData);
            }

            fos.close();
        } catch (IOException io) {
        }

        f = file;
    }

}

