package br.aplicativo.apviaxar.dbos;

public class Usuario implements Cloneable
{
	int    id;
	String email;
	String senha;
	String celular;
	String foto;

	public int getId() {
		return id;
	}

	public void setId(int id) throws Exception{
		if(id < 0)
			throw new Exception("Id invalido");
		this.id = id;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws Exception{
		if(email.equals("") || email == null)
			throw new Exception("email invalido");
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) throws Exception{
		if(senha.equals("") || senha == null)
			throw new Exception("senha inválida");
		this.senha = senha;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) throws Exception{
		if(celular.equals("") || celular == null)
			throw new Exception("celular invalido");
		this.celular = celular;
    }

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) throws Exception{
		if(foto.equals("") || foto == null)
			throw new Exception("foto invalido");
		this.foto = foto;
	}

    public Usuario(int id, String email, String senha, String celular, String foto) throws Exception{
		this.setId(id);
		this.setEmail(email);
		this.setSenha(senha);
		this.setCelular(celular);
		this.setFoto(foto);
	}

	public boolean equals(Object object) {

		if (this == object)
			return true;

		if (!(object instanceof Usuario))
			return false;

		if (!super.equals(object))
			return false;

		Usuario usu = (Usuario) object;

		return  this.getId() == usu.getId() &&
				this.getEmail().equals(usu.getEmail()) &&
				this.getSenha().equals(usu.getSenha()) &&
				this.getCelular().equals(usu.getCelular()) &&
				this.getFoto().equals(usu.getFoto());
	}

	public int hashCode()
	{
		int ret = 1;

		ret = 7 * ret + new Integer(getId()).hashCode();
		ret = 7 * ret + getEmail().hashCode();
		ret = 7 * ret + getSenha().hashCode();
		ret = 7 * ret + getCelular().hashCode();
		ret = 7 * ret + getFoto().hashCode();

		if(ret < 0)
			ret = -ret;

		return ret;
	}

	//@Override
	public String toString() {
		return "Usuario: {\n" +
				"  id= " + this.id +
				", email= '" + this.email + '\'' +
				", senha= '" + this.senha + '\'' +
				", celular= '" + this.celular + '\'' +
				", foto= " + this.foto +
				'}';
	}

	public Usuario (Usuario modelo) throws Exception
	{
		if (modelo==null)
			throw new Exception ("Modelo ausente");

		this.id = modelo.id;
		this.email = modelo.email;
		this.senha = modelo.senha;
		this.celular = modelo.celular;
		this.foto = modelo.foto;
	}

	public Object clone ()
	{
		Usuario ret=null;

		try
		{
			ret = new Usuario (this);
		}
		catch (Exception erro)
		{} // sei que this NUNCA ? null e o contrutor de copia da erro quando seu parametro ? null

		return ret;
	}
}