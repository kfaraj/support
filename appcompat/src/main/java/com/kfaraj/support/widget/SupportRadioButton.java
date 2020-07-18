package com.kfaraj.support.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatRadioButton;

import com.kfaraj.support.appcompat.R;
import com.kfaraj.support.view.TintableTextView;

/**
 * Adds support for compound drawables tint.
 * <ul>
 * <li>{@link #setSupportCompoundDrawableTintList(ColorStateList)}</li>
 * <li>{@link #getSupportCompoundDrawableTintList()}</li>
 * <li>{@link #setSupportCompoundDrawableTintMode(PorterDuff.Mode)}</li>
 * <li>{@link #getSupportCompoundDrawableTintMode()}</li>
 * <li>{@link R.attr#drawableTint}</li>
 * <li>{@link R.attr#drawableTintMode}</li>
 * </ul>
 */
public class SupportRadioButton extends AppCompatRadioButton
        implements TintableTextView {

    private TextViewHelper mHelper;

    /**
     * Constructor.
     *
     * @param context the context.
     */
    public SupportRadioButton(@NonNull Context context) {
        super(context);
        init(context, null, 0);
    }

    /**
     * Constructor.
     *
     * @param context the context.
     * @param attrs the attributes.
     */
    public SupportRadioButton(@NonNull Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    /**
     * Constructor.
     *
     * @param context the context.
     * @param attrs the attributes.
     * @param defStyleAttr the default style attribute.
     */
    public SupportRadioButton(@NonNull Context context,
            @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    /**
     * Common code for different constructors.
     *
     * @param context the context.
     * @param attrs the attributes.
     * @param defStyleAttr the default style attribute.
     */
    private void init(@NonNull Context context,
            @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        mHelper = new TextViewHelper(this);
        mHelper.loadFromAttributes(context, attrs, defStyleAttr);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSupportCompoundDrawableTintList(@Nullable ColorStateList tint) {
        mHelper.setSupportCompoundDrawableTintList(tint);
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public ColorStateList getSupportCompoundDrawableTintList() {
        return mHelper.getSupportCompoundDrawableTintList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSupportCompoundDrawableTintMode(@Nullable PorterDuff.Mode tintMode) {
        mHelper.setSupportCompoundDrawableTintMode(tintMode);
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public PorterDuff.Mode getSupportCompoundDrawableTintMode() {
        return mHelper.getSupportCompoundDrawableTintMode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCompoundDrawables(@Nullable Drawable left, @Nullable Drawable top,
            @Nullable Drawable right, @Nullable Drawable bottom) {
        if (mHelper != null) {
            left = mHelper.applySupportCompoundDrawableTint(left);
            top = mHelper.applySupportCompoundDrawableTint(top);
            right = mHelper.applySupportCompoundDrawableTint(right);
            bottom = mHelper.applySupportCompoundDrawableTint(bottom);
        }
        super.setCompoundDrawables(left, top, right, bottom);
    }

    /**
     * {@inheritDoc}
     */
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void setCompoundDrawablesRelative(@Nullable Drawable start, @Nullable Drawable top,
            @Nullable Drawable end, @Nullable Drawable bottom) {
        if (mHelper != null) {
            start = mHelper.applySupportCompoundDrawableTint(start);
            top = mHelper.applySupportCompoundDrawableTint(top);
            end = mHelper.applySupportCompoundDrawableTint(end);
            bottom = mHelper.applySupportCompoundDrawableTint(bottom);
        }
        super.setCompoundDrawablesRelative(start, top, end, bottom);
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
