package com.make.poster.dialog;

import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.make.poster.R;
import com.make.poster.filters.FilterViewAdapter;

import java.sql.Ref;

public class PosterFilterDialog {


    public static void showFilterDialog(FragmentActivity appCompatActivity,FilterViewAdapter mFilterViewAdapter ){

        Dialog bottomDialog = new Dialog(appCompatActivity, R.style.BottomDialog);
        View contentView = LayoutInflater.from(appCompatActivity).inflate(R.layout.poster_filter_dialog, null);
        bottomDialog.setContentView(contentView);
        RecyclerView ri = contentView.findViewById(R.id.rvFilterView);
        RelativeLayout patent = contentView.findViewById(R.id.poster_partent);

        LinearLayoutManager manager = new LinearLayoutManager(appCompatActivity);
        manager.setOrientation(RecyclerView.HORIZONTAL);
        ri.setLayoutManager(manager);

        ri.setAdapter(mFilterViewAdapter);

        patent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });

        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = appCompatActivity.getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.show();


    }



}
