package cotuca.aplicativo.viaxar;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import cotuca.aplicativo.viaxar.dbos.Pais;
import cotuca.aplicativo.viaxar.svgandroid.SVG;
import cotuca.aplicativo.viaxar.svgandroid.SVGParser;

public class PaisHomeAdapter extends ArrayAdapter<Pais> {
    Context context;
    int layoutResourceId;
    List<Pais> dados;
    View view;

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

        ImageView imgPais = (ImageView) view.findViewById(R.id.imgPais);
        ImageView imgBandeira = (ImageView) view.findViewById(R.id.imgBandeira);
        TextView tvNome = (TextView) view.findViewById(R.id.tvNome);
        TextView tvContinente = (TextView) view.findViewById(R.id.tvContinente);

        Pais pais = this.dados.get(position);

        imgPais.setImageBitmap(pais.getImagem());
        imgBandeira.setImageDrawable(pais.getImagemBandeira());
        tvNome.setText(pais.getNome());
        tvContinente.setText(pais.getContinente());

        return view;
    }


}
