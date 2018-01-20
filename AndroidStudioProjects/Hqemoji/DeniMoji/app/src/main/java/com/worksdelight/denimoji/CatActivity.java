package com.worksdelight.denimoji;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.worksdelight.denimoji.com.tag.trivialdrivesample.util.IabHelper;
import com.worksdelight.denimoji.com.tag.trivialdrivesample.util.IabResult;
import com.worksdelight.denimoji.com.tag.trivialdrivesample.util.Inventory;
import com.worksdelight.denimoji.com.tag.trivialdrivesample.util.Purchase;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

/**
 * Created by worksdelight on 13/09/17.
 */

public class CatActivity extends Activity {
    LinearLayout saying_layout,comon_layout,feel_layout;
    RelativeLayout inside_layout;
    ImageView lock_denim_img;
    private static String PRODUCT_ID = "android.test.purchased";
    // private static String PRODUCT_ID = "";

    private static final String LICENSE_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnSS+Ap37Y3aSxxQz21i4vhBjbYIXNas9W1qGtoOK+TTiF3uuUZyuoqtOqvsyo96L9EQx7DYvT4zrPXHmL0y6FV/SVFQNzba+l6V87zLQT283zSdn3+l0Rd/0DJzrXmnP5L5J5dNWjDY4Yruv3I06h7kPWbii2amA/RX2QSIAwjE21wEc0wzlq0aAuCnqtlEbScXlo856bNj/DBzfhFtaJtnKHUm60XUMLWM7ECeqzGloAfVDflc3AtksLgZT38m7dteUCDPV7XICBVBDwZLQN3OsE2FYqRgirRCcZtQ4HY6Utkvlmu7JHauKZHUjzuxgbhnCm4Sj3BvykdOXvJdaAQIDAQAB"; // PUT

    ArrayList<HashMap<String, Integer>> imagesArray = new ArrayList<>();
    IabHelper mHelper;
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cat_layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        sp = getSharedPreferences("denimoji", Context.MODE_PRIVATE);
        ed = sp.edit();
        lock_denim_img=(ImageView)findViewById(R.id.lock_denim);
        inside_layout=(RelativeLayout) findViewById(R.id.inside_layout) ;
        saying_layout=(LinearLayout) findViewById(R.id.saying_layout) ;
        comon_layout=(LinearLayout) findViewById(R.id.comon_layout) ;
        feel_layout=(LinearLayout) findViewById(R.id.feel_layout) ;
        if(sp.getString("purchase_type","").equalsIgnoreCase("1")){
            lock_denim_img.setVisibility(View.GONE);
        }
        inside_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sp.getString("purchase_type","").equalsIgnoreCase("1")){
                    Intent intent = new Intent(CatActivity.this, MainViewActivity.class);
                    intent.putExtra("type","4");
                    startActivity(intent);
                }else{
                    mHelper.launchPurchaseFlow(CatActivity.this, PRODUCT_ID, 10001, mPurchaseFinishedListener,
                            "mypurchasetoken");
                }

            }
        });
        saying_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatActivity.this, MainViewActivity.class);
                intent.putExtra("type","3");
                startActivity(intent);
            }
        });
        comon_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatActivity.this, MainViewActivity.class);
                intent.putExtra("type","2");
                startActivity(intent);
            }
        });
        feel_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatActivity.this, MainViewActivity.class);
                intent.putExtra("type","1");
                startActivity(intent);
            }
        });
        String base64EncodedPublicKey = LICENSE_KEY;

        mHelper = new IabHelper(this, base64EncodedPublicKey);

        // enable debug logging (for a production application, you should set
        // this to false).
        mHelper.enableDebugLogging(true);

        Log.e(TAG, "Starting setup.");
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    Log.d(TAG, "In-app Billing setup failed: " + result);
                } else {
                    Log.d(TAG, "In-app Billing is set up OK");

                    mHelper.queryInventoryAsync(mReceivedInventoryListener);

                }
            }
        });
    }
    IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {

            if (result.isFailure()) {
                // Handle failure

            } else {

                Purchase gasPurchase = inventory.getPurchase(PRODUCT_ID);
                if (gasPurchase != null && verifyDeveloperPayload(gasPurchase)) {
                    Log.d(TAG, "We have product. Consuming it.");
                    mHelper.consumeAsync(inventory.getPurchase(PRODUCT_ID), mConsumeFinishedListener);
                    return;
                }
            }
        }
    };

    @Override
    public void onDestroy() {
        if (mHelper != null) {
            mHelper.dispose();
            mHelper = null;
        }

        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (mHelper == null)
            return;

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        } else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }

    }

    private void showToast(String message) {
        Toast.makeText(CatActivity.this, message, Toast.LENGTH_LONG).show();
    }

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);
            if (result.isFailure()) {

                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                showToast("Error purchasing. Authenticity verification failed.");
                return;
            }

            Log.d(TAG, "Purchase successful.");

            if (purchase.getSku().equals(PRODUCT_ID)) {

                mHelper.consumeAsync(purchase, mConsumeFinishedListener);

            }

        }
    };
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {

            if (result.isSuccess()) {
                ed.putString("purchase_type","1");
                ed.commit();
lock_denim_img.setVisibility(View.GONE);
            } else {
                // handle error

            }
        }
    };

    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();

        return true;
    }


}
