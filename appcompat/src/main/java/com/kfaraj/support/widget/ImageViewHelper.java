package com.kfaraj.support.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.kfaraj.support.appcompat.R;
import com.kfaraj.support.util.DrawableUtils;
import com.kfaraj.support.util.TypedArrayUtils;
import com.kfaraj.support.view.TintableImageView;

/**
 * Helper class for {@link ImageView}.
 */
class ImageViewHelper implements TintableImageView {

    /**
     * The image view.
     */
    @NonNull
    private final ImageView mImageView;

    /**
     * The tint.
     */
    @Nullable
    private ColorStateList mTint = null;

    /**
     * The tint mode.
     */
    @Nullable
    private PorterDuff.Mode mTintMode = PorterDuff.Mode.SRC_IN;

    /**
     * Constructor
     *
     * @param imageView the image view.
     */
    ImageViewHelper(@NonNull ImageView imageView) {
        mImageView = imageView;
    }

    /**
     * Loads the attributes from XML.
     *
     * @param context the context.
     * @param attrs the attributes.
     * @param defStyleAttr the default style.
     */
    void loadFromAttributes(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ImageView, defStyleAttr, 0);
        if (a.hasValue(R.styleable.ImageView_tint)) {
            ColorStateList tint = a.getColorStateList(R.styleable.ImageView_tint);
            setSupportImageTintList(tint);
        }
        if (a.hasValue(R.styleable.ImageView_tintMode)) {
            PorterDuff.Mode tintMode = TypedArrayUtils.getTintMode(a, R.styleable.ImageView_tintMode, mTintMode);
            setSupportImageTintMode(tintMode);
        }
        a.recycle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSupportImageTintList(@Nullable ColorStateList tint) {
        mTint = tint;
        applyTint();
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public ColorStateList getSupportImageTintList() {
        return mTint;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSupportImageTintMode(@Nullable Mode tintMode) {
        mTintMode = tintMode;
        applyTint();
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public Mode getSupportImageTintMode() {
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
     * Applies the tint to the image drawable.
     */
    private void applyTint() {
        Drawable drawable = mImageView.getDrawable();
        mImageView.setImageDrawable(drawable);
    }

}
