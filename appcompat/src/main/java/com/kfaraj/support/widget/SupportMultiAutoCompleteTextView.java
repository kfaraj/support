package com.kfaraj.support.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatMultiAutoCompleteTextView;
import android.util.AttributeSet;

import com.kfaraj.support.appcompat.R;
import com.kfaraj.support.view.TintableTextView;

/**
 * Adds support for {@link AppCompatMultiAutoCompleteTextView}.
 * <ul>
 * <li>{@link #setSupportCompoundDrawableTintList(ColorStateList)}</li>
 * <li>{@link #getSupportCompoundDrawableTintList()}</li>
 * <li>{@link #setSupportCompoundDrawableTintMode(PorterDuff.Mode)}</li>
 * <li>{@link #getSupportCompoundDrawableTintMode()}</li>
 * <li>{@link R.attr#drawableTint}</li>
 * <li>{@link R.attr#drawableTintMode}</li>
 * </ul>
 */
public class SupportMultiAutoCompleteTextView extends AppCompatMultiAutoCompleteTextView implements TintableTextView {

    /**
     * The helper.
     */
    private TextViewHelper mHelper;

    /**
     * Constructor
     *
     * @param context the context.
     */
    public SupportMultiAutoCompleteTextView(Context context) {
        super(context);
        init(context, null, 0);
    }

    /**
     * Constructor
     *
     * @param context the context.
     * @param attrs the attributes.
     */
    public SupportMultiAutoCompleteTextView(Context context, AttributeSet attrs) {
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
    public SupportMultiAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
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
    public void setCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom) {
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
    public void setCompoundDrawablesRelative(Drawable start, Drawable top, Drawable end, Drawable bottom) {
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