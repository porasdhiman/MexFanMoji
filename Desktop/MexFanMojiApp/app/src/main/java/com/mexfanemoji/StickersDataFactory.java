package com.mexfanemoji;

import java.util.ArrayList;
import java.util.List;

public class StickersDataFactory {
    public static List<Sticker> getAllStickerReference() {
        String[] stickerURLRef = {
               "https://firebasestorage.googleapis.com/v0/b/mexfanemoji.appspot.com/o/FirstPack%2Fsoccer_1.png?alt=media&token=9a643941-2551-4c41-9a14-ca47f5c4faec",
                "https://firebasestorage.googleapis.com/v0/b/mexfanemoji.appspot.com/o/FirstPack%2Fsoccer_2.png?alt=media&token=0fccde89-2c23-4897-9e68-3a75b4d6c5fd",
                "https://firebasestorage.googleapis.com/v0/b/mexfanemoji.appspot.com/o/FirstPack%2Fsoccer_3.png?alt=media&token=4a2e6736-76cf-487f-9220-652fa106638c",
                "https://firebasestorage.googleapis.com/v0/b/mexfanemoji.appspot.com/o/FirstPack%2Fsoccer_4.png?alt=media&token=d0209129-0661-4724-9d89-9da4ab89cceb",
                "https://firebasestorage.googleapis.com/v0/b/mexfanemoji.appspot.com/o/FirstPack%2Fsoccer_5.png?alt=media&token=7d621597-f3d9-4365-8d03-83a176528bbb",
                "https://firebasestorage.googleapis.com/v0/b/mexfanemoji.appspot.com/o/FirstPack%2Fsoccer_6.png?alt=media&token=e3540d90-d229-4a1f-b67b-f9eb5b7c847e",
                "https://firebasestorage.googleapis.com/v0/b/mexfanemoji.appspot.com/o/FirstPack%2Fbig_1.gif?alt=media&token=e7fedf5b-1a8b-4217-9006-7dfe366d2805",
                "https://firebasestorage.googleapis.com/v0/b/mexfanemoji.appspot.com/o/FirstPack%2Fbig_2.gif?alt=media&token=bc7b6d85-00e9-4495-9361-8763b197c85a",
                "https://firebasestorage.googleapis.com/v0/b/mexfanemoji.appspot.com/o/FirstPack%2Fbig_3.gif?alt=media&token=ee0a16aa-9144-442b-90d0-a4f7c07b5e44",
                "https://firebasestorage.googleapis.com/v0/b/mexfanemoji.appspot.com/o/FirstPack%2Fbig_4.gif?alt=media&token=1a625d78-aa9d-4fe1-9509-de9bd9b90260",
                "https://firebasestorage.googleapis.com/v0/b/mexfanemoji.appspot.com/o/FirstPack%2Fbig_6.gif?alt=media&token=56f0e3ef-5a22-477d-96f9-1c37b892f205",
                "https://firebasestorage.googleapis.com/v0/b/mexfanemoji.appspot.com/o/SecondPack%2Fg_1.png?alt=media&token=48fb6392-650d-4bcc-85d7-7fc1c6c9a18f",
                "https://firebasestorage.googleapis.com/v0/b/mexfanemoji.appspot.com/o/SecondPack%2Fg_1.png?alt=media&token=48fb6392-650d-4bcc-85d7-7fc1c6c9a18f",
                "https://firebasestorage.googleapis.com/v0/b/mexfanemoji.appspot.com/o/SecondPack%2Fg_3.png?alt=media&token=878be14c-3718-4c79-add2-66b7361178d2",
                "https://firebasestorage.googleapis.com/v0/b/mexfanemoji.appspot.com/o/SecondPack%2Fg_4.png?alt=media&token=8eb40869-3a99-4dd6-b900-bc5457713832",
                "https://firebasestorage.googleapis.com/v0/b/mexfanemoji.appspot.com/o/SecondPack%2Fg_6.png?alt=media&token=b64481fa-a08f-4d4f-8e05-f2d0f559427d"

        };
        List<Sticker> stickerList = new ArrayList<>();
        for (int i = 0; i < stickerURLRef.length; i++) {
            stickerList.add(new Sticker(stickerURLRef[i]));
        }
        return stickerList;
    }



}