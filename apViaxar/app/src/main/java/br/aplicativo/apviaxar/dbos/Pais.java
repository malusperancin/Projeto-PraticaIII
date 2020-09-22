package br.aplicativo.apviaxar.dbos;

public class Pais implements Cloneable
{
    int id, populacao;
    String nome, bandeira, moeda, idioma, clima, religiao, sigla, capital, continente, descricao;
    boolean lgbt;

    public int getId() {
        return id;
    }

    public void setId(int id) throws Exception {
        if (id < 0)
            throw new Exception("Id inválido");
        this.id = id;
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

    public String getBandeira() {
        return bandeira;
    }

    public void setBandeira(String bandeira) throws Exception{
        if (bandeira.equals("") || bandeira == null)
            throw new Exception("Bandeira invalida");
        this.bandeira = bandeira;
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

    public Pais(int id, int populacao, String nome, String bandeira, String moeda, String idioma, String clima, String religiao, String sigla, String capital, String continente, String descricao, boolean lgbt) throws Exception
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
    }

    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (!(object instanceof Pais))
            return false;

        if (!super.equals(object))
            return false;

        Pais pais = (Pais) object;

        return getId() == pais.getId() &&
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
                getDescricao().equals(pais.getDescricao());
    }

    public int hashCode() {
        int ret = 1;

        ret = 7 * ret + new Integer(getId()).hashCode();
        ret = 7 * ret + new Long(getPopulacao()).hashCode();
        ret = 7 * ret + new Boolean(isLgbt()).hashCode();
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
                ", lgbt=" + lgbt +
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