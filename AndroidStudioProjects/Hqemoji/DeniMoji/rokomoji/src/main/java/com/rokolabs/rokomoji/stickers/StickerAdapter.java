package com.rokolabs.rokomoji.stickers;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.RelativeLayout;

import com.rokolabs.rokomoji.KeyboardService;
import com.rokolabs.rokomoji.R;

import java.io.File;
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
    private List<StickerData> stickerDataList = new ArrayList<StickerData>();
    private KeyboardService keyboardService;
    private List<String> listImg = new ArrayList<String>();

    int img[];
    int[] img1 = {R.drawable.b_01, R.drawable.b_02, R.drawable.b_03, R.drawable.b_04, R.drawable.b_08, R.drawable.b_09, R.drawable.b_010, R.drawable.b_011,
            R.drawable.a_12,R.drawable.a_13, R.drawable.b_012};
    int[] img2={R.drawable.b_0001,R.drawable.b_0002,R.drawable.b_0003,R.drawable.b_0004,R.drawable.b_0005,R.drawable.b_0006,R.drawable.b_0007
            ,R.drawable.b_0008,R.drawable.b_0009,R.drawable.b_00010,R.drawable.b_00011,R.drawable.b_00012,R.drawable.b_00013,R.drawable.b_00014,
            R.drawable.b_00015,R.drawable.b_00016,R.drawable.b_00017,R.drawable.b_00018,R.drawable.b_00019,R.drawable.b_00020};
    int[] img3={R.drawable.b_001,R.drawable.b_002,R.drawable.b_003,R.drawable.b_004,R.drawable.b_005,R.drawable.b_006,R.drawable.b_007
            ,R.drawable.b_008,R.drawable.b_009,R.drawable.a_0010,R.drawable.a_0011};
    int[] img4={R.drawable.b_00001,R.drawable.b_00002,R.drawable.b_00003,R.drawable.b_00004,R.drawable.b_00005,R.drawable.b_00006,
            R.drawable.b_00007,R.drawable.b_00008,R.drawable.b_00009,R.drawable.b_000010,R.drawable.b_000011,R.drawable.b_000012,
            R.drawable.b_000013,R.drawable.b_000014,R.drawable.b_000015,R.drawable.b_000016
            ,R.drawable.b_000017,R.drawable.b_000018,R.drawable.b_000019,R.drawable.a_00020,R.drawable.b_000020,R.drawable.b_000021,R.drawable.b_000023,R.drawable.b_000024};
    int[] img5={R.drawable.b_1,R.drawable.b_2,R.drawable.b_3,R.drawable.b_4,R.drawable.b_5,
            R.drawable.b_6,R.drawable.b_7,R.drawable.b_8,R.drawable.b_9,R.drawable.b_10,
            R.drawable.b_11,R.drawable.b_12,R.drawable.b_13,R.drawable.b_14};
            int value;
    public StickerAdapter(KeyboardService kis, int img[],int value) {
        this.keyboardService = kis;
       this.img = img;
        this.value=value;

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
            String resname = value.string.toString();

            if(resname.contains(".gif")){
                holder.imageView.setVisibility(View.GONE);
                holder.gif.setVisibility(View.VISIBLE);
                try {
                    GifDrawable gifFromResource = new GifDrawable(keyboardService.getResources(), img[position] );
                    holder.gif.setImageDrawable(gifFromResource);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
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
                /*for (int l=0;l<img.length;l++){
                    TypedValue value = new TypedValue();
                    keyboardService.getResources().getValue(img[l], value, true);
                    String resname = value.string.toString();
                    if(resname==global.getListImages().get(l)){

                    }
                }*/
                try {
                    Uri imageUri = null;
                    try {
                        if(value==0){
                            imageUri = Uri.parse(MediaStore.Images.Media.insertImage(keyboardService.getContentResolver(),
                                    BitmapFactory.decodeResource(keyboardService.getResources(), img1[position]), null, null));
                        }else if(value==1){
                            imageUri = Uri.parse(MediaStore.Images.Media.insertImage(keyboardService.getContentResolver(),
                                    BitmapFactory.decodeResource(keyboardService.getResources(), img2[position]), null, null));
                        }
                        else if(value==2){
                            imageUri = Uri.parse(MediaStore.Images.Media.insertImage(keyboardService.getContentResolver(),
                                    BitmapFactory.decodeResource(keyboardService.getResources(), img3[position]), null, null));
                        }
                        else if(value==3){
                            imageUri = Uri.parse(MediaStore.Images.Media.insertImage(keyboardService.getContentResolver(),
                                    BitmapFactory.decodeResource(keyboardService.getResources(), img4[position]), null, null));
                        }
                        else if(value==4){
                            imageUri = Uri.parse(MediaStore.Images.Media.insertImage(keyboardService.getContentResolver(),
                                    BitmapFactory.decodeResource(keyboardService.getResources(), img5[position]), null, null));
                        }

                    } catch (NullPointerException e) {
                    }
                    ActivityInfo ai = getAppForShare("image/png");
                    // Launch the Google+ share dialog with attribution to your app.
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setClassName(ai.applicationInfo.packageName, ai.name);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    i.setType("image/png");
                    //i.putExtra(android.content.Intent.EXTRA_SUBJECT,
                    //   "Install this app and used refer code=1234567890");
                    i.putExtra(Intent.EXTRA_STREAM, imageUri);
                   /* final Intent chooser = Intent.createChooser(i, "share");
                    chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/
                    keyboardService.startActivity(i);
                } catch (android.content.ActivityNotFoundException ex) {
                }
            }
        });
        holder.gif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*for (int l=0;l<img.length;l++){
                    TypedValue value = new TypedValue();
                    keyboardService.getResources().getValue(img[l], value, true);
                    String resname = value.string.toString();
                    if(resname==global.getListImages().get(l)){

                    }
                }*/
                try {
                    String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                    File file = new File(extStorageDirectory, "CarpMoji.gif");
                    InputStream fis=null;
                    try {
                        byte[] readData = new byte[1024*500];
                        try {
                            if(value==0){
                              fis = keyboardService.getResources().openRawResource(img1[position]);
                            }else if(value==1){
                                 fis = keyboardService.getResources().openRawResource(img2[position]);
                            }
                            else if(value==2){
                                 fis = keyboardService.getResources().openRawResource(img3[position]);
                            }
                            else if(value==3){
                                 fis = keyboardService.getResources().openRawResource(img4[position]);
                            }
                            else if(value==4){
                                 fis = keyboardService.getResources().openRawResource(img5[position]);
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
                   /* final Intent chooser = Intent.createChooser(i, "share");
                    chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/
                    keyboardService.startActivity(i);
                } catch (android.content.ActivityNotFoundException ex) {
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
}
