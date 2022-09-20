package com.kfaraj.support.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.core.widget.TextViewCompat;

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
 *
 * @deprecated Use {@link AppCompatAutoCompleteTextView} instead.
 */
@Deprecated
public class SupportAutoCompleteTextView extends AppCompatAutoCompleteTextView
        implements TintableTextView {

    /**
     * Constructor.
     *
     * @param context the context.
     */
    public SupportAutoCompleteTextView(@NonNull Context context) {
        super(context);
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
    }

    /**
     * {@inheritDoc}
     *
     * @deprecated Use
     * {@link TextViewCompat#setCompoundDrawableTintList(TextView, ColorStateList)}
     * instead.
     */
    @Deprecated
    @Override
    public void setSupportCompoundDrawableTintList(@Nullable ColorStateList tint) {
        TextViewCompat.setCompoundDrawableTintList(this, tint);
    }

    /**
     * {@inheritDoc}
     *
     * @deprecated Use
     * {@link TextViewCompat#getCompoundDrawableTintList(TextView)}
     * instead.
     */
    @Deprecated
    @Nullable
    @Override
    public ColorStateList getSupportCompoundDrawableTintList() {
        return TextViewCompat.getCompoundDrawableTintList(this);
    }

    /**
     * {@inheritDoc}
     *
     * @deprecated Use
     * {@link TextViewCompat#setCompoundDrawableTintMode(TextView, Mode)}
     * instead.
     */
    @Deprecated
    @Override
    public void setSupportCompoundDrawableTintMode(@Nullable PorterDuff.Mode tintMode) {
        TextViewCompat.setCompoundDrawableTintMode(this, tintMode);
    }

    /**
     * {@inheritDoc}
     *
     * @deprecated Use
     * {@link TextViewCompat#getCompoundDrawableTintMode(TextView)}
     * instead.
     */
    @Deprecated
    @Nullable
    @Override
    public PorterDuff.Mode getSupportCompoundDrawableTintMode() {
        return TextViewCompat.getCompoundDrawableTintMode(this);
    }

}
