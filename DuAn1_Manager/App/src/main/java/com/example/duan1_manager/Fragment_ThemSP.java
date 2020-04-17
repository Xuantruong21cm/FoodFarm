package com.example.duan1_manager;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Fragment_ThemSP extends Fragment {
    EditText edt_tensanpham, edt_giasanpham, edt_hinhanhsanpham, edt_motasanpham ;
    Spinner spn_maLoaiSP ;
    Button btn_XacNhan_Themsp ,btn_QuayLai_Themsp ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_themsp,container,false);
        edt_tensanpham = view.findViewById(R.id.edt_tensanpham);
        edt_giasanpham = view.findViewById(R.id.edt_giasanpham);
        edt_hinhanhsanpham = view.findViewById(R.id.edt_hinhanhsanpham);
        edt_motasanpham = view.findViewById(R.id.edt_motasanpham);
        spn_maLoaiSP = view.findViewById(R.id.spn_maLoaiSP);
        btn_XacNhan_Themsp = view.findViewById(R.id.btn_XacNhan_Themsp) ;
        btn_QuayLai_Themsp = view.findViewById(R.id.btn_QuayLai_Themsp);
        CatchEvenSpiner();
        initListener();



        return view;
    }

    private void CatchEvenSpiner() {
        String[] idsanpham = new String[]{"1.Thịt Lợn","2.Thịt Bò","3.Thịt Gà","4.Thịt Cá"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,idsanpham);
        spn_maLoaiSP.setAdapter(arrayAdapter);
    }

    private void initListener() {
        btn_QuayLai_Themsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        btn_XacNhan_Themsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tensp  = edt_tensanpham.getText().toString().trim();
                final String giasp = edt_giasanpham.getText().toString().trim();
                final String hinhsp = edt_hinhanhsanpham.getText().toString().trim();
                final String motasp = edt_motasanpham.getText().toString().trim();
                final String idsp = spn_maLoaiSP.getSelectedItem().toString().replaceAll("[^0-9/]","");
                if (tensp.isEmpty()||giasp.isEmpty()||hinhsp.isEmpty()
                ||motasp.isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(),"Không Được Để Trống Dữ Liệu",Toast.LENGTH_SHORT).show();
                }else {
                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                    StringRequest request = new StringRequest(Request.Method.POST, Server_LOcal.ThemSp, new Response.Listener<String>() {
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
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("tensanpham",tensp);
                                jsonObject.put("giasanpham",giasp);
                                jsonObject.put("hinhsanpham",hinhsp);
                                jsonObject.put("motasanpham",motasp);
                                jsonObject.put("idsanpham",idsp);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            jsonArray.put(jsonObject);
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put("json",jsonArray.toString());
                            return hashMap;
                        }
                    };
                    requestQueue.add(request);
                    Toast.makeText(getActivity().getApplicationContext(),"Thêm Sản Phẩm Thành Công",Toast.LENGTH_SHORT).show();
                    for (Fragment fragment : getActivity().getSupportFragmentManager().getFragments()){
                        getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    }
                }
            }
        });
    }
}
