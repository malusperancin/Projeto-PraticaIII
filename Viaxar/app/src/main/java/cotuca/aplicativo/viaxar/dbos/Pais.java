package cotuca.aplicativo.viaxar.dbos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit.http.Url;

public class Pais implements Cloneable
{
    int id, populacao;
    float lat, lng;
    String nome, bandeira, moeda, idioma, clima, religiao, sigla, capital, continente, descricao, foto, ddd;
    Bitmap imagem, imagemBandeira;
    boolean lgbt;

    public int getId() {
        return id;
    }

    public void setId(int id) throws Exception {
        if (id < 0)
            throw new Exception("Id inválido");
        this.id = id;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) throws Exception {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) throws Exception {
        this.lng = lng;
    }

    public int getPopulacao() {
        return populacao;
    }

    public void setPopulacao(int populacao) throws Exception{
        if (populacao < 0)
            throw new Exception("Populacao inválida");
        this.populacao = populacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws Exception {
        if (nome.equals("") || nome == null)
            throw new Exception("Nome invalido");
        this.nome = nome;
    }

    public String getMoeda() {
        return moeda;
    }

    public void setMoeda(String moeda) throws Exception{
        if (moeda.equals("") || moeda == null)
            throw new Exception("Moeda invalida");
        this.moeda = moeda;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) throws Exception{
        if (idioma.equals("") || idioma == null)
            throw new Exception("Idioma invalido");
        this.idioma = idioma;
    }

    public String getClima() {
        return clima;
    }

    public void setClima(String clima) throws Exception {
        if (clima.equals("") || clima == null)
            throw new Exception("Clima invalido");
        this.clima = clima;
    }

    public String getReligiao() {
        return religiao;
    }

    public void setReligiao(String religiao) throws Exception {
        if (religiao.equals("") || religiao == null)
            throw new Exception("Religião invalida");
        this.religiao = religiao;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) throws Exception {
        if (sigla.equals("") || sigla == null)
            throw new Exception("Sigla invalida");
        this.sigla = sigla;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) throws Exception {
        if (capital.equals("") || capital == null)
            throw new Exception("Capital invalida");
        this.capital = capital;
    }

    public String getContinente() {
        return continente;
    }

    public void setContinente(String continente) throws Exception {
        if (continente.equals("") || continente == null)
            throw new Exception("Continente invalido");
        this.continente = continente;
    }

    public String getDdd() {
    return ddd;
}

    public void setDdd(String ddd) throws Exception {
        if (ddd.equals("") || ddd == null)
            throw new Exception("DDD invalido");
        this.ddd = ddd;
    }

    public String getBandeira() {
        return bandeira;
    }

    public void setBandeira(String bandeira) throws Exception{
        if (bandeira.equals("") || bandeira == null)
            throw new Exception("Bandeira invalida");
        this.bandeira = bandeira;
        //this.setImagemBandeira(bandeira);
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String ft) throws Exception {
        if (ft.equals("") || ft == null)
            throw new Exception("Nome invalido");
        this.foto =ft;
        //this.setImagem(ft);
    }

    public Bitmap getImagem() {
        return imagem;
    }

    public void setImagem(Bitmap img) throws Exception {
        if (img.equals("") || img == null)
            throw new Exception("URL inválida");
        this.imagem = img;

    }

    public Bitmap getImagemBandeira() {
        return imagem;
    }

    public void setImagemBandeira(Bitmap img) throws Exception {
        if (img.equals("") || img == null)
            throw new Exception("URL inválida");
        //this.imagemBandeira = getBitmapFromURL(url);
        this.imagemBandeira = img;
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isLgbt() {
        return lgbt;
    }

    public void setLgbt(boolean lgbt) {
        this.lgbt = lgbt;
    }

    public Pais(int id, int populacao, String nome, String bandeira, String moeda, String idioma, String clima, String religiao,
                String sigla, String capital, String continente, String descricao, boolean lgbt, String foto, String ddd, float lat, float lng) throws Exception
    {
        setId(id);
        setPopulacao(populacao);
        setNome(nome);
        setBandeira(bandeira);
        setMoeda(moeda);
        setIdioma(idioma);
        setClima(clima);
        setReligiao(religiao);
        setSigla(sigla);
        setCapital(capital);
        setContinente(continente);
        setDescricao(descricao);
        setLgbt(lgbt);
        setFoto(foto);
        setDdd(ddd);
        setLat(lat);
        setLng(lng);
    }

    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (!(object instanceof Pais))
            return false;

        if (!super.equals(object))
            return false;

        Pais pais = (Pais) object;

        return  getId() == pais.getId() &&
                getPopulacao() == pais.getPopulacao() &&
                isLgbt() == pais.isLgbt() &&
                getNome().equals(pais.getNome()) &&
                getBandeira().equals(pais.getBandeira()) &&
                getMoeda().equals(pais.getMoeda()) &&
                getIdioma().equals(pais.getIdioma()) &&
                getClima().equals(pais.getClima()) &&
                getReligiao().equals(pais.getReligiao()) &&
                getSigla().equals(pais.getSigla()) &&
                getCapital().equals(pais.getCapital()) &&
                getContinente().equals(pais.getContinente()) &&
                getDescricao().equals(pais.getDescricao()) &&
                getDdd().equals(pais.getDdd()) &&
                getFoto().equals(pais.getFoto()) &&
                getLat() == pais.getLat() &&
                getLng() == pais.getLng();
    }

    public int hashCode() {
        int ret = 1;

        ret = 7 * ret + new Integer(getId()).hashCode();
        ret = 7 * ret + new Long(getPopulacao()).hashCode();
        ret = 7 * ret + new Boolean(isLgbt()).hashCode();
        ret = 7 * ret + new Float(getLat()).hashCode();
        ret = 7 * ret + new Float(getLng()).hashCode();
        ret = 7 * ret + getNome().hashCode();
        ret = 7 * ret + getBandeira().hashCode();
        ret = 7 * ret + getMoeda().hashCode();
        ret = 7 * ret + getIdioma().hashCode();
        ret = 7 * ret + getClima().hashCode();
        ret = 7 * ret + getReligiao().hashCode();
        ret = 7 * ret + getSigla().hashCode();
        ret = 7 * ret + getCapital().hashCode();
        ret = 7 * ret + getContinente().hashCode();
        ret = 7 * ret + getDescricao().hashCode();

        if(ret < 0)
            ret = -ret;

        return ret;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Pais{" +
                "id=" + id +
                ", populacao=" + populacao +
                ", nome='" + nome + '\'' +
                ", bandeira='" + bandeira + '\'' +
                ", moeda='" + moeda + '\'' +
                ", idioma='" + idioma + '\'' +
                ", clima='" + clima + '\'' +
                ", religiao='" + religiao + '\'' +
                ", sigla='" + sigla + '\'' +
                ", capital='" + capital + '\'' +
                ", continente='" + continente + '\'' +
                ", descricao='" + descricao + '\'' +
                ", foto=" + foto + '\'' +
                ", ddd=" + ddd + '\'' +
                ", lat=" + lat + '\'' +
                ", lng=" + lng +
                '}';
    }

    public Pais (Pais modelo) throws Exception
    {
        if (modelo==null)
            throw new Exception ("Modelo ausente");

        this.id = modelo.id;
        this.populacao = modelo.populacao;
        this.bandeira = modelo.bandeira;
        this.moeda = modelo.moeda;
        this.idioma = modelo.idioma;
        this.clima = modelo.clima;
        this.religiao = modelo.religiao;
        this.sigla = modelo.sigla;
        this.capital = modelo.capital;
        this.continente = modelo.continente;
        this.descricao = modelo.descricao;
        this.lgbt = modelo.lgbt;
        this.foto = modelo.foto;
        this.lat = modelo.lat;
        this.lng = modelo.lng;
        this.ddd = modelo.ddd;
    }

    public Object clone ()
    {
        Pais ret=null;

        try
        {
            ret = new Pais (this);
        }
        catch (Exception erro)
        {} // sei que this NUNCA ? null e o contrutor de copia da erro quando seu parametro ? null

        return ret;
    }
}