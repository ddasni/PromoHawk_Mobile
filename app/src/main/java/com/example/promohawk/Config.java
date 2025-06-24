package com.example.promohawk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwnerKt;
import com.bumptech.glide.Glide;
import com.example.promohawk.api.ApiService;
import com.example.promohawk.api.RetrofitClient;
import com.example.promohawk.model.FotoPerfilBase64Request;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Config extends AppCompatActivity {

    private ImageView imgPerfil;
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
        btnFavoritos = findViewById(R.id.btnFavoritos);
        btnConta = findViewById(R.id.btnConta);
        btnHistorico = findViewById(R.id.btnHistorico);
        btnSuporte = findViewById(R.id.btnSuporte);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        configurarBottomNavigation();



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
            }else if (id == R.id.nav_lojas) {
                startActivity(new Intent(this, Lojas.class));
                overridePendingTransition(0,0);
                return true;
            }
            return false;
        });
    }

    private void carregarFotoPerfil() {
        SharedPreferences prefs = getSharedPreferences("usuario_prefs", MODE_PRIVATE);
        String fotoBase64 = prefs.getString("fotoPerfil_" + email, null);

        try {
            if (fotoBase64 != null && !fotoBase64.isEmpty()) {
                // Verifica se é uma string Base64 pura ou com prefixo data:image
                String base64Pure = fotoBase64.contains(",") ?
                        fotoBase64.substring(fotoBase64.indexOf(",") + 1) :
                        fotoBase64;

                byte[] decodedBytes = Base64.decode(base64Pure, Base64.DEFAULT);

                Glide.with(this)
                        .asBitmap()
                        .load(decodedBytes)
                        .error(R.drawable.ic_launcher_foreground) // Caso ocorra erro no decode
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .circleCrop() // Adiciona efeito circular
                        .into(imgPerfil);
            } else {
                imgPerfil.setImageResource(R.drawable.ic_launcher_foreground);
            }
        } catch (Exception e) {
            e.printStackTrace();
            imgPerfil.setImageResource(R.drawable.ic_launcher_foreground);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            if (resultCode == UCrop.RESULT_ERROR && data != null) {
                Throwable cropError = UCrop.getError(data);
                Toast.makeText(this, "Erro ao cortar imagem: " + cropError.getMessage(), Toast.LENGTH_SHORT).show();
            }
            return;
        }

        switch (requestCode) {
            case PICK_IMAGE_REQUEST:
                if (data != null && data.getData() != null) {
                    iniciarCorteImagem(data.getData());
                }
                break;

            case UCrop.REQUEST_CROP:
                final Uri resultUri = UCrop.getOutput(data);
                if (resultUri != null) {
                    processarImagemCortada(resultUri);
                }
                break;
        }
    }

    private void iniciarCorteImagem(Uri sourceUri) {
        try {
            // Formato simplificado e seguro para nome de arquivo
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            String fileName = "CROPPED_" + timeStamp + ".jpg";

            // Cria o arquivo no diretório de cache
            File cacheDir = getCacheDir();
            File tempFile = new File(cacheDir, fileName);

            // Cria a URI a partir do arquivo
            Uri destinationUri = Uri.fromFile(tempFile);

            UCrop.Options options = new UCrop.Options();
            options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
            options.setCompressionQuality(80);
            options.setHideBottomControls(true);
            options.setFreeStyleCropEnabled(true);

            UCrop.of(sourceUri, destinationUri)
                    .withAspectRatio(1, 1)
                    .withMaxResultSize(800, 800)
                    .withOptions(options)
                    .start(this);

        } catch (Exception e) {
            Toast.makeText(this, "Erro ao preparar imagem: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("ImageCrop", "Erro no corte de imagem", e);
        }
    }

    private void processarImagemCortada(Uri resultUri) {
        Glide.with(this)
                .load(resultUri)
                .circleCrop()
                .into(imgPerfil);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {
                String base64 = uriToBase64(resultUri);
                handler.post(() -> {
                    if (base64 != null) {
                        enviarFotoParaBackend(base64);
                    } else {
                        Toast.makeText(Config.this, "Erro ao processar imagem", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                handler.post(() -> {
                    Toast.makeText(Config.this, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private String uriToBase64(Uri imageUri) {
        try (InputStream inputStream = getContentResolver().openInputStream(imageUri);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            if (bitmap == null) return null;

            // Redimensiona se necessário para evitar imagens muito grandes
            int maxDimension = 1024;
            if (bitmap.getWidth() > maxDimension || bitmap.getHeight() > maxDimension) {
                bitmap = Bitmap.createScaledBitmap(
                        bitmap,
                        maxDimension,
                        maxDimension,
                        true
                );
            }

            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
            return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void enviarFotoParaBackend(String base64) {
        if (base64 == null || base64.isEmpty()) {
            Toast.makeText(this, "Imagem inválida", Toast.LENGTH_SHORT).show();
            return;
        }

        // Mostra progresso
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Enviando imagem...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        FotoPerfilBase64Request request = new FotoPerfilBase64Request(email, base64);

        apiService.atualizarFotoPerfilBase64(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()) {
                    getSharedPreferences("usuario_prefs", MODE_PRIVATE)
                            .edit()
                            .putString("fotoPerfil_" + email, "data:image/jpeg;base64," + base64)
                            .apply();

                    Toast.makeText(Config.this, "Foto atualizada com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Config.this, "Erro no servidor: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Config.this, "Falha na conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}