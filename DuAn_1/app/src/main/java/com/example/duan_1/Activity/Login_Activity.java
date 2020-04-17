package com.example.duan_1.Activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Login_Activity extends AppCompatActivity {
    private EditText edt_userName, edt_passWord ;
    private Button btn_signIn, btn_signUp;
    LoginButton btn_facebook;
    CallbackManager callbackManager;
    ProgressBar Prb_load ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);
        initUI();
        initListener();
        Prb_load.setVisibility(View.INVISIBLE);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.duan_1",                  //Insert your own package name.
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }

    private void initListener() {
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountDownTimer countDownTimer = new CountDownTimer(1800,1800) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                              Prb_load.setVisibility(View.VISIBLE);
                              Account();
                    }

                    @Override
                    public void onFinish() {
                        Prb_load.setVisibility(View.INVISIBLE);
                    }
                }.start();
            }
        });

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Activity.this,SignUp_Activity.class);
                startActivity(intent);
            }
        });


        setLogin();
    }

    private void setLogin() {
        btn_facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                btn_facebook.setReadPermissions(Arrays.asList("public_profile","email"));
                Intent intent = new Intent(Login_Activity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }


            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void initUI() {
        Prb_load = findViewById(R.id.Prb_load);
        edt_userName = findViewById(R.id.edt_userName);
        edt_passWord = findViewById(R.id.edt_passWord);
        btn_signIn = findViewById(R.id.btn_signIn);
        btn_signUp = findViewById(R.id.btn_signUp);
        btn_facebook = findViewById(R.id.btn_faceBook);
    }
    private void Account() {
        final String username = edt_userName.getText().toString().trim();
        final String password = edt_passWord.getText().toString().trim();
        RequestQueue requestQueue = Volley.newRequestQueue(Login_Activity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server_Local.Account, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("ok")){
                    Intent intent = new Intent(Login_Activity.this,MainActivity.class);
                    intent.putExtra("user",username) ;
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"Sai Tài Khoản Hoặc Mật Khẩu",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("username",username) ;
                hashMap.put("password",password) ;
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
