package com.example.promohawk;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.graphics.Bitmap;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.yalantis.ucrop.UCrop;

import java.io.File;

public class Config extends AppCompatActivity {

    private ImageView imgPerfil;
    private ImageView btnVoltar;
    private LinearLayout btnFavoritos, btnConta, btnHistorico, btnSuporte;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(Config.this, Home.class);
            startActivity(intent);
            finish();
        });

        imgPerfil = findViewById(R.id.imgPerfil);
        imgPerfil.setImageResource(R.drawable.ic_launcher_foreground);

        imgPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        btnFavoritos = findViewById(R.id.btnFavoritos);
        btnConta = findViewById(R.id.btnConta);
        btnHistorico = findViewById(R.id.btnHistorico);
        btnSuporte = findViewById(R.id.btnSuporte);

        btnFavoritos.setOnClickListener(v -> startActivity(new Intent(this, Favoritos.class)));
        btnConta.setOnClickListener(v -> startActivity(new Intent(this, Config_interna.class)));
        btnHistorico.setOnClickListener(v -> startActivity(new Intent(this, Historico.class)));
        btnSuporte.setOnClickListener(v -> startActivity(new Intent(this, Suporte.class)));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri sourceUri = data.getData();
            Uri destinationUri = Uri.fromFile(new File(getCacheDir(), "cropped_" + System.currentTimeMillis() + ".jpg"));

            UCrop.Options options = new UCrop.Options();
            options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
            options.setCompressionQuality(90);

            UCrop.of(sourceUri, destinationUri)
                    .withAspectRatio(1, 1) // Quadrado, ideal para foto de perfil
                    .withOptions(options)
                    .start(this);
        }

        else if (requestCode == UCrop.REQUEST_CROP) {
            if (resultCode == RESULT_OK) {
                final Uri resultUri = UCrop.getOutput(data);
                if (resultUri != null) {
                    Glide.with(this)
                            .load(resultUri)
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .error(R.drawable.imagem_erro)
                            .into(imgPerfil);
                }
            } else if (resultCode == UCrop.RESULT_ERROR) {
                final Throwable cropError = UCrop.getError(data);
                // Aqui pode tratar erro, exibir mensagem para o usuário
                cropError.printStackTrace();
            }
        }
    }
}
