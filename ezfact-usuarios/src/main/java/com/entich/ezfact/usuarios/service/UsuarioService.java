package com.entich.ezfact.usuarios.service;

import java.util.Collection;

import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.usuarios.model.Usuario;

public interface UsuarioService {
	void create(Usuario usuario);

	void update(Usuario usuario);

	void changeStatus(Usuario usuario);

	Usuario validate(String login, String password);

	Collection<Usuario> findAll();

	Usuario getByUsername(String string);

	Usuario read(Long id);

	Collection<Usuario> findAll(Emisor emisor);

	void asignarEmisor(Usuario usuario);
}