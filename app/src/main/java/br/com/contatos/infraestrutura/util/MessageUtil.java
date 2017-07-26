package br.com.contatos.infraestrutura.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import br.com.contatos.R;
import br.com.contatos.visao.usuario.UsuarioActivity;

/**
 * Created by jean on 25/07/2017.
 */

public class MessageUtil {

    public static void exibeMensagem(String mensagem, String tipoMensagem, Activity activity){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(activity);
        dialogo.setTitle(tipoMensagem);
        dialogo.setMessage(mensagem);
        dialogo.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.dismiss();
            }
        });
        dialogo.create();
        dialogo.show();
    }

}
