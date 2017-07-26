package br.com.contatos.infraestrutura.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static android.content.Context.MODE_PRIVATE;

/**
 * Função: Essa classe foi criada para concentrar métodos utilitários.
 */

public class Util {



    public static boolean verificaConectado(Context contexto){
        ConnectivityManager manager = (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();

        if (activeNetwork != null)
            return true;

        return false;
    }




}
