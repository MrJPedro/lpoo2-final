package swing;

import java.awt.Dimension;
import javax.swing.JTabbedPane;
import model.manager.GerenciadorDados;

public class PainelAbas extends JTabbedPane {

    PainelCriarClientes painelCriarClientes;
    PainelComprarVeiculo painelComprarVeiculo;
    PainelLocarVeiculo painelLocarVeiculo;
    PainelDevolverVeiculo painelDevolverVeiculo;
    PainelVenderVeiculo painelVenderVeiculo;

    public PainelAbas() {
        setPreferredSize(new Dimension(1200, 800));

        // Instancia o gerenciador de dados logo no início.
        // Isso garante que todos os dados do banco sejam carregados uma vez.
        GerenciadorDados.getInstance();

        painelCriarClientes = new PainelCriarClientes(this);
        add("Clientes", painelCriarClientes);

        painelComprarVeiculo = new PainelComprarVeiculo(this);
        add("Comprar Veículo", painelComprarVeiculo);

        painelLocarVeiculo = new PainelLocarVeiculo(this);
        add("Locar Veículo", painelLocarVeiculo);

        painelDevolverVeiculo = new PainelDevolverVeiculo(this);
        add("Devolver Veículo", painelDevolverVeiculo);

        painelVenderVeiculo = new PainelVenderVeiculo(this);
        add("Vender Veículo", painelVenderVeiculo);

        setTabLayoutPolicy(SCROLL_TAB_LAYOUT);
    }
    
    public void atualizarTodosOsPaineis() {
        GerenciadorDados.getInstance().recarregarDadosDoBanco();
        
        if (painelLocarVeiculo != null) {
            painelLocarVeiculo.atualizarDados();
        }
        if (painelDevolverVeiculo != null) {
            painelDevolverVeiculo.atualizarDados();
        }
        if (painelVenderVeiculo != null) {
            painelVenderVeiculo.atualizarDados();
        }
    }
}