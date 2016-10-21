package com.kfaraj.support.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;

import com.kfaraj.support.appcompat.R;
import com.kfaraj.support.view.TintableImageView;

/**
 * Adds support for {@link AppCompatImageButton}.
 * <ul>
 * <li>{@link #setSupportImageTintList(ColorStateList)}</li>
 * <li>{@link #getSupportImageTintList()}</li>
 * <li>{@link #setSupportImageTintMode(PorterDuff.Mode)}</li>
 * <li>{@link #getSupportImageTintMode()}</li>
 * <li>{@link R.attr#tint}</li>
 * <li>{@link R.attr#tintMode}</li>
 * </ul>
 */
public class SupportImageButton extends AppCompatImageButton implements TintableImageView {

    /**
     * The helper.
     */
    private ImageViewHelper mHelper;

    /**
     * Constructor
     *
     * @param context the context.
     */
    public SupportImageButton(Context context) {
        super(context);
        init(context, null, 0);
    }

    /**
     * Constructor
     *
     * @param context the context.
     * @param attrs the attributes.
     */
    public SupportImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    /**
     * Constructor
     *
     * @param context the context.
     * @param attrs the attributes.
     * @param defStyleAttr the default style.
     */
    public SupportImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    /**
     * Common code for different constructors.
     *
     * @param context the context.
     * @param attrs the attributes.
     * @param defStyleAttr the default style.
     */
    private void init(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        mHelper = new ImageViewHelper(this);
        mHelper.loadFromAttributes(context, attrs, defStyleAttr);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSupportImageTintList(@Nullable ColorStateList tint) {
        mHelper.setSupportImageTintList(tint);
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public ColorStateList getSupportImageTintList() {
        return mHelper.getSupportImageTintList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSupportImageTintMode(@Nullable PorterDuff.Mode tintMode) {
        mHelper.setSupportImageTintMode(tintMode);
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public PorterDuff.Mode getSupportImageTintMode() {
        return mHelper.getSupportImageTintMode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setImageDrawable(Drawable drawable) {
        if (mHelper != null) {
            drawable = mHelper.applyTint(drawable);
        }
        super.setImageDrawable(drawable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (mHelper != null) {
            invalidate();
        }
    }

}
