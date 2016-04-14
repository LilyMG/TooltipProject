package com.example.lilittevosyan.tooltipproject;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;


public class SettingsSeekBar extends ViewGroup {
    public static final String LONGEST_VALUE_TEXT = "-000";

    private TextView titleTextView;
    private SeekBar seekBar;
    private TextView valueTextView;
    private CharSequence valueTextViewValue;

    private int seekBarWidth;
    private int seekBarX;
    private int actualMeasuredWidth;
    private boolean compactMode;
    private boolean forceCompactMode;

    private float seekBarWidthKoef;
    private int portraitWidth;
    private boolean multiColumn;
    private int forceWidth;
    private int defaultHeight;


    public SettingsSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        initViews(context);

//        initAttributes(context, attrs);
//
//        initProps(context);
    }

    protected void initViews(Context context) {
        seekBar = createSeekBar(context);
        addView(seekBar, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        titleTextView = new TextView(context);
//		titleTextView.setPadding(seekBar.getPaddingLeft(), titleTextView.getPaddingTop(), titleTextView.getPaddingRight(), titleTextView.getPaddingBottom());
        addView(titleTextView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        valueTextView = new TextView(context);
        addView(valueTextView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    }

    protected SeekBar createSeekBar(Context context) {
        return new AppCompatSeekBar(context);
    }

//    protected void initAttributes(Context context, AttributeSet attrs) {
//        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SettingsSeekBar);
//
//        CharSequence title = a.getString(R.styleable.SettingsSeekBar_settingsTitle);
//        if (title != null) {
//            setTitle(title);
//        }
//
//        CharSequence value = a.getString(R.styleable.SettingsSeekBar_settingsValue);
//        if (value != null) {
//            setValue(value);
//        }
//
//        int max = a.getInt(R.styleable.SettingsSeekBar_settingsMax, 100);
//        seekBar.setMax(max);
//
//        int progress = a.getInt(R.styleable.SettingsSeekBar_settingsProgress, 100);
//        seekBar.setProgress(progress);
//
//
//        multiColumn = a.getBoolean(R.styleable.SettingsSeekBar_settingsMultiColumn, false);
//
//        a.recycle();
//    }

//    public void initProps(Context context) {
//        boolean landscapeMode = Utils.isInLandscapeMode(context);
//
//        seekBarWidthKoef = (landscapeMode && multiColumn) ? 0.7f : 0.6f;
//        if (context instanceof Activity) {
//            portraitWidth = (int) Math.min(Utils.getScreenWidthInPx((Activity) context), Utils.getScreenHeightInPx((Activity) context));
//        }
//
//        // height
//        if (!landscapeMode) {
//            defaultHeight = (int) Utils.convertDpToPixel(48, context);
//        } else {
//            defaultHeight = (int) Utils.convertDpToPixel(36, context);
//        }
//    }

    public void setForceCompactMode(boolean forceCompactMode) {
        this.forceCompactMode = forceCompactMode;
        // only change compactMode when forced is true
        if (forceCompactMode) {
            this.compactMode = true;
        }
    }

    public void setForceWidth(int forceWidth) {
        this.forceWidth = forceWidth;
    }


    public void setTitle(CharSequence title) {
        titleTextView.setText(title);

        invalidate();
        requestLayout();
    }

    public void setValue(CharSequence value) {
        valueTextViewValue = value;
        valueTextView.setText(value);
    }

    public TextView getTitleTextView() {
        return titleTextView;
    }

    public void setOnSeekBarChangeListener(SeekBar.OnSeekBarChangeListener l) {
        seekBar.setOnSeekBarChangeListener(l);
    }

    public void setProgress(int progress) {
        seekBar.setProgress(progress);
    }

    protected SeekBar getSeekBar() {
        return seekBar;
    }

    protected int getSeekBarX() {
        return seekBarX;
    }

    protected int getSeekBarWidth() {
        return seekBarWidth;
    }

    /**
     * @see View#measure(int, int)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // measure with longest value
        valueTextView.setText(LONGEST_VALUE_TEXT);

        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));

        valueTextView.setText(valueTextViewValue);
    }

    private int measureHeight(int heightMeasureSpec) {
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            return specSize;
        }

        if (!compactMode) {
            return Math.max(defaultHeight, Math.max(titleTextView.getMeasuredHeight(), seekBar.getMeasuredHeight()));
        } else {
            return Math.max(defaultHeight, Math.max(valueTextView.getMeasuredHeight(), seekBar.getMeasuredHeight()) + titleTextView.getMeasuredHeight());
        }
    }

    /**
     * Determines the width of this view
     * @param measureSpec A measureSpec packed into an int
     * @return The width of the view, honoring constraints from measureSpec
     */
    private int measureWidth(int measureSpec) {

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (forceWidth > 0 && specMode != MeasureSpec.EXACTLY) {
            return forceWidth;
        }

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            calculateMeasuredWidth(specSize);
            return specSize;
        }

        int result = calculateMeasuredWidth(specSize);

        if (specMode == MeasureSpec.AT_MOST) {
            // Respect AT_MOST value if that was what is called for by measureSpec
            return Math.min(result, specSize);
        }
        // MeasureSpec.UNSPECIFIED
        return result;
    }

    private int calculateMeasuredWidth(int specSize) {
        int result;
        seekBarWidth = (int) (seekBarWidthKoef * portraitWidth);

        // by default
        result = seekBarWidth + titleTextView.getMeasuredWidth() + valueTextView.getMeasuredWidth() + getPaddingLeft() + getPaddingRight();

        // if not forced we decide compact mode
        if (!forceCompactMode && specSize > 0) {
            compactMode = result > (0.85 * specSize);
        }

        if (compactMode) {
            if (multiColumn) {
                // this case is portrait mode or multi-column mode, so we decide seekBar width.
                int totalWidth = (int) (0.75 * specSize);
                seekBarWidth = totalWidth - valueTextView.getMeasuredWidth();
            }
            result = seekBarWidth + valueTextView.getMeasuredWidth() + getPaddingLeft() + getPaddingRight();
        }

        // store the actual measured width
        actualMeasuredWidth = result;
        return result;
    }

    public int getActualMeasuredWidth() {
        return actualMeasuredWidth;
    }

    public boolean isCompactMode() {
        return compactMode;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (!compactMode) {
            // seek-bar
            setChildFrame(seekBar, seekBarX, (getMeasuredHeight() - seekBar.getMeasuredHeight()) / 2, seekBarWidth, seekBar.getMeasuredHeight());

            // title
            final int widthTitle = titleTextView.getMeasuredWidth();
            final int heightTitle = titleTextView.getMeasuredHeight();
            setChildFrame(titleTextView, seekBarX - widthTitle, (getMeasuredHeight() - titleTextView.getMeasuredHeight()) / 2, widthTitle, heightTitle);

            // value
            final int widthValue = valueTextView.getMeasuredWidth();
            final int heightValue = valueTextView.getMeasuredHeight();
            int centerYDiff = (getMeasuredHeight() - valueTextView.getMeasuredHeight()) / 2;
            setChildFrame(valueTextView, seekBarX + seekBarWidth, centerYDiff, widthValue, heightValue);
        } else {
            // title
            final int heightTitle = titleTextView.getMeasuredHeight();
            setChildFrame(titleTextView, seekBarX + seekBar.getPaddingLeft(), 0, titleTextView.getMeasuredWidth(), heightTitle);

            // seek-bar
            setChildFrame(seekBar, seekBarX, heightTitle, seekBarWidth, seekBar.getMeasuredHeight());

            // value
            int centerYDiff = (seekBar.getMeasuredHeight() - valueTextView.getMeasuredHeight()) / 2;
            setChildFrame(valueTextView, seekBarX + seekBarWidth, heightTitle + centerYDiff, valueTextView.getMeasuredWidth(), valueTextView.getMeasuredHeight());
        }

        forceWidth = 0;
    }

    protected static void setChildFrame(View child, int left, int top, int width, int height) {
        child.layout(left, top, left + width, top + height);
    }


    public void setSeekBarX(int seekBarX) {
        this.seekBarX = seekBarX;
    }

    public int getProgress() {
        return seekBar.getProgress();
    }

    public void setMax(int max) {
        this.seekBar.setMax(max);
    }

}