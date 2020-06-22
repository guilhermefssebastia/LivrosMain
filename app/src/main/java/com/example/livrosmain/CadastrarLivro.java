package com.example.livrosmain;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CadastrarLivro extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    EditText EditTituloLivro, EditISBNlivro, EditAutorLivro, EditDonoLivro, EditcodLivro;
    Button btnCadastrarLivro;
    ListView ListaLivros;
    List<Livros> LivroList;
    boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar_livro_layout);
        EditcodLivro = (EditText) findViewById(R.id.Edicodlivro);
        EditTituloLivro = (EditText) findViewById(R.id.EditTituloLivro);
        EditISBNlivro = (EditText) findViewById(R.id.EditISBNlivro);
        EditAutorLivro = (EditText) findViewById(R.id.EditAutorLivro);
        EditDonoLivro = (EditText) findViewById(R.id.EditDonoLivro);
        btnCadastrarLivro = (Button) findViewById(R.id.btnCadastrarLivro);
        ListaLivros = (ListView) findViewById(R.id.ListaLivros);
        LivroList = new ArrayList<>();

        btnCadastrarLivro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isUpdating) {
                    updateCadastroLivro();
                } else {
                    createCadastroLivro();
                }
            }
        });
        getCadastroLivro();
    }

    private void createCadastroLivro() {
        String nomeLivro = EditTituloLivro.getText().toString().trim();
        String ISBNlivro = EditISBNlivro.getText().toString().trim();
        String autorLivro = EditAutorLivro.getText().toString().trim();
        String donoLivro = EditDonoLivro.getText().toString().trim();

        if (TextUtils.isEmpty(nomeLivro)) {
            EditTituloLivro.setError("Insira o Título do Livro");
            EditTituloLivro.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(ISBNlivro)) {
            EditISBNlivro.setError("Entre com o ISBN do Livro");
            EditISBNlivro.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(donoLivro)) {
            EditDonoLivro.setError("Digite o seu Nome");
            EditDonoLivro.requestFocus();
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("nomeLivro", nomeLivro);
        params.put("ISBNlivro", ISBNlivro);
        params.put("autor", autorLivro);
        params.put("donoLivro", donoLivro);

        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_CREATE_CADASTRO_LIVRO, params, CODE_POST_REQUEST);
        request.execute();
    }

    private void getCadastroLivro() {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_GET_CADASTRO_LIVRO, null, CODE_GET_REQUEST);
    }

    private void updateCadastroLivro() {
        String codLivro = EditcodLivro.getText().toString();
        String nomeLivro = EditTituloLivro.getText().toString().trim();
        String ISBNlivro = EditISBNlivro.getText().toString().trim();
        String autorLivro = EditAutorLivro.getText().toString().trim();
        String donoLivro = EditDonoLivro.getText().toString().trim();

        if (TextUtils.isEmpty(nomeLivro)) {
            EditTituloLivro.setError("Insira o Título do Livro");
            EditTituloLivro.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(ISBNlivro)) {
            EditISBNlivro.setError("Entre com o ISBN do Livro");
            EditISBNlivro.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(donoLivro)) {
            EditDonoLivro.setError("Digite o seu Nome");
            EditDonoLivro.requestFocus();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("codLivro", codLivro);
        params.put("nomeLivro", nomeLivro);
        params.put("ISBNlivro", ISBNlivro);
        params.put("autor", autorLivro);
        params.put("donoLivro", donoLivro);


        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_UPDATE_CADASTRO_LIVRO, params, CODE_POST_REQUEST);
        request.execute();

        btnCadastrarLivro.setText("Cadastrar");

        EditTituloLivro.setText("");
        EditISBNlivro.setText("");
        EditAutorLivro.setText("");
        EditDonoLivro.setText("");
        isUpdating = false;
    }

    private void deleteCadastroLivros(int codLivro) {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_DELETE_CADASTRO_LIVRO + codLivro, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void refreshLivroList(JSONArray Livros) throws JSONException {
        LivroList.clear();

        for (int i = 0; i < Livros.length(); i++) {
            JSONObject obj = Livros.getJSONObject(i);

            LivroList.add(new Livros(
                    obj.getInt("codCli"),
                    obj.getString("NomeLivro"),
                    obj.getInt("ISBNlivro"),
                    obj.getString("autor"),
                    obj.getString("donoLivro")


            ));
        }
        LivroAdapter adapter = new LivroAdapter(LivroList);
    }

    private class PerformNetworkRequest extends Async<Void, Void, String> {
        String url;
        HashMap<String, String> params;
        int requesteCode;

        PerformNetworkRequest(String url, HashMap<String, String> params, int requesteCode) {
            this.url = url;
            this.params = params;
            this.requesteCode = requesteCode;
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requesteCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requesteCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }


    class LivroAdapter extends ArrayAdapter<Livros> {
        List<Livros> livrosList;

        public LivroAdapter(List<Livros> livrosList) {
            super(CadastrarLivro.this, R.layout.modelolista, livrosList);
            this.livrosList = livrosList;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewitem = inflater.inflate(R.layout.modelolista, null, true);

            TextView textViewTitulo = listViewitem.findViewById(R.id.TextViewTitulo);
            TextView textViewIBSNlivro = listViewitem.findViewById(R.id.TextViewISBNlivro);
            TextView textViewAutor = listViewitem.findViewById(R.id.TextViewAutorLivro);
            TextView textViewDono = listViewitem.findViewById(R.id.TextViewDono);

            TextView textViewAlterarDados = listViewitem.findViewById(R.id.TextViewAlterarDados);
            TextView textViewExcluirLivro = listViewitem.findViewById(R.id.TextViewExcluirLivro);

            final Livros livros = livrosList.get(position);
            textViewTitulo.setText(livros.getTituloLivro());
            textViewIBSNlivro.setText(livros.getISBNlivro());
            textViewAutor.setText(livros.getAutorLivro());
            textViewDono.setText(livros.getDonoLivro());

            textViewAlterarDados.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isUpdating = true;
                    isUpdating = true;
                    EditcodLivro.setText(String.valueOf(livros.getCodLivro()));
                    EditTituloLivro.setText(livros.getTituloLivro());
                    EditISBNlivro.setText(String.valueOf(livros.getISBNlivro()));
                    EditAutorLivro.setText(livros.getAutorLivro());
                    EditDonoLivro.setText(livros.getDonoLivro());
                    btnCadastrarLivro.setText("Alterar");
                }
            });

            textViewExcluirLivro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(CadastrarLivro.this);

                    builder.setTitle("Apagar " + livros.getTituloLivro())
                            .setMessage("Tem certeza que deseja exluir?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteCadastroLivros(livros.getCodLivro());
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            });

            return listViewitem;
        }
    }

}



