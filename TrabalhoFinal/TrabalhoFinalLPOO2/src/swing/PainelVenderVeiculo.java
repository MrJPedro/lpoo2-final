package swing;

import controller.VenderVeiculoController;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
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
import enums.Marca;
import misc.Veiculo;

public class PainelVenderVeiculo extends JPanel implements ActionListener {

    private JComboBox<Object> cMarca, cCategoria;
    private JComboBox<String> cTipo;
    private JLabel lMarca, lTipo, lCategoria, lTabela;
    private JButton bFiltrarVeiculos, bVenderVeiculo;
    private JTable tabelaVeiculos;
    private JScrollPane painelTabelaVeiculos;

    private Font fonte1 = new Font("Arial", 0, 18);
    private Font fonte2 = new Font("Arial", Font.BOLD, 14);
    private Font fonte3 = new Font("Arial", Font.BOLD, 18);

    private Tabela<Veiculo> venderVeiculo;
    private String[] parametros = {"PLACA", "MARCA", "MODELO", "ANO", "PRECO VENDA"};

    private VenderVeiculoController controller;
    private PainelAbas painelAbas;

    public PainelVenderVeiculo(PainelAbas painelAbas) {
        this.painelAbas = painelAbas;
        setLayout(null);
        inicializarComponentes();
        this.controller = new VenderVeiculoController(this);
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
        if (e.getSource() == bFiltrarVeiculos) {
            controller.filtrarVeiculos();
        } else if (e.getSource() == bVenderVeiculo) {
            controller.venderVeiculo();
        }
    }

    private void inicializarComponentes() {
        lTipo = new JLabel("TIPO");
        lTipo.setFont(fonte2);
        lTipo.setBounds(25, 30, 110, 40);
        add(lTipo);
        cTipo = new JComboBox<>(new String[]{"Todos", "Automovel", "Motocicleta", "Van"});
        cTipo.setFont(fonte1);
        cTipo.setBounds(25, 60, 160, 40);
        add(cTipo);

        lMarca = new JLabel("MARCA");
        lMarca.setFont(fonte2);
        lMarca.setBounds(210, 30, 100, 40);
        add(lMarca);
        Object[] marcas = new Object[Marca.values().length + 1];
        marcas[0] = "TODOS";
        System.arraycopy(Marca.values(), 0, marcas, 1, Marca.values().length);
        cMarca = new JComboBox<>(marcas);
        cMarca.setFont(fonte1);
        cMarca.setBounds(210, 60, 160, 40);
        add(cMarca);

        lCategoria = new JLabel("CATEGORIA");
        lCategoria.setFont(fonte2);
        lCategoria.setBounds(25, 120, 110, 40);
        add(lCategoria);
        Object[] categorias = new Object[Categoria.values().length + 1];
        categorias[0] = "TODOS";
        System.arraycopy(Categoria.values(), 0, categorias, 1, Categoria.values().length);
        cCategoria = new JComboBox<>(categorias);
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

        lTabela = new JLabel("VEÍCULOS DISPONÍVEIS PARA VENDA");
        lTabela.setFont(fonte3);
        lTabela.setBounds(625, 15, 400, 40);
        add(lTabela);

        venderVeiculo = new Tabela<>(parametros, null);
        painelTabelaVeiculos = new JScrollPane();
        painelTabelaVeiculos.setBounds(425, 50, 750, 700);
        tabelaVeiculos = new JTable(venderVeiculo);
        configurarTabela();
        painelTabelaVeiculos.setViewportView(tabelaVeiculos);
        add(painelTabelaVeiculos);
    }
    
    private void configurarTabela() {
        tabelaVeiculos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaVeiculos.setFont(fonte1);
        tabelaVeiculos.setRowHeight(30);
        tabelaVeiculos.setBorder(new LineBorder(Color.BLACK, 1));

        tabelaVeiculos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = tabelaVeiculos.getSelectedRow();
                    if (selectedRow != -1 && !venderVeiculo.getDados().isEmpty()) {
                        Veiculo v = venderVeiculo.getDados().get(selectedRow);
                        controller.setVeiculoSelecionado(v);
                    }
                }
            }
        });
    }

    public void atualizarTabela(List<Veiculo> veiculos) {
        venderVeiculo.atualizarTabela(veiculos);
    }
    
    public void mostrarMensagem(String titulo, String mensagem, int tipo) {
        JOptionPane.showMessageDialog(this, mensagem, titulo, tipo);
    }
    
    public int pedirConfirmacao(String titulo, String mensagem) {
        return JOptionPane.showConfirmDialog(this, mensagem, titulo, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }
    
    public void limparSelecaoTabela() {
        tabelaVeiculos.clearSelection();
    }
    
    public String getFiltroTipo() { return (String) cTipo.getSelectedItem(); }
    public Object getFiltroMarca() { return cMarca.getSelectedItem(); }
    public Object getFiltroCategoria() { return cCategoria.getSelectedItem(); }
}