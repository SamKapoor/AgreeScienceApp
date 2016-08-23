package com.agriscienceapp.font;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IGS on 1/23/2016.
 */
public class FontCache {
    private static Map<String, Typeface> sCachedFonts = new HashMap<>();

    public static Typeface getTypeface(Context context, String assetPath) {
        if (!sCachedFonts.containsKey(assetPath)) {
            Typeface tf = Typeface.createFromAsset(context.getAssets(), assetPath);
            sCachedFonts.put(assetPath, tf);
        }

        return sCachedFonts.get(assetPath);
    }
}
