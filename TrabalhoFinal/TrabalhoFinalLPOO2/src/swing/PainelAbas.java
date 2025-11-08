package swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JTabbedPane;

public class PainelAbas extends JTabbedPane{

	PainelCriarClientes painelCriarClientes;
	PainelComprarVeiculo painelComprarVeiculo;
	PainelLocarVeiculo painelLocarVeiculo;
	PainelDevolverVeiculo painelDevolverVeiculo;
	PainelVenderVeiculo painelVenderVeiculo;
	
	PainelAbas(){
		
		setPreferredSize(new Dimension(1200, 800));
		
		painelCriarClientes = new PainelCriarClientes();
		add("Clientes", painelCriarClientes);
		
		painelComprarVeiculo = new PainelComprarVeiculo();
		add("Comprar Veículo", painelComprarVeiculo);
		
		painelLocarVeiculo = new PainelLocarVeiculo();
		add("Locar Veículo", painelLocarVeiculo);
		
		painelDevolverVeiculo = new PainelDevolverVeiculo();
		add("Devolver Veículo", painelDevolverVeiculo);
		
		painelVenderVeiculo = new PainelVenderVeiculo();
		add("Vender Veículo", painelVenderVeiculo);
		
		
		setTabLayoutPolicy(SCROLL_TAB_LAYOUT);
	}
	
	
}
