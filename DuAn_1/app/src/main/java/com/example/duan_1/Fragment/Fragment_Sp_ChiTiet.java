package com.example.duan_1.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.duan_1.Activity.MainActivity;
import com.example.duan_1.Model.GioHang;
import com.example.duan_1.R;

import java.text.DecimalFormat;

public class Fragment_Sp_ChiTiet extends Fragment {
    ImageView img_Chitiet_sp;
    TextView tv_Chitiet_tensp, tv_Chitiet_giasp, tv_Chitiet_mota;
    Spinner spn_Soluong_sp;
    Button btn_Chitiet_Them;
    int id = 0;
    String tenchitiet = "";
    int giachitiet = 0;
    String hinhanhchitiet = "";
    String motachitiet = "";
    int idsanpham = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sp_chitiet, container, false);
        img_Chitiet_sp = view.findViewById(R.id.img_Chitiet_sp);
        tv_Chitiet_tensp = view.findViewById(R.id.tv_Chitiet_tensp);
        tv_Chitiet_giasp = view.findViewById(R.id.tv_Chitiet_giasp);
        tv_Chitiet_mota = view.findViewById(R.id.tv_Chitiet_mota);
        spn_Soluong_sp = view.findViewById(R.id.spn_Soluong_sp);
        btn_Chitiet_Them = view.findViewById(R.id.btn_Chitiet_Them);

        GetInfomation();
        CatchEvenSpinner();
        initListener();
        return view;
    }

    private void initListener() {
        btn_Chitiet_Them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.gioHangList.size() > 0) {
                    int sl = Integer.parseInt(spn_Soluong_sp.getSelectedItem().toString());
                    boolean exists = false;
                    for (int i = 0; i < MainActivity.gioHangList.size(); i++) {
                        if (MainActivity.gioHangList.get(i).getIdsp() == id){
                            MainActivity.gioHangList.get(i).setSoluongsp(MainActivity.gioHangList.get(i).getSoluongsp() + sl);
                            MainActivity.gioHangList.get(i).setGiasp(giachitiet*MainActivity.gioHangList.get(i).getSoluongsp());
                            exists = true;
                        }
                    }
                    if (exists == false){
                        int soluong = Integer.parseInt(spn_Soluong_sp.getSelectedItem().toString());
                        long giamoi = soluong * giachitiet;
                        MainActivity.gioHangList.add(new GioHang(id, tenchitiet, giamoi, hinhanhchitiet, soluong));
                    }
                } else {
                    int soluong = Integer.parseInt(spn_Soluong_sp.getSelectedItem().toString());
                    long giamoi = soluong * giachitiet;

                    MainActivity.gioHangList.add(new GioHang(id, tenchitiet, giamoi, hinhanhchitiet, soluong));

                }
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = new Fragment_Giohang();
                fragmentTransaction.add(R.id.frame_Chitiet_sp, fragment).commit();
                fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
            }
        });
    }

    private void CatchEvenSpinner() {
        Integer[] soluong = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(getActivity().getApplicationContext()
                , android.R.layout.simple_spinner_dropdown_item, soluong);
        spn_Soluong_sp.setAdapter(arrayAdapter);
    }

    private void GetInfomation() {
        Bundle bundle = getArguments();
        id = bundle.getInt("giasp");
        tenchitiet = bundle.getString("tensp");
        giachitiet = bundle.getInt("giasp");
        hinhanhchitiet = bundle.getString("hinhsp");
        motachitiet = bundle.getString("motasp");
        idsanpham = bundle.getInt("idsanpham");

        tv_Chitiet_tensp.setText(tenchitiet);
        Glide.with(getActivity().getApplicationContext()).load(hinhanhchitiet).error(R.drawable.erro).into(img_Chitiet_sp);
        img_Chitiet_sp.setScaleType(ImageView.ScaleType.FIT_XY);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tv_Chitiet_giasp.setText(decimalFormat.format(giachitiet) + "đ");
        tv_Chitiet_mota.setText(motachitiet);

    }
}
