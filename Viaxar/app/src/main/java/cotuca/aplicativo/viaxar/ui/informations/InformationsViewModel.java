package cotuca.aplicativo.viaxar.ui.informations;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InformationsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public InformationsViewModel() {
        mText = new MutableLiveData<>();
        // mText.setValue("This is informations fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}