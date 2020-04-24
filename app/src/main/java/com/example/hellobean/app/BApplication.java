package com.example.hellobean.app;

import android.app.Application;

import com.example.hellobean.style.AppResourcesUtils;
import com.example.hellobean.style.SkinStyleManager;

public class BApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ContextHolder.setContext(this);

        SkinStyleManager.getInstance().readLocalSaveSkinStyle();

        AppResourcesUtils.init(this);
    }
}
