package com.example.duan_1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan_1.R;
import com.example.duan_1.Ultil.Server_Local;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUp_Activity extends AppCompatActivity {
    private EditText edt_signUp_userName, edt_signUp_passWord, edt_signUP_conFirmPassWord;
    private Button btn_signUp_User;
    ProgressBar Prb_refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initUI();
        Prb_refresh.setVisibility(View.INVISIBLE);

        btn_signUp_User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = edt_signUp_userName.getText().toString().trim();
                final String password = edt_signUp_passWord.getText().toString().trim();
                final String RePass = edt_signUP_conFirmPassWord.getText().toString().trim();
                if (username.isEmpty() || password.isEmpty() || RePass.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Không Được Để Trống Dữ Liệu", Toast.LENGTH_SHORT).show();
                } else {
                    CountDownTimer countDownTimer = new CountDownTimer(4000, 4000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            if (password.equals(RePass)) {
                                Prb_refresh.setVisibility(View.VISIBLE);
                                RequestQueue requestQueue = Volley.newRequestQueue(SignUp_Activity.this);
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, Server_Local.RequestAccount, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("ok")) {
                                            Toast.makeText(getApplicationContext(), "Tài Khoản Đã Tồn Tại", Toast.LENGTH_SHORT).show();
                                        } else {
                                            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                            final StringRequest request = new StringRequest(Request.Method.POST, Server_Local.Sign_Up, new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    if (Integer.parseInt(response) > 0) {
                                                        Toast.makeText(getApplicationContext(), "Đăng Ký Thành Công", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(SignUp_Activity.this, Login_Activity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                }
                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {

                                                }
                                            }) {
                                                @Override
                                                protected Map<String, String> getParams() throws AuthFailureError {
                                                    JSONArray jsonArray = new JSONArray();
                                                    JSONObject jsonObject = new JSONObject();
                                                    try {
                                                        jsonObject.put("username", username);
                                                        jsonObject.put("password", password);
                                                    } catch (Exception e) {
                                                    }
                                                    jsonArray.put(jsonObject);
                                                    HashMap<String, String> hashMap = new HashMap<>();
                                                    hashMap.put("json", jsonArray.toString());
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
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put("username", username);
                                        return hashMap;
                                    }
                                };
                                requestQueue.add(stringRequest);
                            } else {
                                Toast.makeText(getApplicationContext(), "Mật Khẩu Không Trùng Nhau", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFinish() {
                            Prb_refresh.setVisibility(View.INVISIBLE);
                        }
                    }.start();
                }
            }
        });
    }

    private void initUI() {
        edt_signUp_userName = findViewById(R.id.edt_signUp_userName);
        edt_signUp_passWord = findViewById(R.id.edt_signUp_passWord);
        edt_signUP_conFirmPassWord = findViewById(R.id.edt_signUP_conFirmPassWord);
        btn_signUp_User = findViewById(R.id.btn_signUp_User);
        Prb_refresh = findViewById(R.id.Prb_refresh);
    }
}
