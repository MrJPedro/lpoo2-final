package model;

import enums.Categoria;
import enums.Estado;
import enums.Marca;
import enums.ModeloMotocicleta;
import misc.Locacao;
import misc.Veiculo;

public class Motocicleta extends Veiculo{

	ModeloMotocicleta modeloMotocicleta;
	
	public Motocicleta(Marca marca, Estado estado, Categoria categoria, ModeloMotocicleta modeloMotocicleta, Locacao locacao, double valorDeCompra, String placa, int ano) {
		super(marca, estado, categoria, locacao, valorDeCompra, placa, ano);
		this.modeloMotocicleta = modeloMotocicleta;
	}

	@Override
	public double getValorDiariaLocacao() {
		double valor = 0;
		switch(getCategoria().toString()) {
			case "POPULAR":
				valor = 70;
				break;
			case "INTERMEDIARIO":
				valor = 200;
				break;
			case "LUXO":
				valor = 350;
				break;
		}
		
		return valor;
	}
	
	public ModeloMotocicleta getModelo() {
		return modeloMotocicleta;
	}

}