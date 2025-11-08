package swing;

import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

import enums.Categoria;
import enums.Estado;
import enums.Marca;
import enums.ModeloAutomovel;
import enums.ModeloMotocicleta;
import enums.ModeloVan;
import misc.Locacao;
import misc.Veiculo;
import model.Automovel;
import model.Motocicleta;
import model.Van;

public class PainelComprarVeiculo extends JPanel implements ActionListener{

	JComboBox cMarca, cModelo, cEstado, cCategoria;
	JFormattedTextField tPreco, tPlaca, tAno;
	JLabel lMarca, lModelo, lEstado, lCategoria, lPreco, lPlaca, lAno, lTitulo;
	
	DefaultComboBoxModel<Categoria> optCategoria;
	DefaultComboBoxModel<Estado> optEstado;
	DefaultComboBoxModel<Marca> optMarca;
	
	JButton bComprar;
	
	Font fonte1 = new Font("Arial", 0, 18);
	Font fonte2 = new Font("Arial", Font.BOLD, 14);
	Font fonte3 = new Font("Arial", Font.BOLD, 18);
	
	public PainelComprarVeiculo(){
		setLayout(null);
		
		lTitulo = new JLabel("COMPRAR VEÍCULO");
		lTitulo.setFont(fonte3);
		lTitulo.setBounds(500, 20, 200, 40);
		add(lTitulo);
		
		//Posicionar Combo-Box's
		//Combo Marca
		lMarca = new JLabel("MARCA");
		lMarca.setFont(fonte2);
		lMarca.setBounds(250, 60, 100, 40);
		add(lMarca);
		
		optMarca = new DefaultComboBoxModel<>(Marca.values());
		
		cMarca = new JComboBox(optMarca);
		cMarca.setFont(fonte1);
		cMarca.setBounds(250, 90, 300, 40);
		cMarca.addActionListener(this);
		add(cMarca);
		
		//Combo Modelo
		lModelo = new JLabel("MODELO");
		lModelo.setFont(fonte2);
		lModelo.setBounds(650, 60, 100, 40);
		add(lModelo);
		
		cModelo = new JComboBox();
		cModelo.setFont(fonte1);
		cModelo.setBounds(650, 90, 300, 40);
		add(cModelo);
		
		Marca selecionada = (Marca) cMarca.getSelectedItem();
	    for (ModeloAutomovel modelo : ModeloAutomovel.values()) {
	        if (modelo.marca == selecionada) {
	            cModelo.addItem(modelo);
	        }
	    }
		
		//Combo Categoria
		lCategoria = new JLabel("CATEGORIA");
		lCategoria.setFont(fonte2);
		lCategoria.setBounds(250, 150, 100, 40);
		add(lCategoria);
		
		optCategoria = new DefaultComboBoxModel<>(Categoria.values());
		
		cCategoria = new JComboBox(optCategoria);
		cCategoria.setFont(fonte1);
		cCategoria.setBounds(250, 180, 300, 40);
		add(cCategoria);
		
		//Combo Estado
		lEstado = new JLabel("ESTADO");
		lEstado.setFont(fonte2);
		lEstado.setBounds(650, 150, 100, 40);
		add(lEstado);
		
		optEstado = new DefaultComboBoxModel<>(new Estado[] { Estado.DISPONIVEL });
		
		cEstado = new JComboBox(optEstado);
		cEstado.setFont(fonte1);
		cEstado.setBounds(650, 180, 300, 40);
		add(cEstado);
		
		//Campo Placa
		lPlaca = new JLabel("PLACA");
		lPlaca.setFont(fonte2);
		lPlaca.setBounds(250, 240, 100, 40);
		add(lPlaca);
		
		try {
			MaskFormatter fPlaca = new MaskFormatter("UUU-####");
			fPlaca.setValueContainsLiteralCharacters(false);
			fPlaca.setOverwriteMode(true);
			fPlaca.setPlaceholderCharacter('_');

			tPlaca = new JFormattedTextField(fPlaca);
			tPlaca.setFont(fonte1);
			tPlaca.setBounds(250, 270, 300, 40);
			add(tPlaca);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//Campo Ano
		lAno = new JLabel("ANO");
		lAno.setFont(fonte2);
		lAno.setBounds(650, 240, 100, 40);
		add(lAno);
		
		try {
			MaskFormatter fAno = new MaskFormatter("####");
			fAno.setValueContainsLiteralCharacters(false);
			fAno.setOverwriteMode(true);
			fAno.setPlaceholderCharacter('_');

			tAno = new JFormattedTextField(fAno);
			tAno.setFont(fonte1);
			tAno.setBounds(650, 270, 300, 40);
			add(tAno);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//Campo Preço
		lPreco = new JLabel("PREÇO");
		lPreco.setFont(fonte2);
		lPreco.setBounds(250, 330, 100, 40);
		add(lPreco);
		
		try {
			MaskFormatter fPreco = new MaskFormatter("R$###.###,##");
			fPreco.setValueContainsLiteralCharacters(false);
			fPreco.setOverwriteMode(true);
			fPreco.setPlaceholderCharacter('0');
			
			tPreco = new JFormattedTextField(fPreco);
			tPreco.setFont(fonte1);
			tPreco.setBounds(250, 360, 300, 40);
			add(tPreco);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//Botão Comprar
		bComprar = new JButton("COMPRAR");
		bComprar.setBounds(650, 360, 300, 40);
		bComprar.setMargin(new Insets(0, 0, 0, 0));
		bComprar.setFont(fonte2);
		bComprar.addActionListener(this);
		add(bComprar);
		
		
	}

	private void validarEConcluir() {		
		if(tPlaca.getText().replaceAll("[_-]","").trim().isEmpty())//Se a placa for vazia
			JOptionPane.showMessageDialog(null,"Informe a placa",  "Operação Não Concluida", JOptionPane.WARNING_MESSAGE);
		else if(tAno.getText().trim().replaceAll("_", "").isEmpty())//Se o ano for vazio
			JOptionPane.showMessageDialog(null,"Preencha o campo ANO",  "Operação Não Concluida", JOptionPane.WARNING_MESSAGE);
		else if(Integer.parseInt(tAno.getText().trim().replaceAll("_", "")) > 2025 || Integer.parseInt(tAno.getText().trim().replaceAll("_", "")) < 1980)//Se ~(1980 < ano < 2025)
			JOptionPane.showMessageDialog(null,"Ano inválido",  "Operação Não Concluida", JOptionPane.WARNING_MESSAGE);
		else if((Double.parseDouble(tPreco.getText().replaceAll("[^\\d]", "")) / 100) < 10000)//Se preço < R$10.000,00
			JOptionPane.showMessageDialog(null,"O preço deve ser no mínimo\nR$10.000,00",  "Operação Não Concluida", JOptionPane.WARNING_MESSAGE);
		else
			comprarVeiculo();
	}

	private void comprarVeiculo() {
		Object selecionado = cModelo.getSelectedItem();

		Marca marcaSelecionada = (Marca) cMarca.getSelectedItem();
		Object modeloSelecionado = cModelo.getSelectedItem();
	    Estado estadoSelecionado = (Estado) cEstado.getSelectedItem();
	    Categoria categoriaSelecionada = (Categoria) cCategoria.getSelectedItem();
	    String placa = tPlaca.getText().replaceAll("[_-]", "").trim();
	    int ano = Integer.parseInt(tAno.getText());
	    double preco = Double.parseDouble(tPreco.getText().replaceAll("[^\\d]", "")) / 100;
		
		if(selecionado instanceof ModeloAutomovel){
			Veiculo automovel = new Automovel(marcaSelecionada, estadoSelecionado, categoriaSelecionada, (ModeloAutomovel) modeloSelecionado, null, preco, placa, ano);
		    PainelLocarVeiculo.veiculos.add(automovel);
		    PainelLocarVeiculo.locarVeiculo.fireTableDataChanged();
		}else if(selecionado instanceof ModeloVan) {
			Veiculo van = new Van(marcaSelecionada, estadoSelecionado, categoriaSelecionada, (ModeloVan) modeloSelecionado, null, preco, placa, ano);
			PainelLocarVeiculo.veiculos.add(van);
		    PainelLocarVeiculo.locarVeiculo.fireTableDataChanged();
		}else if(selecionado instanceof ModeloMotocicleta) {
			Veiculo motocicleta = new Motocicleta(marcaSelecionada, estadoSelecionado, categoriaSelecionada, (ModeloMotocicleta) modeloSelecionado, null, preco, placa, ano);
			PainelLocarVeiculo.veiculos.add(motocicleta);
		    PainelLocarVeiculo.locarVeiculo.fireTableDataChanged();
		} 
		PainelLocarVeiculo.atualizarVeiculosDisponiveis();
		PainelLocarVeiculo.locarVeiculo.atualizarTabela(PainelLocarVeiculo.veiculosDisponiveis);
		PainelVenderVeiculo.venderVeiculo.atualizarTabela(PainelLocarVeiculo.veiculosDisponiveis);
		JOptionPane.showMessageDialog(null,"Veículo comprado com sucesso",  "Sucesso", JOptionPane.DEFAULT_OPTION);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == cMarca){
			Marca selecionada = (Marca) cMarca.getSelectedItem();
		    cModelo.removeAllItems();
		    for (ModeloAutomovel modelo : ModeloAutomovel.values()) {
		        if (modelo.marca == selecionada) {
		            cModelo.addItem(modelo);
		        }
		    }
		    for (ModeloVan modelo : ModeloVan.values()) {
		        if (modelo.marca == selecionada) {
		            cModelo.addItem(modelo);
		        }
		    }
		    for (ModeloMotocicleta modelo : ModeloMotocicleta.values()) {
		        if (modelo.marca == selecionada) {
		            cModelo.addItem(modelo);
		        }
		    }

		}else if(e.getSource() == bComprar) {
			validarEConcluir();
		}
		
	}
}

