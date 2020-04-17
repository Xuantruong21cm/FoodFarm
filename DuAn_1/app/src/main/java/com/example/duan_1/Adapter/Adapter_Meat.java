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
import com.example.duan_1.Interface.Meat_OnClickItemListener;
import com.example.duan_1.Model.SanPham;
import com.example.duan_1.R;

import java.text.DecimalFormat;
import java.util.List;

public class Adapter_Meat extends  RecyclerView.Adapter<Adapter_Meat.MyViewHolder> {
    Context context ;
    List<SanPham> list ;
    Meat_OnClickItemListener onClickItemListener ;

    public void Meat_OnClick(Meat_OnClickItemListener meat_onClickItemListener){
        this.onClickItemListener = meat_onClickItemListener;
    }

    public Adapter_Meat(Context context, List<SanPham> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.sanpham,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final SanPham sanPham = list.get(position);
        holder.tv_tensp_pork.setText(sanPham.getTensanpham());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tv_giasp_pork.setText("Giá : "+decimalFormat.format(sanPham.getGiasanpham())+" Đ");
        Glide.with(context).load(sanPham.getHinhanhsanpham()).into(holder.img_hinhsp_pork);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemListener.onClick(list.get(position));
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView img_hinhsp_pork ;
        public TextView tv_tensp_pork, tv_giasp_pork ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_hinhsp_pork = itemView.findViewById(R.id.img_Product);
            tv_tensp_pork = itemView.findViewById(R.id.tv_Item_tensp) ;
            tv_giasp_pork = itemView.findViewById(R.id.tv_Item_giasp);

        }
    }
}
