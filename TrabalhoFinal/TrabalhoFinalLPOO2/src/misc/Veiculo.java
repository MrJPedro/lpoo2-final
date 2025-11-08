package misc;

import model.Cliente;
import enums.Categoria;
import enums.Estado;
import enums.Marca;
import java.time.LocalDate;

public abstract class Veiculo implements Veiculo1{
	
	private Marca marca;
	private Estado estado;
	private Categoria categoria;
	private Locacao locacao;
	private double valorDeCompra;
	private String placa;
	private int ano;
	
	public Veiculo(Marca marca, Estado estado, Categoria categoria, Locacao locacao, double valorDeCompra, String placa, int ano){
		this.marca = marca;
		this.estado = estado;
		this.categoria = categoria;
		this.locacao = locacao;
		this.valorDeCompra = valorDeCompra;
		this.placa = placa;
		this.ano = ano;
	}

	@Override
	public void locar(int dias, LocalDate data, Cliente cliente) {
		locacao = new Locacao(dias, getValorDiariaLocacao()*dias, data, cliente);
		estado = Estado.LOCADO;
	}

	@Override
	public void vender() {
		estado = Estado.VENDIDO;
		locacao = null;
	}

	@Override
	public void devolver() {
		estado = Estado.DISPONIVEL;
		locacao = null;
	}

	@Override
	public Estado getEstado() {
		return estado;
	}

	@Override
	public Marca getMarca() {
		return marca;
	}

	@Override
	public Categoria getCategoria() {
		return categoria;
	}

	@Override
	public Locacao getLocacao() {
		return this.locacao;
	}

	@Override
	public String getPlaca() {
		return this.placa;
	}

	@Override
	public int getAno() {
		return this.ano;
	}

	public double getValorDeCompra() {
		return this.valorDeCompra;
	}
	
	@Override
	public double getValorParaVenda() {	//Idade do veículo hardcoded
		double valorDeVenda = valorDeCompra - ((2025 - ano) * 0.15 * valorDeCompra);
		
		if(valorDeVenda < valorDeCompra*0.1)
			valorDeVenda = valorDeCompra * 0.1;
		
		return valorDeVenda;
	}

	@Override
	public abstract double getValorDiariaLocacao();

	public abstract Object getModelo();

}
