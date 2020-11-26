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

public class PaisHomeAdapter extends ArrayAdapter<Pais> {
    Context context;
    int layoutResourceId;
    List<Pais> dados;
    View view;
    String continente,nome;

    public PaisHomeAdapter (@NonNull Context context, int resource, @NonNull List<Pais> dados) {
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
        TextView tvNome = (TextView) view.findViewById(R.id.tvNomeCidade);
        TextView tvContinente = (TextView) view.findViewById(R.id.tvContinente);

        final Pais pais = dados.get(position);

        imgPais.setImageBitmap(pais.getImagem());
        imgBandeira.setImageBitmap(pais.getImagemBandeira());

        if(pais.getNome().equals("Federated States of Micronesia"))
            tvNome.setText("Micronesia");
        else
        if(pais.getNome().equals("United Arab Emirates"))
            tvNome.setText("Emirates");
        else
            tvNome.setText(pais.getNome());

        switch (pais.getContinente())
        {
            case "EU": continente = "Europe";
            break;
            case "AM": continente = "America";
            break;
            case "OC": continente = "Oceania";
            break;
            case "AS": continente = "Asia";
            break;
            case "AF": continente = "Africa";
            break;

        }

        tvContinente.setText(continente);

        return view;
    }


}
