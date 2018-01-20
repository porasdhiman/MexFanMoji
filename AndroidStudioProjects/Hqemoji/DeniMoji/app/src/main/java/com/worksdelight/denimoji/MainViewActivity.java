package com.worksdelight.denimoji;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.worksdelight.denimoji.com.tag.trivialdrivesample.util.IabHelper;
import com.worksdelight.denimoji.com.tag.trivialdrivesample.util.IabResult;
import com.worksdelight.denimoji.com.tag.trivialdrivesample.util.Inventory;
import com.worksdelight.denimoji.com.tag.trivialdrivesample.util.Purchase;
import com.worksdelight.denimoji.stickers.MarginDecoration;
import com.worksdelight.denimoji.stickers.StickerHolder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * Created by worksdelight on 16/08/17.
 */

public class MainViewActivity extends Activity {
    ImageView purchase_button;

    private RecyclerView stickerView;
    FullViewAdapter stickerAdapter;

    SharedPreferences sp;
    SharedPreferences.Editor ed;
    int p;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view_layout);
        sp = getSharedPreferences("denimoji", Context.MODE_PRIVATE);
        ed = sp.edit();

        stickerView = (RecyclerView) findViewById(R.id.gif_view);
        stickerView.addItemDecoration(new MarginDecoration(this));
        stickerView.setHasFixedSize(true);
        stickerView.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        p=Integer.parseInt(getIntent().getExtras().getString("type"));
if(p==1){
    stickerAdapter = new FullViewAdapter(this, GlobalConstants.small_img1);
}else  if(p==2){
    stickerAdapter = new FullViewAdapter(this, GlobalConstants.small_img2);
        }else  if(p==3){
    stickerAdapter = new FullViewAdapter(this, GlobalConstants.small_img3);
}else{
    stickerAdapter = new FullViewAdapter(this, GlobalConstants.small_img4);
}

        stickerView.setAdapter(stickerAdapter);



    }



    //-----------------------------Adapter---------------------------------
    public class FullViewAdapter extends RecyclerView.Adapter<StickerHolder> {
        private final String TAG = "StickerAdapter";

        private Context c;
        private List<String> listImg = new ArrayList<String>();

        int img[];
        String resname;

        int value;

        ArrayList<HashMap<String, Integer>> imagesArray = new ArrayList<>();

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
            //final StickerData sticker = stickerDataList.get(position);

            try {

                holder.imageView.setImageResource(img[position]);

                if (p==1) {
                    holder.lock.setVisibility(View.GONE);
                    holder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            Intent intent = new Intent(c, ShareActivity.class);
                            intent.putExtra("pos", String.valueOf(position));
                            intent.putExtra("type","1");

                            c.startActivity(intent);
                        }
                    });
                } else if(p==2) {
                    holder.lock.setVisibility(View.GONE);
                    holder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            Intent intent = new Intent(c, ShareActivity.class);
                            intent.putExtra("pos", String.valueOf(position));
                            intent.putExtra("type","2");
                            c.startActivity(intent);
                        }
                    });


                }else if(p==3) {
                    holder.lock.setVisibility(View.GONE);
                    holder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            Intent intent = new Intent(c, ShareActivity.class);
                            intent.putExtra("pos", String.valueOf(position));
                            intent.putExtra("type","3");
                            c.startActivity(intent);
                        }
                    });


                }

                else {

                        holder.lock.setVisibility(View.GONE);
                        holder.imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                Intent intent = new Intent(c, ShareActivity.class);
                                intent.putExtra("pos", String.valueOf(position));
                                intent.putExtra("type","4");
                                c.startActivity(intent);
                            }
                        });


                }
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



   /* Gson gson = new Gson();
    String json = gson.toJson(list);
                    ed.putString(GlobalConstants.DATE_DATA, json);*/

    }

}
