package com.ayazalam.pratlipi;

import android.app.Application;
import android.content.Context;

/**
 * Created by Ayaz Alam on 2019/5/03.
 */

public class ContactsApplication extends Application{

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override public void onCreate() {
        super.onCreate();
        context = this;
    }
}
