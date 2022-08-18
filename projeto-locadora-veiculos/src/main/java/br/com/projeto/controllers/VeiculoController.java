package br.com.projeto.controllers;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

import br.com.projeto.dao.DaoGenerico;
import br.com.projeto.model.Categoria;
import br.com.projeto.model.Veiculo;

@SessionScoped
@Named(value = "veiculoBean")
public class VeiculoController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Veiculo veiculo = new Veiculo();
	
	private List<Veiculo> veiculos = new ArrayList<Veiculo>();
	
	private List<Categoria> categorias = new ArrayList<Categoria>();
	
	private List<SelectItem> listaVeiculos;
	
	private Part arquivoFoto;
	
	@Inject
	private DaoGenerico<Veiculo> daoGenerico;
	
	@Inject
	private DaoGenerico<Categoria> daoGenerico2;

	
	
	
	public Part getArquivoFoto() {
		return arquivoFoto;
	}

	public void setArquivoFoto(Part arquivoFoto) {
		this.arquivoFoto = arquivoFoto;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public DaoGenerico<Veiculo> getDaoGenerico() {
		return daoGenerico;
	}

	public void setDaoGenerico(DaoGenerico<Veiculo> daoGenerico) {
		this.daoGenerico = daoGenerico;
	}

   
    public DaoGenerico<Categoria> getDaoGenerico2() {
		return daoGenerico2;
	}

	public void setDaoGenerico2(DaoGenerico<Categoria> daoGenerico2) {
		this.daoGenerico2 = daoGenerico2;
	}

    public List<Veiculo> getVeiculos() {
		return veiculos;
	}


	public void setVeiculos(List<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}

	
    public List<Categoria> getCategorias() {
		return categorias;
	}
    
    public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}
    
    public List<SelectItem> getListaVeiculos() {
		return listaVeiculos;
	}

	public void setListaVeiculos(List<SelectItem> listaVeiculos) {
		this.listaVeiculos = listaVeiculos;
	}

	@PostConstruct
    public void chamaCarregaListas() {
		carregaListas();
		
    }
	
    public String salvar() throws IOException {
		
    	if(arquivoFoto != null ) {
			byte[] imagemByte = getByte(arquivoFoto.getInputStream());
			

			/* transforma em um bufferimage, é um formato manipulavel */
			BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imagemByte));
			if (bufferedImage != null) {
				/* pega o tipo da imagem, formato */
				int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
				veiculo.setFotoIconBase64Original(imagemByte);

				/* criar miniatura */
				int largura = 200;
				int altura = 200;

				BufferedImage tamanhoMiniatura = new BufferedImage(largura, altura, type);
				Graphics2D miniatura = tamanhoMiniatura.createGraphics();
				miniatura.drawImage(bufferedImage, 0, 0, largura, altura, null);
				miniatura.dispose();

				/* escrever a miniatura */
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				String extensao = arquivoFoto.getContentType().split("\\/")[1];/* pegar a extensao da imagem */
				ImageIO.write(tamanhoMiniatura, extensao, baos);

				String miniImagem = "data:" + arquivoFoto.getContentType() + ";base64,"
						+ DatatypeConverter.printBase64Binary(baos.toByteArray());
				/* gravando imagem */
				veiculo.setFotoIconBase64(miniImagem);
				veiculo.setExtensao(extensao);
			}
}
		
		/*Upload de imagem, processamento*/
		
    	
    	
    	
    	
		veiculo = daoGenerico.salvarMerge(veiculo);
		veiculos = daoGenerico.getListEntity(Veiculo.class);
		
    	
    	
		return "";
	}
    
    public String novo() {
		
		veiculo = new Veiculo();
		this.carregaListas();
		
		return "";
	}
    
    public String deletar() {
		
		daoGenerico.deletarPorId(veiculo);
		this.novo();
		this.carregaListas();
		
		return "";
	}

    public String carregaListas() {
    	
    	System.out.println("ta chamando o carrega listas");
    	
    	veiculos = daoGenerico.getListEntity(Veiculo.class);
    	categorias = daoGenerico2.getListEntity(Categoria.class);
    	
    	List<SelectItem> selectItems = new ArrayList<SelectItem>();
    	
    	for (Categoria categoria : categorias) {
			selectItems.add(new SelectItem(categoria, categoria.getNomeCategoria()));
		}
    	
    	setListaVeiculos(selectItems);
    	
    	return "";
    }
    
	/*Método que converte um inputStream em um array de bytes*/
	private byte[] getByte(InputStream is) throws IOException{
		
		int len;
		int size = 1024;
		byte[] buf = null;
		
		if(is instanceof ByteArrayInputStream) {
			size = is.available();
			buf = new byte[size];
			len = is.read(buf, 0, size);
		}else {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			buf = new byte[size];
			
			while((len = is.read(buf, 0, size)) != -1) {
				bos.write(buf, 0, len);
			}
			
			buf = bos.toByteArray(); 
		}
		
		return buf;
	}
    
    
    
}
