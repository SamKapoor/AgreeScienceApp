package com.agriscienceapp.font;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by IGS on 1/23/2016.
 */
public class FontUtils {
    public static void setFontForDigits(Context contxt, View view, String type) {

        Typeface tf = null;
        if (type.compareTo("regular") == 0) {
            tf = Typeface.createFromAsset(contxt.getAssets(), "Shruti-font.ttf");
        }
        if (type.compareTo("bold") == 0) {
            tf = Typeface.createFromAsset(contxt.getAssets(), "Shruti-bold.ttf");
        }

        if (view != null) {
            if (view instanceof EditText) {
                // do what you want with Edittext
                EditText edittext = (EditText) view;
                edittext.setTypeface(tf);

            } else if (view instanceof TextView) {
                TextView textView = (TextView) view;
                // do what you want with textView
                textView.setTypeface(tf);

            } else if (view instanceof Button) {
                // do what you want with Button
                Button button = (Button) view;
                button.setTypeface(tf);
            }
        }
    }

    public static void setFontForText(Context contxt, View view, String type) {

        Typeface tf = null;
        if (type.compareTo("regular") == 0) {
            tf = Typeface.createFromAsset(contxt.getAssets(), "Shruti-font.ttf");
        }
        if (type.compareTo("bold") == 0) {
            tf = Typeface.createFromAsset(contxt.getAssets(), "Shruti-bold.ttf");
        }
        if (view != null) {
            if (view instanceof EditText) {
                // do what you want with Edittext
                EditText edittext = (EditText) view;
                edittext.setTypeface(tf);

            } else if (view instanceof TextView) {
                TextView textView = (TextView) view;
                // do what you want with textView
                textView.setTypeface(tf);

            } else if (view instanceof Button) {
                // do what you want with Button
                Button button = (Button) view;
                button.setTypeface(tf);
            }
        }
    }
}