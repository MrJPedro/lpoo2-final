package swing;

import javax.swing.JFrame;

public class Janela extends JFrame{
	
	PainelAbas painelAbas;
	
	public Janela(){
		setTitle("Locadora de Veículos");
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		painelAbas = new PainelAbas();
		add(painelAbas);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
