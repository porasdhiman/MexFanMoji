package com.mexfanemoji;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by worksdelight on 16/08/17.
 */

public class ShareActivity extends Activity {
    ImageView selected_img;
    Button share_btn;
    SeekBar seek_view;
    Bitmap bitmap, resizedbitmap,bm;
    RelativeLayout image_relative_layout;
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    ArrayList<HashMap<String, String>> listing = new ArrayList<HashMap<String, String>>();
    int smallImage[], bigImage[];
    Bundle extras;
    public GifImageView gif;
    RelativeLayout.LayoutParams newParams;
    boolean seekValue = false;
    File f;
String resname;
    byte[] b;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_layout);


        selected_img = (ImageView) findViewById(R.id.selected_image);
        gif = (GifImageView) findViewById(R.id.img);
        share_btn = (Button) findViewById(R.id.share_btn);
        image_relative_layout = (RelativeLayout) findViewById(R.id.image_relative_layout);
        seek_view = (SeekBar) findViewById(R.id.seek_view);
        seek_view.setProgress(0);
        seek_view.incrementProgressBy(1);
        seek_view.setMax(10);

        extras = getIntent().getExtras();
        resname=extras.getString("image_type");
        if (resname.equalsIgnoreCase("gif")) {
            gif.setVisibility(View.VISIBLE);
            selected_img.setVisibility(View.GONE);
            seek_view.setVisibility(View.GONE);
            try {
                GifDrawable gifFromResource = new GifDrawable(getResources(), GlobalConstants.completeArray[Integer.parseInt(extras.getString("pos"))]);
                gif.setImageDrawable(gifFromResource);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            gif.setVisibility(View.GONE);
            selected_img.setVisibility(View.VISIBLE);
            seek_view.setVisibility(View.VISIBLE);

            bitmap = BitmapFactory.decodeResource(getResources(), GlobalConstants.completeArray[Integer.parseInt(extras.getString("pos"))]);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            b = baos.toByteArray();
            bm = BitmapFactory.decodeByteArray(b, 0, b.length);
            resizedbitmap = Bitmap.createScaledBitmap(bm, 190, 150, true);
            selected_img.setImageBitmap(resizedbitmap);
        }
        seek_view.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

                if (progress > 0 && progress <= 1) {
                    resizedbitmap = Bitmap.createScaledBitmap(bm, 190, 150, true);
                    selected_img.setImageBitmap(resizedbitmap);

                }
                if (progress > 1 && progress <= 2) {
                    resizedbitmap = Bitmap.createScaledBitmap(bm, 230, 190, true);
                    selected_img.setImageBitmap(resizedbitmap);

                }
                if (progress > 2 && progress <= 3) {
                    resizedbitmap = Bitmap.createScaledBitmap(bm, 270, 230, true);
                    selected_img.setImageBitmap(resizedbitmap);

                }
                if (progress > 3 && progress <= 4) {
                    resizedbitmap = Bitmap.createScaledBitmap(bm, 310, 270, true);
                    selected_img.setImageBitmap(resizedbitmap);

                }
                if (progress > 4 && progress <= 5) {
                    resizedbitmap = Bitmap.createScaledBitmap(bm, 350, 310, true);
                    selected_img.setImageBitmap(resizedbitmap);

                }
                if (progress > 5 && progress <= 6) {
                    resizedbitmap = Bitmap.createScaledBitmap(bm, 420, 380, true);
                    selected_img.setImageBitmap(resizedbitmap);

                }
                if (progress > 6 && progress <= 7) {
                    resizedbitmap = Bitmap.createScaledBitmap(bm, 470, 430, true);
                    selected_img.setImageBitmap(resizedbitmap);

                }
                if (progress > 7 && progress <= 8) {
                    resizedbitmap = Bitmap.createScaledBitmap(bm, 490, 450, true);
                    selected_img.setImageBitmap(resizedbitmap);

                }
                if (progress > 8 && progress <= 9) {
                    resizedbitmap = Bitmap.createScaledBitmap(bm, 520, 480, true);
                    selected_img.setImageBitmap(resizedbitmap);

                }
                if (progress > 9 && progress <= 10) {
                    resizedbitmap = Bitmap.createScaledBitmap(bm, 570, 530, true);
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



                    if (resname.contains(".gif")) {
                        GifFileMethod();
                        Uri imageUri = null;

                        imageUri = Uri.fromFile(f);
                        Intent i = new Intent(Intent.ACTION_SEND);

                        i.setType("image/gif");
                        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        //i.putExtra(android.content.Intent.EXTRA_SUBJECT,
                        //   "Install this app and used refer code=1234567890");
                        i.putExtra(Intent.EXTRA_STREAM, imageUri);


                        startActivity(Intent.createChooser(i,
                                "Share"));
                    } else {

                        resizedbitmap = getBitmapFromView(image_relative_layout);

                        String imgBitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), resizedbitmap, "title", null);
                        Uri imageUri = Uri.parse(imgBitmapPath);
                   /*     Uri imageUri = null;
                        imageUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(),
                                BitmapFactory.decodeResource(getResources(),  GlobalConstants.completeArray[Integer.parseInt(extras.getString("pos"))]), null, null));*/
                       // imageUri = Uri.parse(imgBitmapPath);
                        Intent i = new Intent(Intent.ACTION_SEND);

                        i.setType("image/png");
                        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        //i.putExtra(android.content.Intent.EXTRA_SUBJECT,
                        //   "Install this app and used refer code=1234567890");
                        i.putExtra(Intent.EXTRA_STREAM, imageUri);


                        startActivity(Intent.createChooser(i,
                                "Share"));
                    }



            }
        });

    }


  public static Bitmap getBitmapFromView(View view) {
      Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
      Canvas canvas = new Canvas(returnedBitmap);
      Drawable bgDrawable = view.getBackground();
      if (bgDrawable != null)
          bgDrawable.draw(canvas);
      else
          canvas.drawColor(Color.WHITE);
      view.draw(canvas);
      return returnedBitmap;
  }


    public void GifFileMethod() {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File file = new File(extStorageDirectory, "MexFanMoji.gif");



            try {
                byte[] readData = new byte[1024 * 500];


                InputStream fis = getResources().openRawResource(bigImage[Integer.parseInt(extras.getString("pos"))]);

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
