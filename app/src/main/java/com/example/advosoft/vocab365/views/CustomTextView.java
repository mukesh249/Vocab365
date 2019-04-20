package com.example.advosoft.vocab365.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;

import com.example.advosoft.vocab365.R;


/**
 * Created by Adminis on 3/3/2016.
 */
public class CustomTextView extends android.support.v7.widget.AppCompatTextView
{
    /*public CustomTextView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        if(!isInEditMode())
            init(attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        if(!isInEditMode())
            init(attrs);
    }

    public CustomTextView(Context context) {
        super(context);
        if(!isInEditMode())
            init(null);
    }

    private void init(AttributeSet attrs)
    {
        if (attrs!=null)
        {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.custom_text_style);
            String fontName = a.getString(R.styleable.custom_text_style_fontFace);
            //String textColor = a.getString(R.styleable.custom_text_style_textColor);
            //String hintTextColor = a.getString(R.styleable.custom_text_style_hintTextColor);

            if (fontName!=null)
            {
                Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/"+fontName);
                setTypeface(myTypeface);
            }

			*//*			if(textColor!=null)
				this.setTextColor(Color.parseColor(textColor));
			else
				this.setTextColor(getResources().getColor(R.color.app_theme_text_color));

			if(hintTextColor!=null)
				this.setHintTextColor(Color.parseColor(hintTextColor));
			else
				this.setHintTextColor(getResources().getColor(R.color.app_theme_text_color));*//*

            a.recycle();
        }
    }*/

    private static final String TAG = "CustomTextView";

    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        String customFont = a.getString(R.styleable.CustomTextView_fontFamily);
        setCustomFont(ctx, customFont);
        a.recycle();
    }

    public boolean setCustomFont(Context ctx, String asset) {
        Typeface tf = null;
        try {
            tf = Typeface.createFromAsset(ctx.getAssets(), asset);
        } catch (Exception e) {
            Log.e(TAG, "Could not get typeface: " + e.getMessage());
            return false;
        }

        setTypeface(tf);
        return true;
    }
}
