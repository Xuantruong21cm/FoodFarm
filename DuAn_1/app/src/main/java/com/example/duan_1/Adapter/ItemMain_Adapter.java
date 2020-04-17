package com.example.duan_1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan_1.Model.ItemProduct;
import com.example.duan_1.Interface.MyOnClickItemListener;
import com.example.duan_1.R;

import java.util.List;

public class ItemMain_Adapter extends RecyclerView.Adapter<ItemMain_Adapter.ViewHolder>  {
    List<ItemProduct> list ;
    Context context;
    public MyOnClickItemListener myOnClickItemListener ;

    public void setMyOnClickItemListener(MyOnClickItemListener myOnClickItemListener) {
        this.myOnClickItemListener = myOnClickItemListener;
    }

    public ItemMain_Adapter(List<ItemProduct> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.carview_main,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final ItemProduct itemProduct = list.get(position);
            holder.tv_name_product.setText(itemProduct.getProductName());
            holder.img_Item_main.setImageResource(itemProduct.getImageItem());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myOnClickItemListener.onClick(list.get(position));
                }
            });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name_product ;
        ImageView img_Item_main ;
        public ViewHolder(View itemView) {
            super(itemView);
            this.tv_name_product = itemView.findViewById(R.id.tv_nameProduct);
            this.img_Item_main = itemView.findViewById(R.id.img_Item_Main);
        }
    }
}
