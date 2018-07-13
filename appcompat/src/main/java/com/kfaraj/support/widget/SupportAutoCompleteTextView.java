package com.kfaraj.support.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.AttributeSet;

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
public class SupportAutoCompleteTextView extends AppCompatAutoCompleteTextView
        implements TintableTextView {

    /**
     * The helper.
     */
    private TextViewHelper mHelper;

    /**
     * Constructor.
     *
     * @param context the context.
     */
    public SupportAutoCompleteTextView(@NonNull Context context) {
        super(context);
        init(context, null, 0);
    }

    /**
     * Constructor.
     *
     * @param context the context.
     * @param attrs the attributes.
     */
    public SupportAutoCompleteTextView(@NonNull Context context,
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
    public SupportAutoCompleteTextView(@NonNull Context context,
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
            left = mHelper.applyTint(left);
            top = mHelper.applyTint(top);
            right = mHelper.applyTint(right);
            bottom = mHelper.applyTint(bottom);
        }
        super.setCompoundDrawables(left, top, right, bottom);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCompoundDrawablesRelative(@Nullable Drawable start, @Nullable Drawable top,
            @Nullable Drawable end, @Nullable Drawable bottom) {
        if (mHelper != null) {
            start = mHelper.applyTint(start);
            top = mHelper.applyTint(top);
            end = mHelper.applyTint(end);
            bottom = mHelper.applyTint(bottom);
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
