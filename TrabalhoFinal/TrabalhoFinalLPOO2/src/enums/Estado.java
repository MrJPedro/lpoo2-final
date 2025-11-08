package enums;


public enum Estado {
	NOVO(0),
	LOCADO(1),
	DISPONIVEL(2),
	VENDIDO(3);
	
	// Mudei para public para que consiga acessar no DAO
        public final int ID_ESTADO;
	
	Estado(int idEstado){
		this.ID_ESTADO = idEstado;
	}
}


/*
public class Estado {

    private int idEstado;
    private String nomeEstado;
    
    public Estado(int id, String nome){
        this.idEstado = id;
        this.nomeEstado = nome;
    }

    // Criar setters e Getters (convertendo nomes pra UPPERCASE)
    public int getIdEstado(){
        return this.idEstado;
    }
    
    public void setIdEstado(int id){
        this.idEstado = id;
    }
    
    public String getNomeEstado(){
        return this.nomeEstado;
    }
    
    public void setNomeEstado(String nome){
        this.nomeEstado = nome.toUpperCase();
    }
}
*/