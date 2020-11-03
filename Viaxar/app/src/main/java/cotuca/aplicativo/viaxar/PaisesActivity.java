package cotuca.aplicativo.viaxar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
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
    GridView gvPais;
    SessionManager session;
    HashMap<String, String> user;
    String continente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paises);

        Intent intent = getIntent();
        Bundle params = intent.getExtras();
        continente = params.getString("continente");

        session = new SessionManager(this);
        listaPais = new ArrayList<Pais>();
        gvPais = (GridView) findViewById(R.id.listaPaises);
        gvPais.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), PaisActivity.class);
                Bundle params = new Bundle();

                Pais pais = (Pais) adapterView.getItemAtPosition(i);

                params.putInt ("idPais", pais.getId());

                intent.putExtras(params);

                startActivity(intent);
            }
        });

        ImageView back = (ImageView) findViewById(R.id.imgBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        consultarPaisesContinente();
    }

    protected void atualizarView(){
        PaisContinenteItemAdapter adapter = new PaisContinenteItemAdapter(this, R.layout.pais_continente_item, listaPais);
        gvPais.setAdapter(adapter);
    }

    public void consultarPaisesContinente(){
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