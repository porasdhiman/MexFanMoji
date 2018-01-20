package com.rokolabs.rokomoji.stickers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.rokolabs.rokomoji.GlobalConstants;
import com.rokolabs.rokomoji.KeyboardService;
import com.rokolabs.rokomoji.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;

/**
 * Created by mist on 12.12.16.
 */

public class StickerAdapter extends RecyclerView.Adapter<StickerHolder> {
    private final String TAG = "StickerAdapter";

    private KeyboardService keyboardService;
    private List<String> listImg = new ArrayList<String>();
    String resname;
    int img[];

    int value;
    SharedPreferences sp;

    public StickerAdapter(KeyboardService kis, int img[], int value) {
        this.keyboardService = kis;
        this.img = img;
        this.value = value;
        sp = keyboardService.getSharedPreferences("Denimoji", Context.MODE_PRIVATE);
    }

    @Override
    public StickerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new StickerHolder(view);
    }

    @Override
    public void onBindViewHolder(final StickerHolder holder, final int position) {
        //final StickerData sticker = stickerDataList.get(position);

        try {
           /* if(sticker.iconKey.getPath().contains(".gif")){
               // sticker.iconKey.getPath();
                Log.e("path",sticker.iconKey.getPath());
             File f=new File(sticker.iconKey.getPath());

                try {
                    GifDrawable gifFromResource = new GifDrawable(f);
                    holder.gif.setImageDrawable(gifFromResource);
                    holder.imageView.setVisibility(View.GONE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{*/

            TypedValue value = new TypedValue();
            keyboardService.getResources().getValue(img[position], value, true);
            resname = value.string.toString();

            if (resname.contains(".gif")) {
                holder.imageView.setVisibility(View.GONE);
                holder.gif.setVisibility(View.VISIBLE);
                try {
                    GifDrawable gifFromResource = new GifDrawable(keyboardService.getResources(), img[position]);
                    holder.gif.setImageDrawable(gifFromResource);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                holder.imageView.setVisibility(View.VISIBLE);
                holder.gif.setVisibility(View.GONE);

                holder.imageView.setImageResource(img[position]);

            }

            // holder.gif.setVisibility(View.GONE);
            // }

        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sp.getString("type", "0").equalsIgnoreCase("1")) {
                    Bitmap bitmap = null;

                    if (value == 0) {
                        bitmap = BitmapFactory.decodeResource(keyboardService.getResources(), GlobalConstants.img1[position]);

                    } else if (value == 1) {
                        bitmap = BitmapFactory.decodeResource(keyboardService.getResources(), GlobalConstants.img2[position]);

                    } else if (value == 2) {
                        bitmap = BitmapFactory.decodeResource(keyboardService.getResources(), GlobalConstants.img3[position]);
                    }

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] b = baos.toByteArray();
                    keyboardService.visibilityOfShareView(position, b, value);
                } else {
                    try {


                        Bitmap resizedbitmap = null, bm = null;

                        if (value == 0) {
                            resizedbitmap = BitmapFactory.decodeResource(keyboardService.getResources(), GlobalConstants.img1[position]);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            resizedbitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            bm = Bitmap.createScaledBitmap(resizedbitmap, 600, 600, true);


                        } else if (value == 1) {
                            resizedbitmap = BitmapFactory.decodeResource(keyboardService.getResources(), GlobalConstants.img2[position]);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            resizedbitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            bm = Bitmap.createScaledBitmap(resizedbitmap, 600, 600, true);

                        } else if (value == 2) {
                            resizedbitmap = BitmapFactory.decodeResource(keyboardService.getResources(), GlobalConstants.img3[position]);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            resizedbitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            bm = Bitmap.createScaledBitmap(resizedbitmap, 600, 600, true);
                        }
                        int[] allpixels = new int[bm.getHeight() * bm.getWidth()];

                        bm.getPixels(allpixels, 0, bm.getWidth(), 0, 0, bm.getWidth(), bm.getHeight());

                        for (int i = 0; i < allpixels.length; i++) {
                            if (allpixels[i] == Color.TRANSPARENT) {
                                allpixels[i] = Color.WHITE;
                            }
                        }

                        bm.setPixels(allpixels, 0, bm.getWidth(), 0, 0, bm.getWidth(), bm.getHeight());

                        String imgBitmapPath = MediaStore.Images.Media.insertImage(keyboardService.getContentResolver(), bm, "title", null);
                        Uri imageUri = Uri.parse(imgBitmapPath);

                        try {
                            ActivityInfo ai = getAppForShare("image/jpg");
                            // Launch the Google+ share dialog with attribution to your app.
                            Intent i = new Intent(Intent.ACTION_SEND);
                            i.setClassName(ai.applicationInfo.packageName, ai.name);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            i.setType("image/jpg");
                            //i.putExtra(android.content.Intent.EXTRA_SUBJECT,
                            //   "Install this app and used refer code=1234567890");
                            i.putExtra(Intent.EXTRA_STREAM, imageUri);
                   /* final Intent chooser = Intent.createChooser(i, "share");
                    chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/
                            keyboardService.startActivity(i);
                        } catch (Exception c) {
                            // Toast.makeText(keyboardService, "Application does not support", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Intent.ACTION_SEND);

                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            i.setType("image/jpg");
                            //i.putExtra(android.content.Intent.EXTRA_SUBJECT,
                            //   "Install this app and used refer code=1234567890");
                            i.putExtra(Intent.EXTRA_STREAM, imageUri);
                            final Intent chooser = Intent.createChooser(i, "share");
                            chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            keyboardService.startActivity(chooser);
                        }

                    } catch (android.content.ActivityNotFoundException ex) {
                    }
                }
            }
        });
       /* holder.gif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                *//*for (int l=0;l<img.length;l++){
                    TypedValue value = new TypedValue();
                    keyboardService.getResources().getValue(img[l], value, true);
                    String resname = value.string.toString();
                    if(resname==global.getListImages().get(l)){

                    }
                }*//*
                try {
                    File file1 = new File(Environment.getExternalStorageDirectory() + "/Denimoji/");

                    if (!file1.exists())     //check if file already exists
                    {
                        file1.mkdirs();     //if not, create it
                    }
                    File file = new File(file1, "denimoji.gif");

                    InputStream fis = null;
                    try {
                        byte[] readData = new byte[1024 * 500];
                        try {
                            if (value == 0) {
                                fis = keyboardService.getResources().openRawResource(GlobalConstants.img1[position]);
                            } else if (value == 1) {
                                fis = keyboardService.getResources().openRawResource(GlobalConstants.img2[position]);
                            } else if (value == 2) {
                                fis = keyboardService.getResources().openRawResource(GlobalConstants.img3[position]);
                            }


                        } catch (NullPointerException e) {
                        }


                        FileOutputStream fos = new FileOutputStream(file);
                        int i = fis.read(readData);

                        while (i != -1) {
                            fos.write(readData, 0, i);
                            i = fis.read(readData);
                        }

                        fos.close();
                    } catch (IOException io) {
                    }
                    Uri imageUri = Uri.fromFile(file);

                    try {
                        ActivityInfo ai = getAppForShare("image/gif");
                        // Launch the Google+ share dialog with attribution to your app.
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setClassName(ai.applicationInfo.packageName, ai.name);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        i.setType("image/gif");
                        //i.putExtra(android.content.Intent.EXTRA_SUBJECT,
                        //   "Install this app and used refer code=1234567890");
                        i.putExtra(Intent.EXTRA_STREAM, imageUri);
                   *//* final Intent chooser = Intent.createChooser(i, "share");
                    chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*//*
                        keyboardService.startActivity(i);
                    } catch (Exception c) {
                        Toast.makeText(keyboardService, "Application does not support", Toast.LENGTH_SHORT).show();
                    }
                } catch (android.content.ActivityNotFoundException ex) {
                }
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return img.length;
    }

    public static Bitmap loadBitmapFromView(View v) {
        if (v.getMeasuredHeight() <= 0) {
            v.measure(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            Bitmap b = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b);
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
            v.draw(c);
            return b;
        }
        return null;
    }

    private ActivityInfo getAppForShare(String type) {
        final EditorInfo editorInfo = keyboardService.getCurrentInputEditorInfo();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        //intent.setType("image/gif");
        intent.setType(type);
        PackageManager pm = keyboardService.getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo act : activities) {
            ActivityInfo ai = act.activityInfo;
            Log.e("###", "" + editorInfo.packageName + " :: " + ai.applicationInfo.packageName + " | " + ai.name);
            if (editorInfo.packageName.equalsIgnoreCase(ai.applicationInfo.packageName)) {
                return ai;
            }
        }
        return null;
    }

    public static Drawable setTint(Drawable d, int color) {

        Drawable wrappedDrawable = DrawableCompat.wrap(d);
        DrawableCompat.setTint(wrappedDrawable, color);
        return wrappedDrawable;
    }

    public static Bitmap drawableToBitmap(Drawable drawable, KeyboardService kis) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel

        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        int[] allpixels = new int[bitmap.getHeight() * bitmap.getWidth()];

        bitmap.getPixels(allpixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        for (int i = 0; i < allpixels.length; i++) {
            if (allpixels[i] == Color.TRANSPARENT) {
                allpixels[i] = Color.WHITE;
            }
        }

        bitmap.setPixels(allpixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        return bitmap;
    }
}
