package com.example.promohawk;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Aqui você pode ter o layout com animação ou imagem

        ImageView logo = findViewById(R.id.logo);
        logo.animate()
                .alpha(1f)
                .setDuration(1000)
                .setStartDelay(300)
                .start();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            SharedPreferences prefs = getSharedPreferences("usuario_prefs", MODE_PRIVATE);
            String token = prefs.getString("token", null);

            if (token != null && !token.isEmpty()) {
                // Token existe, pode ir direto pra tela principal (Config, Home etc)
                startActivity(new Intent(MainActivity.this, Config.class));
            } else {
                // Não tem token, vai para login
                startActivity(new Intent(MainActivity.this, Config.class));
            }
            finish();

        }, 3000); // 3 segundos de splash
    }
}
