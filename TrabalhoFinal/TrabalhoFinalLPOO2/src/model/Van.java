package model;

import enums.Categoria;
import enums.Estado;
import enums.Marca;
import enums.ModeloVan;
import misc.Locacao;
import misc.Veiculo;

public class Van extends Veiculo{

	ModeloVan modeloVan;
	
	public Van(Marca marca, Estado estado, Categoria categoria, ModeloVan modeloVan, Locacao locacao, double valorDeCompra, String placa, int ano) {
		super(marca, estado, categoria, locacao, valorDeCompra, placa, ano);
		this.modeloVan = modeloVan;
	}

	@Override
	public double getValorDiariaLocacao() {
		double valor = 0;
		switch(getCategoria().getNomeCategoria()) {
			case "POPULAR":
				valor = 200;
				break;
			case "INTERMEDIARIO":
				valor = 400;
				break;
			case "LUXO":
				valor = 600;
				break;
		}
		
		return valor;
	}
	
	public ModeloVan getModelo() {
		return modeloVan;
	}

}
