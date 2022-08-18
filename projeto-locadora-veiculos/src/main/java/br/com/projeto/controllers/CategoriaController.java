package br.com.projeto.controllers;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.projeto.dao.DaoGenerico;
import br.com.projeto.model.Categoria;

@SessionScoped
@Named(value = "categoriaBean")
public class CategoriaController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	private Categoria categoria = new Categoria();
	
	@Inject
	private DaoGenerico<Categoria> daoCategoria;

	
	
	
	
	
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public DaoGenerico<Categoria> getDaoCategoria() {
		return daoCategoria;
	}

	public void setDaoCategoria(DaoGenerico<Categoria> daoCategoria) {
		this.daoCategoria = daoCategoria;
	}
	
	public String salvar() {
		
		
		 System.out.println("ta chamando...............");
		daoCategoria.salvarMerge(categoria);
		
		return "";
	}
	
	public String novo() {
		
		categoria = new Categoria();
		
		return "";
	}
	
	

}
