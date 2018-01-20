package com.mexfanemoji;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;

import com.google.firebase.appindexing.FirebaseAppIndex;

public class AppIndexingUpdateService extends JobIntentService {
    // Job-ID must be unique across your whole app.
    private  final int UNIQUE_JOB_ID = 42;

    Context context;
    public  void enqueueWork(Context context) {
        this.context=context;

        enqueueWork(context, AppIndexingUpdateService.class, UNIQUE_JOB_ID, new Intent());
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {

            AppIndexingUtil appIndexingUtil = new AppIndexingUtil();
            appIndexingUtil.setStickers(getApplicationContext(), FirebaseAppIndex.getInstance());

    }
}