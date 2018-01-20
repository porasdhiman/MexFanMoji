package com.mexfanemoji;

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
import android.graphics.drawable.Drawable;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mexfanemoji.stickers.MarginDecoration;
import com.mexfanemoji.stickers.StickerAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;


/**
 * Created by mist on 02.12.16.
 */

public class KeyboardService extends InputMethodService {
    private static final String TAG = "KeyboardService";
    private final static String SERVICE_NAME = "com.mexfanmoji.KeyboardService";
    private static final String MIME_TYPE_GIF = "image/gif";


    LinearLayout mainBoard;
    ScrollView scrollView;
    private StickerAdapter stickerAdapter;

    ImageView packNameLabel, cancel, global, cancel_img, selected_image,first_image,second_image;
    private boolean contentSupportedGif;
    private RecyclerView packView, stickerView;

    private EditorInfo editorInfo;
    LatinKeyboardView keyboard_view;
    LatinKeyboard keyboard, numerice_keyboared;
    boolean caps = false;
    LinearLayout frame_contain, share_view;
    int j =1;
    RelativeLayout view_layout;
    TextView size_txt, share_txt;

    ImageView selected_img;
    Button share_btn;
    SeekBar seek_view;
    Bitmap bm, resizedbitmap;
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    ArrayList<String> assestPathArray = new ArrayList<>();
    ArrayList<HashMap<String, String>> listing = new ArrayList<HashMap<String, String>>();
    RelativeLayout image_relative_layout;
    RelativeLayout share_layout;
    GifImageView girView;
    int k;
    File f;
    RelativeLayout first_layout,second_layout;
    public static boolean rokomojiEnabled(Context activity) {
//        requestPermissionIfNeeded(Manifest.permission.READ_EXTERNAL_STORAGE, activity);
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        List<InputMethodInfo> imList = imm.getEnabledInputMethodList();
        for (InputMethodInfo imi : imList) {

            if (activity.getPackageName().equalsIgnoreCase(imi.getPackageName()) && SERVICE_NAME.equalsIgnoreCase(imi.getServiceName())) {
                //if (SERVICE_NAME.equalsIgnoreCase(imi.getServiceName())) {
                return true;
            }
        }
        return false;
    }


    private KeyboardService returnThis() {
        return this;
    }

    private void getStickers() {
        switchBoard();
    }

    public void switchBoard() {

if(j==1) {
    stickerAdapter = new StickerAdapter(this, GlobalConstants.firstArray, j);
}else if(j==2) {
    stickerAdapter = new StickerAdapter(this, GlobalConstants.secondArray, j);
}

        if (stickerView != null) {
            stickerView.setAdapter(stickerAdapter);
        }
    }


    @Override
    public View onCreateInputView() {
        mainBoard = (LinearLayout) getLayoutInflater().inflate(R.layout.main_board_layout, null);


        packNameLabel = (ImageView) mainBoard.findViewById(R.id.packNameLabel);
        frame_contain = (LinearLayout) mainBoard.findViewById(R.id.frame_contain);
        share_view = (LinearLayout) mainBoard.findViewById(R.id.share_view);
        cancel_img = (ImageView) mainBoard.findViewById(R.id.cancel_img);
        share_btn = (Button) mainBoard.findViewById(R.id.share_btn);
        selected_image = (ImageView) mainBoard.findViewById(R.id.selected_image);
        girView = (GifImageView) mainBoard.findViewById(R.id.img);
        image_relative_layout = (RelativeLayout) mainBoard.findViewById(R.id.image_relative_layout);
         cancel = (ImageView) mainBoard.findViewById(R.id.cancel);
        global = (ImageView) mainBoard.findViewById(R.id.global);
        first_image = (ImageView) mainBoard.findViewById(R.id.first_image);
        second_image = (ImageView) mainBoard.findViewById(R.id.second_image);
        share_layout=(RelativeLayout) mainBoard.findViewById(R.id.share_layout);
        share_txt = (TextView) mainBoard.findViewById(R.id.share_txt);
first_layout=(RelativeLayout)mainBoard.findViewById(R.id.first_layout);
        second_layout=(RelativeLayout)mainBoard.findViewById(R.id.second_layout);
        // scrollView = (ScrollView) mainBoard.findViewById(R.id.gif_view);
        keyboard_view = (LatinKeyboardView) mainBoard.findViewById(R.id.keyboard_view);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getCurrentInputConnection().sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));

            }
        });
         share_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final InputConnection ic = getCurrentInputConnection();
                ic.commitText(" https://www.MexFanEmoji.com/", 0);
            }
        });
        stickerView = (RecyclerView) mainBoard.findViewById(R.id.gif_view);
        stickerView.addItemDecoration(new MarginDecoration(this));
        stickerView.setHasFixedSize(true);
        stickerView.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.HORIZONTAL, false));

        cancel_img.setColorFilter(cancel_img.getContext().getResources().getColor(R.color.app_color), PorterDuff.Mode.SRC_ATOP);

        global.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (KeyboardService.rokomojiEnabled(getApplicationContext())) {
                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showInputMethodPicker();
                } else {
                    startActivity(new Intent("android.settings.INPUT_METHOD_SETTINGS"));
                }
            }
        });

        mainBoard.setBackgroundColor(Color.parseColor("#ffffff"));
        keyboard = new LatinKeyboard(this, R.xml.qwerty);
        numerice_keyboared = new LatinKeyboard(this, R.xml.numeric_value);
        keyboard_view.setKeyboard(keyboard);
        keyboard_view.setPreviewEnabled(true);
        keyboard_view.setVisibility(View.GONE);
        frame_contain.setVisibility(View.VISIBLE);
        share_layout.setVisibility(View.VISIBLE);


        keyboard_view.setOnKeyboardActionListener(new KeyboardView.OnKeyboardActionListener() {
            @Override
            public void onPress(int i) {
            }

            @Override
            public void onRelease(int i) {

            }

            @Override
            public void onKey(int primaryCode, int[] ints) {
                InputConnection ic = getCurrentInputConnection();
                playClick(primaryCode);
                switch (primaryCode) {
                    case Keyboard.KEYCODE_DELETE:
                        getCurrentInputConnection().sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                        break;
                    case Keyboard.KEYCODE_SHIFT:
                        caps = !caps;
                        keyboard.setShifted(caps);
                        keyboard_view.invalidateAllKeys();
                        break;
                    case Keyboard.KEYCODE_DONE:
                        ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                        ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_ENTER));
                        break;
                    case Keyboard.KEYCODE_MODE_CHANGE:
                        if (KeyboardService.rokomojiEnabled(getApplicationContext())) {
                            InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.showInputMethodPicker();
                        } else {
                            startActivity(new Intent("android.settings.INPUT_METHOD_SETTINGS"));
                        }
                        break;
                    case -100:
                        mainBoard.setBackgroundColor(Color.parseColor("#ffffff"));
                        ic.deleteSurroundingText(1, 0);
                        packNameLabel.setVisibility(View.VISIBLE);

                        stickerView.setVisibility(View.VISIBLE);
                        keyboard_view.setVisibility(View.GONE);
                        frame_contain.setVisibility(View.VISIBLE);
                        share_layout.setVisibility(View.VISIBLE);

                        break;
                    case 82:

                        keyboard_view.setKeyboard(numerice_keyboared);
                        keyboard_view.setPreviewEnabled(true);
                        break;
                    case 23:

                        keyboard_view.setKeyboard(keyboard);
                        keyboard_view.setPreviewEnabled(true);
                        break;
                    default:
                        char code = (char) primaryCode;
                        if (Character.isLetter(code) && caps) {
                            code = Character.toUpperCase(code);
                        }
                        ic.commitText(String.valueOf(code), 1);
                }
            }

            @Override
            public void onText(CharSequence charSequence) {

            }

            @Override
            public void swipeLeft() {

            }

            @Override
            public void swipeRight() {

            }

            @Override
            public void swipeDown() {

            }

            @Override
            public void swipeUp() {

            }
        });


        packNameLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainBoard.setBackgroundColor(Color.parseColor("#f5f5f5"));
                packNameLabel.setVisibility(View.GONE);

                stickerView.setVisibility(View.GONE);
                keyboard_view.setVisibility(View.VISIBLE);
                frame_contain.setVisibility(View.GONE);
                share_layout.setVisibility(View.GONE);


            }
        });
        // packs bar
        packView = (RecyclerView) mainBoard.findViewById(R.id.pack_recycler_view);
        packView.setVisibility(View.GONE);
        switchBoard();
        MethodForClick();

        return mainBoard;
    }

    private void playClick(int keyCode) {
        AudioManager am = (AudioManager) getSystemService(AUDIO_SERVICE);

        switch (keyCode) {
            case 32:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case Keyboard.KEYCODE_DONE:
            case 10:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
            case Keyboard.KEYCODE_DELETE:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
            default:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);

        }
    }


    public void onCreate() {
        super.onCreate();


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


    private ActivityInfo getAppForShare(String type) {
        final EditorInfo editorInfo = getCurrentInputEditorInfo();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        //intent.setType("image/gif");
        intent.setType(type);
        PackageManager pm = getPackageManager();
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
public void ChangeTab(){
    if(j==1){
        first_image.setBackgroundColor(Color.parseColor("#ffffff"));
        second_image.setBackgroundColor(getResources().getColor(R.color.app_color));


        j = 2;
        switchBoard();

    }else if(j==2){
        first_image.setBackgroundColor(getResources().getColor(R.color.app_color));
        second_image.setBackgroundColor(Color.parseColor("#ffffff"));
        j = 1;
        switchBoard();
    }
}
    public void MethodForClick() {
        second_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                first_layout.setBackgroundColor(Color.parseColor("#ffffff"));
                second_layout.setBackgroundColor(getResources().getColor(R.color.app_color));


                j = 2;
                switchBoard();

            }
        });
        first_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                first_layout.setBackgroundColor(getResources().getColor(R.color.app_color));
                second_layout.setBackgroundColor(Color.parseColor("#ffffff"));
                j = 1;
                switchBoard();
            }
        });

    }

    public void visibilityOfShareView(final int position, final int i, final String type) {
        share_view.setVisibility(View.VISIBLE);
        packNameLabel.setVisibility(View.GONE);
        stickerView.setVisibility(View.GONE);
        share_layout.setVisibility(View.GONE);
        frame_contain.setVisibility(View.GONE);
        k = i;
        if(type.contains(".gif")){
            selected_image.setVisibility(View.GONE);
            girView.setVisibility(View.VISIBLE);


                try {
                    GifDrawable gifFromResource = new GifDrawable(getResources(), GlobalConstants.completeArray[position]);
                    girView.setImageDrawable(gifFromResource);
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }else {
            selected_image.setVisibility(View.VISIBLE);
            girView.setVisibility(View.GONE);

                bm = BitmapFactory.decodeResource(getResources(), GlobalConstants.completeArray[position]);


            selected_image.setImageBitmap(bm);

        }
        // seek_view.setOnSeekBarChangeListener(this);


        cancel_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share_view.setVisibility(View.GONE);
                packNameLabel.setVisibility(View.VISIBLE);
                stickerView.setVisibility(View.VISIBLE);
                share_layout.setVisibility(View.VISIBLE);
                frame_contain.setVisibility(View.VISIBLE);
            }
        });

        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type.contains(".gif")){
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
                   /* final Intent chooser = Intent.createChooser(i, "share");
                    chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/
                       startActivity(i);
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
                       startActivity(chooser);
                    }

                }else {
                    resizedbitmap = getBitmapFromView(image_relative_layout);

                    try {


                        String imgBitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), resizedbitmap, "title", null);
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
                            startActivity(i);
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
                            startActivity(chooser);
                        }

                    } catch (android.content.ActivityNotFoundException ex) {
                    }
                }

            }
        });

    }
//--------------Gif store method--------------
public void GifFileMethod(int pos,int k) {
    String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
    File file = new File(extStorageDirectory, "Trumojis.gif");
    // if (seekValue) {


    try {
        byte[] readData = new byte[1024 * 500];
        InputStream fis=null;


            fis = getResources().openRawResource(GlobalConstants.completeArray[pos]);



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

