package enums;


public enum ModeloMotocicleta {
	//HONDA
	CG_125(0, Marca.HONDA),
	XRE_300(1, Marca.HONDA),
	FALCON(2, Marca.HONDA),
	
	//YAMAHA
	FAZER_250(3, Marca.YAMAHA),
	MT_03(4, Marca.YAMAHA),
	LANDER_250(5, Marca.YAMAHA),
	
	//HARLEY DAVIDSON
	IRON_883(6, Marca.HARLEY_DAVIDSON),
	FAT_BOY(7, Marca.HARLEY_DAVIDSON),
	STREET_GLIDE(8, Marca.HARLEY_DAVIDSON);
	
	public final int ID_MODELO_MOTOCICLETA;
	public final Marca marca;
	
	ModeloMotocicleta(int idModeloMotocicleta, Marca marca){
		this.ID_MODELO_MOTOCICLETA = idModeloMotocicleta;
		this.marca = marca;
	}
}



/*
public class ModeloMotocicleta {

    private int idModeloMotocicleta;
    private String nomeModeloMotocicleta;
    private Marca marcaModeloMotocicleta;

    public ModeloMotocicleta(int id, String nome, Marca marca){
        this.idModeloMotocicleta = id;
        this.nomeModeloMotocicleta = nome;
        this.marcaModeloMotocicleta = marca;
    }
    
    // Criar setters e Getters (convertendo nomes pra UPPERCASE)
    public int getIdModeloMotocicleta(){
        return this.idModeloMotocicleta;
    }
    
    public void setIdModeloMotocicleta(int id){
        this.idModeloMotocicleta = id;
    }
    
    public String getNomeModeloMotocicleta(){
        return this.nomeModeloMotocicleta;
    }
    
    public void setNomeModeloMotocicleta(String nome){
        this.nomeModeloMotocicleta = nome.toUpperCase();
    }
    
    public Marca getMarcaModeloMotocicleta(){
        return this.marcaModeloMotocicleta;
    }
    
    public void setMarcaModeloMotocicleta(Marca marca){
        this.marcaModeloMotocicleta = marca;
    }
}
*/