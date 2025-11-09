package swing;

import controller.LocarVeiculoController;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.List;
import javax.swing.ButtonGroup;
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
import enums.Marca;
import model.Cliente;
import misc.Veiculo;

public class PainelLocarVeiculo extends JPanel implements ActionListener {

    private JTextField tNome, tSobrenome;
    private JFormattedTextField tCpf, tDias, tData;
    private JComboBox<Object> cMarca;
    private JComboBox<String> cTipo;
    private JComboBox<Object> cCategoria;
    private JLabel lNome, lSobrenome, lCpf, lMarca, lTipo, lCategoria, lData, lDias, lTabelaClientes, lTabelaVeiculos, lFiltroClientes, lFiltroVeiculos, lRadio;
    private JRadioButton rNome, rSobrenome, rCpf;
    private ButtonGroup gFiltro;
    private JButton bFiltrarClientes, bLimpar, bFiltrarVeiculos, bLocar;
    private JScrollPane painelTabelaClientes, painelTabelaVeiculos;
    private JTable tabelaClientes, tabelaVeiculos;

    private Tabela<Veiculo> locarVeiculo;
    private Tabela<Cliente> tabelaClientesAptos;
    private String[] parametrosVeiculos = {"PLACA", "MARCA", "MODELO", "ANO", "PRECO DIARIA"};
    private String[] parametrosClientes = {"NOME", "SOBRENOME", "RG", "CPF", "ENDEREÇO"};

    private Font fonte1 = new Font("Arial", 0, 18);
    private Font fonte2 = new Font("Arial", Font.BOLD, 14);
    private Font fonte3 = new Font("Arial", Font.BOLD, 18);
    private Font fonte4 = new Font("Arial", Font.BOLD, 12);

    private LocarVeiculoController controller;
    private PainelAbas painelAbas;

    public PainelLocarVeiculo(PainelAbas painelAbas) {
        this.painelAbas = painelAbas;
        setLayout(null);
        inicializarComponentes();
        this.controller = new LocarVeiculoController(this);
        this.controller.carregarDadosIniciais();
    }

    public void atualizarDados() {
        this.controller.carregarDadosIniciais();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bFiltrarClientes) {
            controller.filtrarClientes();
        } else if (e.getSource() == bLimpar) {
            controller.limparFiltrosClientes();
        } else if (e.getSource() == bFiltrarVeiculos) {
            controller.filtrarVeiculos();
        } else if (e.getSource() == bLocar) {
            controller.locarVeiculo();
        }
    }

    private void inicializarComponentes() {
        lFiltroClientes = new JLabel("FILTRAR CLIENTES");
        lFiltroClientes.setFont(fonte2);
        lFiltroClientes.setBounds(140, 15, 400, 40);
        add(lFiltroClientes);

        lNome = new JLabel("NOME");
        lNome.setFont(fonte2);
        lNome.setBounds(25, 30, 100, 40);
        add(lNome);
        tNome = new JTextField();
        tNome.setBounds(25, 60, 350, 40);
        tNome.setFont(fonte1);
        add(tNome);

        lSobrenome = new JLabel("SOBRENOME");
        lSobrenome.setFont(fonte2);
        lSobrenome.setBounds(25, 120, 100, 40);
        add(lSobrenome);
        tSobrenome = new JTextField();
        tSobrenome.setBounds(25, 150, 350, 40);
        tSobrenome.setFont(fonte1);
        add(tSobrenome);

        lCpf = new JLabel("CPF");
        lCpf.setFont(fonte2);
        lCpf.setBounds(25, 210, 100, 40);
        add(lCpf);
        try {
            MaskFormatter fCpf = new MaskFormatter("###.###.###-##");
            tCpf = new JFormattedTextField(fCpf);
            tCpf.setBounds(25, 240, 150, 40);
            tCpf.setFont(fonte1);
            add(tCpf);
        } catch (ParseException e) { e.printStackTrace(); }

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

        lFiltroVeiculos = new JLabel("FILTRAR VEÍCULOS");
        lFiltroVeiculos.setFont(fonte2);
        lFiltroVeiculos.setBounds(140, 395, 400, 40);
        add(lFiltroVeiculos);

        lTipo = new JLabel("TIPO");
        lTipo.setFont(fonte2);
        lTipo.setBounds(25, 420, 110, 40);
        add(lTipo);
        cTipo = new JComboBox<>(new String[]{"Todos", "Automovel", "Motocicleta", "Van"});
        cTipo.setFont(fonte1);
        cTipo.setBounds(25, 450, 160, 40);
        add(cTipo);

        lMarca = new JLabel("MARCA");
        lMarca.setFont(fonte2);
        lMarca.setBounds(210, 420, 100, 40);
        add(lMarca);
        Object[] marcas = new Object[Marca.values().length + 1];
        marcas[0] = "TODOS";
        System.arraycopy(Marca.values(), 0, marcas, 1, Marca.values().length);
        cMarca = new JComboBox<>(marcas);
        cMarca.setFont(fonte1);
        cMarca.setBounds(210, 450, 160, 40);
        add(cMarca);

        lCategoria = new JLabel("CATEGORIA");
        lCategoria.setFont(fonte2);
        lCategoria.setBounds(25, 510, 110, 40);
        add(lCategoria);
        Object[] categorias = new Object[Categoria.values().length + 1];
        categorias[0] = "TODOS";
        System.arraycopy(Categoria.values(), 0, categorias, 1, Categoria.values().length);
        cCategoria = new JComboBox<>(categorias);
        cCategoria.setFont(fonte1);
        cCategoria.setBounds(25, 540, 160, 40);
        add(cCategoria);

        bFiltrarVeiculos = new JButton("Filtrar");
        bFiltrarVeiculos.setBounds(210, 540, 160, 40);
        bFiltrarVeiculos.setFont(fonte2);
        bFiltrarVeiculos.addActionListener(this);
        add(bFiltrarVeiculos);

        lData = new JLabel("Data da Locação");
        lData.setFont(fonte2);
        lData.setBounds(25, 600, 100, 40);
        add(lData);
        try {
            MaskFormatter fData = new MaskFormatter("##/##/####");
            tData = new JFormattedTextField(fData);
            tData.setBounds(25, 630, 160, 40);
            tData.setFont(fonte1);
            add(tData);
        } catch (ParseException e) { e.printStackTrace(); }

        lDias = new JLabel("Qtd. Dias");
        lDias.setFont(fonte2);
        lDias.setBounds(210, 600, 100, 40);
        add(lDias);
        try {
            MaskFormatter fDias = new MaskFormatter("##");
            tDias = new JFormattedTextField(fDias);
            tDias.setBounds(210, 630, 160, 40);
            tDias.setFont(fonte1);
            add(tDias);
        } catch (ParseException e) { e.printStackTrace(); }

        bLocar = new JButton("Locar");
        bLocar.setBounds(25, 720, 350, 40);
        bLocar.setFont(fonte2);
        bLocar.addActionListener(this);
        add(bLocar);

        lTabelaClientes = new JLabel("CLIENTES APTOS");
        lTabelaClientes.setFont(fonte3);
        lTabelaClientes.setBounds(430, 15, 400, 40);
        add(lTabelaClientes);

        tabelaClientesAptos = new Tabela<>(parametrosClientes, null);
        painelTabelaClientes = new JScrollPane();
        painelTabelaClientes.setBounds(425, 50, 750, 320);
        tabelaClientes = new JTable(tabelaClientesAptos);
        configurarTabelaClientes();
        painelTabelaClientes.setViewportView(tabelaClientes);
        add(painelTabelaClientes);

        lTabelaVeiculos = new JLabel("VEÍCULOS DISPONÍVEIS");
        lTabelaVeiculos.setFont(fonte3);
        lTabelaVeiculos.setBounds(430, 395, 400, 40);
        add(lTabelaVeiculos);
        
        locarVeiculo = new Tabela<>(parametrosVeiculos, null);
        painelTabelaVeiculos = new JScrollPane();
        painelTabelaVeiculos.setBounds(425, 430, 750, 320);
        tabelaVeiculos = new JTable(locarVeiculo);
        configurarTabelaVeiculos();
        painelTabelaVeiculos.setViewportView(tabelaVeiculos);
        add(painelTabelaVeiculos);
    }
    
    private void configurarTabelaClientes() {
        tabelaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaClientes.setFont(fonte1);
        tabelaClientes.setRowHeight(30);
        tabelaClientes.getColumnModel().getColumn(0).setPreferredWidth(30);
        tabelaClientes.getColumnModel().getColumn(1).setPreferredWidth(30);
        tabelaClientes.getColumnModel().getColumn(2).setPreferredWidth(10);
        tabelaClientes.getColumnModel().getColumn(3).setPreferredWidth(45);
        tabelaClientes.setBorder(new LineBorder(Color.BLACK, 1));
        
        tabelaClientes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = tabelaClientes.getSelectedRow();
                    if (selectedRow != -1 && !tabelaClientesAptos.getDados().isEmpty()) {
                        Cliente cliente = tabelaClientesAptos.getDados().get(selectedRow);
                        controller.setClienteSelecionado(cliente);
                    }
                }
            }
        });
    }

    private void configurarTabelaVeiculos() {
        tabelaVeiculos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaVeiculos.setFont(fonte1);
        tabelaVeiculos.setRowHeight(30);
        tabelaVeiculos.setBorder(new LineBorder(Color.BLACK, 1));
        
        tabelaVeiculos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = tabelaVeiculos.getSelectedRow();
                    if (selectedRow != -1 && !locarVeiculo.getDados().isEmpty()) {
                        Veiculo veiculo = locarVeiculo.getDados().get(selectedRow);
                        controller.setVeiculoSelecionado(veiculo);
                    }
                }
            }
        });
    }

    public void notificarAtualizacaoGeral() {
        painelAbas.atualizarTodosOsPaineis();
    }
    
    public void atualizarTabelaClientes(List<Cliente> clientes) {
        this.tabelaClientesAptos.atualizarTabela(clientes);
    }

    public void atualizarTabelaVeiculos(List<Veiculo> veiculos) {
        this.locarVeiculo.atualizarTabela(veiculos);
    }
    
    public void mostrarMensagem(String titulo, String mensagem, int tipo) {
        JOptionPane.showMessageDialog(this, mensagem, titulo, tipo);
    }
    
    public void limparCamposFiltroCliente() {
        tNome.setText("");
        tSobrenome.setText("");
        tCpf.setText("");
        rNome.setSelected(true);
    }
    
    public void limparCamposDeLocacao() {
        tData.setText("");
        tDias.setText("");
    }
    
    public void limparSelecaoTabelaClientes() {
        tabelaClientes.clearSelection();
    }

    public void limparSelecaoTabelaVeiculos() {
        tabelaVeiculos.clearSelection();
    }

    public String getFiltroNomeCliente() { return tNome.getText().trim(); }
    public String getFiltroSobrenomeCliente() { return tSobrenome.getText().trim(); }
    public String getFiltroCpfCliente() { return tCpf.getText().trim(); }
    public boolean isFiltroClientePorNome() { return rNome.isSelected(); }
    public boolean isFiltroClientePorSobrenome() { return rSobrenome.isSelected(); }
    public boolean isFiltroClientePorCpf() { return rCpf.isSelected(); }
    public String getFiltroTipoVeiculo() { return (String) cTipo.getSelectedItem(); }
    public Object getFiltroMarcaVeiculo() { return cMarca.getSelectedItem(); }
    public Object getFiltroCategoriaVeiculo() { return cCategoria.getSelectedItem(); }
    public String getDataLocacao() { return tData.getText(); }
    public String getDiasLocacao() { return tDias.getText(); }
}