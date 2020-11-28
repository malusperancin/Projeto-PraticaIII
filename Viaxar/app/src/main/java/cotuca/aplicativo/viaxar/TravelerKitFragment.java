package cotuca.aplicativo.viaxar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class TravelerKitFragment extends Fragment {

    Spinner spInicialMedida, spFinalMedida, spInicialCambio, spFinalCambio, spInicialRoupa, spFinalRoupa, spCategoria;
    Button btnCambio, btnMedida, btnRoupa, btnDDD;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_kit, container, false);

        spInicialMedida = (Spinner) root.findViewById(R.id.spInicialMedida);
        spFinalMedida = (Spinner) root.findViewById(R.id.spFinalMedida);
        spInicialCambio = (Spinner) root.findViewById(R.id.spInicialCambio);
        spFinalCambio = (Spinner) root.findViewById(R.id.spFinalCambio);
        spInicialRoupa = (Spinner) root.findViewById(R.id.spInicialRoupa);
        spFinalRoupa = (Spinner) root.findViewById(R.id.spFinalRoupa);
        spCategoria = (Spinner) root.findViewById(R.id.spCategoria);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.coins, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        btnCambio = (Button)root.findViewById(R.id.btnCambio);
        btnMedida = (Button)root.findViewById(R.id.btnMedida);
        btnRoupa= (Button)root.findViewById(R.id.btnRoupa);
        btnDDD = (Button)root.findViewById(R.id.btnDDD);

        return root;
    }
}