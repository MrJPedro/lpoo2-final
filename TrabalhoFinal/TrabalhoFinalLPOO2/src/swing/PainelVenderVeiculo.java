package swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import enums.Categoria;
import enums.Estado;
import enums.Marca;
import misc.Veiculo;

public class PainelVenderVeiculo extends JPanel implements ActionListener{
	
	JComboBox cMarca, cTipo, cCategoria;
	JLabel lMarca, lTipo, lCategoria, lTabela;
	
	JButton bFiltrarVeiculos, bVenderVeiculo;
	
	JTable tabelaVeiculos;
	JScrollPane painelTabelaVeiculos;
	
	Veiculo veiculoSelecionado;
	
	Font fonte1 = new Font("Arial", 0, 18);
	Font fonte2 = new Font("Arial", Font.BOLD, 14);
	Font fonte3 = new Font("Arial", Font.BOLD, 18);
	Font fonte4 = new Font("Arial", Font.BOLD, 12);
	
	public static Tabela<Veiculo> venderVeiculo;
	private String[] parametros = {"PLACA", "MARCA", "MODELO", "ANO", "PRECO VENDA"};
	
	public PainelVenderVeiculo(){
		setLayout(null);
		
		//Combo Tipo
		lTipo = new JLabel("TIPO");
		lTipo.setFont(fonte2);
		lTipo.setBounds(25, 30, 110, 40);
		add(lTipo);

		DefaultComboBoxModel<String> optTipo = new DefaultComboBoxModel<>(new String[] { "Todos", "Automovel", "Motocicleta", "Van" });

		cTipo = new JComboBox(optTipo);
		cTipo.setFont(fonte1);
		cTipo.setBounds(25, 60, 160, 40);
		add(cTipo);

		//Combo Marca
		lMarca = new JLabel("MARCA");
		lMarca.setFont(fonte2);
		lMarca.setBounds(210, 30, 100, 40);
		add(lMarca);

		Object[] marcas = new Object[Marca.values().length + 1];
		marcas[0] = "TODOS";
		for (int i = 0; i < Marca.values().length; i++) {
		    marcas[i + 1] = Marca.values()[i];
		}

		cMarca = new JComboBox(marcas);
		cMarca.setFont(fonte1);
		cMarca.setBounds(210, 60, 160, 40);
		add(cMarca);

		//Combo Categoria
		lCategoria = new JLabel("CATEGORIA");
		lCategoria.setFont(fonte2);
		lCategoria.setBounds(25, 120, 110, 40);
		add(lCategoria);

		Object[] categorias = new Object[Categoria.values().length + 1];
		categorias[0] = "TODOS";
		for (int i = 0; i < Categoria.values().length; i++) {
		    categorias[i + 1] = Categoria.values()[i];
		}

		cCategoria = new JComboBox(categorias);
		cCategoria.setFont(fonte1);
		cCategoria.setBounds(25, 150, 160, 40);
		add(cCategoria);

		bFiltrarVeiculos = new JButton("Filtrar");
		bFiltrarVeiculos.setBounds(210, 150, 160, 40);
		bFiltrarVeiculos.setFont(fonte2);
		bFiltrarVeiculos.addActionListener(this);
		add(bFiltrarVeiculos);
		
		bVenderVeiculo = new JButton("Vender Veículo");
		bVenderVeiculo.setBounds(25, 240, 350, 40);
		bVenderVeiculo.setFont(fonte2);
		bVenderVeiculo.addActionListener(this);
		add(bVenderVeiculo);
		
		
		PainelLocarVeiculo.atualizarVeiculosDisponiveis();
		
		lTabela = new JLabel("VEÍCULOS NÃO LOCADOS");
		lTabela.setFont(fonte3);
		lTabela.setBounds(430, 15, 400, 40);
		add(lTabela);
		
		venderVeiculo = new Tabela<Veiculo>(parametros, PainelLocarVeiculo.veiculosDisponiveis);
		//Configurar posição e aparência da tabela Veiculos
		painelTabelaVeiculos = new JScrollPane();
		painelTabelaVeiculos.setBounds(425, 50, 750, 700);
		tabelaVeiculos = new JTable(venderVeiculo);
		tabelaVeiculos.setBounds(0, 0, painelTabelaVeiculos.getWidth(), painelTabelaVeiculos.getHeight());
		tabelaVeiculos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaVeiculos.setFont(fonte1);
		tabelaVeiculos.setRowHeight(30);
		tabelaVeiculos.getColumnModel().getColumn(0).setHeaderValue("PLACA");
		tabelaVeiculos.getColumnModel().getColumn(1).setHeaderValue("MARCA");
	    tabelaVeiculos.getColumnModel().getColumn(2).setHeaderValue("MODELO");
	    tabelaVeiculos.getColumnModel().getColumn(3).setHeaderValue("ANO");
	    tabelaVeiculos.getColumnModel().getColumn(4).setHeaderValue("PREÇO DE VENDA");
	    
	    tabelaVeiculos.setBorder(new LineBorder(Color.BLACK, 1));
		painelTabelaVeiculos.setViewportView(tabelaVeiculos);
		add(painelTabelaVeiculos);
		//Selecionar Veículo
		tabelaVeiculos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Get the selected row index
                int selectedRow = tabelaVeiculos.getSelectedRow();

                // If a row is selected (non-negative index)
                if (selectedRow != -1) {
                    // Perform your action when a row is selected
                	veiculoSelecionado = venderVeiculo.getDados().get(selectedRow);
                }
            }
        });
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bFiltrarVeiculos)
			filtrarVeiculos();
		else if(e.getSource() == bVenderVeiculo)
			verificarEVender();
	}
	
	private void verificarEVender(){
		if(veiculoSelecionado == null)
			JOptionPane.showMessageDialog(null,"Selecione um veículo",  "Operação Não Concluida", JOptionPane.WARNING_MESSAGE);
		else {
			veiculoSelecionado.vender();
			veiculoSelecionado = null;
			JOptionPane.showMessageDialog(null,"Veículo vendido com sucesso",  "Sucesso", JOptionPane.DEFAULT_OPTION);
			PainelLocarVeiculo.atualizarVeiculosDisponiveis();
			PainelLocarVeiculo.locarVeiculo.atualizarTabela(PainelLocarVeiculo.veiculosDisponiveis);
			PainelVenderVeiculo.venderVeiculo.atualizarTabela(PainelLocarVeiculo.veiculosDisponiveis);
			filtrarVeiculos();
		}
	}
	
	private void filtrarVeiculos() {
	    String tipoSelecionado = (String) cTipo.getSelectedItem();
	    Object marcaSelecionada = cMarca.getSelectedItem();
	    Object categoriaSelecionada = cCategoria.getSelectedItem();

	    List<Veiculo> veiculosFiltrados = new ArrayList<>();

	    for (Veiculo v : PainelLocarVeiculo.veiculos) {
	        boolean filtraTipo = tipoSelecionado.equals("Todos") || 
	            (tipoSelecionado.equalsIgnoreCase("Automovel") && v instanceof model.Automovel) ||
	            (tipoSelecionado.equalsIgnoreCase("Motocicleta") && v instanceof model.Motocicleta) ||
	            (tipoSelecionado.equalsIgnoreCase("Van") && v instanceof model.Van);

	        boolean filtraMarca = marcaSelecionada.toString().equals("TODOS") || v.getMarca().equals(marcaSelecionada);

	        boolean filtraCategoria = categoriaSelecionada.toString().equals("TODOS") || v.getCategoria().equals(categoriaSelecionada);

	        if (filtraTipo && filtraMarca && filtraCategoria && v.getEstado() == Estado.DISPONIVEL) {
	            veiculosFiltrados.add(v);
	        }
	    }

	    venderVeiculo.atualizarTabela(veiculosFiltrados);
	}
}
