package com.example.duan_1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duan_1.Model.Sanpham_New;
import com.example.duan_1.R;

import java.text.DecimalFormat;
import java.util.List;

public class SanphamNew_Adapter extends RecyclerView.Adapter<SanphamNew_Adapter.MyViewHolder> {
    Context context ;
    List<Sanpham_New> list ;

    public SanphamNew_Adapter(Context context, List<Sanpham_New> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_sanpham_new,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Sanpham_New sanpham_new = list.get(position);
        holder.tv_tensp.setText(sanpham_new.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tv_giasp.setText("Giá : "+decimalFormat.format(sanpham_new.getGiasanpham())+" Đ");
        Glide.with(context).load(sanpham_new.getHinhanhsanpham()).into(holder.img_hinhsanpham);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView img_hinhsanpham ;
        public TextView tv_tensp , tv_giasp ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_hinhsanpham = itemView.findViewById(R.id.img_spNew);
            tv_tensp  = itemView.findViewById(R.id.tv_tensp_new);
            tv_giasp = itemView.findViewById(R.id.tv_giasp_new);
        }
    }
}
