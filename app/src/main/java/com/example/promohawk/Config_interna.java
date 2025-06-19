package com.example.promohawk;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Config_interna extends AppCompatActivity {

    private Switch switchNotificacoes;
    private LinearLayout layoutTermos;
    private TextView textoTermos;
    private boolean termosExpandido = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_config_interna);

        // Notificações
        switchNotificacoes = findViewById(R.id.switchNotificacoes);
        switchNotificacoes.setChecked(areNotificationsEnabled());

        switchNotificacoes.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!areNotificationsEnabled()) {
                Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                startActivity(intent);
                switchNotificacoes.setChecked(false);
                Toast.makeText(this, "Ative as notificações para este app nas configurações.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Notificações " + (isChecked ? "ativadas" : "desativadas"), Toast.LENGTH_SHORT).show();
            }
        });

        // Termos
        layoutTermos = findViewById(R.id.layoutTermos);
        textoTermos = findViewById(R.id.textoTermos);
        layoutTermos.setOnClickListener(v -> {
            termosExpandido = !termosExpandido;
            textoTermos.setVisibility(termosExpandido ? View.VISIBLE : View.GONE);
        });

        // Segurança e Privacidade
        findViewById(R.id.SegurancaPriv).setOnClickListener(v ->
                Toast.makeText(this, "Segurança e Privacidade clicado", Toast.LENGTH_SHORT).show());

        // Sair da conta
        findViewById(R.id.Sair_tudo).setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("usuario_prefs", MODE_PRIVATE);
            prefs.edit().clear().apply(); // Limpa tudo (inclui email, token, etc.)

            Intent intent = new Intent(Config_interna.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Garante que a pilha seja limpa
            startActivity(intent);
            finish(); // Finaliza a activity atual
        });

        // Voltar
        ImageView botaoVoltar = findViewById(R.id.botaoVoltar);
        botaoVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(Config_interna.this, Config.class);
            startActivity(intent);
            finish();
        });
    }

    private boolean areNotificationsEnabled() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return notificationManager != null && notificationManager.areNotificationsEnabled();
        }
        return false;
    }
}
