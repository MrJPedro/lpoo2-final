package enums;


public enum ModeloVan {
	
	//VOLKS
	KOMBI(0, Marca.WOLKSWAGEN),
	TRANSPORTER(1, Marca.WOLKSWAGEN),
	CARAVELLE(2, Marca.WOLKSWAGEN),
	
	//FIAT
	FIORINO(3, Marca.FIAT),
	DOBLO(4, Marca.FIAT),
	SCUDO(5, Marca.FIAT),
	
	//MERCEDES
	SPRINTER(6, Marca.MERCEDES),
	VITO(7, Marca.MERCEDES),
	CLASSE_V(8, Marca.MERCEDES);
		
	public final int ID_MODELO_VAN;
	public final Marca marca;
	
	ModeloVan(int idModeloVan, Marca marca){
		this.ID_MODELO_VAN = idModeloVan;
		this.marca = marca;
	}
}

/*
public class ModeloVan {

    private int idModeloVan;
    private String nomeModeloVan;
    private Marca marcaModeloVan;

    public ModeloVan(int id, String nome, Marca marca){
        this.idModeloVan = id;
        this.nomeModeloVan = nome;
        this.marcaModeloVan = marca;
    }
    
    // Criar setters e Getters (convertendo nomes pra UPPERCASE)
    public int getIdModeloVan(){
        return this.idModeloVan;
    }
    
    public void setIdModeloVan(int id){
        this.idModeloVan = id;
    }
    
    public String getNomeModeloVan(){
        return this.nomeModeloVan;
    }
    
    public void setNomeModeloVan(String nome){
        this.nomeModeloVan = nome.toUpperCase();
    }
    
    public Marca getMarcaModeloVan(){
        return this.marcaModeloVan;
    }
    
    public void setMarcaModeloVan(Marca marca){
        this.marcaModeloVan = marca;
    }
}
*/