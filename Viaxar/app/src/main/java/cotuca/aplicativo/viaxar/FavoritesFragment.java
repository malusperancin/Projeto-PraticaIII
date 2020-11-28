package cotuca.aplicativo.viaxar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

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

public class FavoritesFragment extends Fragment {

    List<Pais> listaPais;
    ListView lvPais;
    SessionManager session;
    HashMap<String, String> user;
    PaisFavAdapter adapter;
    FavoritesFragmentViewModel favoritesFragmentViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favorites, container, false);
        favoritesFragmentViewModel = ViewModelProviders.of(this).get(FavoritesFragmentViewModel.class);
        session = new SessionManager(getContext());
        listaPais = new ArrayList<Pais>();
        lvPais = (ListView) root.findViewById(R.id.lvPaises);
        consultarPaisesFavoritos();
        //    lvPais.
        return root;
    }

    protected void atualizarView(){
        PaisFavAdapter adapter = new PaisFavAdapter(getActivity(), R.layout.pais_fav, listaPais);
        lvPais.setAdapter(adapter);
    }

    public void consultarPaisesFavoritos(){
        // Classe Call é usada p/ executar a solicitação à API
        user = session.getUserDetail();
        Call<List<Pais>> call = new RetrofitConfig().getService().selecionarFavs(Integer.parseInt(user.get(session.ID)));
        call.enqueue(new Callback<List<Pais>>() {

            @Override
            public void onResponse(Response<List<Pais>> response, Retrofit retrofit) {
                if(response.isSuccess()) //conectou com o node
                {
                    listaPais = response.body();
                    MyTask task = new MyTask();
                    task.execute();
                }
                else
                    Toast.makeText(getActivity(), "There was an error retrieving your favorite countries", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getActivity(), "A network error has occurred", Toast.LENGTH_LONG).show();
            }
        });
    }

    private class MyTask extends AsyncTask<String, String, List<Pais>>
    {
        @Override
        protected List<Pais> doInBackground(String... params) {
            for(Pais pais:listaPais){
                try{
                    String urlBandeira = pais.getBandeira();
                    String urlImagem = pais.getFoto();
                    InputStream inputStreamBandeira = (InputStream) new URL(urlBandeira).getContent();
                    Bitmap bitmapBandeira = BitmapFactory.decodeStream(inputStreamBandeira);

                    InputStream inputStreamFoto = (InputStream) new URL(urlImagem).getContent();
                    Bitmap bitmapFoto = BitmapFactory.decodeStream(inputStreamFoto);

                    pais.setImagem(bitmapFoto);
                    pais.setImagemBandeira(bitmapBandeira);

                    inputStreamFoto.close();
                    inputStreamBandeira.close();
                }
                catch (Exception e){e.printStackTrace();}
            }

            return listaPais;
        }

        @Override
        protected void onPostExecute(List<Pais> s)
        {
            adapter = new PaisFavAdapter(getActivity(), R.layout.pais_fav, s);
            lvPais.setAdapter(adapter);
        }
    }
}