package com.example.lilittevosyan.tooltipproject;

import android.animation.LayoutTransition;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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

    public ToolTipLinear(Context context, AttributeSet attrs, ViewGroup parentView) {
        super(context, attrs);
        this.context = context;
        this.parentView = parentView;
        initViews();
    }

    private void initViews() {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = (ViewGroup) layoutInflater.inflate(R.layout.popup_view, null);
        titleTextView = (TextView) rootView.findViewById(R.id.tooltip_title);
        addView(rootView);
        LayoutTransition lt = new LayoutTransition();
        lt.enableTransitionType(LayoutTransition.CHANGE_DISAPPEARING);
        lt.setDuration(2500);
        parentView.setLayoutTransition(lt);
        setVisibility(View.GONE);
    }

    public void setTitle(String title) {
        titleTextView.setText(title);
    }

}
