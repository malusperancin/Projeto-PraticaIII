package cotuca.aplicativo.viaxar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;
import java.util.List;
import cotuca.aplicativo.viaxar.dbos.Pais;
import cotuca.aplicativo.viaxar.dbos.Cidade;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class PaisActivity extends AppCompatActivity {

    List<Cidade> listaCidades;
    TextView descricao, capital, lingua, moeda, populacao, clima, religiao, lgbt,nome;
    boolean ehFavorito;

    ImageView back, fav;
    SessionManager session;
    Pais pais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pais);

        session = new SessionManager(this);

        final HashMap<String, String> user = session.getUserDetail();

        Intent intent = getIntent();
        Bundle params = intent.getExtras();
        final int id = params.getInt("idPais");

        back = (ImageView) findViewById(R.id.imgBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //Toast.makeText(getApplication(), "Entro", Toast.LENGTH_LONG).show();
               finish();
            }
        });

        fav = (ImageView) findViewById(R.id.imgFav);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarAosFavoritos();
                checarFavorito(Integer.parseInt(user.get(session.ID)),id);
            }
        });

        nome = (TextView) findViewById(R.id.nome_pais);
        descricao = (TextView) findViewById(R.id.descricao);
        capital = (TextView) findViewById(R.id.tvCapital);
        lingua = (TextView) findViewById(R.id.tvLingua);
        moeda = (TextView) findViewById(R.id.tvMoeda);
        populacao = (TextView) findViewById(R.id.tvPopulacao);
        clima = (TextView) findViewById(R.id.tvClima);
        religiao = (TextView) findViewById(R.id.tvReligiao);
        lgbt = (TextView) findViewById(R.id.tvLGBT);

        consultarPais(id);
        checarFavorito(Integer.parseInt(user.get(session.ID)),id);
        //consultarCidadesPais(id);
    }

    public void adicionarAosFavoritos(){
        // Classe Call é usada p/ executar a solicitação à API
        Call<Pais> call = new RetrofitConfig().getService().adicionarPaisFavoritos(pais,  Integer.parseInt(session.getUserDetail().get(session.ID)));
        call.enqueue(new Callback<Pais>() {
            @Override
            public void onResponse(Response<Pais> response, Retrofit retrofit) {
                if(response.isSuccess()) //conectou com o node
                {
                    //COLOCAR CORAÇÃO VERMELHO
                }
                else
                    Toast.makeText(getApplication(), "Erro ao adicionar esse país aos favoritos", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplication(), "Ocorreu um erro na rede", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void consultarPais(int id){
        // Classe Call é usada p/ executar a solicitação à API
        Call<List<Pais>> call = new RetrofitConfig().getService().selecionarPais(id);
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Response<List<Pais>> response, Retrofit retrofit) {
                if(response.isSuccess()) //conectou com o node
                {
                    pais = response.body().get(0);

                    nome.setText(pais.getNome());
                    descricao.setText(pais.getDescricao());
                    capital.setText(pais.getCapital());
                    lingua.setText(pais.getIdioma());
                    moeda.setText(pais.getMoeda());
                    populacao.setText(pais.getPopulacao() + "");
                    clima.setText(pais.getClima());
                    religiao.setText(pais.getReligiao());

                    if(pais.isLgbt())
                        lgbt.setText("Friendly");
                    else
                        lgbt.setText("Not safe");
                }
                else {
                    Toast.makeText(getApplication(), "Ocorreu um erro ao recuperar as informações deste país", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplication(), "Ocorreu um erro na rede", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }


    public void checarFavorito(int idPais, int idUsuario){
        // Classe Call é usada p/ executar a solicitação à API

        Call<Boolean> call = new RetrofitConfig().getService().checarFavorito(idUsuario, idPais);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Response<Boolean> response, Retrofit retrofit) {
                if(response.isSuccess()) //conectou com o node
                {
                    if(response.body())
                        Toast.makeText(getApplication(), "FIOIOIO", Toast.LENGTH_LONG).show();
                     //   fav.setBackgroundResource(R.drawable.red_fav);
                }
                else {
                    Toast.makeText(getApplication(), "Ocorreu um erro ao recuperar as informações deste país", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplication(), "Ocorreu um erro na rede", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    public void consultarCidadesPais(int id){
        // Classe Call é usada p/ executar a solicitação à API
        Call<List<Cidade>> call = new RetrofitConfig().getService().selecionarCidadesByPais(id);
        call.enqueue(new Callback<List<Cidade>>() {
            @Override
            public void onResponse(Response<List<Cidade>> response, Retrofit retrofit) {
                if(response.isSuccess()) //conectou com o node
                {
                    listaCidades = response.body();
                    //atualizarView();
                }
                else {
                    Toast.makeText(getApplication(), "Ocorreu um erro ao recuperar os paises favs", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplication(), "Ocorreu um erro na rede", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}