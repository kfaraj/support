package com.kfaraj.support.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION_CODES;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.kfaraj.support.appcompat.R;
import com.kfaraj.support.util.DrawableUtils;
import com.kfaraj.support.util.TypedArrayUtils;
import com.kfaraj.support.view.TintableTextView;

/**
 * Helper class for {@link TextView}.
 */
class TextViewHelper implements TintableTextView {

    /**
     * The text view.
     */
    @NonNull
    private final TextView mTextView;

    /**
     * The tint.
     */
    private ColorStateList mTint = null;

    /**
     * The tint mode.
     */
    private PorterDuff.Mode mTintMode = PorterDuff.Mode.SRC_IN;

    /**
     * Constructor
     *
     * @param textView the text view.
     */
    TextViewHelper(@NonNull TextView textView) {
        mTextView = textView;
    }

    /**
     * Loads the attributes from XML.
     *
     * @param context the context.
     * @param attrs the attributes.
     * @param defStyleAttr the default style.
     */
    void loadFromAttributes(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextView, defStyleAttr, 0);
        if (a.hasValue(R.styleable.TextView_drawableTint)) {
            ColorStateList tint = a.getColorStateList(R.styleable.TextView_drawableTint);
            setSupportCompoundDrawableTintList(tint);
        }
        if (a.hasValue(R.styleable.TextView_drawableTintMode)) {
            PorterDuff.Mode tintMode = TypedArrayUtils.getTintMode(a, R.styleable.TextView_drawableTintMode, mTintMode);
            setSupportCompoundDrawableTintMode(tintMode);
        }
        a.recycle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSupportCompoundDrawableTintList(@Nullable ColorStateList tint) {
        mTint = tint;
        applyTint();
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public ColorStateList getSupportCompoundDrawableTintList() {
        return mTint;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSupportCompoundDrawableTintMode(@Nullable PorterDuff.Mode tintMode) {
        mTintMode = tintMode;
        applyTint();
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public PorterDuff.Mode getSupportCompoundDrawableTintMode() {
        return mTintMode;
    }

    /**
     * Applies the tint to the drawable.
     *
     * @param drawable the drawable.
     * @return the tinted drawable.
     */
    Drawable applyTint(@Nullable Drawable drawable) {
        return DrawableUtils.applyTint(drawable, mTint, mTintMode);
    }

    /**
     * Applies the tint to the compound drawables.
     */
    @TargetApi(VERSION_CODES.JELLY_BEAN_MR1)
    private void applyTint() {
        try {
            Drawable[] drawables = mTextView.getCompoundDrawablesRelative();
            if (drawables[0] != null || drawables[2] != null) {
                mTextView.setCompoundDrawablesRelative(drawables[0], drawables[1], drawables[2], drawables[3]);
                return;
            }
        } catch (NoSuchMethodError ignored) {
        }
        Drawable[] drawables = mTextView.getCompoundDrawables();
        mTextView.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
    }

}
