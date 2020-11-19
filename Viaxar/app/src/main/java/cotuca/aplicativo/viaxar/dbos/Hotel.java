package cotuca.aplicativo.viaxar.dbos;

import android.graphics.Bitmap;

public class Hotel implements Cloneable
{
    int    id;
    String nome;
    String imagem;
    String avaliacao;
    String preco;
    Bitmap imagemBitmap;

    public int getId() {
        return id;
    }

    public void setId(int id) throws Exception{
        if(id < 0)
            throw new Exception("Id inválido");
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws Exception{
        if(nome.equals("") || nome == null)
            throw new Exception("Nome inválido");
        this.nome = nome;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) throws Exception{
        if(imagem.equals("") || imagem == null)
            throw new Exception("Imagem inválida");
        this.imagem = imagem;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) throws Exception{
        if(preco.equals("") || preco == null)
            throw new Exception("Preço inválido");
        this.preco = preco;
    }

    public String getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(String avaliacao) throws Exception{
        if(avaliacao.equals("") || avaliacao == null)
            throw new Exception("Avaliacao inválida");
        this.avaliacao = preco;
    }

    public Bitmap getImagemBitmap() {
        return imagemBitmap;
    }

    public void setImagemBitmap(Bitmap imagemBitmap) throws Exception{
        if(imagemBitmap.equals("") || imagemBitmap == null)
            throw new Exception("Bitmap inválido");
        this.imagemBitmap = imagemBitmap;
    }

    public Hotel (int id, String nome, String preco, String avaliacao, String imagem) throws Exception
    {
        this.setId(id);
        this.setNome(nome);
        this.setPreco(preco);
        this.setAvaliacao(avaliacao);
        this.setImagem(imagem);
        this.setImagemBitmap(imagemBitmap);
    }

    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (!(object instanceof Hotel))
            return false;

        if (!super.equals(object))
            return false;

        Hotel hotel = (Hotel) object;

        return  this.getId() == hotel.getId() &&
                this.getNome().equals(hotel.getNome()) &&
                this.getPreco().equals(hotel.getPreco()) &&
                this.getAvaliacao().equals(hotel.getAvaliacao()) &&
                this.getImagem().equals(hotel.getImagem()) &&
                this.getImagemBitmap().equals(hotel.getImagemBitmap());
    }

    public int hashCode()
    {
        int ret = 1;

        ret = 7 * ret + new Integer(getId()).hashCode();
        ret = 7 * ret + getNome().hashCode();
        ret = 7 * ret + getPreco().hashCode();
        ret = 7 * ret + getAvaliacao().hashCode();
        ret = 7 * ret + getImagem().hashCode();
        ret = 7 * ret + getImagemBitmap().hashCode();

        if(ret < 0)
            ret = -ret;

        return ret;
    }

    //@Override
    public String toString() {
        return "Atração: {\n" +
                "  id= " + this.id +
                ", nome= '" + this.nome + '\'' +
                ", preco= '" + this.preco + '\'' +
                ", avaliacao= '" + this.avaliacao + '\'' +
                ", imagem= '" + this.imagem +
                '}';
    }

    public Hotel (Hotel modelo) throws Exception
    {
        if (modelo==null)
            throw new Exception ("Modelo ausente");

        this.id = modelo.id;
        this.nome = modelo.nome;
        this.avaliacao = modelo.avaliacao;
        this.preco = modelo.preco;
        this.imagem = modelo.imagem;
        this.imagemBitmap = modelo.imagemBitmap;
    }

    public Object clone ()
    {
        Hotel ret=null;
        try
        {
            ret = new Hotel (this);
        }
        catch (Exception erro)
        {} // sei que this NUNCA ? null e o contrutor de copia da erro quando seu parametro ? null

        return ret;
    }
}