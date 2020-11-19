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
import cotuca.aplicativo.viaxar.dbos.Hotel;

public class HotelAdapter extends ArrayAdapter<Hotel> {

    Context context;
    int layoutResourceId;
    List<Hotel> dados;

    public HotelAdapter(@NonNull Context context, int resource, @NonNull List<Hotel> dados) {
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

        ImageView imgHotel = (ImageView) view.findViewById(R.id.imgHotel);
        TextView tvNome = (TextView) view.findViewById(R.id.tvNome);
        TextView tvPreco = (TextView) view.findViewById(R.id.tvPreco);
        TextView tvAvaliacao = (TextView) view.findViewById(R.id.tvAvaliacao);

        Hotel hotel = dados.get(position);
        imgHotel.setImageBitmap(hotel.getImagemBitmap());
        tvNome.setText(hotel.getNome());
        tvPreco.setText(hotel.getPreco());
        tvAvaliacao.append(" "+hotel.getAvaliacao());

        return view;
    }
}