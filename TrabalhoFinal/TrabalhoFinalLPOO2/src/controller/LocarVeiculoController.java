package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import misc.Locacao;
import misc.Veiculo;
import model.Automovel;
import model.Cliente;
import model.Motocicleta;
import model.Van;
import model.dao.DaoFactory;
import model.manager.GerenciadorDados;
import swing.PainelLocarVeiculo;

public class LocarVeiculoController {

    private final PainelLocarVeiculo view;
    private final GerenciadorDados gerenciador;

    private Cliente clienteSelecionado;
    private Veiculo veiculoSelecionado;

    public LocarVeiculoController(PainelLocarVeiculo view) {
        this.view = view;
        this.gerenciador = GerenciadorDados.getInstance();
    }

    public void carregarDadosIniciais() {
        view.atualizarTabelaClientes(gerenciador.getClientesAptos());
        view.atualizarTabelaVeiculos(gerenciador.getVeiculosDisponiveis());
    }

    public void locarVeiculo() {
        if (clienteSelecionado == null) {
            view.mostrarMensagem("Operação Não Concluída", "Selecione um cliente.", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (veiculoSelecionado == null) {
            view.mostrarMensagem("Operação Não Concluída", "Selecione um veículo.", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!diasValidos()) {
            return;
        }
        if (!dataValida()) {
            view.mostrarMensagem("Operação Não Concluída", "Data inválida. A data não pode ser no passado e deve estar no formato dd/mm/aaaa.", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate data = LocalDate.parse(view.getDataLocacao(), formatter);
            veiculoSelecionado.locar(Integer.parseInt(view.getDiasLocacao().replaceAll("_", "").trim()), data, clienteSelecionado);
            Locacao novaLocacao = veiculoSelecionado.getLocacao();
            DaoFactory.getLocacaoDAO().add(novaLocacao);
            
            if (veiculoSelecionado instanceof Automovel) {
                DaoFactory.getAutomovelDAO().update((Automovel) veiculoSelecionado);
            } else if (veiculoSelecionado instanceof Motocicleta) {
                DaoFactory.getMotocicletaDAO().update((Motocicleta) veiculoSelecionado);
            } else if (veiculoSelecionado instanceof Van) {
                DaoFactory.getVanDAO().update((Van) veiculoSelecionado);
            }
            
            view.mostrarMensagem("Sucesso", "Veículo locado com sucesso", JOptionPane.INFORMATION_MESSAGE);
            
            view.notificarAtualizacaoGeral();
            limparCamposAposLocacao();

        } catch (Exception ex) {
            view.mostrarMensagem("Erro", "Erro ao locar veículo: " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public void filtrarClientes() {
        String textoNome = view.getFiltroNomeCliente().toUpperCase();
        String textoSobrenome = view.getFiltroSobrenomeCliente().toUpperCase();
        String textoCpf = view.getFiltroCpfCliente().replaceAll("[.-]", "");

        List<Cliente> resultado = new ArrayList<>();
        List<Cliente> clientesAptos = gerenciador.getClientesAptos();

        for (Cliente cliente : clientesAptos) {
            if (view.isFiltroClientePorNome()) {
                if (cliente.getNome().toUpperCase().contains(textoNome)) resultado.add(cliente);
            } else if (view.isFiltroClientePorSobrenome()) {
                if (cliente.getSobrenome().toUpperCase().contains(textoSobrenome)) resultado.add(cliente);
            } else if (view.isFiltroClientePorCpf()) {
                if (Long.toString(cliente.getCpf()).contains(textoCpf)) resultado.add(cliente);
            }
        }
        view.atualizarTabelaClientes(resultado);
    }

    public void limparFiltrosClientes() {
        view.limparCamposFiltroCliente();
        clienteSelecionado = null;
        view.limparSelecaoTabelaClientes();
        view.atualizarTabelaClientes(gerenciador.getClientesAptos());
    }

    public void filtrarVeiculos() {
        String tipoSelecionado = view.getFiltroTipoVeiculo();
        Object marcaSelecionada = view.getFiltroMarcaVeiculo();
        Object categoriaSelecionada = view.getFiltroCategoriaVeiculo();

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
        view.atualizarTabelaVeiculos(veiculosFiltrados);
    }

    private void atualizarTodasAsTelas() {
        gerenciador.recarregarDadosDoBanco();
        view.notificarAtualizacaoGeral();
    }
    
    private void limparCamposAposLocacao() {
        clienteSelecionado = null;
        veiculoSelecionado = null;
        view.limparSelecaoTabelaClientes();
        view.limparSelecaoTabelaVeiculos();
        view.limparCamposDeLocacao();
    }
    
    public void setClienteSelecionado(Cliente cliente) {
        this.clienteSelecionado = cliente;
    }

    public void setVeiculoSelecionado(Veiculo veiculo) {
        this.veiculoSelecionado = veiculo;
    }

    private boolean diasValidos() {
        String diasStr = view.getDiasLocacao().replaceAll("_", "").trim();
        if (diasStr.isEmpty()) {
            view.mostrarMensagem("Operação Não Concluída", "Insira o número de dias.", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (Integer.parseInt(diasStr) == 0) {
            view.mostrarMensagem("Operação Não Concluída", "O número de dias deve ser maior que zero.", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    
    private boolean dataValida() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataSelecionada = LocalDate.parse(view.getDataLocacao(), formatter);
            return !dataSelecionada.isBefore(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}