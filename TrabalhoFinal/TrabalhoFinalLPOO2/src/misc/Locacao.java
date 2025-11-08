package misc;
import model.Cliente;
import java.time.LocalDate;

public class Locacao {

	private int dias;
	private double valor;
	private LocalDate data;
	private Cliente cliente;
	
	public Locacao(int dias, double valor, LocalDate data, Cliente cliente){
		this.dias = dias;
		this.valor = valor;
		this.data = data;
		this.cliente = cliente;
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
}
