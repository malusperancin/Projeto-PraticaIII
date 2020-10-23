package cotuca.aplicativo.viaxar.dbos;

public class Cidade implements Cloneable
{
    int id, codAPI, idPais;
    String nome, descricao, foto,localizacao;

    public int getId() {
        return id;
    }

    public void setId(int id) throws Exception{
        if(id < 0)
            throw new Exception("Id inválido");
        this.id = id;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) throws Exception{
        if(localizacao.equals("") || localizacao == null)
            throw new Exception("localização inválida");
        this.localizacao = localizacao;
    }

    public int getCodAPI() {
        return codAPI;
    }

    public void setCodAPI(int codAPI) throws Exception {
        if(codAPI < 0)
            throw new Exception("Código da API inválido");
        this.codAPI = codAPI;
    }

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) throws Exception {
        if(idPais < 0)
            throw new Exception("Id do país inválido");
        this.idPais = idPais;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws Exception{
        if(nome.equals("") || nome == null)
            throw new Exception("Nome inválido");
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) throws Exception{
        if(descricao.equals("") || descricao == null)
            throw new Exception("Descricao inválida");
        this.descricao = descricao;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) throws Exception{
        if(foto.equals("") || foto == null)
            throw new Exception("Nome inválido");
        this.foto = foto;
    }

    public Cidade(int id, int codAPI, int idPais, String nome, String descricao, String foto) throws Exception
    {
        setId(id);
        setCodAPI(codAPI);
        setIdPais(idPais);
        setNome(nome);
        setDescricao(descricao);
        setFoto(foto);
    }

    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (!(object instanceof Cidade))
            return false;

        if (!super.equals(object))
            return false;

        Cidade pais = (Cidade) object;

        return getId() == pais.getId() &&
                getIdPais() == pais.getIdPais() &&
                getCodAPI() == pais.getCodAPI() &&
                getNome().equals(pais.getNome()) &&
                getDescricao().equals(pais.getDescricao()) &&
                java.util.Objects.equals(getFoto(), pais.getFoto());
    }

    public int hashCode() {
        int ret = 1;

        ret = 7 * ret + new Integer(getId()).hashCode();
        ret = 7 * ret + new Integer(getIdPais()).hashCode();
        ret = 7 * ret + new Integer(getCodAPI()).hashCode();
        ret = 7 * ret + getNome().hashCode();
        ret = 7 * ret + getDescricao().hashCode();
        ret = 7 * ret + getFoto().hashCode();

        if(ret < 0)
            ret = -ret;

        return ret;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Cidade: {\n" +
                "  id=" + id +
                ", idPais=" + idPais +
                ", codAPI=" + codAPI +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", foto='" + foto + '\'' +
                '}';
    }

    public Cidade (Cidade modelo) throws Exception
    {
        if (modelo==null)
            throw new Exception ("Modelo ausente");

        this.id = modelo.id;
        this.nome = modelo.nome;
        this.codAPI = modelo.codAPI;
        this.descricao = modelo.descricao;
        this.foto = modelo.foto;
        this.idPais = modelo.idPais;
    }

    public Object clone ()
    {
        Cidade ret=null;

        try
        {
            ret = new Cidade (this);
        }
        catch (Exception erro)
        {} // sei que this NUNCA ? null e o contrutor de copia da erro quando seu parametro ? null

        return ret;
    }
}
