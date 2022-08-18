package br.com.projeto.converter;

import java.io.Serializable;

import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;


import br.com.projeto.model.Categoria;

@FacesConverter(forClass = Categoria.class, value = "categoriaConverter")
public class CategoriaConverter implements Converter, Serializable{

	private static final long serialVersionUID = 1L;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String codCategoria) {
		
		EntityManager entityManager = CDI.current().select(EntityManager.class).get();
		
		Categoria categoria = entityManager.find(Categoria.class, Long.parseLong(codCategoria));
		
		return categoria;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object categoria) {

		if(categoria == null) {
			return null;
		}else {
			return ((Categoria) categoria).getId().toString();
		}

	}

}
