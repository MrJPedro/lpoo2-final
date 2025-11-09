package enums;

public enum Marca {
	WOLKSWAGEN(1),
	FIAT(2),
	RENAULT(3),
	MERCEDES(4),
	PORSCHE(5),
	HONDA(6),
	YAMAHA(7),
	HARLEY_DAVIDSON(8);
	
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