package com.kii.hello.world;

import android.app.Application;

import com.kii.cloud.storage.Kii;

public class HelloKii extends Application {
	
    @Override
    public void onCreate() {
        super.onCreate();
                
        // initialize the Kii SDK!
        Kii.initialize("sandbox", "dummy", "http://dev-ufe-jp.internal.kii.com:12090");
    }
	
}
