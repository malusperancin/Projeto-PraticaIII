package cotuca.aplicativo.viaxar.ui.world;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WorldViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public WorldViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}