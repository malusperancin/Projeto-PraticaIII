package cotuca.aplicativo.viaxar;

import android.content.Context;
import android.net.Uri;
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

public class PaisContinenteItemAdapter extends ArrayAdapter<Pais>{
    Context context;
    int layoutResourceId;
    List<Pais> dados;

    public PaisContinenteItemAdapter (@NonNull Context context, int resource, @NonNull List<Pais> dados) {
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

        ImageView imgPais = (ImageView) view.findViewById(R.id.imgPais);
        TextView tvNome = (TextView) view.findViewById(R.id.tvNome);
        TextView tvCapital = (TextView) view.findViewById(R.id.tvCapital);
        TextView tvIdioma = (TextView) view.findViewById(R.id.tvIdioma);

        Pais pais = dados.get(position);
        imgPais.setImageBitmap(pais.getImagem());
        tvNome.setText(pais.getNome());
        tvCapital.setText(pais.getCapital());
        tvIdioma.setText(pais.getIdioma());

        return view;
    }
}
