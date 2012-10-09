package com.kii.world;

import android.app.Application;

import com.kii.cloud.storage.Kii;

public class HelloKii extends Application {
	
    @Override
    public void onCreate() {
        super.onCreate();
                
        // initialize the Kii SDK!
        Kii.initialize("__KII_APP_ID__", "__KII_APP_KEY__", __KII_APP_SITE__);
    }
	
}
