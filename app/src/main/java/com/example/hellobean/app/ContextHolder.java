package com.example.hellobean.app;

import android.content.Context;

public final class ContextHolder {

    public static Context sContext;

    public static Context getContext() {
        return sContext;
    }

    public static void setContext(Context context) {
        sContext = context;
    }
}
