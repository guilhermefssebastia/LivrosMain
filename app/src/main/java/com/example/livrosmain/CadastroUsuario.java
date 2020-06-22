package com.example.livrosmain;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

public class CadastroUsuario extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    EditText Edit_codCli,Edit_NomeCli, Edit_EmailCli,Edit_Cpfcli,Edit_TelCli, Edit_EndCli, Edit_CEPcli,Edit_SenhaCli;
    Button btnCriaCli;
    boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_usuario_layout);

        btnCriaCli = (Button) findViewById(R.id.btnCriaCli);
        Edit_codCli = (EditText) findViewById(R.id.Edit_codCli);
        Edit_NomeCli = (EditText) findViewById(R.id.Edit_NomeCli);
        Edit_EmailCli = (EditText) findViewById(R.id.Edit_EmailCli);
        Edit_Cpfcli = (EditText) findViewById(R.id.Edit_Cpfcli);
        Edit_TelCli = (EditText) findViewById(R.id.Edit_TelCli);
        Edit_EndCli = (EditText) findViewById(R.id.Edit_EndCli);
        Edit_CEPcli = (EditText) findViewById(R.id.Edit_CEPcli);

        btnCriaCli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdating = false;

                if (isUpdating) {

                } else {
                    createCadastroCli();
                }
            }
        });

    }

    private void createCadastroCli() {
        String nomeCli = Edit_NomeCli.getText().toString().trim();
        String emailCli = Edit_EmailCli.getText().toString().trim();
        String CPFcli = Edit_Cpfcli.getText().toString().trim();
        String TelCli = Edit_TelCli.getText().toString().trim();
        String EndCli = Edit_EndCli.getText().toString().trim();
        String CEPcli = Edit_CEPcli.getText().toString().trim();
        String SenhaCli = Edit_SenhaCli.getText().toString().trim();

        if (TextUtils.isEmpty(nomeCli)) {
            Edit_NomeCli.setError("Por favor entre com o nome");
            Edit_NomeCli.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(emailCli)) {
            Edit_EmailCli.setError("Por favor insira seu e-mail");
            Edit_EmailCli.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(CPFcli)) {
            Edit_Cpfcli.setError("Por favor entre com o seu CPF");
            Edit_Cpfcli.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(EndCli)) {
            Edit_EndCli.setError("Por favor insira seu Endereço");
            Edit_EndCli.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(CEPcli)) {
            Edit_CEPcli.setError("Por favor insira seu CEP");
            Edit_CEPcli.requestFocus();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("nomeCli", nomeCli);
        params.put("emailCli", emailCli);
        params.put("CPFcli", CPFcli);
        params.put("TelCli", TelCli);
        params.put("EndCli", EndCli);
        params.put("CEPcli", CEPcli);

        CadastroUsuario.PerformNetworkRequest request = new CadastroUsuario.PerformNetworkRequest(Api.URL_CREATE_CADASTRO_CLI, params, CODE_POST_REQUEST);
        request.execute();
    }

    private void getCadastroCli() {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_GET_CADASTRO_CLI, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void updatecli() {
        String codCli = Edit_codCli.getText().toString().trim();
        String nomeCli = Edit_NomeCli.getText().toString().trim();
        String emailCli = Edit_EmailCli.getText().toString().trim();
        String CPFcli = Edit_Cpfcli.getText().toString().trim();
        String TelCli = Edit_TelCli.getText().toString().trim();
        String EndCli = Edit_EndCli.getText().toString().trim();
        String CEPcli = Edit_CEPcli.getText().toString().trim();
        String SenhaCli = Edit_SenhaCli.getText().toString().trim();

        if (TextUtils.isEmpty(nomeCli)) {
            Edit_NomeCli.setError("Por favor entre com o nome");
            Edit_NomeCli.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(emailCli)) {
            Edit_EmailCli.setError("Por favor insira seu e-mail");
            Edit_EmailCli.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(CPFcli)) {
            Edit_Cpfcli.setError("Por favor entre com o seu CPF");
            Edit_Cpfcli.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(EndCli)) {
            Edit_EndCli.setError("Por favor insira seu Endereço");
            Edit_EndCli.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(CEPcli)) {
            Edit_CEPcli.setError("Por favor insira seu CEP");
            Edit_CEPcli.requestFocus();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("codCli", nomeCli);
        params.put("nomeCli", nomeCli);
        params.put("emailCli", emailCli);
        params.put("CPFcli", CPFcli);
        params.put("TelCli", TelCli);
        params.put("EndCli", EndCli);
        params.put("CEPcli", CEPcli);
        params.put("SenhaCli", SenhaCli);


        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_UPDATE_CLI, params, CODE_POST_REQUEST);
        request.execute();

        btnCriaCli.setText("Adicionar");
        Edit_NomeCli.setText("");
        Edit_EmailCli.setText("");
        Edit_Cpfcli.setText("");
        Edit_TelCli.setText("");
        Edit_EndCli.setText("");
        Edit_CEPcli.setText("");
        Edit_SenhaCli.setText("");

        isUpdating = false;
    }

    private void deletecli(int codCli) {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_DELETE_CLI + codCli, null, CODE_GET_REQUEST);
        request.execute();
    }


    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
        String url;
        HashMap<String, String> params;
        int requestCode;

        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }
}
