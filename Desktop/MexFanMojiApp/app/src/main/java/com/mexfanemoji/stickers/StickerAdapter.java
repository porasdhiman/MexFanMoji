package com.mexfanemoji.stickers;

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
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.RelativeLayout;

import com.mexfanemoji.KeyboardService;
import com.mexfanemoji.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;


/**
 * Created by mist on 12.12.16.
 */

public class StickerAdapter extends RecyclerView.Adapter<StickerHolder> {
    private final String TAG = "StickerAdapter";

    private KeyboardService keyboardService;
    private List<String> listImg = new ArrayList<String>();
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    String type;
    ArrayList<HashMap<String, String>> listing = new ArrayList<HashMap<String, String>>();
    int img[];
    int i;
    int value;
Bitmap resizedbitmap,bm;
    File f;
    String resname;
    public StickerAdapter(KeyboardService kis, int img[], int i) {
        this.keyboardService = kis;
        this.img = img;
        this.i = i;


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
                Drawable d = ContextCompat.getDrawable(keyboardService.getApplicationContext(), img[position]);
                Bitmap bitmap = ((BitmapDrawable)resize(d)).getBitmap();
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
                //keyboardService.visibilityOfShareView(position,i,resname);


                resizedbitmap = BitmapFactory.decodeResource(keyboardService.getResources(), img[position]);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                resizedbitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                bm = Bitmap.createScaledBitmap(resizedbitmap, 480, 280, true);
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
 imageUri = Uri.parse(MediaStore.Images.Media.insertImage(keyboardService.getContentResolver(),
                        BitmapFactory.decodeResource(keyboardService.getResources(), img[position]), null, null));

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
 final Intent chooser = Intent.createChooser(i, "share");
                    chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

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
        });
        holder.gif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  keyboardService.visibilityOfShareView(position,i,resname);

                GifFileMethod(position,i);
                try {
                    Uri imageUri = null;
                    imageUri = Uri.fromFile(f);
                    ActivityInfo ai = getAppForShare("image/gif");
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setClassName(ai.applicationInfo.packageName, ai.name);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    i.setType("image/gif");
                    //i.putExtra(android.content.Intent.EXTRA_SUBJECT,
                    //   "Install this app and used refer code=1234567890");
                    i.putExtra(Intent.EXTRA_STREAM, imageUri);
                    final Intent chooser = Intent.createChooser(i, "share");
                    chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    keyboardService.startActivity(i);
                }catch (Exception e){
                    Uri imageUri = null;
                    imageUri = Uri.fromFile(f);
                    Intent i = new Intent(Intent.ACTION_SEND);

                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    i.setType("image/gif");
                    //i.putExtra(android.content.Intent.EXTRA_SUBJECT,
                    //   "Install this app and used refer code=1234567890");
                    i.putExtra(Intent.EXTRA_STREAM, imageUri);
                    final Intent chooser = Intent.createChooser(i, "share");
                    chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    keyboardService.startActivity(chooser);
                }

            }
        });
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
    public void GifFileMethod(int pos,int k) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File file = new File(extStorageDirectory, "MexFanMoji.gif");
        // if (seekValue) {


        try {
            byte[] readData = new byte[1024 * 500];
            InputStream fis=null;

                fis = keyboardService.getResources().openRawResource(img[pos]);



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
    private Drawable resize(Drawable image) {

        Bitmap b = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 200, 140, false);
        return new BitmapDrawable(keyboardService.getResources(), bitmapResized);
    }
}
