package model;

import enums.Categoria;
import enums.Estado;
import enums.Marca;
import enums.ModeloAutomovel;
import misc.Locacao;
import misc.Veiculo;

public class Automovel extends Veiculo{

	private ModeloAutomovel modeloAutomovel;
	
	public Automovel(
                Marca marca,
                Estado estado,
                Categoria categoria,
                ModeloAutomovel modeloAutomovel,
                Locacao locacao,
                double valorDeCompra,
                String placa,
                int ano) {
		super(marca, estado, categoria, locacao, valorDeCompra, placa, ano);
		this.modeloAutomovel = modeloAutomovel;
	}

	@Override
	public double getValorDiariaLocacao() {
		double valor = 0;
		switch(getCategoria().getNomeCategoria()) {
			case "POPULAR":
				valor = 100;
				break;
			case "INTERMEDIARIO":
				valor = 300;
				break;
			case "LUXO":
				valor = 450;
				break;
		}
		
		return valor;
	}
	
	public ModeloAutomovel getModelo() {
		return modeloAutomovel;
	}

}
