package com.example.duan_1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duan_1.Activity.MainActivity;
import com.example.duan_1.Fragment.Fragment_Giohang;
import com.example.duan_1.Model.GioHang;
import com.example.duan_1.R;

import java.text.DecimalFormat;
import java.util.List;

public class Adapter_GioHang extends RecyclerView.Adapter<Adapter_GioHang.ViewHolder> {

    Context context;
    List<GioHang> listGioHang;

    public Adapter_GioHang(Context context, List<GioHang> listGioHang) {
        this.context = context;
        this.listGioHang = listGioHang;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_giohang,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        GioHang gioHang = listGioHang.get(position) ;
        holder.tv_itemTensp_Giohang.setText(gioHang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tv_giaSp_Giohang.setText(decimalFormat.format(gioHang.getGiasp())+ "đ");
        Glide.with(context).load(gioHang.getHinhsp()).error(R.drawable.erro).into(holder.img_Item_GioHang);
        holder.btn_Values.setText(gioHang.getSoluongsp()+ "");

        //Lấy số lượng đang có của sản phẩm
        int sl = Integer.parseInt(holder.btn_Values.getText().toString());
        if (sl <= 1){
            holder.btn_TruSp.setVisibility(View.INVISIBLE);
        }else if (sl > 1){
            holder.btn_TruSp.setVisibility(View.VISIBLE);
        }

        //Cộng thêm sản phẩm
        holder.btn_CongSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(holder.btn_Values.getText().toString())+1;
                int slhientai = MainActivity.gioHangList.get(position).getSoluongsp();
                long giaht = MainActivity.gioHangList.get(position).getGiasp();
                MainActivity.gioHangList.get(position).setSoluongsp(slmoinhat);
                long giamoinhat = (giaht*slmoinhat)/slhientai;
                MainActivity.gioHangList.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                holder.tv_giaSp_Giohang.setText(decimalFormat.format(giamoinhat)+ "đ");
                Fragment_Giohang.EventUltil();
                if (slmoinhat <2 ){
                    holder.btn_TruSp.setVisibility(View.INVISIBLE);
                    holder.btn_Values.setText(String.valueOf(slmoinhat));
                }else {
                    holder.btn_TruSp.setVisibility(View.VISIBLE);
                    holder.btn_Values.setText(String.valueOf(slmoinhat));
                }
            }
        });
        //Cộng thêm sản phẩm

        holder.btn_TruSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(holder.btn_Values.getText().toString())-1;
                int slhientai = MainActivity.gioHangList.get(position).getSoluongsp();
                long giaht = MainActivity.gioHangList.get(position).getGiasp();
                MainActivity.gioHangList.get(position).setSoluongsp(slmoinhat);
                long giamoinhat = (giaht*slmoinhat)/slhientai;
                MainActivity.gioHangList.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                holder.tv_giaSp_Giohang.setText(decimalFormat.format(giamoinhat)+ "đ");
                Fragment_Giohang.EventUltil();
                if (slmoinhat <2 ){
                    holder.btn_TruSp.setVisibility(View.INVISIBLE);
                    holder.btn_Values.setText(String.valueOf(slmoinhat));
                }else {
                    holder.btn_TruSp.setVisibility(View.VISIBLE);
                    holder.btn_Values.setText(String.valueOf(slmoinhat));
                }
            }
        });

        holder.img_remove_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.gioHangList.size()<=0){
                    Fragment_Giohang.tv_thongbao.setVisibility(View.VISIBLE);
                }else {
                    MainActivity.gioHangList.remove(position);
                    notifyDataSetChanged();
                    Fragment_Giohang.EventUltil();
                    if (MainActivity.gioHangList.size() <=0){
                        Fragment_Giohang.tv_thongbao.setVisibility(View.VISIBLE);
                    }else {
                        Fragment_Giohang.tv_thongbao.setVisibility(View.INVISIBLE);
                        notifyDataSetChanged();
                        Fragment_Giohang.EventUltil();
                    }
                }
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listGioHang.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img_Item_GioHang , img_remove_sp;
        TextView tv_itemTensp_Giohang,tv_giaSp_Giohang ;
        Button btn_TruSp, btn_Values,btn_CongSp ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_Item_GioHang = itemView.findViewById(R.id.img_Item_GioHang);
            tv_itemTensp_Giohang = itemView.findViewById(R.id.tv_itemTensp_Giohang);
            tv_giaSp_Giohang = itemView.findViewById(R.id.tv_giaSp_Giohang);
            btn_TruSp = itemView.findViewById(R.id.btn_TruSp);
            btn_Values = itemView.findViewById(R.id.btn_Values);
            btn_CongSp = itemView.findViewById(R.id.btn_CongSp);
            img_remove_sp = itemView.findViewById(R.id.img_remove_sp);
        }
    }
}
