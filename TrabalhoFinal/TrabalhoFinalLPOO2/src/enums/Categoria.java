package enums;


public enum Categoria {
	POPULAR(0),
	INTERMEDIARIO(1),
	LUXO(2);
	
	public final int ID_CATEGORIA;
	
	Categoria(int idCategoria){
		this.ID_CATEGORIA = idCategoria;
	}
}


/*
public class Categoria {
    
    private int idCategoria;
    private String nomeCategoria;
    
    public Categoria(int id, String nome){
        this.idCategoria = id;
        this.nomeCategoria = nome;
    }

    // Criar setters e Getters (convertendo nomes pra UPPERCASE)
    public int getIdCategoria(){
        return this.idCategoria;
    }
    
    public void setIdCategoria(int id){
        this.idCategoria = id;
    }
    
    public String getNomeCategoria(){
        return this.nomeCategoria;
    }
    
    public void setNomeCategoria(String nome){
        this.nomeCategoria = nome.toUpperCase();
    }
}
*/