package br.com.projeto.jpautil;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class JpaUtil {
	
private  EntityManagerFactory minhaFactory = null;
	
	public JpaUtil() {
		
		if(minhaFactory == null) {
			minhaFactory = Persistence.createEntityManagerFactory("locadora-veiculos");
		}
		
	}
	
	
	@Produces
	@RequestScoped
	public EntityManager getEntityManager(){
		return minhaFactory.createEntityManager();
	}
	
	public Object getPrimaryKey(Object entidade) {
		return minhaFactory.getPersistenceUnitUtil().getIdentifier(entidade);
	}
	
}
