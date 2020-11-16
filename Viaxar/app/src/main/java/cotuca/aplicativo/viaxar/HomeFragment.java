package cotuca.aplicativo.viaxar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import cotuca.aplicativo.viaxar.R;
import cotuca.aplicativo.viaxar.dbos.Pais;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class HomeFragment extends Fragment {

    List<Pais> listaPais,consulta;
    ListView lvPais;
    SearchView pesquisa;
    PaisHomeAdapter adapter;
    TextView tvIndicador;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        listaPais = new ArrayList<Pais>();
        lvPais = (ListView) root.findViewById(R.id.lvPaises);
        lvPais.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), PaisActivity.class);
                Bundle params = new Bundle();

                Pais pais = (Pais) adapterView.getItemAtPosition(i);

                params.putInt ("idPais", pais.getId());

                intent.putExtras(params);

                startActivity(intent);
            }
        });


        tvIndicador = (TextView) root.findViewById(R.id.tvIndicador);

        pesquisa = (SearchView) root.findViewById(R.id.searchView);
        pesquisa.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                consulta = new ArrayList<Pais>();

                if(!newText.equals("")) {
                    tvIndicador.setText("'"+newText+"'");
                    for (int i = 0; i < listaPais.size(); i++)
                        if (listaPais.get(i).getNome().toLowerCase().contains(newText.toLowerCase()) ||
                            listaPais.get(i).getContinente().toLowerCase().contains(newText.toLowerCase()))
                            consulta.add(listaPais.get(i));
                }
                else
                {
                    consulta = new ArrayList<Pais>(listaPais);
                    tvIndicador.setText("All");
                }


                adapter = new PaisHomeAdapter(getActivity(), R.layout.pais_home, consulta);
                lvPais.setAdapter(adapter);
                setListViewHeightBasedOnChildren(lvPais);

                return false;
            }
        });

        consultarPaises();

        return root;
    }

    protected void atualizarView(){
        PaisHomeAdapter adapter = new PaisHomeAdapter(getActivity(), R.layout.pais_home, listaPais);
        lvPais.setAdapter(adapter);
    }

    public void consultarPaises()
    {
        Call<List<Pais>> call = new RetrofitConfig().getService().selecionarPaises();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Response<List<Pais>> response, Retrofit retrofit) {
                if(response.isSuccess()) //conectou com o node
                {
                    listaPais = response.body();
                    MyTask task = new MyTask();
                    task.execute();//listaPais);
                    //atualizarView();
                }
                else
                    Toast.makeText(getActivity(), "Ocorreu um erro ao recuperar os paises", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getActivity(), "Ocorreu um erro na rede", Toast.LENGTH_LONG).show();
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

                    String urlFoto = pais.getFoto();
                    String urlBandeira = pais.getBandeira();

                    InputStream inputImagem = (InputStream) new URL(urlFoto).getContent();
                    Bitmap bitmapFoto = BitmapFactory.decodeStream(inputImagem);
                    pais.setImagem(bitmapFoto);

                    InputStream inputBandeira = (InputStream) new URL(urlBandeira).getContent();
                    Bitmap bitmapBandeira = BitmapFactory.decodeStream(inputBandeira);
                    pais.setImagemBandeira(bitmapBandeira);

                    inputImagem.close();
                    inputBandeira.close();
                }
            } catch (Exception err) {
                err.printStackTrace();
            }

            return listaPais;
        }

        @Override
        protected void onPostExecute(List<Pais> s) {
            adapter = new PaisHomeAdapter(getActivity(), R.layout.pais_home, s);
            lvPais.setAdapter(adapter);
            setListViewHeightBasedOnChildren(lvPais);
            //progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null)
            return;

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1)) + 25;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
