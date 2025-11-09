package misc;
import model.Cliente;
import java.time.LocalDate;

public class Locacao {
        
        private long idLocacao;
        private int dias;
	private double valor;
	private LocalDate data;
	private Cliente cliente;
	
	public Locacao(long idLocacao, int dias, double valor, LocalDate data, Cliente cliente){
                this.idLocacao = idLocacao;
                this.dias = dias;
		this.valor = valor;
		this.data = data;
		this.cliente = cliente;
	}
	
        public long getIdLocacao(){
            return this.idLocacao;
        }
        
        public double getValor() {
		return valor;
	}

	public LocalDate getData() {
		return data;
	}
	
	public int getDias() {
		return dias;
	}

	public Cliente getCliente() {
		return cliente;
	}
        
        public void setIdLocacao(long idLocacao) {
        this.idLocacao = idLocacao;
    }
}
