package cotuca.aplicativo.viaxar.ui.informations;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import cotuca.aplicativo.viaxar.R;

public class InformationsFragment extends Fragment {

    private InformationsViewModel informationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        informationsViewModel =
                ViewModelProviders.of(this).get(InformationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_informations, container, false);

        final Button btnAlterarSenha = (Button) root.findViewById(R.id.btnAlterarSenha);
        final EditText edtPass = (EditText) root.findViewById(R.id.edtPass);
        final EditText edtPassConfirm = (EditText) root.findViewById(R.id.edtPassConfirm);
        final Button btnSalvarSenha = (Button) root.findViewById(R.id.btnSalvarSenha);
        final LinearLayout layout = (LinearLayout) root.findViewById(R.id.lt_trocarSenha);
        btnAlterarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtPass.getVisibility() == EditText.GONE) {
                    edtPass.setVisibility(EditText.VISIBLE);
                    edtPassConfirm.setVisibility(EditText.VISIBLE);
                    btnSalvarSenha.setVisibility(EditText.VISIBLE);
                } else {
                    edtPass.setVisibility(EditText.GONE);
                    edtPassConfirm.setVisibility(EditText.GONE);
                    btnSalvarSenha.setVisibility(EditText.GONE);
                }
            }
        });
            return root;
    }
}