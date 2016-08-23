package com.agriscienceapp.font;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Button;

import com.agriscienceapp.R;

/**
 * Created by IGS on 1/27/2016.
 */
public class AgriScienceButton extends Button {

    private static final int Shruti_regular = 1;
    private static final int Shruti_bold = 2;

    public AgriScienceButton(Context context) {
        super(context);
    }

    public AgriScienceButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            parseAttributes(context, attrs);
        }
    }

    public AgriScienceButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            parseAttributes(context, attrs);
        }
    }

    private void parseAttributes(Context context, AttributeSet attrs) {
        TypedArray values = context.obtainStyledAttributes(attrs, R.styleable.AgriScienceTextView);

        int typeface = values.getInt(R.styleable.AgriScienceTextView_typeface, 0);

        switch (typeface) {
            default:
                break;
            case Shruti_regular:
                setTypeface(FontCache.getTypeface(context, "Shruti-font.ttf"));
                break;
            case Shruti_bold:
                setTypeface(FontCache.getTypeface(context, "Shruti-bold.ttf"));

        }
        values.recycle();
    }

}
