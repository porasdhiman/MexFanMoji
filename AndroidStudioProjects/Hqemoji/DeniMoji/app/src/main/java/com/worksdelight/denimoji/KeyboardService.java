package com.worksdelight.denimoji;

import android.app.Activity;
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
import android.graphics.drawable.Drawable;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.SeekBar;
import android.widget.TextView;

import com.worksdelight.denimoji.stickers.MarginDecoration;
import com.worksdelight.denimoji.stickers.StickerAdapter;

import java.util.List;



public class KeyboardService extends InputMethodService {
    private static final String TAG = "KeyboardService";
    private final static String SERVICE_NAME = "com.worksdelight.denimoji.KeyboardService";
    LinearLayout mainBoard;

    private StickerAdapter stickerAdapter;

    private ImageView packNameLabel, cancel, global, first_image, second_image, third_image,fourth_image;

    private RecyclerView packView, stickerView;

    KeyboardView keyboard_view;
    Keyboard keyboard, numerice_keyboared;
    boolean caps = false;
    LinearLayout frame_contain, share_view;
    int k = 0;
    Button share_btn;
    SeekBar seek_view;
    RelativeLayout view_layout, image_relative_layout;
    Bitmap bm, resizedbitmap;
    ImageView selected_image;
    TextView size_txt;
    SharedPreferences sp;
    SharedPreferences.Editor ed;
ImageView cancel_img;
    public static boolean rokomojiEnabled(Context activity) {
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

    private static boolean requestPermissionIfNeeded(String permission, Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, 1);
            return true;
        }
        return false;
    }


    public void switchBoard() {


        if (k == 0) {
            stickerAdapter = new StickerAdapter(this, GlobalConstants.small_img1, k);

        } else if (k == 1) {
            stickerAdapter = new StickerAdapter(this, GlobalConstants.small_img2, k);

        } else if (k == 2) {
            stickerAdapter = new StickerAdapter(this, GlobalConstants.small_img3, k);

        }else if (k == 3) {
            stickerAdapter = new StickerAdapter(this, GlobalConstants.small_img4, k);

        }


        if (stickerView != null) {
            stickerView.setAdapter(stickerAdapter);
        }
    }

    @Override
    public View onCreateInputView() {
        mainBoard = (LinearLayout) getLayoutInflater().inflate(R.layout.main_board_layout, null);
        sp = getSharedPreferences("denimoji", Context.MODE_PRIVATE);
        ed = sp.edit();
        share_view = (LinearLayout) mainBoard.findViewById(R.id.share_view);
        cancel_img = (ImageView) mainBoard.findViewById(R.id.cancel_img);
        seek_view = (SeekBar) mainBoard.findViewById(R.id.seek_view);
        share_btn = (Button) mainBoard.findViewById(R.id.share_btn);
        view_layout = (RelativeLayout) mainBoard.findViewById(R.id.view_layout);
        selected_image = (ImageView) mainBoard.findViewById(R.id.selected_image);
        image_relative_layout = (RelativeLayout) mainBoard.findViewById(R.id.image_relative_layout);
        size_txt = (TextView) mainBoard.findViewById(R.id.size_txt);
        packNameLabel = (ImageView) mainBoard.findViewById(R.id.packNameLabel);
        frame_contain = (LinearLayout) mainBoard.findViewById(R.id.frame_contain);
        cancel = (ImageView) mainBoard.findViewById(R.id.cancel);
        global = (ImageView) mainBoard.findViewById(R.id.global);
        first_image = (ImageView) mainBoard.findViewById(R.id.first_image);
        second_image = (ImageView) mainBoard.findViewById(R.id.second_image);
        third_image = (ImageView) mainBoard.findViewById(R.id.third_image);
        fourth_image=(ImageView)mainBoard.findViewById(R.id.fourth_image);
        // scrollView = (ScrollView) mainBoard.findViewById(R.id.gif_view);
        keyboard_view = (KeyboardView) mainBoard.findViewById(R.id.keyboard_view);
        stickerView = (RecyclerView) mainBoard.findViewById(R.id.gif_view);
        stickerView.addItemDecoration(new MarginDecoration(this));
        stickerView.setHasFixedSize(true);
        stickerView.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false));

        // scrollView.addView(stickerView);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getCurrentInputConnection().sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));

            }
        });
        if (sp.getString("type", "0").equalsIgnoreCase("1")) {

            size_txt.setTextColor(getResources().getColor(R.color.appColor));
            size_txt.setText("SIZE ON");
        } else {

            size_txt.setTextColor(Color.GRAY);
            size_txt.setText("SIZE OFF");

        }
        size_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sp.getString("type", "0").equalsIgnoreCase("1")) {
                    ed.putString("type", "0");
                    ed.commit();
                    size_txt.setTextColor(Color.GRAY);
                    size_txt.setText("SIZE OFF");
                } else {
                    ed.putString("type", "1");
                    ed.commit();
                    size_txt.setTextColor(getResources().getColor(R.color.appColor));
                    size_txt.setText("SIZE ON");

                }
            }
        });
        third_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                k = 0;
                switchBoard();
                second_image.setAlpha(0.3f);
                first_image.setAlpha(0.3f);
                third_image.setAlpha(1.0f);
                fourth_image.setAlpha(0.3f);
                //stickerView.getLayoutManager().scrollToPosition(1);
            }
        });
        first_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                k = 1;
                switchBoard();
                second_image.setAlpha(0.3f);
                first_image.setAlpha(1.0f);
                third_image.setAlpha(0.3f);
                fourth_image.setAlpha(0.3f);
                //stickerView.getLayoutManager().scrollToPosition(13);

            }
        });

        second_image.setAlpha(0.3f);
        second_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                k = 2;
                second_image.setAlpha(1.0f);
                first_image.setAlpha(0.3f);
                third_image.setAlpha(0.3f);
                fourth_image.setAlpha(0.3f);
                switchBoard();
                //stickerView.getLayoutManager().scrollToPosition(37);

            }
        });
        fourth_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                k = 3;
                fourth_image.setAlpha(1.0f);
                first_image.setAlpha(0.3f);
                third_image.setAlpha(0.3f);
                second_image.setAlpha(0.3f);
                switchBoard();
                //stickerView.getLayoutManager().scrollToPosition(37);

            }
        });
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
        keyboard = new Keyboard(this, R.xml.qwerty);
        numerice_keyboared = new Keyboard(this, R.xml.numeric_value);

        keyboard_view.setKeyboard(keyboard);
        //Fonts.overrideFonts(getApplicationContext(),keyboard_view);
        keyboard_view.setPreviewEnabled(false);
        keyboard_view.setVisibility(View.GONE);
        view_layout.setVisibility(View.VISIBLE);
        frame_contain.setVisibility(View.VISIBLE);
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
                        view_layout.setVisibility(View.VISIBLE);
                        keyboard_view.setVisibility(View.GONE);
                        frame_contain.setVisibility(View.VISIBLE);
                        break;
                    case 82:

                        keyboard_view.setKeyboard(numerice_keyboared);
                        keyboard_view.setPreviewEnabled(false);
                        break;
                    case 23:

                        keyboard_view.setKeyboard(keyboard);
                        keyboard_view.setPreviewEnabled(false);
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
                view_layout.setVisibility(View.GONE);
                stickerView.setVisibility(View.GONE);
                keyboard_view.setVisibility(View.VISIBLE);
                frame_contain.setVisibility(View.GONE);
            }
        });
        // packs bar
        packView = (RecyclerView) mainBoard.findViewById(R.id.pack_recycler_view);
        packView.setVisibility(View.GONE);
        switchBoard();
        // stickerView.getLayoutManager().scrollToPosition(1);

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


    //-----------------------------------------Seek view Method--------------
    public void visibilityOfShareView(byte[] b) {
        share_view.setVisibility(View.VISIBLE);
        packNameLabel.setVisibility(View.GONE);
        view_layout.setVisibility(View.GONE);
        stickerView.setVisibility(View.GONE);

        frame_contain.setVisibility(View.GONE);
        bm = BitmapFactory.decodeByteArray(b, 0, b.length);
        seek_view.setProgress(0);
        seek_view.incrementProgressBy(1);
        seek_view.setMax(10);

        resizedbitmap = Bitmap.createScaledBitmap(bm, 120, 120, true);


        selected_image.setImageBitmap(resizedbitmap);

        // seek_view.setOnSeekBarChangeListener(this);


        cancel_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share_view.setVisibility(View.GONE);
                packNameLabel.setVisibility(View.VISIBLE);
                view_layout.setVisibility(View.VISIBLE);
                stickerView.setVisibility(View.VISIBLE);

                frame_contain.setVisibility(View.VISIBLE);
            }
        });
        seek_view.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

                if (progress > 0 && progress <= 1) {
                    resizedbitmap = Bitmap.createScaledBitmap(bm, 120, 120, true);
                    selected_image.setImageBitmap(resizedbitmap);

                }
                if (progress > 1 && progress <= 2) {
                    resizedbitmap = Bitmap.createScaledBitmap(bm, 160, 160, true);
                    selected_image.setImageBitmap(resizedbitmap);

                }
                if (progress > 2 && progress <= 3) {
                    resizedbitmap = Bitmap.createScaledBitmap(bm, 200, 200, true);
                    selected_image.setImageBitmap(resizedbitmap);

                }
                if (progress > 3 && progress <= 4) {
                    resizedbitmap = Bitmap.createScaledBitmap(bm, 240, 240, true);
                    selected_image.setImageBitmap(resizedbitmap);

                }
                if (progress > 4 && progress <= 5) {
                    resizedbitmap = Bitmap.createScaledBitmap(bm, 280, 280, true);
                    selected_image.setImageBitmap(resizedbitmap);

                }
                if (progress > 5 && progress <= 6) {
                    resizedbitmap = Bitmap.createScaledBitmap(bm, 320, 320, true);
                    selected_image.setImageBitmap(resizedbitmap);

                }
                if (progress > 6 && progress <= 7) {
                    resizedbitmap = Bitmap.createScaledBitmap(bm, 360, 360, true);
                    selected_image.setImageBitmap(resizedbitmap);

                }
                if (progress > 7 && progress <= 8) {
                    resizedbitmap = Bitmap.createScaledBitmap(bm, 400, 400, true);
                    selected_image.setImageBitmap(resizedbitmap);

                }
                if (progress > 8 && progress <= 9) {
                    resizedbitmap = Bitmap.createScaledBitmap(bm, 440, 440, true);
                    selected_image.setImageBitmap(resizedbitmap);

                }
                if (progress > 9 && progress <= 10) {
                    resizedbitmap = Bitmap.createScaledBitmap(bm, 480, 480, true);
                    selected_image.setImageBitmap(resizedbitmap);

                }
                selected_image.setImageBitmap(resizedbitmap);
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

                try {

                    resizedbitmap = getBitmapFromView(image_relative_layout);


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
        });

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


}

