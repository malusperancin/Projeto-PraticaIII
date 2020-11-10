package cotuca.aplicativo.viaxar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import cotuca.aplicativo.viaxar.dbos.Pais;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class WorldFragment extends Fragment {
    List<Pais> listaPais;
    Pais selecionado;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            consultarPaises(googleMap);
            try{
                boolean sucess = googleMap.setMapStyle(MapStyleOptions.
                        loadRawResourceStyle(getContext(),R.raw.map_style));
            }
            catch (Exception e){
                //
            }

            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    String nome = (marker.getTitle());
                    consultarPaisesByNome(nome);
                }
            });
        }



    };

    public void marcar (GoogleMap googleMap)
    {
        for (int i = 0; i < listaPais.size(); i++)
        {
            LatLng pais = new LatLng(listaPais.get(i).getLat(), listaPais.get(i).getLng());
           /* int height = 100;
            int width = 100;
            BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.marker);
            Bitmap b = bitmapdraw.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
            BitmapDescriptor smallMarkerIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);*/
            MarkerOptions marker = new MarkerOptions().position(pais).title(listaPais.get(i).getNome()).snippet("Click to see more");
            //.icon(smallMarkerIcon);
            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(pais));

            googleMap.addMarker(marker);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_world, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    public void consultarPaises(GoogleMap googleMap)
    {
        Call<List<Pais>> call = new RetrofitConfig().getService().selecionarPaises();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Response<List<Pais>> response, Retrofit retrofit) {
                if(response.isSuccess()) //conectou com o node
                {
                    listaPais = response.body();
                    marcar(googleMap);
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

    public void consultarPaisesByNome(String nome)
    {
        Call<List<Pais>> call = new RetrofitConfig().getService().selecionarPaisByNome(nome);
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Response<List<Pais>> response, Retrofit retrofit) {
                if(response.isSuccess()) //conectou com o node
                {
                    Intent intent = new Intent(getContext(), PaisActivity.class);
                    Bundle params = new Bundle();
                    selecionado = response.body().get(0);
                    params.putInt ("idPais", selecionado.getId());
                    intent.putExtras(params);
                    startActivity(intent);
                }
                else
                    Toast.makeText(getActivity(), "Ocorreu um erro ao recuperar o pais selecionado", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getActivity(), "Ocorreu um erro na rede", Toast.LENGTH_LONG).show();
            }
        });
    }
}