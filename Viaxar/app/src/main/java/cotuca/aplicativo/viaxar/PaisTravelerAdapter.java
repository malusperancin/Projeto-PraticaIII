package cotuca.aplicativo.viaxar;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import cotuca.aplicativo.viaxar.dbos.Pais;

public class PaisTravelerAdapter extends ArrayAdapter<Pais> {

    Context context;
    int layoutResourceId;
    List<Pais> dados;

    public PaisTravelerAdapter(@NonNull Context context, int resource, @NonNull List<Pais> dados) {
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

        TextView tvNome = (TextView) view.findViewById(R.id.tvNomeCidade);
        TextView tvDDD = (TextView) view.findViewById(R.id.tvContinente);

        Pais pais = dados.get(position);
        tvNome.setText(pais.getNome());
        tvDDD.setText(pais.getDdd());

        return view;
    }
}
