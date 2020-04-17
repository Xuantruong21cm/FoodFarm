package com.example.duan_1.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.duan_1.Activity.MainActivity;
import com.example.duan_1.R;
import com.example.duan_1.Ultil.CheckConnection;
import com.example.duan_1.Ultil.Server_Local;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Fragment_ThanhToan extends Fragment {
    Button btn_XacNhan,btn_QuayLai ;
    EditText edt_TenKhachHang,edt_SoDienThoai_KH, edt_Email_KhachHang;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thanhtoan, container, false);
        btn_XacNhan = view.findViewById(R.id.btn_XacNhan_ThanhToan);
        btn_QuayLai = view.findViewById(R.id.btn_QuayLai_ThanhToan);
        edt_TenKhachHang = view.findViewById(R.id.edt_TenKhachHang);
        edt_SoDienThoai_KH = view.findViewById(R.id.edt_SoDienThoai_KH);
        edt_Email_KhachHang = view.findViewById(R.id.edt_Email_KhachHang);
        initListener();

        return view;
    }

    private void initListener() {
        btn_XacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Ten = edt_TenKhachHang.getText().toString().trim();
                final String Sđt = edt_SoDienThoai_KH.getText().toString().trim();
                final String email =edt_Email_KhachHang.getText().toString().trim();
                if (Ten.isEmpty() || Sđt.isEmpty() || email.isEmpty()){
                    Toast.makeText(getContext(),"Vui Lòng Nhập Đầy Đủ Thông Tin",Toast.LENGTH_SHORT).show();
                }else {
                    if (CheckConnection.haveNetworkConnection(getContext())){
                        ImageView img_icon,img_close_TT ;

                        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server_Local.DonHang, new Response.Listener<String>() {
                            @Override
                            public void onResponse(final String madonhang) {
                                Log.d("madonhang", madonhang);
                                if (Integer.parseInt(madonhang)>0){
                                    RequestQueue queue = Volley.newRequestQueue(getContext());
                                    StringRequest request = new StringRequest(Request.Method.POST, Server_Local.Chitiet_Donhang, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    }){
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            JSONArray jsonArray = new JSONArray();
                                            for (int i = 0; i < MainActivity.gioHangList.size() ; i++) {
                                                JSONObject jsonObject = new JSONObject();
                                                try {
                                                    jsonObject.put("madonhang",madonhang);
                                                    jsonObject.put("masanpham",MainActivity.gioHangList.get(i).getIdsp());
                                                    jsonObject.put("tensanpham",MainActivity.gioHangList.get(i).getTensp());
                                                    jsonObject.put("giasanpham",MainActivity.gioHangList.get(i).getGiasp());
                                                    jsonObject.put("soluongsanpham",MainActivity.gioHangList.get(i).getSoluongsp());
                                                    jsonObject.put("tenkhachhang",Ten);
                                                    jsonObject.put("sodienthoai",Sđt);
                                                    jsonObject.put("email",email);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                jsonArray.put(jsonObject);
                                            }
                                            HashMap<String, String> hashMap = new HashMap<String, String>();
                                            hashMap.put("json",jsonArray.toString());
                                            return hashMap;
                                        }
                                    };
                                    queue.add(request);
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String,String> hashMap = new HashMap<String, String>();
                                hashMap.put("tenkhachhang",Ten);
                                hashMap.put("sodienthoai",Sđt);
                                hashMap.put("email",email);
                                return hashMap;
                            }
                        };
                        requestQueue.add(stringRequest);

                        final Dialog dialog = new Dialog(getContext());
                        dialog.setContentView(R.layout.diaglog_thanhtoan_thanhcong);
                        img_icon = dialog.findViewById(R.id.img_QooBee2);
                        img_close_TT = dialog.findViewById(R.id.img_close_DiaglogTT);
                        img_close_TT.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                for (Fragment fragment : getActivity().getSupportFragmentManager().getFragments()){
                                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                                }
                                MainActivity.gioHangList.clear();
                            }
                        });
                        Glide.with(getContext()).load("https://2.bp.blogspot.com/-ikPXRlt9ANM/XMQlU4nsk4I/AAAAAAAABGE/zILd3E9F5PUlEuXuirFL52eH2YvQlCqkwCLcBGAs/s1600/AW396347_03.gif")
                                .into(img_icon);
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();
                    }else {
                        Toast.makeText(getContext(),"Vui Lòng Kiểm Tra Kết Nối Mạng",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btn_QuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }
}
