package com.erbeandroid.petfinder.feature.animal.common;

import android.view.View;

public class Extension {

    public static Integer isVisible(Boolean check) {
        return check ? View.VISIBLE : View.GONE;
    }
}