package com.make.poster.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.make.poster.R;
import com.make.poster.app.MPApplication;
import com.make.poster.bean.OpertionItemBean;
import com.make.poster.utils.MakePosterLog;

import java.util.List;

public class OpertionRecAdapter extends RecyclerView.Adapter<OpertionRecAdapter.OpertionRecAdapterHolder> {


    private List<OpertionItemBean> list;
    private Context context;
    private opertionClick opertionClick;


    public OpertionRecAdapter(List<OpertionItemBean> list, Context context, OpertionRecAdapter.opertionClick opertionClick) {
        this.list = list;
        this.context = context;
        this.opertionClick = opertionClick;
    }

    @NonNull
    @Override
    public OpertionRecAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.poster_opertion_rec_item,null);
        return new OpertionRecAdapterHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull OpertionRecAdapterHolder holder, @SuppressLint("RecyclerView") int position) {

        Drawable drawable = context.getResources().getDrawable(list.get(position).getOpertionImageId()).mutate();
        drawable.setColorFilter(context.getColor(MPApplication.OPERTIONSVGCOLOR),PorterDuff.Mode.SRC_ATOP);

        holder.itemIamge.setImageDrawable(drawable);

        holder.itemTips.setText(list.get(position).getOpertionTips());
        holder.itemParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opertionClick.itemClick(list.get(position).getOpertionType(),list.get(position).isOpenFunction());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class OpertionRecAdapterHolder extends RecyclerView.ViewHolder{

        private ImageView itemIamge;
        private TextView itemTips;
        private LinearLayout itemParent;

        public OpertionRecAdapterHolder(@NonNull View itemView) {
            super(itemView);

            itemIamge = itemView.findViewById(R.id.poster_opertion_rec_item_img);
            itemTips = itemView.findViewById(R.id.poster_opertion_rec_item_text);
            itemParent = itemView.findViewById(R.id.poster_opertion_rec_item_parent);


        }
    }


    public interface opertionClick{
        void itemClick(int opertionType,boolean open);
    }

}
