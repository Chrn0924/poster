package com.make.poster.dialog;

import android.app.AlertDialog;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.make.poster.R;
import com.make.poster.base.MPBaseActivity;
import com.make.poster.color.ColorEnvelope;
import com.make.poster.color.ColorPickerView;
import com.make.poster.color.listeners.ColorEnvelopeListener;
import com.make.poster.color.sliders.AlphaSlideBar;
import com.make.poster.color.sliders.BrightnessSlideBar;
import com.make.poster.utils.MakePosterLog;

public class ColorAlertDialog {

    private static AlertDialog dialog;

    private static String colorSelect;

    public static void showAlertDialog(MPBaseActivity activity, String title,  String positiveText, String cancelText, boolean cancelableTouchOut, final AlertDialogBtnClickListener alertDialogBtnClickListener) {

        colorSelect = "";

        View view = LayoutInflater.from(activity).inflate(R.layout.color_alertdialog_layout,null);

        //设置title
        TextView colorTitle = (TextView)view.findViewById(R.id.color_alert_title);
        colorTitle.setText(title);

        ColorPickerView colorPickerView = (ColorPickerView) view.findViewById(R.id.color_alert_colorpicker);
        BrightnessSlideBar brightnessSlideBar = (BrightnessSlideBar) view.findViewById(R.id.color_alert_brightness);

        colorPickerView.attachBrightnessSlider(brightnessSlideBar);

        LinearLayout colorShowColorView = (LinearLayout) view.findViewById(R.id.color_alert_selectview);
        colorShowColorView.setBackgroundColor(Color.parseColor("#000000"));

        TextView colorCancel = (TextView)view.findViewById(R.id.color_alert_cancel_button);
        TextView colorConfrim = (TextView)view.findViewById(R.id.color_alert_confrim_button);
        colorCancel.setText(cancelText);
        colorConfrim.setText(positiveText);

        colorPickerView.setColorListener(new ColorEnvelopeListener() {
            @Override
            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                colorShowColorView.setBackgroundColor(Color.parseColor("#"+envelope.getHexCode()));
                colorSelect = "#"+envelope.getHexCode();
            }
        });

        colorConfrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogBtnClickListener.clickPositive(colorSelect);
                dialog.dismiss();
            }
        });

        colorCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogBtnClickListener.clickNegative();
                dialog.dismiss();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view);
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);//去掉圆角背景背后的棱角
        dialog.setCanceledOnTouchOutside(cancelableTouchOut);   //失去焦点dismiss
        dialog.show();


    }

    public interface AlertDialogBtnClickListener {
        void clickPositive(String con);

        void clickNegative();
    }

}
