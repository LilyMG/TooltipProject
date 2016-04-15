package com.example.lilittevosyan.tooltipproject;

import android.animation.LayoutTransition;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by LilitTevosyan on 4/14/16.
 */
public class ToolTipLinear extends LinearLayout {

    public static final int ARROW_POSITION_NONE = 0;
    public static final int ARROW_POSITION_TOP_LEFT = 1;
    public static final int ARROW_POSITION_TOP_CENTER = 2;
    public static final int ARROW_POSITION_TOP_RIGHT = 3;
    public static final int ARROW_POSITION_BOTTOM_LEFT = 4;
    public static final int ARROW_POSITION_BOTTOM_CENTER = 5;
    public static final int ARROW_POSITION_BOTTOM_RIGHT = 6;

    public static final int TOOLTIP_POSITION_UP = 7;
    public static final int TOOLTIP_POSITION_DOWN = 8;


    private TextView titleTextView;
    private Context context;
    private LayoutInflater layoutInflater;
    private ViewGroup rootView;
    private ViewGroup parentView;
    private View anchorView;
    private ImageView arrow;
    private int arrowPosition = 0;
    private int tooltipPosition = 0;
    private boolean dismissFromOutside = false;
    private int height;

    public void setTooltipPosition(int tooltipPosition) {
        this.tooltipPosition = tooltipPosition;
    }

    public void setDismissFromOutside(boolean dismissFromOutside) {
        this.dismissFromOutside = dismissFromOutside;
    }

    private void correctDismissFromOutside() {
        if (dismissFromOutside) {
            parentView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        }
    }

    public void dismiss() {
        parentView.removeView(this);
    }

    public void setArrowPosition(int arrowPosition) {
        this.arrowPosition = arrowPosition;
    }


    public ToolTipLinear(Context context, AttributeSet attrs, ViewGroup parentView) {
        super(context, attrs);
        this.context = context;
        this.parentView = parentView;
        initViews();
    }

    public ToolTipLinear(Context context, AttributeSet attrs, ViewGroup parentView, View anchorView) {
        super(context, attrs);
        this.context = context;
        this.parentView = parentView;
        this.anchorView = anchorView;
        initViews();
    }

    public void setAnchorView(View anchorView) {
        this.anchorView = anchorView;
    }

    public void show() {
        correctAnchorView();
        correctArrowPosition();
        correctDismissFromOutside();
        postDelayed(new Runnable() {
            public void run() {
                dismiss();
            }
        }, 3000);
    }


    private void correctArrowPosition() {
        arrow.setVisibility(View.VISIBLE);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) arrow.getLayoutParams();
        switch (arrowPosition) {
            case ARROW_POSITION_NONE:
                arrow.setVisibility(View.GONE);
                break;
            case ARROW_POSITION_TOP_LEFT:
                layoutParams.gravity = Gravity.TOP | Gravity.LEFT;
                arrow.setLayoutParams(layoutParams);
                break;
            case ARROW_POSITION_TOP_CENTER:
                layoutParams.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
                arrow.setLayoutParams(layoutParams);
                break;
            case ARROW_POSITION_TOP_RIGHT:
                layoutParams.gravity = Gravity.TOP | Gravity.RIGHT;
                arrow.setLayoutParams(layoutParams);
                break;
            case ARROW_POSITION_BOTTOM_LEFT:
                layoutParams.gravity = Gravity.BOTTOM | Gravity.LEFT;
                arrow.setLayoutParams(layoutParams);
                break;
            case ARROW_POSITION_BOTTOM_CENTER:
                layoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
                arrow.setLayoutParams(layoutParams);
                break;
            case ARROW_POSITION_BOTTOM_RIGHT:
                layoutParams.gravity = Gravity.BOTTOM | Gravity.RIGHT;
                arrow.setLayoutParams(layoutParams);
                break;
        }


    }

    private void correctAnchorView() {
        if (anchorView != null) {
            //TODO make sure setX and setY are positive values
            switch (tooltipPosition) {
                case TOOLTIP_POSITION_UP:
                    setX(anchorView.getX());
                    setY(anchorView.getY() - height);
                    break;
                case TOOLTIP_POSITION_DOWN:
                    setX(anchorView.getX());
                    setY(anchorView.getY() + anchorView.getHeight());
                    break;
                default:
                    throw new UnsupportedOperationException("position of popup is not specified, use  TOOLTIP_POSITION_UP or TOOLTIP_POSITION_DOWN");
            }

        } else {
            if (parentView instanceof RelativeLayout) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.CENTER_IN_PARENT);
                setLayoutParams(params);
            } else {
                setGravity(Gravity.CENTER);
            }
        }
        setVisibility(View.VISIBLE);
    }

    private void initViews() {
        setVisibility(View.GONE);
        parentView.addView(this);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = (ViewGroup) layoutInflater.inflate(R.layout.popup_view, null);
        addView(rootView);
        titleTextView = (TextView) rootView.findViewById(R.id.tooltip_title);
        arrow = (ImageView) rootView.findViewById(R.id.arrow);
        LayoutTransition lt = new LayoutTransition();
        lt.enableTransitionType(LayoutTransition.CHANGE_DISAPPEARING);
        lt.setDuration(500);
        parentView.setLayoutTransition(lt);
        measure(0, 0);
        height = getMeasuredHeight();
    }

    public void setTitle(String title) {
        titleTextView.setText(title);
    }
}
