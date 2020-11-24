package cotuca.aplicativo.viaxar;

import java.util.List;
import cotuca.aplicativo.viaxar.dbos.Atracao;
import cotuca.aplicativo.viaxar.dbos.Cidade;
import cotuca.aplicativo.viaxar.dbos.Hotel;
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

    @PUT("usuario/editarSenha/{senhaOld}/{senhaNew}")
    Call<Usuario> alterarSenha(@Body Usuario usuario, @Path("senhaOld") String senhaOld,@Path("senhaNew") String senhaNew);

    @PUT("usuario/put")
    Call<Usuario> alterarUsuario(@Body Usuario usuario);

    @DELETE("usuario/delete/{ra}")
    Call<Usuario> excluirUsuario(@Path("email") String email);

    @GET("paises")
    Call<List<Pais>> selecionarPaises();

    @GET("paises/{id}")
    Call<List<Pais>> selecionarPais(@Path("id") int id);

    @GET("paises/nome/{nome}")
    Call<List<Pais>> selecionarPaisByNome(@Path("nome") String nome);

    @GET("cidades/pais/{id}")
    Call<List<Cidade>> selecionarCidadesByPais(@Path("id") int id);

    @GET("paises/favoritos/{id}")
    Call<List<Pais>> selecionarFavs(@Path("id") int id);

    @POST("usuario/favoritar/{idUsuario}/{idPais}")
    Call<Pais> adicionarPaisFavoritos(@Path("idUsuario") int idUsuario, @Path("idPais") int idPais);

    @GET("paises/continente/{continente}")
    Call<List<Pais>> selecionarPaisesContinente(@Path("continente") String continente);

    @DELETE("usuario/desfavoritar/{idUsuario}/{idPais}")
    Call<Usuario> excluirFavorito(@Path("idUsuario") int idUsuario, @Path("idPais") int idPais);

    @GET("paises/favoritos/checar/{idUsuario}/{idPais}")
    Call<Boolean> checarFavorito(@Path("idUsuario") int idUsuario, @Path("idPais") int idPais);

    @GET("cidade/restaurantes/{idCidade}")
    Call<List<Atracao>> selecionarRestaurantes(@Path("idCidade") int idCidade);

    @GET("cidade/pontos/{idCidade}")
    Call<List<Atracao>> selecionarPontos(@Path("idCidade") int idCidade);

    @GET("cidade/hoteis/{idCidade}")
    Call<List<Hotel>> selecionarHoteis(@Path("idCidade") int idCidade);
}