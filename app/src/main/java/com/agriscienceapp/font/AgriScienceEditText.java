package com.agriscienceapp.font;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.EditText;

import com.agriscienceapp.R;

/**
 * Created by IGS on 1/23/2016.
 */
public class AgriScienceEditText extends EditText {

    private static final int Shruti_regular = 1;
    private static final int Shruti_bold = 2;

    public AgriScienceEditText(Context context) {
        super(context);
    }

    public AgriScienceEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            parseAttributes(context, attrs);
        }
    }

    public AgriScienceEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            parseAttributes(context, attrs);
        }
    }

    private void parseAttributes(Context context, AttributeSet attrs) {
        TypedArray values = context.obtainStyledAttributes(attrs, R.styleable.AgriScienceTextView);

        //The value 0 is a default, but shouldn't ever be used since the attr is an enum
        int typeface = values.getInt(R.styleable.AgriScienceTextView_typeface, 1);

        switch (typeface) {
            default:
                break;
            case Shruti_regular:
                setTypeface(FontCache.getTypeface(context, "Shruti-font.ttf"));
                break;
            case Shruti_bold:
                setTypeface(FontCache.getTypeface(context, "Shruti-bold.ttf"));
                break;
        }
        values.recycle();
    }
}
