package cotuca.aplicativo.viaxar.dbos;

public class Favorito implements Cloneable
{
	int    id;
	int idUsuario;
	int    idPais;

	public int getId() {
		return id;
	}

	public void setId(int id) throws Exception{
		if(id < 0)
			throw new Exception("Id invalido");
		this.id = id;
    }

    public int getIdPais() {
		return idPais;
	}

	public void setIdPais(int idPais) throws Exception {
		if(idPais < 0)
			throw new Exception("Id do pais invalido");
		this.idPais = idPais;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) throws Exception{
		if(idUsuario < 0)
			throw new Exception("Id do usuario invalido");
		this.idUsuario = idUsuario;
	}


    public Favorito(int id, int idPais, int idUsuario) throws Exception {
		this.setId(id);
		this.setIdPais(idPais);
		this.setIdUsuario(idUsuario);
	}

	public boolean equals(Object object) {

		if (this == object)
			return true;

		if (!(object instanceof Favorito))
			return false;

		if (!super.equals(object))
			return false;

		Favorito fav = (Favorito) object;

		return  this.getId() == fav.getId() &&
				this.getIdUsuario() == fav.getIdUsuario() &&
				this.getIdPais() == fav.getIdPais();
	}

	public int hashCode()
	{
		int ret = 1;

		ret = 7 * ret + new Integer(getId()).hashCode();
		ret = 7 * ret + new Integer(getIdPais()).hashCode();
		ret = 7 * ret + new Integer(getIdUsuario()).hashCode();

		if(ret < 0)
			ret = -ret;

		return ret;
	}

	//@Override
	public String toString() {
		return "Favorito: {\n" +
				"  id= " + this.id +
				", idUsuario= '" + this.idUsuario + '\'' +
				", idPais= " + this.idPais +
				'}';
	}

	public Favorito (Favorito modelo) throws Exception
	{
		if (modelo==null)
			throw new Exception ("Modelo ausente");

		this.id = modelo.id;
		this.idPais = modelo.idPais;
		this.idUsuario = modelo.idUsuario;
	}

	public Object clone ()
	{
		Favorito ret=null;

		try
		{
			ret = new Favorito (this);
		}
		catch (Exception erro)
		{} // sei que this NUNCA ? null e o contrutor de copia da erro quando seu parametro ? null

		return ret;
	}
}