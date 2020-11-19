package cotuca.aplicativo.viaxar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import cotuca.aplicativo.viaxar.dbos.Atracao;
import cotuca.aplicativo.viaxar.dbos.Hotel;
import cotuca.aplicativo.viaxar.dbos.Pais;
import cotuca.aplicativo.viaxar.dbos.Cidade;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class PaisActivity extends AppCompatActivity {

    List<Cidade> listaCidades;
    List<Atracao> listaPontos;
    List<Atracao> listaRestaurantes;
    List<Hotel> listaHoteis;
    AtracoesAdapter atracoesAdapter;
    HotelAdapter hotelAdapter;
    TextView descricao, capital, lingua, moeda, populacao, clima, religiao, lgbt, nome, estado,cidadeNome;
    Button btn1, btn2, btn3;
    ListView lvPontos,lvRestaurantes,lvHoteis;
    ImageView imgCidade;
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

        lvHoteis = (ListView) findViewById(R.id.lvHoteis);
        lvPontos = (ListView) findViewById(R.id.lvPontosTuristicos);
        lvRestaurantes = (ListView) findViewById(R.id.lvRestaurantes);

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
        consultarCidadesPais(id);

        btn1 = (Button)findViewById(R.id.btnCidade1);
        btn2 = (Button)findViewById(R.id.btnCidade2);
        btn3 = (Button)findViewById(R.id.btnCidade3);
        estado = (TextView)findViewById(R.id.tvEstado);
        imgCidade = (ImageView)findViewById(R.id.imgCidade);
        cidadeNome = (TextView)findViewById(R.id.tvCidadeNome);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cidade cidade = listaCidades.get(0);
                estado.setText(cidade.getEstado());
                cidadeNome.setText(cidade.getNome());
                imgCidade.setImageBitmap(cidade.getImagem());
                consultarRestaurantes(cidade.getId());
                consultarPontos(cidade.getId());
                consultarHoteis(cidade.getId());
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cidade cidade = listaCidades.get(1);
                estado.setText(cidade.getEstado());
                cidadeNome.setText(cidade.getNome());
                imgCidade.setImageBitmap(cidade.getImagem());
                consultarRestaurantes(cidade.getId());
                consultarPontos(cidade.getId());
                consultarHoteis(cidade.getId());
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cidade cidade = listaCidades.get(2);
                estado.setText(cidade.getEstado());
                cidadeNome.setText(cidade.getNome());
                imgCidade.setImageBitmap(cidade.getImagem());
                consultarRestaurantes(cidade.getId());
                consultarPontos(cidade.getId());
                consultarHoteis(cidade.getId());
            }
        });
    }

    private class TaskCidade extends AsyncTask<String, String, List<Cidade>> {

        @Override
        protected void onPreExecute() {
            //progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Cidade> doInBackground(String... params) {
            try {
                for (Cidade cidade : listaCidades) {

                    String urlFoto = cidade.getFoto();

                    InputStream inputImagem = (InputStream) new URL(urlFoto).getContent();
                    Bitmap bitmapFoto = BitmapFactory.decodeStream(inputImagem);
                    cidade.setImagem(bitmapFoto);

                    inputImagem.close();
                }
            } catch (Exception err) {
                err.printStackTrace();
            }
            return listaCidades;
        }

        @Override
        protected void onPostExecute(List<Cidade> s) {
            btn1.callOnClick();
            //setListViewHeightBasedOnChildren(lvPais);
            //progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private class TaskPontos extends AsyncTask<String, String, List<Atracao>> {
        @Override
        protected void onPreExecute() {
            //progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Atracao> doInBackground(String... params) {
            try {
                for (Atracao atracao : listaPontos) {

                    String urlFoto = atracao.getImagem();

                    InputStream inputImagem = (InputStream) new URL(urlFoto).getContent();
                    Bitmap bitmapFoto = BitmapFactory.decodeStream(inputImagem);
                    atracao.setImagemBitmap(bitmapFoto);

                    inputImagem.close();
                }
            } catch (Exception err) {
                err.printStackTrace();
            }
            return listaPontos;
        }

        @Override
        protected void onPostExecute(List<Atracao> s) {
            //adapter aqui
            atracoesAdapter = new AtracoesAdapter(getBaseContext(), R.layout.card_atracoes, listaPontos);
            lvPontos.setAdapter(atracoesAdapter);

             //setListViewHeightBasedOnChildren(lvPais);
            //progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private class TaskRestaurantes extends AsyncTask<String, String, List<Atracao>> {
        @Override
        protected void onPreExecute() {
            //progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Atracao> doInBackground(String... params) {
            try {
                for (Atracao atracao : listaRestaurantes) {

                    String urlFoto = atracao.getImagem();

                    InputStream inputImagem = (InputStream) new URL(urlFoto).getContent();
                    Bitmap bitmapFoto = BitmapFactory.decodeStream(inputImagem);
                    atracao.setImagemBitmap(bitmapFoto);

                    inputImagem.close();
                }
            } catch (Exception err) {
                err.printStackTrace();
            }
            return listaRestaurantes;
        }

        @Override
        protected void onPostExecute(List<Atracao> s) {
            //adapter aqui
            atracoesAdapter = new AtracoesAdapter(getBaseContext(), R.layout.card_atracoes, listaRestaurantes);
            lvRestaurantes.setAdapter(atracoesAdapter);
            //setListViewHeightBasedOnChildren(lvPais);
            //progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private class TaskHoteis extends AsyncTask<String, String, List<Hotel>> {
        @Override
        protected void onPreExecute() {
            //progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Hotel> doInBackground(String... params) {
            try {
                for (Hotel hotel : listaHoteis) {

                    String urlFoto = hotel.getImagem();

                    InputStream inputImagem = (InputStream) new URL(urlFoto).getContent();
                    Bitmap bitmapFoto = BitmapFactory.decodeStream(inputImagem);
                    hotel.setImagemBitmap(bitmapFoto);

                    inputImagem.close();
                }
            } catch (Exception err) {
                err.printStackTrace();
            }
            return listaHoteis;
        }

        @Override
        protected void onPostExecute(List<Hotel> s) {
            //adapter aqui
            hotelAdapter = new HotelAdapter(getBaseContext(), R.layout.card_atracoes, listaHoteis);
            lvHoteis.setAdapter(hotelAdapter);
            //setListViewHeightBasedOnChildren(lvPais);
            //progressBar.setVisibility(View.INVISIBLE);
        }
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

    public void consultarPontos(int idCidade){
        // Classe Call é usada p/ executar a solicitação à API
        Call<List<Atracao>> call = new RetrofitConfig().getService().selecionarPontos(idCidade);
        call.enqueue(new Callback<List<Atracao>>() {
            @Override
            public void onResponse(Response<List<Atracao>> response, Retrofit retrofit) {
                if(response.isSuccess()) //conectou com o node
                {
                    listaPontos = response.body();
                    TaskPontos task = new TaskPontos();
                    task.execute();
                }
                else {
                    Toast.makeText(getApplication(), "Ocorreu um erro ao recuperar as informações dos pontos turísticos", Toast.LENGTH_LONG).show();
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

    public void consultarRestaurantes(int idCidade){
        // Classe Call é usada p/ executar a solicitação à API
        Call<List<Atracao>> call = new RetrofitConfig().getService().selecionarRestaurantes(idCidade);
        call.enqueue(new Callback<List<Atracao>>() {
            @Override
            public void onResponse(Response<List<Atracao>> response, Retrofit retrofit) {
                if(response.isSuccess()) //conectou com o node
                {
                    listaRestaurantes = response.body();
                    TaskRestaurantes task = new TaskRestaurantes();
                    task.execute();
                }
                else {
                    Toast.makeText(getApplication(), "Ocorreu um erro ao recuperar as informações dos restaurantes", Toast.LENGTH_LONG).show();
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

    public void consultarHoteis(int idCidade){
        // Classe Call é usada p/ executar a solicitação à API
        Call<List<Hotel>> call = new RetrofitConfig().getService().selecionarHoteis(idCidade);
        call.enqueue(new Callback<List<Hotel>>() {
            @Override
            public void onResponse(Response<List<Hotel>> response, Retrofit retrofit) {
                if(response.isSuccess()) //conectou com o node
                {
                    listaHoteis = response.body();
                    TaskHoteis task = new TaskHoteis();
                    task.execute();
                }
                else {
                    Toast.makeText(getApplication(), "Ocorreu um erro ao recuperar as informações dos hoteis", Toast.LENGTH_LONG).show();
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
                if(!response.isSuccess())
                {//conectou com o node
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
                    TaskCidade task = new TaskCidade();
                    task.execute();
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