package cotuca.aplicativo.viaxar.ui.travelerkit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TravelerKitViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TravelerKitViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is TravelerKit fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}