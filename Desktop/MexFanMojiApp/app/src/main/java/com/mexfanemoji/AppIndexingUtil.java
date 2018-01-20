package com.mexfanemoji;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.appindexing.FirebaseAppIndex;
import com.google.firebase.appindexing.FirebaseAppIndexingInvalidArgumentException;
import com.google.firebase.appindexing.Indexable;
import com.google.firebase.appindexing.builders.Indexables;
import com.google.firebase.appindexing.builders.StickerBuilder;
import com.google.firebase.appindexing.builders.StickerPackBuilder;

import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * See firebase app indexing api code lab
 * https://codelabs.developers.google.com/codelabs/app-indexing/#0
 */

public class AppIndexingUtil {
    private static final String STICKER_FILENAME_PATTERN = "sticker%s.png";

    private static final String STICKER_URL_PATTERN_First = "mystickers://sticker/1/%s";

    private static final String STICKER_PACK_URL_PATTERN_First = "mystickers://sticker/pack/1/%s";

    private static final String STICKER_PACK_NAME_FIRST = "First pack";


    private static final String TAG = "AppIndexingUtil";
    public static final String FAILED_TO_CLEAR_STICKERS = "Failed to clear stickers";
    public static final String FAILED_TO_INSTALL_STICKERS = "Failed to install stickers";

    int j;

    List<Sticker> stickerList = new ArrayList<>();
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    List<Indexable> indexableStickers = new ArrayList<>();
    StickerPackBuilder stickerPackBuilder;
  List<Indexable> indexables;

    public void setStickers(final Context context, FirebaseAppIndex firebaseAppIndex) {

        try {
            sp = context.getSharedPreferences(context.getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
            ed = sp.edit();



                List<Indexable> stickers=getIndexableStickers(context);
                Log.e("sticker size", stickers.size() + "");
                Indexable stickerPack = getIndexableStickerPack(context);


                indexables = new ArrayList<>(stickers);
                Log.e("sticker size", indexables.size() + "");
                indexables.add(stickerPack);

                // indexables.add(stickerPack);
                Log.e("indexable", indexables.size()+"");
                Task<Void> task = firebaseAppIndex.update(indexables.toArray(new Indexable[indexables.size()]));

                task.addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Successfully added stickers", Toast.LENGTH_SHORT)
                                .show();

                    }
                });

                task.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, FAILED_TO_INSTALL_STICKERS, e);
                        Toast.makeText(context, FAILED_TO_INSTALL_STICKERS, Toast.LENGTH_SHORT)
                                .show();
                    }
                });

        } catch (IOException | FirebaseAppIndexingInvalidArgumentException e) {
            Log.e(TAG, "Unable to set stickers", e);
        }
    }


    private Indexable getIndexableStickerPack(Context context)
            throws IOException, FirebaseAppIndexingInvalidArgumentException {

        List<StickerBuilder> stickerBuilders = getStickerBuilders(context);



            int last = 0;
            String Name = getStickerFilename(StickersDataFactory.getAllStickerReference(), last);
            String Url = StickersDataFactory.getAllStickerReference().get(last).getURL();

            stickerPackBuilder = Indexables.stickerPackBuilder()

                    .setName(STICKER_PACK_NAME_FIRST)
                    // Firebase App Indexing unique key that must match an intent-filter.
                    // (e.g. mystickers://sticker/pack/0)
                    .setUrl(String.format(STICKER_PACK_URL_PATTERN_First, last))
                    // Defaults to the first sticker in "hasSticker". Used to select between sticker
                    // packs so should be representative of the sticker pack.
                    .setImage(Url)
                    .setHasSticker(stickerBuilders.toArray(new StickerBuilder[stickerBuilders.size()]))



                    .setDescription("content description");



        return stickerPackBuilder.build();
    }

    private List<Indexable> getIndexableStickers(Context context) throws IOException,
            FirebaseAppIndexingInvalidArgumentException {
        List<StickerBuilder> stickerBuilders = getStickerBuilders(context);



            for (StickerBuilder stickerBuilder : stickerBuilders) {
                stickerBuilder

                        .setIsPartOf(Indexables.stickerPackBuilder().setName(STICKER_PACK_NAME_FIRST))

                        .put("keywords", "tag1", "tag2");

                indexableStickers.add(stickerBuilder.build());
            }




        return indexableStickers;
    }

    private List<StickerBuilder> getStickerBuilders(Context context) throws IOException {
        List<StickerBuilder> stickerBuilders = new ArrayList<>();



            for (int i = 0; i < StickersDataFactory.getAllStickerReference().size(); i++) {
                String stickerFilename = getStickerFilename(StickersDataFactory.getAllStickerReference(), i);
                Log.e("name", stickerFilename);
                String imageUrl = StickersDataFactory.getAllStickerReference().get(i).getURL();


                StickerBuilder stickerBuilder = null;

                stickerBuilder = Indexables.stickerBuilder()
                        .setName(stickerFilename)
                        // Firebase App Indexing unique key that must match an intent-filter
                        // (e.g. mystickers://sticker/0)
                        .setUrl(String.format(STICKER_URL_PATTERN_First, i))
                        // http url or content uri that resolves to the sticker
                        // (e.g. http://www.google.com/sticker.png or content://some/path/0)
                        .setImage(imageUrl)
                        .setDescription("content description")

                        .setIsPartOf(Indexables.stickerPackBuilder()
                                .setName(STICKER_PACK_NAME_FIRST))
                        .put("keywords", "tag1", "tag2");

                stickerBuilders.add(stickerBuilder);
            }



        return stickerBuilders;
    }

    private String getStickerFilename(List<Sticker> stickerList, int index) {
        URL url = null;
        try {
            url = new URL(stickerList.get(index).getURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        String filenameWithoutExtension = FilenameUtils.getName(url.getPath());
        int slashIndex = filenameWithoutExtension.lastIndexOf('2');
        String name = filenameWithoutExtension.substring(slashIndex + 2);
        return name;
    }


}