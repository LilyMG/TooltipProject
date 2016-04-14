package com.example.lilittevosyan.tooltipproject;

import android.animation.LayoutTransition;
import android.annotation.TargetApi;
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

    public static int ARROW_POSITION_NONE = 0;
    public static int ARROW_POSITION_TOP_LEFT = 1;
    public static int ARROW_POSITION_TOP_CENTER = 2;
    public static int ARROW_POSITION_TOP_RIGHT = 3;
    public static int ARROW_POSITION_BOTTOM_LEFT = 4;
    public static int ARROW_POSITION_BOTTOM_CENTER = 5;
    public static int ARROW_POSITION_BOTTOM_RIGHT = 6;


    private TextView titleTextView;
    private Context context;
    private LayoutInflater layoutInflater;
    private ViewGroup rootView;
    private ViewGroup parentView;
    private View anchorView;
    private ImageView arrow;
    private int arrowPosition = 0;
    private boolean dismissFromOutside = false;

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

        } else {
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
    }


    private void correctArrowPosition() {
        arrow.setVisibility(View.VISIBLE);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) arrow.getLayoutParams();
        switch (arrowPosition) {
            case 0:
                arrow.setVisibility(View.GONE);
                break;
            case 1:
                layoutParams.gravity = Gravity.TOP | Gravity.LEFT;
                arrow.setLayoutParams(layoutParams);
                break;
            case 2:
                layoutParams.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
                arrow.setLayoutParams(layoutParams);
                break;
            case 3:
                layoutParams.gravity = Gravity.TOP | Gravity.RIGHT;
                arrow.setLayoutParams(layoutParams);
                break;
            case 4:
                layoutParams.gravity = Gravity.BOTTOM | Gravity.LEFT;
                arrow.setLayoutParams(layoutParams);
                break;
            case 5:
                layoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
                arrow.setLayoutParams(layoutParams);
                break;
            case 6:
                layoutParams.gravity = Gravity.BOTTOM | Gravity.RIGHT;
                arrow.setLayoutParams(layoutParams);
                break;
        }


    }

    private void correctAnchorView() {
        if (anchorView != null) {
            setX(anchorView.getRight());
            setY(anchorView.getBottom());
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
    }

    public void setTitle(String title) {
        titleTextView.setText(title);
    }
}
