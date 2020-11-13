package cotuca.aplicativo.viaxar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import cotuca.aplicativo.viaxar.dbos.Pais;

public class CidadeHomeAdapter extends ArrayAdapter<Pais> {

    Context context;
    int layoutResourceId;
    List<Pais> dados;

    public CidadeHomeAdapter(@NonNull Context context, int resource, @NonNull List<Pais> dados) {
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
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if(view == null) {
            LayoutInflater layoutinflater = LayoutInflater.from(context);
            view = layoutinflater.inflate(layoutResourceId,parent,false);
        }

        ImageView imgPais = (ImageView) view.findViewById(R.id.imgBack);
        ImageView imgBandeira = (ImageView) view.findViewById(R.id.imgBandeira);
        TextView tvPais = (TextView) view.findViewById(R.id.tvNome);
        TextView tvContinente = (TextView) view.findViewById(R.id.tvContinente);
        TextView tvLingua = (TextView) view.findViewById(R.id.tvLingua);

        Pais pais = dados.get(position);
        imgPais.setImageBitmap(pais.getImagem());
        //imgBandeira.setImageDrawable(pais.getImagemBandeira());
        tvPais.setText(pais.getNome());
        tvContinente.setText(pais.getContinente());
        tvLingua.setText(pais.getIdioma());

        return view;
    }
}
