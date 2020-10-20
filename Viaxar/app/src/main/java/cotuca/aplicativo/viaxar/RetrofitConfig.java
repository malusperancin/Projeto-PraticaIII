package cotuca.aplicativo.viaxar;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class RetrofitConfig {

    private final Retrofit retrofit;

    public RetrofitConfig(){

        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.184.1:3000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Service getService(){
        return this.retrofit.create(Service.class);
    }
}