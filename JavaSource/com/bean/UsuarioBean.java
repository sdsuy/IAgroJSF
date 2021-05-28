package com.bean;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.entidad.Rol;
import com.entidad.Roles;
import com.entidad.Usuario;
import com.remote.UsuarioCliente;

@Named
@SessionScoped
public class UsuarioBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private String apellido;
	private String documento;
	private String clave;
	private String email;
	private String nickname;
	private String instituto;
	private String profesion;
	private String rol;
	
	private String selectedNombre;
	private String selectedRol;
	
	private List<Usuario> usuarios;
	private List<Usuario> selectedUsuarios;
	private Usuario selectedUsuario;
	private Usuario createUsuario;
	
	UsuarioCliente usuario_;

	public UsuarioBean() {
		usuario_ = new UsuarioCliente();
		this.usuarios = usuario_.getBean().readAll();
		this.selectedUsuarios = this.usuarios;
		System.out.println("Construyo el Bean de Usuarios");
	}
	
	public String getSelectedNombre() {
		return selectedNombre;
	}

	public void setSelectedNombre(String selectedNombre) {
		this.selectedNombre = selectedNombre;
	}

	public String getSelectedRol() {
		return selectedRol;
	}

	public void setSelectedRol(String selectedRol) {
		this.selectedRol = selectedRol;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Usuario> getSelectedUsuarios() {
		return selectedUsuarios;
	}

	public void setSelectedUsuarios(List<Usuario> selectedUsuarios) {
		this.selectedUsuarios = selectedUsuarios;
	}

	public Usuario getSelectedUsuario() {
		return selectedUsuario;
	}

	public void setSelectedUsuario(Usuario selectedUsuario) {
		System.out.println("Se ha selecionado el usuario con id: " + selectedUsuario.getId());
		this.selectedUsuario = selectedUsuario;
	}
	
	/**
	 * 
	 * Para dar alta
	 */

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

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getInstituto() {
		return instituto;
	}

	public void setInstituto(String instituto) {
		this.instituto = instituto;
	}

	public String getProfesion() {
		return profesion;
	}

	public void setProfesion(String profesion) {
		this.profesion = profesion;
	}
	
	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
	
	// FIN

	public String filterUsuarios() {
		this.usuarios = usuario_.getBean().readAll();
		System.out.println("Cantidad total de usuarios: " + this.usuarios.size());
		
		System.out.println("Filtrando por nombre: " + selectedNombre + " y Rol: " + selectedRol);
		
		this.selectedUsuario = this.usuarios.get(0);
		System.out.println("El primer usuario recuperado de la lista total de usuarios es: " + this.selectedUsuario.getId() + " - " + this.selectedUsuario.getNickname());
		
		List<Usuario> selectedUsuarios = this.usuarios.stream()
				.filter(u -> u.getNombre().toString().toUpperCase().contains(selectedNombre.toUpperCase()))
				.collect(Collectors.toList());
		
		if(selectedRol != null) selectedUsuarios = this.usuarios.stream()
				.filter(u -> u.getRol().getRol().name().equals(selectedRol))
				.collect(Collectors.toList());
		
		this.selectedUsuarios = selectedUsuarios;
		System.out.println("Cantidad filtrada de usuarios: " + this.selectedUsuarios.size());
		
		return "";
	}
	
	

	public String modificar() {
		return "modificarUsuario";
	}
	
	public String modificarUsuario() {
		usuario_.getBean().update(selectedUsuario);
		this.usuarios = usuario_.getBean().readAll();
		this.selectedUsuarios = this.usuarios;
		return "usuarios";
	}
	
	public String crearUsuario() {
		createUsuario = new Usuario();
		createUsuario.setNombre(nombre);
		createUsuario.setApellido(apellido);
		createUsuario.setDocumento(documento);
		createUsuario.setClave(clave);
		createUsuario.setEmail(email);
		createUsuario.setNickname(nickname);
		createUsuario.setInstituto(instituto);
		createUsuario.setProfesion(profesion);
		
		switch(rol.toUpperCase()) {
		case "COMUN":
			createUsuario.setRol(Roles.COMUN);
			break;
		case "EXPERTO":
			createUsuario.setRol(Roles.EXPERTO);
			break;
		case "ADMINISTRADOR":
			createUsuario.setRol(Roles.ADMINISTRADOR);
			break;
		}
		
		usuario_.getBean().create(createUsuario);
		this.usuarios = usuario_.getBean().readAll();
		this.selectedUsuarios = this.usuarios;
		return "usuarios";
	}
		
}
