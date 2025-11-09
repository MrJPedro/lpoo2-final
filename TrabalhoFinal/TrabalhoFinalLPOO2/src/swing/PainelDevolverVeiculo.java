package swing;

import controller.DevolverVeiculoController;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
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

public class PainelDevolverVeiculo extends JPanel implements ActionListener {

    private JButton bDevolverVeiculo;
    private JLabel lTabela;
    private JTable tabelaVeiculos;
    private JScrollPane painelTabelaVeiculos;

    private Font fonte1 = new Font("Arial", 0, 18);
    private Font fonte2 = new Font("Arial", Font.BOLD, 14);
    private Font fonte3 = new Font("Arial", Font.BOLD, 18);

    private Tabela<Veiculo> devolverVeiculo;
    private String[] parametros = {"NOME CLIENTE", "PLACA", "MARCA", "MODELO", "ANO", "DATA", "PRECO DIARIA", "DIAS", "VALOR LOCACAO"};

    private DevolverVeiculoController controller;
    private PainelAbas painelAbas;

    public PainelDevolverVeiculo(PainelAbas painelAbas) {
        this.painelAbas = painelAbas;
        setLayout(null);
        inicializarComponentes();
        this.controller = new DevolverVeiculoController(this);
        this.controller.carregarDadosIniciais();
    }

    public void atualizarDados() {
        controller.carregarDadosIniciais();
    }
    
    public void notificarAtualizacaoGeral() {
        painelAbas.atualizarTodosOsPaineis();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bDevolverVeiculo) {
            controller.devolverVeiculo();
        }
    }

    private void inicializarComponentes() {
        bDevolverVeiculo = new JButton("Devolver veículo");
        bDevolverVeiculo.setBounds(300, 675, 600, 75);
        bDevolverVeiculo.setFont(fonte2);
        bDevolverVeiculo.addActionListener(this);
        add(bDevolverVeiculo);

        lTabela = new JLabel("VEÍCULOS LOCADOS");
        lTabela.setFont(fonte3);
        lTabela.setBounds(500, 15, 300, 40);
        add(lTabela);

        devolverVeiculo = new Tabela<>(parametros, null);
        painelTabelaVeiculos = new JScrollPane();
        painelTabelaVeiculos.setBounds(25, 50, 1150, 600);
        tabelaVeiculos = new JTable(devolverVeiculo);
        configurarTabela();
        painelTabelaVeiculos.setViewportView(tabelaVeiculos);
        add(painelTabelaVeiculos);
    }
    
    private void configurarTabela() {
        tabelaVeiculos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaVeiculos.setFont(fonte1);
        tabelaVeiculos.setRowHeight(30);
        tabelaVeiculos.getColumnModel().getColumn(2).setPreferredWidth(120);
        tabelaVeiculos.getColumnModel().getColumn(4).setPreferredWidth(30);
        tabelaVeiculos.getColumnModel().getColumn(7).setPreferredWidth(15);
        tabelaVeiculos.setBorder(new LineBorder(Color.BLACK, 1));

        tabelaVeiculos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = tabelaVeiculos.getSelectedRow();
                    if (selectedRow != -1 && !devolverVeiculo.getDados().isEmpty()) {
                        Veiculo v = devolverVeiculo.getDados().get(selectedRow);
                        controller.setVeiculoSelecionado(v);
                    }
                }
            }
        });
    }

    public void atualizarTabela(List<Veiculo> veiculos) {
        devolverVeiculo.atualizarTabela(veiculos);
    }
    
    public void mostrarMensagem(String titulo, String mensagem, int tipo) {
        JOptionPane.showMessageDialog(this, mensagem, titulo, tipo);
    }
    
    public void limparSelecaoTabela() {
        tabelaVeiculos.clearSelection();
    }
}