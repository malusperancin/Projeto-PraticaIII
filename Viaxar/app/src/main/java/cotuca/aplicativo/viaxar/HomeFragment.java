package cotuca.aplicativo.viaxar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

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
                        if (listaPais.get(i).getNome().toLowerCase().contains(newText.toLowerCase()))
                            consulta.add(listaPais.get(i));
                }
                else
                {
                    consulta = new ArrayList<Pais>(listaPais);
                    tvIndicador.setText("All");
                }


                adapter = new PaisHomeAdapter(getActivity(), R.layout.pais_home, consulta);

                lvPais.setAdapter(adapter);

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
                    consulta = response.body();
                    adapter = new PaisHomeAdapter(getActivity(), R.layout.pais_home, listaPais);
                    lvPais.setAdapter(adapter);
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
}
