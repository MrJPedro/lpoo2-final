package enums;


public enum Marca {
	WOLKSWAGEN(0),
	FIAT(1),
	RENAULT(2),
	MERCEDES(3),
	PORSCHE(4),
	HONDA(5),
	YAMAHA(6),
	HARLEY_DAVIDSON(7);
	
	public final int ID_MARCA;
	
	Marca(int idMarca){
		this.ID_MARCA = idMarca;
	}
}

/*    
public class Marca {

    private int idMarca;
    private String nomeMarca;
    
    public Marca(int id, String nome){
        this.idMarca = id;
        this.nomeMarca= nome;
    }

    // Criar setters e Getters (convertendo nomes pra UPPERCASE)
    public int getIdMarca(){
        return this.idMarca;
    }
    
    public void setIdMarca(int id){
        this.idMarca = id;
    }
    
    public String getNomeMarca(){
        return this.nomeMarca;
    }
    
    public void setNomeMarca(String nome){
        this.nomeMarca = nome.toUpperCase();
    }
}
*/