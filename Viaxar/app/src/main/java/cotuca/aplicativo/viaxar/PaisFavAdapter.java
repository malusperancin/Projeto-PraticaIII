package cotuca.aplicativo.viaxar;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.List;

import cotuca.aplicativo.viaxar.dbos.Pais;
import cotuca.aplicativo.viaxar.dbos.Usuario;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class PaisFavAdapter extends ArrayAdapter<Pais> {
    Context context;
    int layoutResourceId;
    List<Pais> dados;
    View view;

    public PaisFavAdapter (@NonNull Context context, int resource, @NonNull List<Pais> dados) {
        super(context, resource, dados);

        //Informação global do ambiente. Define o resource usado
        this.context = context;

        //Id do layout que queremos usar de referência
        this.layoutResourceId = resource;

        //Lista de dados
        this.dados = dados;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

        view = convertView;

        if(view == null) {
            LayoutInflater layoutinflater = LayoutInflater.from(context);
            view = layoutinflater.inflate(layoutResourceId,parent,false);
        }

        ImageView imgPais = (ImageView) view.findViewById(R.id.imgBack);
        ImageView imgBandeira = (ImageView) view.findViewById(R.id.imgBandeira);
        TextView tvNome = (TextView) view.findViewById(R.id.tvNome);
        TextView tvContinente = (TextView) view.findViewById(R.id.tvContinente);
        TextView tvLingua = (TextView) view.findViewById(R.id.tvLingua);

        final Pais pais = dados.get(position);

        imgPais.setImageBitmap(pais.getImagem());
        imgBandeira.setImageBitmap(pais.getImagemBandeira());
        tvNome.setText(pais.getNome());
        tvContinente.setText(pais.getContinente());
        tvLingua.setText(pais.getIdioma());

        ImageView fav = (ImageView) view.findViewById(R.id.imgCoracaoVermelho);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager session = new SessionManager(getContext());
                HashMap<String, String>  user = session.getUserDetail();
                Call<Usuario> call = new RetrofitConfig().getService().excluirFavorito(Integer.parseInt(user.get(session.ID)),pais.getId());
                call.enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Response<Usuario> response, Retrofit retrofit) {
                        if(response.isSuccess()) //conectou com o node
                        {
                            Toast.makeText(getContext(), "uhul foi", Toast.LENGTH_LONG).show();
                            parent.removeViewInLayout(parent.getChildAt(position));
                            //parent.notifyDataSetChanged();
                            //parent.removeView(parent);
                            //parent.getChildAt(position).setVisibility(View.GONE);
                            //parent.refreshDrawableState()
                            //view.refreshDrawableState();
                        }
                        else
                            Toast.makeText(getContext(), "Ocorreu um erro ao excluir o pais fav", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getContext(), "Ocorreu um erro na rede", Toast.LENGTH_LONG).show();
                    }

                });
            }
        });

        return view;
    }
}
