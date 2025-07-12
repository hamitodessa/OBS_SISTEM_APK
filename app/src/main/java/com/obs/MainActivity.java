package com.obs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int statusBarHeight = 0;
        @SuppressLint("DiscouragedApi")
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        findViewById(R.id.statusBarView).getLayoutParams().height = statusBarHeight;

        // Buton tıklama olayı

        EditText edtUsername = findViewById(R.id.edtUsername);
        EditText edtPassword = findViewById(R.id.edtPassword);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString().trim().toLowerCase();
                String password = edtPassword.getText().toString().trim();
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this,
                            "Kullanıcı Adı ve Şifre boş olamaz!", Toast.LENGTH_SHORT).show();
                    return; // İşlemi sonlandır
                }
                JSONObject json = new JSONObject();
                try {
                    json.put("username", username);
                    json.put("password", password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
              RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
              StringRequest request = new StringRequest(Request.Method.POST,
                        "https://www.obs-web.com/mobillogin",
                        response -> Toast.makeText(MainActivity.this, "Yanıt: " + response, Toast.LENGTH_LONG).show(),
                        error -> Toast.makeText(MainActivity.this, "Hata: " + error.toString(), Toast.LENGTH_LONG).show()
                ) {
                    @Override
                    public byte[] getBody() {
                        return json.toString().getBytes();
                    }
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }
                };
                queue.add(request);
            }
        });
    }
}