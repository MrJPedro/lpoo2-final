package enums;

public enum ModeloAutomovel {
    //WOLKS
    GOL(1, Marca.WOLKSWAGEN),
    POLO(2, Marca.WOLKSWAGEN),
    JETTA(3, Marca.WOLKSWAGEN),
    
    //MERDECES
    CLASSE_A(4, Marca.MERCEDES),
    CLASSE_B(5, Marca.MERCEDES),
    CLASSE_C(6, Marca.MERCEDES),
    
    //FIAT
    UNO(7, Marca.FIAT),
    PALIO(8, Marca.FIAT),
    CRONOS(9, Marca.FIAT),
    
    //RENAULT
    SANDERO(10, Marca.RENAULT),
    CLIO(11, Marca.RENAULT),
    DUSTER(12, Marca.RENAULT),
    
    //PORSCHE
    PANAMERA(13, Marca.PORSCHE),
    CAYENNE(14, Marca.PORSCHE),
    CARRERA(15, Marca.PORSCHE),
    
    //HONDA
    CITY(16, Marca.HONDA),
    CIVIC(17, Marca.HONDA),
    ACCORD(18, Marca.HONDA);
    
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