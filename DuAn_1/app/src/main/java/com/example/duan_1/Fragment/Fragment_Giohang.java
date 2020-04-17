package com.example.duan_1.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duan_1.Activity.MainActivity;
import com.example.duan_1.Adapter.Adapter_GioHang;
import com.example.duan_1.R;

import java.text.DecimalFormat;

public class Fragment_Giohang extends Fragment {
    RecyclerView recyclerView_GioHang ;
    public static TextView tv_thongbao ;
    static TextView tv_tongtien ;
    Button btn_ThanhToan, btn_TiepTucMua ;
    Adapter_GioHang adapter_gioHang ;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_giohang,container,false);
        recyclerView_GioHang = view.findViewById(R.id.recycleView_Giohang);
        tv_thongbao = view.findViewById(R.id.tv_gioHang_trong);
        tv_tongtien = view.findViewById(R.id.tv_TongTien);
        btn_ThanhToan = view.findViewById(R.id.btn_ThanhToan);
        btn_TiepTucMua = view.findViewById(R.id.btn_TiepTucMuaHang);
        adapter_gioHang = new Adapter_GioHang(getActivity().getApplicationContext(), MainActivity.gioHangList);
        recyclerView_GioHang.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView_GioHang.setHasFixedSize(true);
        recyclerView_GioHang.setAdapter(adapter_gioHang);
        CheckData();
        EventUltil();
        initListener();


        return view;
    }

    public static void EventUltil() {
        long tongtien = 0 ;
        for (int i = 0; i < MainActivity.gioHangList.size(); i++) {
            tongtien += MainActivity.gioHangList.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tv_tongtien.setText(decimalFormat.format(tongtien)+ "đ");
    }

    private void initListener() {
        btn_ThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.gioHangList.size()>0){
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment fragment = new Fragment_ThanhToan();
                    fragmentTransaction.replace(R.id.frame_Giohang, fragment).commit();
                    fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
                }else {
                    ImageView img_close, img_icon;
                    final Dialog dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.dialog_giohangtrong);
                    img_close = dialog.findViewById(R.id.img_close_Diaglog);
                    img_icon = dialog.findViewById(R.id.img_QooBee);
                    Glide.with(getContext()).load("https://i.pinimg.com/originals/2e/b8/dd/2eb8dda12be99f0385e10f048ac81aae.gif").into(img_icon);
                    img_close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }
        });

        btn_TiepTucMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Fragment fragment : getActivity().getSupportFragmentManager().getFragments()){
                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
            }
        });
    }

    private void CheckData() {
        if (MainActivity.gioHangList.size()<=0){
            adapter_gioHang.notifyDataSetChanged();
            tv_thongbao.setVisibility(View.VISIBLE);
            recyclerView_GioHang.setVisibility(View.INVISIBLE);
        }else {
            adapter_gioHang.notifyDataSetChanged();
            tv_thongbao.setVisibility(View.INVISIBLE);
            recyclerView_GioHang.setVisibility(View.VISIBLE);
        }
    }
}
