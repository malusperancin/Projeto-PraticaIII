package cotuca.aplicativo.viaxar.dbos;

public class Arte implements Cloneable
{
	int    id;
	String nome;
	String descricao;
	String imagem;
	int    idPais;

	public int getId() {
		return id;
	}

	public void setId(int id) throws Exception{
		if(id < 0)
			throw new Exception("Id inv�lido");
		this.id = id;
    }

    public int getIdPais() {
		return idPais;
	}

	public void setIdPais(int idPais) throws Exception {
		if(idPais < 0)
			throw new Exception("Id do pa�s inv�lido");
		this.idPais = idPais;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) throws Exception{
		if(nome.equals("") || nome == null)
			throw new Exception("Nome inv�lido");
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) throws Exception{
		if(descricao.equals("") || descricao == null)
			throw new Exception("Descricao inv�lida");
		this.descricao = descricao;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) throws Exception{
		if(imagem.equals("") || imagem == null)
			throw new Exception("Nome inv�lido");
		this.imagem = imagem;
    }

    public Arte(int id, String nome, String descricao, String imagem, int idPais) throws Exception {
		this.setId(id);
		this.setNome(nome);
		this.setDescricao(descricao);
		this.setImagem(imagem);
		this.setIdPais(idPais);
	}

	public boolean equals(Object object) {

		if (this == object)
			return true;

		if (!(object instanceof Arte))
			return false;

		if (!super.equals(object))
			return false;

		Arte arte = (Arte) object;

		return  this.getId() == arte.getId() &&
				this.getNome().equals(arte.getNome()) &&
				this.getDescricao().equals(arte.getDescricao()) &&
				this.getImagem().equals(arte.getImagem()) &&
				this.getIdPais() == arte.getIdPais();
	}

	public int hashCode()
	{
		int ret = 1;

		ret = 7 * ret + new Integer(getId()).hashCode();
		ret = 7 * ret + getNome().hashCode();
		ret = 7 * ret + getDescricao().hashCode();
		ret = 7 * ret + getImagem().hashCode();
		ret = 7 * ret + new Integer(getIdPais()).hashCode();

		if(ret < 0)
			ret = -ret;

		return ret;
	}

	//@Override
	public String toString() {
		return "Arte: {\n" +
				"  id= " + this.id +
				", nome= '" + this.nome + '\'' +
				", descricao= '" + this.descricao + '\'' +
				", imagem= '" + this.imagem + '\'' +
				", idPais= " + this.idPais +
				'}';
	}

	public Arte (Arte modelo) throws Exception
	{
		if (modelo==null)
			throw new Exception ("Modelo ausente");

		this.id = modelo.id;
		this.nome = modelo.nome;
		this.descricao = modelo.descricao;
		this.idPais = modelo.idPais;
		this.imagem = modelo.imagem;
	}

	public Object clone ()
	{
		Arte ret=null;

		try
		{
			ret = new Arte (this);
		}
		catch (Exception erro)
		{} // sei que this NUNCA ? null e o contrutor de copia da erro quando seu parametro ? null

		return ret;
	}
}