package ar.edu.ubp.das.beans;

import ar.edu.ubp.das.db.Bean;

public class UsuarioBean implements Bean{
	private String cuil;
	private String nombre;
	private String apellido;
	private String email;	
	private String clave;
	private String emailVerificado;
	
	public String getCuil() {
		return cuil;
	}
	public void setCuil(String cuil) {
		this.cuil = cuil;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public String getEmailVerificado() {
		return emailVerificado;
	}
	public void setEmailVerificado(String emailVerificado) {
		this.emailVerificado = emailVerificado;
	}
	
	
}
