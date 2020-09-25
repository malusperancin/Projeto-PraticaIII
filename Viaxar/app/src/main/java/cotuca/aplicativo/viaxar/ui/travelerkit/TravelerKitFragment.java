package cotuca.aplicativo.viaxar.ui.travelerkit;

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

public class TravelerKitFragment extends Fragment {

    private TravelerKitViewModel travelerKitViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        travelerKitViewModel =
                ViewModelProviders.of(this).get(TravelerKitViewModel.class);
        View root = inflater.inflate(R.layout.fragment_kit, container, false);
        final TextView textView = root.findViewById(R.id.text_travelerkit);
        travelerKitViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}