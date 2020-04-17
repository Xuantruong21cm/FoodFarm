package com.example.duan_1.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan_1.Adapter.Adapter_Meat;
import com.example.duan_1.Interface.Meat_OnClickItemListener;
import com.example.duan_1.Model.SanPham;
import com.example.duan_1.R;
import com.example.duan_1.Ultil.Server_Local;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Beef extends Fragment {
    List<SanPham> list;
    GridLayoutManager layoutManager;
    Adapter_Meat adapter__meat;
    RecyclerView recyclerView_Pork;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__pork, container, false);

        recyclerView_Pork = view.findViewById(R.id.recycleView_Pork);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Server_Local.Sp_Pork_Meat, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        int ID = jsonObject.getInt("id");
                        String Tensp = jsonObject.getString("tensp");
                        Integer Giasp = jsonObject.getInt("giasp");
                        String Hinhanhsp = jsonObject.getString("hinhanhsp");
                        String Motasp = jsonObject.getString("motasp");
                        int IDsanpham = jsonObject.getInt("idsanpham");
                        if (IDsanpham==2){
                            list.add(new SanPham(ID, Tensp, Giasp, Hinhanhsp, Motasp, IDsanpham));
                            adapter__meat.notifyDataSetChanged();
                        }
                    }
                } catch (Exception e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
        list = new ArrayList<>();
        layoutManager = new GridLayoutManager(getContext(), 3);
        adapter__meat = new Adapter_Meat(getContext(), list);
        recyclerView_Pork.setHasFixedSize(true);
        recyclerView_Pork.setAdapter(adapter__meat);
        recyclerView_Pork.setLayoutManager(layoutManager);


        adapter__meat.Meat_OnClick(new Meat_OnClickItemListener() {
            @Override
            public void onClick(SanPham sanPham) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = new Fragment_Sp_ChiTiet();
                Bundle bundle = new Bundle();
                bundle.putInt("id",sanPham.getID());
                bundle.putString("tensp", sanPham.getTensanpham());
                bundle.putInt("giasp", sanPham.getGiasanpham());
                bundle.putString("hinhsp", sanPham.getHinhanhsanpham());
                bundle.putString("motasp", sanPham.getMotasanpham());
                bundle.putInt("idsanpham",sanPham.getIDsanpham());
                fragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.frame_thit, fragment).commit();
                fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
            }
        });

        return view;
    }
}
