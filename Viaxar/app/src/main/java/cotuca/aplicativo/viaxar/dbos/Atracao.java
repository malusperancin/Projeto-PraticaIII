package cotuca.aplicativo.viaxar.dbos;

import android.graphics.Bitmap;

public class Atracao implements Cloneable
{
	int    id;
	String nome;
	String imagem;
	String url;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) throws Exception{
		if(url.equals("") || url == null)
			throw new Exception("Url inválida");
		this.url = url;
	}

	public Bitmap getImagemBitmap() {
		return imagemBitmap;
	}

	public void setImagemBitmap(Bitmap imagemBitmap) throws Exception{
		if(imagemBitmap.equals("") || imagemBitmap == null)
			throw new Exception("Bitmap inválido");
		this.imagemBitmap = imagemBitmap;
    }

    public Atracao (int id, String nome, String url, String imagem) throws Exception
	{
		this.setId(id);
		this.setNome(nome);
		this.setUrl(url);
		this.setImagem(imagem);
		this.setImagemBitmap(imagemBitmap);
	}

	public boolean equals(Object object) {

		if (this == object)
			return true;

		if (!(object instanceof Atracao))
			return false;

		if (!super.equals(object))
			return false;

		Atracao atracao = (Atracao) object;

		return  this.getId() == atracao.getId() &&
				this.getNome().equals(atracao.getNome()) &&
				this.getUrl().equals(atracao.getUrl()) &&
				this.getImagem().equals(atracao.getImagem()) &&
				this.getImagemBitmap().equals(atracao.getImagemBitmap());
	}

	public int hashCode()
	{
		int ret = 1;

		ret = 7 * ret + new Integer(getId()).hashCode();
		ret = 7 * ret + getNome().hashCode();
		ret = 7 * ret + getUrl().hashCode();
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
				", url= '" + this.url + '\'' +
				", imagem= '" + this.imagem +
				'}';
	}

	public Atracao (Atracao modelo) throws Exception
	{
		if (modelo==null)
			throw new Exception ("Modelo ausente");

		this.id = modelo.id;
		this.nome = modelo.nome;
		this.url = modelo.url;
		this.imagem = modelo.imagem;
		this.imagemBitmap = modelo.imagemBitmap;
	}

	public Object clone ()
	{
		Atracao ret=null;
		try
		{
			ret = new Atracao (this);
		}
		catch (Exception erro)
		{} // sei que this NUNCA ? null e o contrutor de copia da erro quando seu parametro ? null

		return ret;
	}
}