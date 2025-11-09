package swing;

import controller.ClienteController;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
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

public class PainelCriarClientes extends JPanel implements ActionListener {

    private JTextField tNome, tSobrenome, tEndereco;
    private JFormattedTextField tRg, tCpf;
    private JButton bCriar, bAtualizar, bExcluir, bLimpar, bSalvar, bCancelar;
    private JLabel lNome, lSobrenome, lRg, lCpf, lEndereco, lClientes;
    private JScrollPane painelTabela;
    private JTable tabela;
    
    private Font fonte1 = new Font("Arial", 0, 18);
    private Font fonte2 = new Font("Arial", Font.BOLD, 14);
    private Font fonte3 = new Font("Arial", Font.BOLD, 18);

    private Tabela<Cliente> criarCliente;
    private List<Cliente> clientes = new ArrayList<>();
	private static String[] parametros = {"NOME", "SOBRENOME", "RG", "CPF", "ENDEREÇO"};

    private ClienteController controller;
    private PainelAbas painelAbas;

    public PainelCriarClientes(PainelAbas painelAbas) {
        this.painelAbas = painelAbas;
        setLayout(null);
        this.controller = new ClienteController(this);
        inicializarComponentes();
        controller.carregarClientes();
    }
    
    public void notificarAtualizacaoGeral() {
        painelAbas.atualizarTodosOsPaineis();
    }

    private void inicializarComponentes() {
        lNome = new JLabel("NOME");
        lNome.setFont(fonte2);
        lNome.setBounds(50, 30, 100, 40);
        add(lNome);
        tNome = new JTextField();
        tNome.setBounds(50, 60, 350, 40);
        tNome.setFont(fonte1);
        add(tNome);

        lSobrenome = new JLabel("SOBRENOME");
        lSobrenome.setFont(fonte2);
        lSobrenome.setBounds(50, 120, 100, 40);
        add(lSobrenome);
        tSobrenome = new JTextField();
        tSobrenome.setBounds(50, 150, 350, 40);
        tSobrenome.setFont(fonte1);
        add(tSobrenome);

        lRg = new JLabel("RG");
        lRg.setFont(fonte2);
        lRg.setBounds(50, 210, 100, 40);
        add(lRg);
        try {
            MaskFormatter fRg = new MaskFormatter("##.###.###-#");
            fRg.setValueContainsLiteralCharacters(false);
            fRg.setOverwriteMode(true);
            fRg.setPlaceholderCharacter('_');
            tRg = new JFormattedTextField(fRg);
            tRg.setBounds(50, 240, 150, 40);
            tRg.setFont(fonte1);
            add(tRg);
        } catch (ParseException e) { e.printStackTrace(); }

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
        } catch (ParseException e) { e.printStackTrace(); }

        lEndereco = new JLabel("ENDEREÇO");
        lEndereco.setFont(fonte2);
        lEndereco.setBounds(50, 300, 100, 40);
        add(lEndereco);
        tEndereco = new JTextField();
        tEndereco.setBounds(50, 330, 350, 40);
        tEndereco.setFont(fonte1);
        add(tEndereco);

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

        lClientes = new JLabel("CLIENTES");
        lClientes.setFont(fonte3);
        lClientes.setBounds(430, 15, 100, 40);
        add(lClientes);

        clientes = controller.getClientes(); // Pega a lista inicial do controller
        criarCliente = new Tabela<Cliente>(parametros, clientes);
        
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
        tabela.setBorder(new LineBorder(Color.BLACK, 1));
        
        tabela.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = tabela.getSelectedRow();
                    if (selectedRow != -1) {
                        controller.selecionarClienteNaTabela(selectedRow);
                    }
                }
            }
        });
        
        painelTabela.setViewportView(tabela);
        add(painelTabela);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bCriar) {
            controller.criarCliente();
        } else if (e.getSource() == bAtualizar) {
            controller.alternarParaModoEdicao();
        } else if (e.getSource() == bExcluir) {
            controller.excluirCliente();
        } else if (e.getSource() == bLimpar) {
            controller.limparSelecao();
        } else if (e.getSource() == bSalvar) {
            controller.salvarAtualizacaoCliente();
        } else if (e.getSource() == bCancelar) {
            controller.cancelarEdicao();
        }
    }
    
    public void preencherCampos(Cliente cliente) {
        tNome.setText(cliente.getNome());
        tSobrenome.setText(cliente.getSobrenome());
        tRg.setText(String.format("%09d", cliente.getRg()));
        tCpf.setText(String.format("%011d", cliente.getCpf()));
        tEndereco.setText(cliente.getEndereco());
        liberarCampos(false);
    }

    public void limparCampos() {
        tNome.setText("");
        tSobrenome.setText("");
        tRg.setText("");
        tCpf.setText("");
        tEndereco.setText("");
    }
    
    public void liberarCampos(boolean liberar) {
        tNome.setEditable(liberar);
        tSobrenome.setEditable(liberar);
        tRg.setEditable(liberar);
        tCpf.setEditable(liberar);
        tEndereco.setEditable(liberar);
    }

    public void desabilitarCpf() {
        tCpf.setEditable(false);
    }
    
    public void alternarBotoesSelecao(boolean selecionado) {
        bCriar.setEnabled(!selecionado);
        bAtualizar.setEnabled(selecionado);
        bExcluir.setEnabled(selecionado);
    }

    public void alternarModoEdicao() {
        bCriar.setVisible(false);
        bAtualizar.setVisible(false);
        bExcluir.setVisible(false);
        bLimpar.setVisible(false);
        bSalvar.setVisible(true);
        bCancelar.setVisible(true);
    }
    
    public void alternarModoPadrao() {
        bCriar.setVisible(true);
        bAtualizar.setVisible(true);
        bExcluir.setVisible(true);
        bLimpar.setVisible(true);
        bSalvar.setVisible(false);
        bCancelar.setVisible(false);
    }
    
    public void limparTudo() {
        tabela.clearSelection();
        limparCampos();
        liberarCampos(true);
        alternarBotoesSelecao(false);
    }

    public void atualizarTabela(List<Cliente> novaLista) {
        clientes = novaLista; // Atualiza a lista local da view
        criarCliente.atualizarTabela(novaLista);
    }

    public void mostrarMensagem(String titulo, String mensagem, int tipo) {
        JOptionPane.showMessageDialog(this, mensagem, titulo, tipo);
    }
    
    public int pedirConfirmacao(String titulo, String mensagem) {
        return JOptionPane.showConfirmDialog(this, mensagem, titulo, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    }

    public String getNome() { return tNome.getText().trim(); }
    public String getSobrenome() { return tSobrenome.getText().trim(); }
    public String getRg() { return tRg.getText().trim(); }
    public String getCpf() { return tCpf.getText().trim(); }
    public String getEndereco() { return tEndereco.getText().trim(); }
}