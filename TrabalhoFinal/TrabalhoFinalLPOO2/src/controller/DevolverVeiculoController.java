package controller;

import javax.swing.JOptionPane;
import misc.Locacao;
import misc.Veiculo;
import model.Automovel;
import model.Motocicleta;
import model.Van;
import model.dao.DaoFactory;
import model.manager.GerenciadorDados;
import swing.PainelDevolverVeiculo;

public class DevolverVeiculoController {

    private final PainelDevolverVeiculo view;
    private final GerenciadorDados gerenciador;
    private Veiculo veiculoSelecionado;

    public DevolverVeiculoController(PainelDevolverVeiculo view) {
        this.view = view;
        this.gerenciador = GerenciadorDados.getInstance();
    }

    public void carregarDadosIniciais() {
        view.atualizarTabela(gerenciador.getVeiculosLocados());
    }

    public void devolverVeiculo() {
        if (veiculoSelecionado == null) {
            view.mostrarMensagem("Operação Não Concluída", "Selecione um veículo para devolver.", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Locacao locacaoParaDeletar = veiculoSelecionado.getLocacao();
            veiculoSelecionado.devolver();

            if (veiculoSelecionado instanceof Automovel) {
                DaoFactory.getAutomovelDAO().update((Automovel) veiculoSelecionado);
            } else if (veiculoSelecionado instanceof Motocicleta) {
                DaoFactory.getMotocicletaDAO().update((Motocicleta) veiculoSelecionado);
            } else if (veiculoSelecionado instanceof Van) {
                DaoFactory.getVanDAO().update((Van) veiculoSelecionado);
            }

            if (locacaoParaDeletar != null) {
                DaoFactory.getLocacaoDAO().delete(locacaoParaDeletar);
            }

            view.mostrarMensagem("Sucesso", "Veículo devolvido com sucesso", JOptionPane.INFORMATION_MESSAGE);

            GerenciadorDados.getInstance().recarregarDadosDoBanco();
            limparAposDevolucao();
            view.atualizarDados();

        } catch (Exception ex) {
            view.mostrarMensagem("Erro", "Erro ao devolver veículo: " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public void setVeiculoSelecionado(Veiculo veiculo) {
        this.veiculoSelecionado = veiculo;
    }
    
    private void limparAposDevolucao() {
        veiculoSelecionado = null;
        view.limparSelecaoTabela();
    }
}