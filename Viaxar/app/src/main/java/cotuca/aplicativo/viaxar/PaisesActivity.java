package cotuca.aplicativo.viaxar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cotuca.aplicativo.viaxar.dbos.Pais;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class PaisesActivity extends AppCompatActivity {

    List<Pais> listaPais;
    ListView lvPais;
    SessionManager session;
    HashMap<String, String> user;
    String continente;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_favorites, container, false);
        session = new SessionManager(this);
        listaPais = new ArrayList<Pais>();
        lvPais = (ListView) root.findViewById(R.id.listaPaises);
        consultarPaisesFavoritos();

        Intent intent = getIntent();
        Bundle params = intent.getExtras();

        continente = params.getString("continente");

        return root;
    }

    protected void atualizarView(){
        PaisFavAdapter adapter = new PaisFavAdapter(this, R.layout.pais_fav, listaPais);
        lvPais.setAdapter(adapter);
    }

    public void consultarPaisesFavoritos(){
        // Classe Call é usada p/ executar a solicitação à API
        user = session.getUserDetail();
        Call<List<Pais>> call = new RetrofitConfig().getService().selecionarPaisesContinente(continente);
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Response<List<Pais>> response, Retrofit retrofit) {
                if(response.isSuccess()) //conectou com o node
                {
                    listaPais = response.body();
                    atualizarView();
                }
                else
                    Toast.makeText(getApplication(), "Ocorreu um erro ao recuperar os paises favs", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplication(), "Ocorreu um erro na rede", Toast.LENGTH_LONG).show();
            }
        });
    }
}