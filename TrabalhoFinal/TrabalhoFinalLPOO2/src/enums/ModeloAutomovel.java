package enums;



public enum ModeloAutomovel {
    //WOLKS
    GOL(0, Marca.WOLKSWAGEN),
    POLO(1, Marca.WOLKSWAGEN),
    JETTA(2, Marca.WOLKSWAGEN),
    
    //MERDECES
    CLASSE_A(3, Marca.MERCEDES),
    CLASSE_B(4, Marca.MERCEDES),
    CLASSE_C(5, Marca.MERCEDES),
    
    //FIAT
    UNO(6, Marca.FIAT),
    PALIO(7, Marca.FIAT),
    CRONOS(8, Marca.FIAT),
    
    //RENAULT
    SANDERO(9, Marca.RENAULT),
    CLIO(10, Marca.RENAULT),
    DUSTER(11, Marca.RENAULT),
    
    //PORSCHE
    PANAMERA(12, Marca.PORSCHE),
    CAYENNE(13, Marca.PORSCHE),
    CARRERA(14, Marca.PORSCHE),
    
    //HONDA
    CITY(15, Marca.HONDA),
    CIVIC(16, Marca.HONDA),
    ACCORD(17, Marca.HONDA);
    
    public final int ID_MODELO_AUTOMOVEL;
    public final Marca marca;
    
    ModeloAutomovel(int idModeloAutomovel, Marca marca){
        this.ID_MODELO_AUTOMOVEL = idModeloAutomovel;
        this.marca = marca;
    }
}


/*
public class ModeloAutomovel {

    private int idModeloAutomovel;
    private String nomeModeloAutomovel;
    private Marca marcaModeloAutomovel;

    public ModeloAutomovel(int id, String nome, Marca marca){
        this.idModeloAutomovel = id;
        this.nomeModeloAutomovel = nome;
        this.marcaModeloAutomovel = marca;
    }
    
    // Criar setters e Getters (convertendo nomes pra UPPERCASE)
    public int getIdModeloAutomovel(){
        return this.idModeloAutomovel;
    }
    
    public void setIdModeloAutomovel(int id){
        this.idModeloAutomovel = id;
    }
    
    public String getNomeModeloAutomovel(){
        return this.nomeModeloAutomovel;
    }
    
    public void setNomeModeloAutomovel(String nome){
        this.nomeModeloAutomovel = nome.toUpperCase();
    }
    
    public Marca getMarcaModeloAutomovel(){
        return this.marcaModeloAutomovel;
    }
    
    public void setMarcaModeloAutomovel(Marca marca){
        this.marcaModeloAutomovel = marca;
    }
}
*/