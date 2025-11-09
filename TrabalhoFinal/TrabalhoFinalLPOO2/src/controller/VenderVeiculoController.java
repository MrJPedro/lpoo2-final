package controller;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import misc.Veiculo;
import model.Automovel;
import model.Motocicleta;
import model.Van;
import model.dao.DaoFactory;
import model.manager.GerenciadorDados;
import swing.PainelVenderVeiculo;

public class VenderVeiculoController {

    private final PainelVenderVeiculo view;
    private final GerenciadorDados gerenciador;
    private Veiculo veiculoSelecionado;

    public VenderVeiculoController(PainelVenderVeiculo view) {
        this.view = view;
        this.gerenciador = GerenciadorDados.getInstance();
    }

    public void carregarDadosIniciais() {
        view.atualizarTabela(gerenciador.getVeiculosDisponiveis());
    }

    public void venderVeiculo() {
        if (veiculoSelecionado == null) {
            view.mostrarMensagem("Operação Não Concluída", "Selecione um veículo para vender.", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacao = view.pedirConfirmacao("Confirmar Venda", "Confirmar a venda deste veículo?");
        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                veiculoSelecionado.vender();

                if (veiculoSelecionado instanceof Automovel) {
                    DaoFactory.getAutomovelDAO().update((Automovel) veiculoSelecionado);
                } else if (veiculoSelecionado instanceof Motocicleta) {
                    DaoFactory.getMotocicletaDAO().update((Motocicleta) veiculoSelecionado);
                } else if (veiculoSelecionado instanceof Van) {
                    DaoFactory.getVanDAO().update((Van) veiculoSelecionado);
                }

                view.mostrarMensagem("Sucesso", "Veículo vendido com sucesso", JOptionPane.INFORMATION_MESSAGE);
                
                GerenciadorDados.getInstance().recarregarDadosDoBanco();
                limparAposVenda();
                view.notificarAtualizacaoGeral();

            } catch (Exception e) {
                view.mostrarMensagem("Erro", "Erro ao vender veículo: " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    public void filtrarVeiculos() {
        String tipoSelecionado = view.getFiltroTipo();
        Object marcaSelecionada = view.getFiltroMarca();
        Object categoriaSelecionada = view.getFiltroCategoria();

        List<Veiculo> veiculosFiltrados = new ArrayList<>();
        List<Veiculo> veiculosDisponiveis = gerenciador.getVeiculosDisponiveis();

        for (Veiculo v : veiculosDisponiveis) {
            boolean filtraTipo = tipoSelecionado.equals("Todos") ||
                (tipoSelecionado.equalsIgnoreCase("Automovel") && v instanceof Automovel) ||
                (tipoSelecionado.equalsIgnoreCase("Motocicleta") && v instanceof Motocicleta) ||
                (tipoSelecionado.equalsIgnoreCase("Van") && v instanceof Van);

            boolean filtraMarca = marcaSelecionada.toString().equals("TODOS") || v.getMarca().equals(marcaSelecionada);
            boolean filtraCategoria = categoriaSelecionada.toString().equals("TODOS") || v.getCategoria().equals(categoriaSelecionada);

            if (filtraTipo && filtraMarca && filtraCategoria) {
                veiculosFiltrados.add(v);
            }
        }
        view.atualizarTabela(veiculosFiltrados);
    }
    
    public void setVeiculoSelecionado(Veiculo veiculo) {
        this.veiculoSelecionado = veiculo;
    }
    
    private void limparAposVenda() {
        veiculoSelecionado = null;
        view.limparSelecaoTabela();
    }
}