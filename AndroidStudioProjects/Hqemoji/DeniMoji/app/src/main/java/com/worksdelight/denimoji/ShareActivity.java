package com.worksdelight.denimoji;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import java.io.ByteArrayOutputStream;

/**
 * Created by worksdelight on 06/09/17.
 */

public class ShareActivity extends Activity {
    RelativeLayout image_relative_layout;
    Button share_btn;
    ImageView selected_img;
    Bitmap resizedbitmap,bm,bitmap;
    byte[] b;
    Bundle extras;
    SeekBar seek_view;
    int p;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_layout);
        image_relative_layout=(RelativeLayout)findViewById(R.id.image_relative_layout);
        share_btn=(Button)findViewById(R.id.share_btn);
        selected_img=(ImageView)findViewById(R.id.selected_img);
        seek_view = (SeekBar) findViewById(R.id.seek_view);
        seek_view.setProgress(0);
        seek_view.incrementProgressBy(1);
        seek_view.setMax(10);
        p=Integer.parseInt(getIntent().getExtras().getString("type"));
        if(p==1){
            bitmap = BitmapFactory.decodeResource(getResources(), GlobalConstants.img1[Integer.parseInt(getIntent().getExtras().getString("pos"))]);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            b = baos.toByteArray();
        }else  if(p==2){
            bitmap = BitmapFactory.decodeResource(getResources(), GlobalConstants.img2[Integer.parseInt(getIntent().getExtras().getString("pos"))]);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            b = baos.toByteArray();
        }else  if(p==3){
            bitmap = BitmapFactory.decodeResource(getResources(), GlobalConstants.img3[Integer.parseInt(getIntent().getExtras().getString("pos"))]);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            b = baos.toByteArray();
        }else{
            bitmap = BitmapFactory.decodeResource(getResources(), GlobalConstants.img4[Integer.parseInt(getIntent().getExtras().getString("pos"))]);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            b = baos.toByteArray();
        }


        bm = BitmapFactory.decodeByteArray(b, 0, b.length);
        resizedbitmap = Bitmap.createScaledBitmap(bm, 170, 170, true);
        selected_img.setImageBitmap(resizedbitmap);
        seek_view.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

                if (progress > 0 && progress <= 1) {
                    resizedbitmap = Bitmap.createScaledBitmap(bm, 170, 170, true);
                    selected_img.setImageBitmap(resizedbitmap);

                }
                if (progress > 1 && progress <= 2) {
                    resizedbitmap = Bitmap.createScaledBitmap(bm, 210, 210, true);
                    selected_img.setImageBitmap(resizedbitmap);

                }
                if (progress > 2 && progress <= 3) {
                    resizedbitmap = Bitmap.createScaledBitmap(bm, 250, 250, true);
                    selected_img.setImageBitmap(resizedbitmap);

                }
                if (progress > 3 && progress <= 4) {
                    resizedbitmap = Bitmap.createScaledBitmap(bm, 290, 290, true);
                    selected_img.setImageBitmap(resizedbitmap);

                }
                if (progress > 4 && progress <= 5) {
                    resizedbitmap = Bitmap.createScaledBitmap(bm, 330, 330, true);
                    selected_img.setImageBitmap(resizedbitmap);

                }
                if (progress > 5 && progress <= 6) {
                    resizedbitmap = Bitmap.createScaledBitmap(bm, 400, 400, true);
                    selected_img.setImageBitmap(resizedbitmap);

                }
                if (progress > 6 && progress <= 7) {
                    resizedbitmap = Bitmap.createScaledBitmap(bm, 450, 450, true);
                    selected_img.setImageBitmap(resizedbitmap);

                }
                if (progress > 7 && progress <= 8) {
                    resizedbitmap = Bitmap.createScaledBitmap(bm, 470, 470, true);
                    selected_img.setImageBitmap(resizedbitmap);

                }
                if (progress > 8 && progress <= 9) {
                    resizedbitmap = Bitmap.createScaledBitmap(bm, 500, 500, true);
                    selected_img.setImageBitmap(resizedbitmap);

                }
                if (progress > 9 && progress <= 10) {
                    resizedbitmap = Bitmap.createScaledBitmap(bm, 550, 550, true);
                    selected_img.setImageBitmap(resizedbitmap);

                }
                selected_img.setImageBitmap(resizedbitmap);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resizedbitmap=getBitmapFromView(image_relative_layout);
                Uri imageUri = null;

                String imgBitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), resizedbitmap, "title", null);
                imageUri = Uri.parse(imgBitmapPath);
                Intent i = new Intent(Intent.ACTION_SEND);

                i.setType("image/png");
                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                //i.putExtra(android.content.Intent.EXTRA_SUBJECT,
                //   "Install this app and used refer code=1234567890");
                i.putExtra(Intent.EXTRA_STREAM, imageUri);


                startActivity(Intent.createChooser(i,
                        "Share"));
            }
        });
    }
    public static Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }
}
