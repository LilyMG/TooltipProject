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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by LilitTevosyan on 4/14/16.
 */
public class ToolTipLinear extends LinearLayout {

    private TextView titleTextView;
    private Context context;
    private LayoutInflater layoutInflater;
    private ViewGroup rootView;
    private ViewGroup parentView;
    private View anchorView = null;

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
        LayoutTransition lt = new LayoutTransition();
        lt.enableTransitionType(LayoutTransition.CHANGE_DISAPPEARING);
        lt.setDuration(500);
        parentView.setLayoutTransition(lt);
    }

    public void setTitle(String title) {
        titleTextView.setText(title);
    }


    public void setTooltipPosition() {
    }

}
