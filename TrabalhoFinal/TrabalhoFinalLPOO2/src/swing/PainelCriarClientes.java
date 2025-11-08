package swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.DesignMode;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.MaskFormatter;

import model.Cliente;

public class PainelCriarClientes extends JPanel implements ActionListener{
	//Declaração dos elementos SWING
	JTextField tNome, tSobrenome, tEndereco;
	JFormattedTextField tRg, tCpf;
	JButton bCriar, bAtualizar, bExcluir, bLimpar, bSalvar, bCancelar;
	JLabel lNome, lSobrenome, lRg, lCpf, lEndereco, lClientes;
	
	JScrollPane painelTabela;
	
	Font fonte1 = new Font("Arial", 0, 18);
	Font fonte2 = new Font("Arial", Font.BOLD, 14);
	Font fonte3 = new Font("Arial", Font.BOLD, 18);
	
	Cliente clienteSelecionado = null;
	
	JTable tabela;
	/*
	 * Criação da tabela CLIENTES, esse trecho
	 * vai ser referenciado posteriormente
	 * sempre que um novo painel quiser usar
	 * a tabela CLIENTES.
	 * 
	 * A o conteúdo da tabela é o mesmo em
	 * qualquer painel, e está sempre sincronizado
	 * entre eles.
	 * */
	public static Tabela<Cliente> criarCliente;
	public static List<Cliente> clientes = new ArrayList<>();
	public static String[] parametros = {"NOME", "SOBRENOME", "RG", "CPF", "ENDEREÇO"};
	
	PainelCriarClientes(){
		setLayout(null);
		//Campo Nome
		lNome = new JLabel("NOME");
		lNome.setFont(fonte2);
		lNome.setBounds(50, 30, 100, 40);
		add(lNome);
		
		tNome = new JTextField();
		tNome.setBounds(50, 60, 350, 40);
		tNome.setFont(fonte1);
		add(tNome);
		
		//Campo Sobrenome
		lSobrenome = new JLabel("SOBRENOME");
		lSobrenome.setFont(fonte2);
		lSobrenome.setBounds(50, 120, 100, 40);
		add(lSobrenome);
		
		tSobrenome = new JTextField();
		tSobrenome.setBounds(50, 150, 350, 40);
		tSobrenome.setFont(fonte1);
		add(tSobrenome);
		
		//Campo RG
		lRg = new JLabel("RG");
		lRg.setFont(fonte2);
		lRg.setBounds(50, 210, 100, 40);
		add(lRg);
		//Aplicação da formatação do campo RG
		try {
			MaskFormatter fRg = new MaskFormatter("##.###.###-#");
			fRg.setValueContainsLiteralCharacters(false);
			fRg.setOverwriteMode(true);
			fRg.setPlaceholderCharacter('_');
			
			tRg = new JFormattedTextField(fRg);
			tRg.setBounds(50, 240, 150, 40);
			tRg.setFont(fonte1);
			add(tRg);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//Campo CPF
		lCpf= new JLabel("CPF");
		lCpf.setFont(fonte2);
		lCpf.setBounds(250, 210, 100, 40);
		add(lCpf);
		
		try {
			MaskFormatter fCpf = new MaskFormatter("###.###.###-##");
			fCpf.setValueContainsLiteralCharacters(false);
			fCpf.setOverwriteMode(true);
			fCpf.setPlaceholderCharacter('_');

			tCpf = new JFormattedTextField(fCpf);
			tCpf.setBounds(250, 240, 150, 40);
			tCpf.setFont(fonte1);
			add(tCpf);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//Campo Endereco
		lEndereco = new JLabel("ENDEREÇO");
		lEndereco.setFont(fonte2);
		lEndereco.setBounds(50, 300, 100, 40);
		add(lEndereco);
		
		tEndereco = new JTextField();
		tEndereco.setBounds(50, 330, 350, 40);
		tEndereco.setFont(fonte1);
		add(tEndereco);
		
		//Botões
		bCriar = new JButton("Cadastrar");
		bCriar.setBounds(50, 420, 80, 40);
		bCriar.setMargin(new Insets(0, 0, 0, 0));
		bCriar.setFont(fonte2);
		bCriar.addActionListener(this);
		add(bCriar);
		
		bAtualizar = new JButton("Atualizar");
		bAtualizar.setBounds(140, 420, 80, 40);
		bAtualizar.setMargin(new Insets(0, 0, 0, 0));
		bAtualizar.setFont(fonte2);
		bAtualizar.setEnabled(false);
		bAtualizar.addActionListener(this);
		add(bAtualizar);
		//Oculto
		bSalvar = new JButton("Salvar");
		bSalvar.setBounds(140, 420, 80, 40);
		bSalvar.setMargin(new Insets(0, 0, 0, 0));
		bSalvar.setFont(fonte2);
		bSalvar.setVisible(false);
		bSalvar.addActionListener(this);
		add(bSalvar);
		
		bExcluir = new JButton("Excluir");
		bExcluir.setBounds(230, 420, 80, 40);
		bExcluir.setMargin(new Insets(0, 0, 0, 0));
		bExcluir.setFont(fonte2);
		bExcluir.setEnabled(false);
		bExcluir.addActionListener(this);
		add(bExcluir);
		//Oculto
		bCancelar = new JButton("Cancelar");
		bCancelar.setBounds(230, 420, 80, 40);
		bCancelar.setMargin(new Insets(0, 0, 0, 0));
		bCancelar.setFont(fonte2);
		bCancelar.setVisible(false);
		bCancelar.addActionListener(this);
		add(bCancelar);
		
		bLimpar = new JButton("Limpar");
		bLimpar.setBounds(320, 420, 80, 40);
		bLimpar.setMargin(new Insets(0, 0, 0, 0));
		bLimpar.setFont(fonte2);
		bLimpar.addActionListener(this);
		add(bLimpar);
		
		
		//Adicionar clientes à lista
		lerClientes();
		
		//Tabela
		lClientes = new JLabel("CLIENTES");
		lClientes.setFont(fonte3);
		lClientes.setBounds(430, 15, 100, 40);
		add(lClientes);
		
		criarCliente = new Tabela<Cliente>(parametros, clientes);
		//Configurar posição e aparência da tabela
		painelTabela = new JScrollPane();
		painelTabela.setBounds(425, 50, 750, 700);
		tabela = new JTable(criarCliente);
		tabela.setBounds(0, 0, painelTabela.getWidth(), painelTabela.getHeight());
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabela.setFont(fonte1);
		tabela.setRowHeight(30);
		tabela.getColumnModel().getColumn(0).setPreferredWidth(30);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(30);
		tabela.getColumnModel().getColumn(2).setPreferredWidth(10);
		tabela.getColumnModel().getColumn(3).setPreferredWidth(45);

		tabela.getColumnModel().getColumn(0).setHeaderValue("NOME");
	    tabela.getColumnModel().getColumn(1).setHeaderValue("SOBRENOME");
	    tabela.getColumnModel().getColumn(2).setHeaderValue("RG");
	    tabela.getColumnModel().getColumn(3).setHeaderValue("CPF");
	    tabela.getColumnModel().getColumn(4).setHeaderValue("ENDEREÇO");
	    
	    //ChatGPT (nunca usei abstractTableModel, não sei como funciona a seleção de itens)
	    tabela.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Get the selected row index
                int selectedRow = tabela.getSelectedRow();

                // If a row is selected (non-negative index)
                if (selectedRow != -1) {
                    // Perform your action when a row is selected
                    clienteSelecionado = clientes.get(selectedRow);
                    preencherCampos();
                    bloquearCampos();
                    bCriar.setEnabled(false);
                    bAtualizar.setEnabled(true);
                    bExcluir.setEnabled(true);
                }
            }
        });
		
		tabela.setBorder(new LineBorder(Color.BLACK, 1));
		painelTabela.setViewportView(tabela);
		add(painelTabela);
	}

	@Override	//Capturar ação dos botões
	public void actionPerformed(ActionEvent e) {
				
		if(e.getSource() == bCriar)
			validarECriar();
		else if(e.getSource() == bAtualizar)
			atualizar();
		else if(e.getSource() == bExcluir)
			verificarEExcluir();
		else if(e.getSource() == bLimpar)
			limpar();
		else if(e.getSource() == bSalvar)
			salvar();
		else if(e.getSource() == bCancelar)
			cancelar();
	}
	
	//----------Funções----------
	//(EXCLUIR -> NÃO) PRECISA ARRUMAR OS BOTÕES!!!!!!!!!!!!!!!!!!!
	
	private void bloquearCampos(){		//Travar todos os campos
		tNome.setEditable(false);		//de texto para edição
		tSobrenome.setEditable(false);
		tRg.setEditable(false);
		tCpf.setEditable(false);
		tEndereco.setEditable(false);
	}
	
	private void liberarCampos(){		//Destravar todos os
		tNome.setEditable(true);		//campos de texto para
		tSobrenome.setEditable(true);	//edição
		tRg.setEditable(true);
		tCpf.setEditable(true);
		tEndereco.setEditable(true);
	}
	
	private void preencherCampos() {								//Preencher todos os campos
		tNome.setText(clienteSelecionado.getNome());				//de texto com os dados dos
		tSobrenome.setText(clienteSelecionado.getSobrenome());		//clientes
		tRg.setText(String.format("%09d", clienteSelecionado.getRg()));
		tCpf.setText(String.format("%011d", clienteSelecionado.getCpf()));
		tEndereco.setText(clienteSelecionado.getEndereco());
	}
	
	private void limparCampos() {	//Limpar os campos de texto
		tNome.setText("");			//sem alterar os dados dos
		tSobrenome.setText("");		//clientes
		tRg.setText("");
		tCpf.setText("");
		tEndereco.setText("");
	}
	
	private void limpar() {
		tabela.clearSelection();
		bCriar.setEnabled(true);
		bAtualizar.setEnabled(false);
		bExcluir.setEnabled(false);
		
		clienteSelecionado = null;
		
		liberarCampos();
		limparCampos();
	}

	private void validarECriar(){	//Validar os dados nos campos de texto
		if(checarValidade()) {		//e criar um cliente com esses dados
			clientes.add(new Cliente(tNome.getText().trim(), tSobrenome.getText().trim(), Integer.parseInt(tRg.getText().trim().replaceAll("[.-]", "")), Long.parseLong(tCpf.getText().trim().replaceAll("[.-]", "")), tEndereco.getText().trim()));
			criarCliente.fireTableDataChanged();
			JOptionPane.showMessageDialog(null,"Cliente Cadastrado com Sucesso",  "Sucesso", JOptionPane.DEFAULT_OPTION);
			limparCampos();
			clienteSelecionado = null;
			PainelLocarVeiculo.atualizarClientesAptos();
			PainelLocarVeiculo.tabelaClientesAptos.atualizarTabela(PainelLocarVeiculo.clientesAptos);
		}
	}
	
	private void verificarEExcluir() {		//Excluir cliente após confirmação
		int confirmacao = JOptionPane.showConfirmDialog(null, "Confirmar Exclusão?\nEssa ação não pode ser desfeita.", "Confirmar Exclusão", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE);
		if(confirmacao == 0) {
			clientes.remove(clienteSelecionado);
			clienteSelecionado = null;
			criarCliente.fireTableDataChanged();
			
			bCriar.setEnabled(true);
			bExcluir.setEnabled(false);
			bAtualizar.setEnabled(false);
			
			limparCampos();
			liberarCampos();
			
			PainelLocarVeiculo.atualizarClientesAptos();
			PainelLocarVeiculo.tabelaClientesAptos.atualizarTabela(PainelLocarVeiculo.clientesAptos);
		}else {
			bAtualizar.setEnabled(true);
			bExcluir.setEnabled(true);
		}
	}
	
	private void atualizar() {			//Mudar os botões para
		bCriar.setVisible(false);		//atualizar os clientes
		bAtualizar.setVisible(false);	//(entrar no modo de
		bExcluir.setVisible(false);		//edição do cliente)
		bLimpar.setVisible(false);
		bSalvar.setVisible(true);
		bCancelar.setVisible(true);
		
		liberarCampos();
	}
	
	private void salvar() {			//Validar os dados nos campos de
		if(checarValidade()) {		//texto e atualizar o cliente
			clienteSelecionado.setNome(tNome.getText().trim());
			clienteSelecionado.setSobrenome(tSobrenome.getText().trim());
			clienteSelecionado.setRg(Integer.parseInt(tRg.getText().trim().replaceAll("[._-]", "")));
			clienteSelecionado.setCpf(Long.parseLong(tCpf.getText().trim().replaceAll("[._-]", "")));
			clienteSelecionado.setEndereco(tEndereco.getText().trim());
			criarCliente.fireTableDataChanged();
			JOptionPane.showMessageDialog(null,"Cliente atualizado com Sucesso",  "Sucesso", JOptionPane.DEFAULT_OPTION);
			
			cancelar();
			liberarCampos();
		}
	}
	
	private void cancelar() {		//Sair do modo de edição
		bCriar.setVisible(true);	//dos dados do cliente
		bExcluir.setVisible(true);
		bLimpar.setVisible(true);
		bSalvar.setVisible(false);
		bCancelar.setVisible(false);
		bAtualizar.setVisible(true);
		bAtualizar.setEnabled(false);
		bCriar.setEnabled(true);
		bExcluir.setEnabled(false);
		
		clienteSelecionado = null;
		tabela.clearSelection();
		
		limparCampos();
		bloquearCampos();
	}
	
	private boolean checarValidade() {
		Boolean valido = false;
		if(tNome.getText().trim().isEmpty())
			JOptionPane.showMessageDialog(null,"Preencha o campo NOME.",  "Operação Não Concluida", JOptionPane.WARNING_MESSAGE);
		else if(tSobrenome.getText().trim().isEmpty())
			JOptionPane.showMessageDialog(null,"Preencha o campo SOBRENOME.",  "Operação Não Concluida", JOptionPane.WARNING_MESSAGE);
		else if(tRg.getText().trim().replaceAll("[._-]", "").isEmpty())
			JOptionPane.showMessageDialog(null,"Preencha o campo RG",  "Operação Não Concluida", JOptionPane.WARNING_MESSAGE);
		else if(tCpf.getText().trim().replaceAll("[._-]", "").isEmpty())
			JOptionPane.showMessageDialog(null, "Preencha o campo CPF",  "Operação Não Concluida",JOptionPane.WARNING_MESSAGE);
		else if(tEndereco.getText().trim().isEmpty())
			JOptionPane.showMessageDialog(null,"Preencha o campo ENDEREÇO",  "Operação Não Concluida", JOptionPane.WARNING_MESSAGE);
		else
			valido = true;
		
		return valido;
	}
	
	private void lerClientes(){		//Adicionar clientes à lista a partir
		try{						//de um arquivo de texto
			File saveFile = new File("src/clientes.txt");
			Scanner scanner = new Scanner(saveFile);
			
			while(scanner.hasNextLine()){
				String txtClientes = scanner.nextLine();
				String[] dadosClientes = txtClientes.split(";");
				//Adicionar clientes
				clientes.add(new Cliente(
						dadosClientes[0],
						dadosClientes[1],
						Integer.parseInt(dadosClientes[2]),
						Long.parseLong(dadosClientes[3]),
						dadosClientes[4]
						));
			}
			
			scanner.close();
		}catch (FileNotFoundException e){
			System.out.println("Arquivo não encontrado.");
			e.printStackTrace();
		}
	}
}
