package com.bean;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.entidad.Usuario;
import com.remote.UsuarioCliente;

@Named
@SessionScoped
public class UsuarioBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String selectedNombre;
	private String selectedRol;
	
	private List<Usuario> usuarios;
	private List<Usuario> selectedUsuarios;
	private Usuario selectedUsuario;
	
	UsuarioCliente usuario_;

	public UsuarioBean() {
		usuario_ = new UsuarioCliente();
		this.usuarios = usuario_.getBean().readAll();
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
		return "altaUsuario";
	}

}
