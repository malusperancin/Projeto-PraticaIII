package cotuca.aplicativo.viaxar;

import android.content.Context;
import android.content.Intent;
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

import cotuca.aplicativo.viaxar.dbos.Atracao;

public class AtracoesAdapter extends ArrayAdapter<Atracao> {

    Context context;
    int layoutResourceId;
    List<Atracao> dados;

    public AtracoesAdapter(@NonNull Context context, int resource, @NonNull List<Atracao> dados) {
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

        ImageView imgAtracao = (ImageView) view.findViewById(R.id.imgAtracao);
        TextView tvNome = (TextView) view.findViewById(R.id.tvNome);

        Atracao atracao = dados.get(position);
        imgAtracao.setImageBitmap(atracao.getImagemBitmap());
        tvNome.setText(atracao.getNome());

        imgAtracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse(atracao.getUrl());
                Intent webView = new Intent(Intent.ACTION_VIEW, uriUrl);
                context.startActivity(webView);
            }
        });

        return view;
    }
}