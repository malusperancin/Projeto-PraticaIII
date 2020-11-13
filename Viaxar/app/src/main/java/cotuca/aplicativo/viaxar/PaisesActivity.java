package cotuca.aplicativo.viaxar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
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
    PaisContinenteItemAdapter adapter;
    HashMap<String, String> user;
    String continente;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paises);

        Intent intent = getIntent();
        Bundle params = intent.getExtras();
        continente = params.getString("continente");

        session = new SessionManager(this);
        listaPais = new ArrayList<Pais>();
        gvPais = (GridView) findViewById(R.id.lvPaises);
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
                    MyTask task = new MyTask();
                    task.execute();
                    //atualizarView();
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

    private class MyTask extends AsyncTask<String, String, List<Pais>> {

        @Override
        protected void onPreExecute() {
            //progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Pais> doInBackground(String... params) {
            try {
                for (Pais pais : listaPais) {

                    String urlBandeira = pais.getBandeira();

                    InputStream inputBandeira = (InputStream) new URL(urlBandeira).getContent();
                    Bitmap bitmapBandeira = BitmapFactory.decodeStream(inputBandeira);
                    pais.setImagemBandeira(bitmapBandeira);

                    inputBandeira.close();
                }
            } catch (Exception err) {
                err.printStackTrace();
            }


            return listaPais;
        }

        @Override
        protected void onPostExecute(List<Pais> s) {
            atualizarView();
            //progressBar.setVisibility(View.INVISIBLE);
        }
    }
}