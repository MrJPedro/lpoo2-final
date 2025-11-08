package swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import misc.Veiculo;

public class PainelDevolverVeiculo extends JPanel implements ActionListener{

	JButton bDevolverVeiculo;
	JLabel lTabela;
	
	JTable tabelaVeiculos;
	JScrollPane painelTabelaVeiculos;
	
	Veiculo veiculoSelecionado;
	
	Font fonte1 = new Font("Arial", 0, 18);
	Font fonte2 = new Font("Arial", Font.BOLD, 14);
	Font fonte3 = new Font("Arial", Font.BOLD, 18);
	Font fonte4 = new Font("Arial", Font.BOLD, 12);
	
	public static Tabela<Veiculo> devolverVeiculo;
	private String[] parametros = {"NOME CLIENTE", "PLACA", "MARCA", "MODELO", "ANO", "DATA", "PRECO DIARIA", "DIAS", "VALOR LOCACAO"};
	
	public PainelDevolverVeiculo() {
		setLayout(null);
		
		bDevolverVeiculo = new JButton("Devolver veículo");
		bDevolverVeiculo.setBounds(300, 675, 600, 75);
		bDevolverVeiculo.setFont(fonte2);
		bDevolverVeiculo.addActionListener(this);
		add(bDevolverVeiculo);
		
		PainelLocarVeiculo.atualizarVeiculosDisponiveis();
		
		lTabela = new JLabel("VEÍCULOS LOCADOS");
		lTabela.setFont(fonte3);
		lTabela.setBounds(500, 15, 300, 40);
		add(lTabela);
		
		devolverVeiculo = new Tabela<Veiculo>(parametros, PainelLocarVeiculo.veiculosLocados);
		//Configurar posição e aparência da tabela Veiculos
		painelTabelaVeiculos = new JScrollPane();
		painelTabelaVeiculos.setBounds(25, 50, 1150, 600);
		tabelaVeiculos = new JTable(devolverVeiculo);
		tabelaVeiculos.setBounds(0, 0, painelTabelaVeiculos.getWidth(), painelTabelaVeiculos.getHeight());
		tabelaVeiculos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaVeiculos.setFont(fonte1);
		tabelaVeiculos.setRowHeight(30);
		tabelaVeiculos.getColumnModel().getColumn(2).setPreferredWidth(120);
		tabelaVeiculos.getColumnModel().getColumn(4).setPreferredWidth(30);
		tabelaVeiculos.getColumnModel().getColumn(7).setPreferredWidth(15);
		tabelaVeiculos.getColumnModel().getColumn(0).setHeaderValue("NOME DO CLIENTE");
		tabelaVeiculos.getColumnModel().getColumn(1).setHeaderValue("PLACA");
	    tabelaVeiculos.getColumnModel().getColumn(2).setHeaderValue("MARCA");
	    tabelaVeiculos.getColumnModel().getColumn(3).setHeaderValue("MODELO");
	    tabelaVeiculos.getColumnModel().getColumn(4).setHeaderValue("ANO");
		tabelaVeiculos.getColumnModel().getColumn(5).setHeaderValue("DATA DA LOCAÇÃO");
	    tabelaVeiculos.getColumnModel().getColumn(6).setHeaderValue("PREÇO DIÁRIA");
	    tabelaVeiculos.getColumnModel().getColumn(7).setHeaderValue("DIAS LOCADOS");
	    tabelaVeiculos.getColumnModel().getColumn(8).setHeaderValue("VALOR DA LOCAÇÃO");
	    
	    tabelaVeiculos.setBorder(new LineBorder(Color.BLACK, 1));
		painelTabelaVeiculos.setViewportView(tabelaVeiculos);
		add(painelTabelaVeiculos);
		//Selecionar Veículo
		tabelaVeiculos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    @Override
		    public void valueChanged(ListSelectionEvent e) {
		        int selectedRow = tabelaVeiculos.getSelectedRow();
		        if (selectedRow != -1) {
		        	veiculoSelecionado = devolverVeiculo.getDados().get(selectedRow);

		        }
		    }
		});

		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bDevolverVeiculo) {
			if(veiculoSelecionado == null)
				JOptionPane.showMessageDialog(null,"Selecione um veículo",  "Operação Não Concluida", JOptionPane.WARNING_MESSAGE);
			else {
				veiculoSelecionado.devolver();
				veiculoSelecionado = null;
				JOptionPane.showMessageDialog(null,"Veículo devolvido com sucesso",  "Sucesso", JOptionPane.DEFAULT_OPTION);
				PainelLocarVeiculo.atualizarVeiculosDisponiveis();
				PainelLocarVeiculo.atualizarVeiculosLocados();
				PainelLocarVeiculo.atualizarClientesAptos();
				PainelLocarVeiculo.locarVeiculo.atualizarTabela(PainelLocarVeiculo.veiculosDisponiveis);
				PainelVenderVeiculo.venderVeiculo.atualizarTabela(PainelLocarVeiculo.veiculosDisponiveis);
				PainelDevolverVeiculo.devolverVeiculo.atualizarTabela(PainelLocarVeiculo.veiculosLocados);
				PainelLocarVeiculo.tabelaClientesAptos.atualizarTabela(PainelLocarVeiculo.clientesAptos);
			}
		}
	}
}
