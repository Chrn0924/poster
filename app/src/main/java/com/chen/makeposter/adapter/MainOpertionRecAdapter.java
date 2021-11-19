package com.chen.makeposter.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chen.makeposter.R;
import com.chen.makeposter.bean.MainOpertionBean;
import com.chen.makeposter.listener.MainOpertionRecClickItemListener;
import com.make.poster.app.MPApplication;

import java.util.ArrayList;

public class MainOpertionRecAdapter extends RecyclerView.Adapter<MainOpertionRecAdapter.MainOpertionRecAdapterHolder> {

    private MainOpertionRecClickItemListener mainOpertionRecClickItemListener;

    private Context context;

    private ArrayList<MainOpertionBean> mainOpertionBeanArrayList;

    public MainOpertionRecAdapter(MainOpertionRecClickItemListener mainOpertionRecClickItemListener, Context context, ArrayList<MainOpertionBean> mainOpertionBeanArrayList) {
        this.mainOpertionRecClickItemListener = mainOpertionRecClickItemListener;
        this.context = context;
        this.mainOpertionBeanArrayList = mainOpertionBeanArrayList;
    }

    @NonNull
    @Override
    public MainOpertionRecAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_opertion_item,null);
        return new MainOpertionRecAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainOpertionRecAdapterHolder holder, @SuppressLint("RecyclerView") int position) {

        Drawable drawable = context.getResources().getDrawable(mainOpertionBeanArrayList.get(position).getOpetionIcon()).mutate();
        drawable.setColorFilter(context.getResources().getColor(MPApplication.OPERTIONSVGCOLOR), PorterDuff.Mode.SRC_ATOP);

        holder.itemIcon.setImageDrawable(drawable);

        holder.itemTips.setText(mainOpertionBeanArrayList.get(position).getOpetionName());

        holder.itemLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainOpertionRecClickItemListener.clickItemListener(mainOpertionBeanArrayList.get(position).getOpertionClass(),position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mainOpertionBeanArrayList.size();
    }

    public class MainOpertionRecAdapterHolder extends RecyclerView.ViewHolder{

        private ImageView itemIcon;
        private TextView itemTips;
        private LinearLayout itemLin;

        public MainOpertionRecAdapterHolder(@NonNull View itemView) {
            super(itemView);

            itemIcon = itemView.findViewById(R.id.opertion_main_item_icon);
            itemTips = itemView.findViewById(R.id.opetion_main_item_tips);
            itemLin = itemView.findViewById(R.id.opetion_main_item);


        }
    }

}
