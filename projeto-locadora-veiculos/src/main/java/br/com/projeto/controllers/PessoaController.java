package br.com.projeto.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.projeto.dao.DaoGenerico;
import br.com.projeto.model.PessoaTeste;
import br.com.projeto.model.Veiculo;

@SessionScoped
@Named(value = "pessoaBean")
public class PessoaController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	private PessoaTeste pessoa = new PessoaTeste();
	
	private Veiculo veiculo = new Veiculo();
	
	private List<Veiculo> listaVeiculos = new ArrayList<Veiculo>();	
	
	public Veiculo getVeiculo() {
		return veiculo;
	}
	
	public List<Veiculo> getListaVeiculos() {
		return listaVeiculos;
	}
	
    
	
	
	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}
	
	@Inject
	private DaoGenerico<PessoaTeste> daoGenerico;
	
	@Inject
	private DaoGenerico<Veiculo> daoGenericoV;
	
	public DaoGenerico<PessoaTeste> getDaoGenerico() {
		return daoGenerico;
	}
	
	public void setDaoGenerico(DaoGenerico<PessoaTeste> daoGenerico) {
		this.daoGenerico = daoGenerico;
	}
	
	public PessoaTeste getPessoaTeste() {
		return pessoa;
	}
	
	public void setPessoaTeste(PessoaTeste pessoaTeste) {
		this.pessoa = pessoaTeste;
	}
	
	public String salvar() {
			
		pessoa = daoGenerico.salvarMerge(pessoa);
		
		return "";
	}
	
	public String salvarVeiculo() {
		
		System.out.println("ta chamando!");
		
		veiculo = daoGenericoV.salvarMerge(veiculo);
		listaVeiculos = daoGenericoV.getListEntity(Veiculo.class);
		
		return "";
	}
	
	public String novo() {
		
		veiculo = new Veiculo();
		
		return "";
	}
	
	
	

}
