package com.example.promohawk;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class FavoritosManager {
    private static final String PREFS_NAME = "favoritos";
    private static final String KEY_LIST = "ids_favoritos";

    public static Set<String> getFavoritos(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getStringSet(KEY_LIST, new HashSet<>());
    }

    public static void toggleFavorito(Context context, String id) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> favoritos = new HashSet<>(getFavoritos(context));

        if (favoritos.contains(id)) {
            favoritos.remove(id);
        } else {
            favoritos.add(id);
        }

        prefs.edit().putStringSet(KEY_LIST, favoritos).apply();
    }

    public static boolean isFavorito(Context context, String id) {
        return getFavoritos(context).contains(id);
    }
}

