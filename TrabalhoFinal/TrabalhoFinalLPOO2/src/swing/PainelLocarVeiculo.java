	package swing;
	
	import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.MaskFormatter;

import enums.Categoria;
import enums.Estado;
import enums.Marca;
import enums.ModeloAutomovel;
import model.Cliente;
import misc.Locacao;
import misc.Veiculo;
import model.Automovel;
	
	public class PainelLocarVeiculo extends JPanel implements ActionListener{
		
		JTextField tNome, tSobrenome;
		JFormattedTextField tCpf, tDias, tData;
		
		JComboBox cMarca, cTipo, cCategoria;
		JLabel lNome, lSobrenome, lCpf, lMarca, lTipo, lCategoria, lData, lDias, lTabelaClientes, lTabelaVeiculos, lFiltroClientes, lFiltroVeiculos, lRadio;
		
		JRadioButton rNome, rSobrenome, rCpf;
		ButtonGroup gFiltro;
		
		JButton bFiltrarClientes, bLimpar, bFiltrarVeiculos, bLocar;
		
		JScrollPane painelTabelaClientes, painelTabelaVeiculos;
		
		JTable tabelaClientes, tabelaVeiculos;
		
		/*
		 * Criação da tabela LOCAR VEÍCULOS.
		 * A lista veiculos será usada por
		 * outras tabelas para que o conteúdo
		 * seja o mesmo.
		 * 
		 * A o conteúdo da tabela é o mesmo em
		 * qualquer painel, e está sempre sincronizado
		 * entre eles.
		 * */
		public static Tabela<Veiculo> locarVeiculo;
		public static Tabela<Cliente> tabelaClientesAptos;
		public static List<Veiculo> veiculos = new ArrayList<>();
		public static List<Veiculo> veiculosDisponiveis = new ArrayList<>();
		public static List<Veiculo> veiculosLocados = new ArrayList<>();
		public static List<Cliente> clientesAptos = new ArrayList<>(); 
		private String[] parametros = {"PLACA", "MARCA", "MODELO", "ANO", "PRECO DIARIA"};
		
		Cliente clienteSelecionado;
		Veiculo veiculoSelecionado;
		
		Font fonte1 = new Font("Arial", 0, 18);
		Font fonte2 = new Font("Arial", Font.BOLD, 14);
		Font fonte3 = new Font("Arial", Font.BOLD, 18);
		Font fonte4 = new Font("Arial", Font.BOLD, 12);
		
		public PainelLocarVeiculo() {
			setLayout(null);
			//Label Filtrar Clientes
			lFiltroClientes = new JLabel("FILTRAR CLIENTES");
			lFiltroClientes.setFont(fonte2);
			lFiltroClientes.setBounds(140, 15, 400, 40);
			add(lFiltroClientes);
			
			//Campo Nome
			lNome = new JLabel("NOME");
			lNome.setFont(fonte2);
			lNome.setBounds(25, 30, 100, 40);
			add(lNome);
			
			tNome = new JTextField();
			tNome.setBounds(25, 60, 350, 40);
			tNome.setFont(fonte1);
			add(tNome);
			
			//Campo Sobrenome
			lSobrenome = new JLabel("SOBRENOME");
			lSobrenome.setFont(fonte2);
			lSobrenome.setBounds(25, 120, 100, 40);
			add(lSobrenome);
			
			tSobrenome = new JTextField();
			tSobrenome.setBounds(25, 150, 350, 40);
			tSobrenome.setFont(fonte1);
			add(tSobrenome);
			
			//Campo CPF
			lCpf = new JLabel("CPF");
			lCpf.setFont(fonte2);
			lCpf.setBounds(25, 210, 100, 40);
			add(lCpf);
			
			try {
				MaskFormatter fCpf = new MaskFormatter("###.###.###-##");
				fCpf.setValueContainsLiteralCharacters(false);
				fCpf.setOverwriteMode(true);
				fCpf.setPlaceholderCharacter('_');
				tCpf = new JFormattedTextField(fCpf);
				tCpf.setBounds(25, 240, 150, 40);
				tCpf.setFont(fonte1);
				add(tCpf);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			//RadioButtons
			
			lRadio = new JLabel("Filtrar por:");
			lRadio.setFont(fonte2);
			lRadio.setBounds(250, 210, 100, 40);
			add(lRadio);
			
			gFiltro = new ButtonGroup();
			
			rNome = new JRadioButton("NOME");
			rNome.setBounds(175, 240, 75, 40);
			rNome.setFont(fonte4);
			rNome.setSelected(true);
			gFiltro.add(rNome);
			add(rNome);
			
			rSobrenome = new JRadioButton("SOBRENOME");
			rSobrenome.setBounds(250, 240, 125, 40);
			rSobrenome.setFont(fonte4);
			gFiltro.add(rSobrenome);
			add(rSobrenome);
			
			rCpf = new JRadioButton("CPF");
			rCpf.setBounds(375, 240, 50, 40);
			rCpf.setFont(fonte4);
			gFiltro.add(rCpf);
			add(rCpf);
			
			//Botões
			bLimpar = new JButton("Limpar Filtros");
			bLimpar.setBounds(25, 330, 160, 40);
			bLimpar.setFont(fonte2);
			bLimpar.addActionListener(this);
			add(bLimpar);
			
			bFiltrarClientes = new JButton("Filtrar Clientes");
			bFiltrarClientes.setBounds(210, 330, 160, 40);
			bFiltrarClientes.setFont(fonte2);
			bFiltrarClientes.addActionListener(this);
			add(bFiltrarClientes);
			
			//Label Filtrar Veículos
			lFiltroClientes = new JLabel("FILTRAR VEÍCULOS");
			lFiltroClientes.setFont(fonte2);
			lFiltroClientes.setBounds(140, 395, 400, 40);
			add(lFiltroClientes);
			
			//Combo Tipo
			lTipo = new JLabel("TIPO");
			lTipo.setFont(fonte2);
			lTipo.setBounds(25, 420, 110, 40);
			add(lTipo);
	
			DefaultComboBoxModel<String> optTipo = new DefaultComboBoxModel<>(new String[] { "Todos", "Automovel", "Motocicleta", "Van" });
	
			cTipo = new JComboBox(optTipo);
			cTipo.setFont(fonte1);
			cTipo.setBounds(25, 450, 160, 40);
			add(cTipo);
	
			//Combo Marca
			lMarca = new JLabel("MARCA");
			lMarca.setFont(fonte2);
			lMarca.setBounds(210, 420, 100, 40);
			add(lMarca);
	
			Object[] marcas = new Object[Marca.values().length + 1];
			marcas[0] = "TODOS";
			for (int i = 0; i < Marca.values().length; i++) {
			    marcas[i + 1] = Marca.values()[i];
			}
	
			cMarca = new JComboBox(marcas);
			cMarca.setFont(fonte1);
			cMarca.setBounds(210, 450, 160, 40);
			add(cMarca);
	
			//Combo Categoria
			lCategoria = new JLabel("CATEGORIA");
			lCategoria.setFont(fonte2);
			lCategoria.setBounds(25, 510, 110, 40);
			add(lCategoria);
	
			Object[] categorias = new Object[Categoria.values().length + 1];
			categorias[0] = "TODOS";
			for (int i = 0; i < Categoria.values().length; i++) {
			    categorias[i + 1] = Categoria.values()[i];
			}
	
			cCategoria = new JComboBox(categorias);
			cCategoria.setFont(fonte1);
			cCategoria.setBounds(25, 540, 160, 40);
			add(cCategoria);
	
			bFiltrarVeiculos = new JButton("Filtrar");
			bFiltrarVeiculos.setBounds(210, 540, 160, 40);
			bFiltrarVeiculos.setFont(fonte2);
			bFiltrarVeiculos.addActionListener(this);
			add(bFiltrarVeiculos);
			
			//Campo Data
			lData = new JLabel("Data da Locação");
			lData.setFont(fonte2);
			lData.setBounds(25, 600, 100, 40);
			add(lData);
			
			try {
				MaskFormatter fData = new MaskFormatter("##/##/####");
				fData.setValueContainsLiteralCharacters(false);
				fData.setOverwriteMode(true);
				fData.setPlaceholderCharacter('_');
				tData = new JFormattedTextField(fData);
				tData.setBounds(25, 630, 160, 40);
				tData.setFont(fonte1);
				add(tData);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			//Campo Dias
			lDias = new JLabel("Qtd. Dias");
			lDias.setFont(fonte2);
			lDias.setBounds(210, 600, 100, 40);
			add(lDias);
			
			try {
				MaskFormatter fDias = new MaskFormatter("##");
				fDias.setValueContainsLiteralCharacters(false);
				fDias.setOverwriteMode(true);
				fDias.setPlaceholderCharacter('_');
				tDias = new JFormattedTextField(fDias);
				tDias.setBounds(210, 630, 160, 40);
				tDias.setFont(fonte1);
				add(tDias);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			//Botão Locar
			bLocar = new JButton("Locar");
			bLocar.setBounds(25, 720, 350, 40);
			bLocar.setFont(fonte2);
			bLocar.addActionListener(this);
			add(bLocar);
			
			
			//Label Tabela Clientes
			lTabelaClientes = new JLabel("CLIENTES APTOS");
			lTabelaClientes.setFont(fonte3);
			lTabelaClientes.setBounds(430, 15, 400, 40);
			add(lTabelaClientes);
	
			atualizarClientesAptos();
			
			tabelaClientesAptos = new Tabela<Cliente>(PainelCriarClientes.parametros, clientesAptos);
			//Configurar posição e aparência da tabela Clientes
			painelTabelaClientes = new JScrollPane();
			painelTabelaClientes.setBounds(425, 50, 750, 320);
			tabelaClientes = new JTable(tabelaClientesAptos);
			tabelaClientes.setBounds(0, 0, painelTabelaClientes.getWidth(), painelTabelaClientes.getHeight());
			tabelaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tabelaClientes.setFont(fonte1);
			tabelaClientes.setRowHeight(30);
			tabelaClientes.getColumnModel().getColumn(0).setPreferredWidth(30);
			tabelaClientes.getColumnModel().getColumn(1).setPreferredWidth(30);
			tabelaClientes.getColumnModel().getColumn(2).setPreferredWidth(10);
			tabelaClientes.getColumnModel().getColumn(3).setPreferredWidth(45);
	
			tabelaClientes.getColumnModel().getColumn(0).setHeaderValue("NOME");
		    tabelaClientes.getColumnModel().getColumn(1).setHeaderValue("SOBRENOME");
		    tabelaClientes.getColumnModel().getColumn(2).setHeaderValue("RG");
		    tabelaClientes.getColumnModel().getColumn(3).setHeaderValue("CPF");
		    tabelaClientes.getColumnModel().getColumn(4).setHeaderValue("ENDEREÇO");
		    
		    tabelaClientes.setBorder(new LineBorder(Color.BLACK, 1));
			painelTabelaClientes.setViewportView(tabelaClientes);
			add(painelTabelaClientes);
			//Selecionar Cliente
			tabelaClientes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
	            @Override
	            public void valueChanged(ListSelectionEvent e) {
	                // Get the selected row index
	                int selectedRow = tabelaClientes.getSelectedRow();
	
	                // If a row is selected (non-negative index)
	                if (selectedRow != -1) {
	                    // Perform your action when a row is selected
	                	clienteSelecionado = tabelaClientesAptos.getDados().get(selectedRow);

	                }
	            }
	        });
			
			lerVeiculos();
			
			atualizarVeiculosDisponiveis();
			
			lTabelaVeiculos = new JLabel("VEÍCULOS DISPONÍVEIS");
			lTabelaVeiculos.setFont(fonte3);
			lTabelaVeiculos.setBounds(430, 395, 400, 40);
			add(lTabelaVeiculos);
			
			locarVeiculo = new Tabela<Veiculo>(parametros, veiculosDisponiveis);
			//Configurar posição e aparência da tabela Veiculos
			painelTabelaVeiculos = new JScrollPane();
			painelTabelaVeiculos.setBounds(425, 430, 750, 320);
			tabelaVeiculos = new JTable(locarVeiculo);
			tabelaVeiculos.setBounds(0, 0, painelTabelaVeiculos.getWidth(), painelTabelaVeiculos.getHeight());
			tabelaVeiculos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tabelaVeiculos.setFont(fonte1);
			tabelaVeiculos.setRowHeight(30);
			tabelaVeiculos.getColumnModel().getColumn(0).setHeaderValue("PLACA");
			tabelaVeiculos.getColumnModel().getColumn(1).setHeaderValue("MARCA");
		    tabelaVeiculos.getColumnModel().getColumn(2).setHeaderValue("MODELO");
		    tabelaVeiculos.getColumnModel().getColumn(3).setHeaderValue("ANO");
		    tabelaVeiculos.getColumnModel().getColumn(4).setHeaderValue("PREÇO DIÁRIA");
		    
		    tabelaVeiculos.setBorder(new LineBorder(Color.BLACK, 1));
			painelTabelaVeiculos.setViewportView(tabelaVeiculos);
			add(painelTabelaVeiculos);
			//Selecionar Veículo
			tabelaVeiculos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			    @Override
			    public void valueChanged(ListSelectionEvent e) {
			        int selectedRow = tabelaVeiculos.getSelectedRow();

			        if (selectedRow != -1) {
			        	veiculoSelecionado = locarVeiculo.getDados().get(selectedRow);

			        }
			    }
			});


		}	
		
		private void confirmarELocar(){
			if(clienteSelecionado == null)
				JOptionPane.showMessageDialog(null,"Selecione um cliente",  "Operação Não Concluida", JOptionPane.WARNING_MESSAGE);
			else if(veiculoSelecionado == null)
				JOptionPane.showMessageDialog(null,"Selecione um veículo",  "Operação Não Concluida", JOptionPane.WARNING_MESSAGE);
			else if(!dataValida())
				JOptionPane.showMessageDialog(null,"Data inválida",  "Operação Não Concluida", JOptionPane.WARNING_MESSAGE);
			else if(!diasValidos())
				System.out.println();
			else {
				String[] dataStr = tData.getText().split("/");
				int dia = Integer.parseInt(dataStr[0]);
				int mes = Integer.parseInt(dataStr[1]);
				int ano = Integer.parseInt(dataStr[2]);
				Calendar data = Calendar.getInstance();
				data.set(ano, mes - 1, dia);
				veiculoSelecionado.locar(Integer.parseInt(tDias.getText().trim()), data, clienteSelecionado);
				clienteSelecionado = null;
				veiculoSelecionado = null;
				//Atualizar lista com veículos disponíveis e as tabelas que a utilizam
				atualizarVeiculosDisponiveis();
				PainelLocarVeiculo.locarVeiculo.atualizarTabela(veiculosDisponiveis);
				PainelVenderVeiculo.venderVeiculo.atualizarTabela(veiculosDisponiveis);
				//Atualizar lista com veículos locados e a tabela que a utiliza
				atualizarVeiculosLocados();
				PainelDevolverVeiculo.devolverVeiculo.atualizarTabela(veiculosLocados);
				//Atualizar lista de clientes aptos a locar um veículo
				atualizarClientesAptos();
				tabelaClientesAptos.atualizarTabela(clientesAptos);
				
				JOptionPane.showMessageDialog(null,"Veículo locado com sucesso",  "Sucesso", JOptionPane.DEFAULT_OPTION);
			}
		}
		
		private boolean diasValidos() {
			if(tDias.getText().trim().replaceAll("_", "").isEmpty()) {
				JOptionPane.showMessageDialog(null,"Insira o número de dias\n(máx: 29)",  "Operação Não Concluida", JOptionPane.WARNING_MESSAGE);
				return false;
			}else if(Integer.parseInt(tDias.getText().replaceAll("_", "")) > 29) {
				JOptionPane.showMessageDialog(null,"Número máximo de\ndias permitidos: 29",  "Operação Não Concluida", JOptionPane.WARNING_MESSAGE);
				return false;
			}else
				return true;
				
		}
		
		private boolean dataValida(){	//Validar data
			
			if(tData.getText().replaceAll("[_/]", "").trim().isEmpty())
				return false;
			
			String[] dataStr = tData.getText().split("/");
			int dia = Integer.parseInt(dataStr[0]);
			int mes = Integer.parseInt(dataStr[1]);
			int ano = Integer.parseInt(dataStr[2]);
			
			if(ano != 2025)			//Da pra botar tudo num if só,
				return false;		//mas a leitura fica pior
			else if(mes < 1 || mes > 12)
				return false;
			else if(dia < 1 || ((mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) && dia > 31))
				return false;
			else if(mes == 2 && dia > 28)
				return false;
			else if(dia > 30)
				return false;
			else
				return true;
		}
		
	
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == bFiltrarClientes)
				filtrarClientes();
			else if(e.getSource() == bLimpar)
				limparFiltrosClientes();
			else if(e.getSource() == bFiltrarVeiculos)
				filtrarVeiculos();
			else if(e.getSource() == bLocar)
				confirmarELocar();
		}
	
		private void filtrarClientes() {
		    String textoNome = tNome.getText().trim().toUpperCase();
		    String textoSobrenome = tSobrenome.getText().trim().toUpperCase();
		    String textoCpf = tCpf.getText().trim().replaceAll("[.-]", "");
	
		    List<Cliente> resultado = new ArrayList<>();
	
		    for (Cliente cliente : clientesAptos) {
		        if (rNome.isSelected() && cliente.getNome().toUpperCase().contains(textoNome)) {
		            resultado.add(cliente);
		        } else if (rSobrenome.isSelected() && cliente.getSobrenome().toUpperCase().contains(textoSobrenome)) {
		            resultado.add(cliente);
		        } else if (rCpf.isSelected() && Long.toString(cliente.getCpf()).contains(textoCpf)) {
		            resultado.add(cliente);
		        }
		    }
	
		    tabelaClientesAptos.atualizarTabela(resultado);
		}
		
		private void limparFiltrosClientes() {
		    tNome.setText("");
		    tSobrenome.setText("");
		    tCpf.setText("");
		    rNome.setSelected(true);
		    
		    clienteSelecionado = null;
	
		    tabelaClientesAptos.atualizarTabela(clientesAptos);
		}
	
		private void filtrarVeiculos() {
		    String tipoSelecionado = (String) cTipo.getSelectedItem();
		    Object marcaSelecionada = cMarca.getSelectedItem();
		    Object categoriaSelecionada = cCategoria.getSelectedItem();
	
		    List<Veiculo> veiculosFiltrados = new ArrayList<>();
	
		    for (Veiculo v : veiculos) {
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
	
		    locarVeiculo.atualizarTabela(veiculosFiltrados);
		    if(tipoSelecionado.toUpperCase().equals("TODOS") && marcaSelecionada.toString().equals("TODOS") && categoriaSelecionada.toString().equals("TODOS"))
		    	veiculoSelecionado = null;
		}
	
		public static void atualizarClientesAptos() {
			clientesAptos.clear();
			clientesAptos.addAll(PainelCriarClientes.clientes);
			for(Cliente c : PainelCriarClientes.clientes) {
				for(Veiculo v : veiculosLocados){
					if(c.equals(v.getLocacao().getCliente())){
						clientesAptos.remove(c);
					}
				}
			}
		}
		
		public static void atualizarVeiculosDisponiveis() {
			//Filtra a lista Veiculos e armazena os
			//resultados na lista VeiculosDisponiveis
			veiculosDisponiveis.clear();
			for(Veiculo v : veiculos){
				if(v.getEstado() == Estado.DISPONIVEL){
					veiculosDisponiveis.add(v);
				}
			}
		}
		
		public static void atualizarVeiculosLocados() {
			veiculosLocados.clear();
			for(Veiculo v : veiculos){
				if(v.getEstado() == Estado.LOCADO){
					veiculosLocados.add(v);
				}
			}
		}
		
		private void lerVeiculos(){		//Adicionar veículos à lista a partir
			try{						//de um arquivo de texto
				File saveFile = new File("src/veiculos.txt");
				Scanner scanner = new Scanner(saveFile);
				
				while(scanner.hasNextLine()){
					String txtVeiculos = scanner.nextLine();
					String[] dadosVeiculos = txtVeiculos.split(";");
	
					String tipo = dadosVeiculos[0];
					Marca marca = Marca.valueOf(dadosVeiculos[1]);
					Estado estado = Estado.valueOf(dadosVeiculos[2]);
					Categoria categoria = Categoria.valueOf(dadosVeiculos[3]);
					String modeloStr = dadosVeiculos[4];
					String locacaoStr = dadosVeiculos[5];
					double valorCompra = Double.parseDouble(dadosVeiculos[6]);
					String placa = dadosVeiculos[7];
					int ano = Integer.parseInt(dadosVeiculos[8]);
					
					Locacao locacao = null;
	
					switch(tipo){
						case "Automovel":
							veiculos.add(new Automovel(
								marca,
								estado,
								categoria,
								ModeloAutomovel.valueOf(modeloStr),
								locacao,
								valorCompra,
								placa,
								ano
							));
							break;
						case "Motocicleta":
							veiculos.add(new model.Motocicleta(
								marca,
								estado,
								categoria,
								enums.ModeloMotocicleta.valueOf(modeloStr),
								locacao,
								valorCompra,
								placa,
								ano
							));
							break;
						case "Van":
							veiculos.add(new model.Van(
								marca,
								estado,
								categoria,
								enums.ModeloVan.valueOf(modeloStr),
								locacao,
								valorCompra,
								placa,
								ano
							));
							break;
					}
				}
				
				scanner.close();
			}catch (FileNotFoundException e){
				System.out.println("Arquivo não encontrado.");
				e.printStackTrace();
			}
		}
	}
