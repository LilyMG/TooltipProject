package com.example.lilittevosyan.tooltipproject;

import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
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
    private View rootView;

    public ToolTipLinear(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        initViews();
    }

    private void initViews() {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = layoutInflater.inflate(R.layout.popup_view, null);
        titleTextView = (TextView) rootView.findViewById(R.id.tooltip_title);
        addView(rootView);

    }

    public void setTitle(String title) {
        titleTextView.setText(title);
    }

}
