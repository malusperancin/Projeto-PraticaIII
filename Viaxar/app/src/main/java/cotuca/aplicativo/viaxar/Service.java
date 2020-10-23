package cotuca.aplicativo.viaxar;

import java.util.List;

import cotuca.aplicativo.viaxar.dbos.Pais;
import cotuca.aplicativo.viaxar.dbos.Usuario;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface Service {

    @POST("usuario/post")
    Call<Usuario> incluirUsuario(@Body Usuario usuario);

    @GET("usuario/login/{email}/{senha}")
    Call<Usuario> login(@Path("email") String email,@Path("senha") String senha);

    @PUT("usuario/put/{email}")
    Call<Usuario> alterarUsuario(@Path("email") String email, @Body Usuario usuario);

    @DELETE("usuario/delete/{ra}")
    Call<Usuario> excluirUsuario(@Path("email") String email);

    @GET("api/get")
    Call<List<Pais>> selecionaTudo();
}