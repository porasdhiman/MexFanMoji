package com.worksdelight.denimoji.stickers;

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
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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


import com.worksdelight.denimoji.GlobalConstants;
import com.worksdelight.denimoji.KeyboardService;
import com.worksdelight.denimoji.MainViewActivity;
import com.worksdelight.denimoji.R;
import com.worksdelight.denimoji.ShareActivity;
import com.worksdelight.denimoji.com.tag.trivialdrivesample.util.IabHelper;
import com.worksdelight.denimoji.com.tag.trivialdrivesample.util.IabResult;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;

import static android.content.ContentValues.TAG;


public class StickerAdapter extends RecyclerView.Adapter<StickerHolder> {
    private final String TAG = "StickerAdapter";

    private KeyboardService keyboardService;
    private List<String> listImg = new ArrayList<String>();
    String resname;
    int img[];
    Bitmap bitmap = null;
    int value;
    SharedPreferences sp;
    Bitmap resizedbitmap = null, bm = null;
    public StickerAdapter(KeyboardService kis, int img[], int value) {
        this.keyboardService = kis;
        this.img = img;
        this.value = value;
        sp = keyboardService.getSharedPreferences("denimoji", Context.MODE_PRIVATE);

    }

    @Override
    public StickerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new StickerHolder(view);
    }

    @Override
    public void onBindViewHolder(final StickerHolder holder, final int position) {
        //final StickerData sticker = stickerDataList.get(position);

        holder.imageView.setImageResource(img[position]);
        if(value==3){

            if (sp.getString("purchase_type", "").equalsIgnoreCase("1")) {
                holder.lock.setVisibility(View.GONE);
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (sp.getString("type", "0").equalsIgnoreCase("1")) {
                            bitmap = BitmapFactory.decodeResource(keyboardService.getResources(), GlobalConstants.img4[position]);

                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                            byte[] b = baos.toByteArray();
                            keyboardService.visibilityOfShareView(b);
                        }else{
                            resizedbitmap = BitmapFactory.decodeResource(keyboardService.getResources(), GlobalConstants.img4[position]);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            resizedbitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            bm = Bitmap.createScaledBitmap(resizedbitmap, 600, 600, true);
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
                        }

                    }
                });
            } else {
                holder.lock.setVisibility(View.VISIBLE);
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Toast.makeText(keyboardService,"Please go to application buy more sticker",Toast.LENGTH_SHORT).show();

                    }
                });

            }
        }else {
            holder.lock.setVisibility(View.GONE);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (sp.getString("type", "0").equalsIgnoreCase("1")) {


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
                        keyboardService.visibilityOfShareView(b);
                    } else {
                        try {




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
        }
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
