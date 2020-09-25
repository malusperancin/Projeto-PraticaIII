package cotuca.aplicativo.viaxar.ui.informations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
        final TextView textView = root.findViewById(R.id.text_informations);
        informationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}