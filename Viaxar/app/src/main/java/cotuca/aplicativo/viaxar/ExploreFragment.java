package cotuca.aplicativo.viaxar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class ExploreFragment extends Fragment {

    ImageButton btnAmerica, btnAfrica, btnAsia, btnOceania, btnEuropa;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_explore, container, false);

        btnAmerica = (ImageButton) root.findViewById(R.id.btn_america);
        btnAfrica = (ImageButton) root.findViewById(R.id.btn_africa);
        btnAsia = (ImageButton) root.findViewById(R.id.btn_asia);
        btnOceania = (ImageButton) root.findViewById(R.id.btn_oceania);
        btnEuropa = (ImageButton) root.findViewById(R.id.btn_europa);

        btnAfrica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PaisesActivity.class);
                Bundle params = new Bundle();

                params.putString("continente", "Africa");

                intent.putExtras(params);

                startActivity(intent);
            }
        });

        btnAmerica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PaisesActivity.class);
                Bundle params = new Bundle();

                params.putString("continente", "America");

                intent.putExtras(params);

                startActivity(intent);
            }
        });

        btnAsia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PaisesActivity.class);
                Bundle params = new Bundle();

                params.putString("continente", "Asia");

                intent.putExtras(params);

                startActivity(intent);
            }
        });

        btnEuropa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PaisesActivity.class);
                Bundle params = new Bundle();

                params.putString("continente", "Europe");

                intent.putExtras(params);

                startActivity(intent);
            }
        });
        btnOceania.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PaisesActivity.class);
                Bundle params = new Bundle();

                params.putString("continente", "Oceania");

                intent.putExtras(params);

                startActivity(intent);
            }
        });

        return root;
    }
}
