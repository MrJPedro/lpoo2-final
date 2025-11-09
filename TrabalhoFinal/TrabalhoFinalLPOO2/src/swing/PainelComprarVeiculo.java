package swing;

import controller.ComprarVeiculoController;
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

public class PainelComprarVeiculo extends JPanel implements ActionListener {

    private JComboBox cMarca, cModelo, cEstado, cCategoria;
    private JFormattedTextField tPreco, tPlaca, tAno;
    private JLabel lMarca, lModelo, lEstado, lCategoria, lPreco, lPlaca, lAno, lTitulo;
    private JButton bComprar;

    private Font fonte1 = new Font("Arial", 0, 18);
    private Font fonte2 = new Font("Arial", Font.BOLD, 14);
    private Font fonte3 = new Font("Arial", Font.BOLD, 18);

    private ComprarVeiculoController controller;
    private PainelAbas painelAbas; // Novo campo para a referência

    // O construtor agora recebe o PainelAbas
    public PainelComprarVeiculo(PainelAbas painelAbas) {
        this.painelAbas = painelAbas; // Armazena a referência
        setLayout(null);
        this.controller = new ComprarVeiculoController(this);
        inicializarComponentes();
        controller.atualizarModelosConformeMarca();
    }
    
    // Novo método para o controller chamar a atualização
    public void notificarAtualizacaoGeral() {
        painelAbas.atualizarTodosOsPaineis();
    }

    private void inicializarComponentes() {
        lTitulo = new JLabel("COMPRAR VEÍCULO");
        lTitulo.setFont(fonte3);
        lTitulo.setBounds(500, 20, 200, 40);
        add(lTitulo);

        lMarca = new JLabel("MARCA");
        lMarca.setFont(fonte2);
        lMarca.setBounds(250, 60, 100, 40);
        add(lMarca);
        cMarca = new JComboBox<>(new DefaultComboBoxModel<>(Marca.values()));
        cMarca.setFont(fonte1);
        cMarca.setBounds(250, 90, 300, 40);
        cMarca.addActionListener(this);
        add(cMarca);

        lModelo = new JLabel("MODELO");
        lModelo.setFont(fonte2);
        lModelo.setBounds(650, 60, 100, 40);
        add(lModelo);
        cModelo = new JComboBox();
        cModelo.setFont(fonte1);
        cModelo.setBounds(650, 90, 300, 40);
        add(cModelo);

        lCategoria = new JLabel("CATEGORIA");
        lCategoria.setFont(fonte2);
        lCategoria.setBounds(250, 150, 100, 40);
        add(lCategoria);
        cCategoria = new JComboBox<>(new DefaultComboBoxModel<>(Categoria.values()));
        cCategoria.setFont(fonte1);
        cCategoria.setBounds(250, 180, 300, 40);
        add(cCategoria);

        lEstado = new JLabel("ESTADO");
        lEstado.setFont(fonte2);
        lEstado.setBounds(650, 150, 100, 40);
        add(lEstado);
        cEstado = new JComboBox<>(new DefaultComboBoxModel<>(new Estado[] { Estado.DISPONIVEL, Estado.NOVO }));
        cEstado.setFont(fonte1);
        cEstado.setBounds(650, 180, 300, 40);
        add(cEstado);

        lPlaca = new JLabel("PLACA");
        lPlaca.setFont(fonte2);
        lPlaca.setBounds(250, 240, 100, 40);
        add(lPlaca);
        try {
            MaskFormatter fPlaca = new MaskFormatter("UUU####");
            fPlaca.setValueContainsLiteralCharacters(false);
            fPlaca.setOverwriteMode(true);
            fPlaca.setPlaceholderCharacter('_');
            tPlaca = new JFormattedTextField(fPlaca);
            tPlaca.setFont(fonte1);
            tPlaca.setBounds(250, 270, 300, 40);
            add(tPlaca);
        } catch (ParseException e) { e.printStackTrace(); }

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
        } catch (ParseException e) { e.printStackTrace(); }

        lPreco = new JLabel("PREÇO");
        lPreco.setFont(fonte2);
        lPreco.setBounds(250, 330, 100, 40);
        add(lPreco);
        try {
            MaskFormatter fPreco = new MaskFormatter("R$ ###.###,##");
            fPreco.setValueContainsLiteralCharacters(false);
            fPreco.setOverwriteMode(true);
            fPreco.setPlaceholderCharacter('0');
            tPreco = new JFormattedTextField(fPreco);
            tPreco.setFont(fonte1);
            tPreco.setBounds(250, 360, 300, 40);
            add(tPreco);
        } catch (ParseException e) { e.printStackTrace(); }

        bComprar = new JButton("COMPRAR");
        bComprar.setBounds(650, 360, 300, 40);
        bComprar.setMargin(new Insets(0, 0, 0, 0));
        bComprar.setFont(fonte2);
        bComprar.addActionListener(this);
        add(bComprar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cMarca) {
            controller.atualizarModelosConformeMarca();
        } else if (e.getSource() == bComprar) {
            controller.comprarVeiculo();
        }
    }
    
    public void limparCampos() {
        cMarca.setSelectedIndex(0);
        cModelo.setSelectedIndex(0);
        cCategoria.setSelectedIndex(0);
        cEstado.setSelectedIndex(0);
        tPlaca.setText("");
        tAno.setText("");
        tPreco.setValue(0);
    }

    public void mostrarMensagem(String titulo, String mensagem, int tipo) {
        JOptionPane.showMessageDialog(this, mensagem, titulo, tipo);
    }
    
    public void limparModelos() {
        cModelo.removeAllItems();
    }
    
    public void adicionarModelo(Object modelo) {
        cModelo.addItem(modelo);
    }

    public Marca getMarcaSelecionada() { return (Marca) cMarca.getSelectedItem(); }
    public Object getModeloSelecionado() { return cModelo.getSelectedItem(); }
    public Estado getEstadoSelecionado() { return (Estado) cEstado.getSelectedItem(); }
    public Categoria getCategoriaSelecionada() { return (Categoria) cCategoria.getSelectedItem(); }
    public String getPlaca() { return tPlaca.getText().replaceAll("[_-]", "").trim(); }
    public int getAno() { return Integer.parseInt(tAno.getText().trim().replaceAll("_", "")); }
    public String getAnoAsString() { return tAno.getText().trim().replaceAll("_", ""); }
    public double getPreco() { return Double.parseDouble(tPreco.getText().replaceAll("[^\\d]", "")) / 100; }
}