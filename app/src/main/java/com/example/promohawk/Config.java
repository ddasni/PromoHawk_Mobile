package com.example.promohawk;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.promohawk.api.ApiService;
import com.example.promohawk.api.RetrofitClient;
import com.example.promohawk.model.FotoPerfilBase64Request;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayOutputStream;
import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Config extends AppCompatActivity {

    private ImageView imgPerfil;
    private ImageView btnVoltar;
    private LinearLayout btnFavoritos, btnConta, btnHistorico, btnSuporte;

    private BottomNavigationView bottomNavigationView;

    private static final int PICK_IMAGE_REQUEST = 1;

    private ApiService apiService;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        apiService = RetrofitClient.getPromoHawkInstance().create(ApiService.class);

        email = getSharedPreferences("usuario_prefs", MODE_PRIVATE).getString("email", "");

        imgPerfil = findViewById(R.id.imgPerfil);
        btnVoltar = findViewById(R.id.btnVoltar);
        btnFavoritos = findViewById(R.id.btnFavoritos);
        btnConta = findViewById(R.id.btnConta);
        btnHistorico = findViewById(R.id.btnHistorico);
        btnSuporte = findViewById(R.id.btnSuporte);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        configurarBottomNavigation();

        btnVoltar.setOnClickListener(v -> {
            startActivity(new Intent(this, Home.class));
            finish();
        });

        imgPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        btnFavoritos.setOnClickListener(v -> startActivity(new Intent(this, Favoritos.class)));
        btnConta.setOnClickListener(v -> startActivity(new Intent(this, Config_interna.class)));
        btnHistorico.setOnClickListener(v -> startActivity(new Intent(this, Historico.class)));
        btnSuporte.setOnClickListener(v -> startActivity(new Intent(this, Suporte.class)));

        carregarFotoPerfil();
    }

    private void configurarBottomNavigation() {
        bottomNavigationView.setSelectedItemId(R.id.nav_config);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(this, Home.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.nav_produtos) {
                startActivity(new Intent(this, ProdutosActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.nav_cupons) {
                startActivity(new Intent(this, CuponsActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.nav_config) {
                return true;
            }
            return false;
        });
    }

    private void carregarFotoPerfil() {
        String fotoBase64 = getSharedPreferences("usuario_prefs", MODE_PRIVATE)
                .getString("fotoPerfil_" + email, null);

        if (fotoBase64 != null && fotoBase64.startsWith("data:image")) {
            String base64Pure = fotoBase64.substring(fotoBase64.indexOf(",") + 1);
            byte[] decodedBytes = Base64.decode(base64Pure, Base64.DEFAULT);
            Glide.with(this)
                    .asBitmap()
                    .load(decodedBytes)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(imgPerfil);
        } else {
            imgPerfil.setImageResource(R.drawable.ic_launcher_foreground);
        }
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
                    .withAspectRatio(1, 1)
                    .withOptions(options)
                    .start(this);
        } else if (requestCode == UCrop.REQUEST_CROP) {
            if (resultCode == RESULT_OK) {
                final Uri resultUri = UCrop.getOutput(data);
                if (resultUri != null) {
                    Glide.with(this).load(resultUri).into(imgPerfil);

                    String base64 = uriToBase64(resultUri);
                    if (base64 != null) {
                        enviarFotoParaBackend(base64);
                    }
                }
            } else if (resultCode == UCrop.RESULT_ERROR) {
                Throwable cropError = UCrop.getError(data);
                cropError.printStackTrace();
            }
        }
    }

    private String uriToBase64(Uri imageUri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
            byte[] imageBytes = baos.toByteArray();
            return Base64.encodeToString(imageBytes, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void enviarFotoParaBackend(String base64) {
        FotoPerfilBase64Request request = new FotoPerfilBase64Request(email, base64);

        apiService.atualizarFotoPerfilBase64(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    getSharedPreferences("usuario_prefs", MODE_PRIVATE)
                            .edit()
                            .putString("fotoPerfil_" + email, "data:image/jpeg;base64," + base64)
                            .apply();

                    Toast.makeText(Config.this, "Foto atualizada com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Config.this, "Erro ao atualizar foto no servidor", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Config.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
