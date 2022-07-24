package br.com.projeto.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;


import br.com.projeto.jpautil.JpaUtil;

public class DaoGenerico<E> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	
	@Inject
	private JpaUtil jpaUtil;
	
	public void salvar(E entidade) {
		
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		entityManager.persist(entidade);
		
		entityTransaction.commit();
		
	}
	
	public E salvarMerge(E entidade) {
		
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		E retorno = entityManager.merge(entidade);
		
		entityTransaction.commit();
		
		
		return retorno;
	}
	
    public void deletar(E entidade) {
		
	
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		entityManager.remove(entidade);
		
		entityTransaction.commit();
		
	}
    
    public void deletarPorId(E entidade) {
		
  	
  		EntityTransaction entityTransaction = entityManager.getTransaction();
  		entityTransaction.begin();
  		
  		Object id = jpaUtil.getPrimaryKey(entidade);
  		entityManager.createNativeQuery("delete from "+entidade.getClass().getSimpleName().toLowerCase()
  				+" where id = "+id).executeUpdate();
  		
  		entityTransaction.commit();
  	
  	}
    
    	public List<E> getListEntity(Class<E> entidade) {
		
  	
  		EntityTransaction entityTransaction = entityManager.getTransaction();
  		entityTransaction.begin();
  		
  		List<E> minhaLista = entityManager.createQuery("from "+entidade.getName()).getResultList();
  		
  		entityTransaction.commit();

  		
  		return minhaLista;
  	}
    
    public E consultar(Class<E> entidade, String codigo) {

    	EntityManager entityManager = jpaUtil.getEntityManager();
  		EntityTransaction entityTransaction = entityManager.getTransaction();
  		entityTransaction.begin();
  		
  		E objeto = entityManager.find(entidade, Long.parseLong(codigo));
  		entityTransaction.commit();
  		return objeto;
    }
    

}
