package controller;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import misc.Veiculo;
import model.Cliente;
import model.dao.DaoFactory;
import model.dao.interfaces.ClienteDAO;
import model.manager.GerenciadorDados;
import swing.PainelCriarClientes;
import swing.PainelLocarVeiculo;

public class ClienteController {

    private final PainelCriarClientes view;
    private final ClienteDAO clienteDAO;
    private List<Cliente> clientes;
    private Cliente clienteSelecionado;

    public ClienteController(PainelCriarClientes view) {
        this.view = view;
        this.clienteDAO = DaoFactory.getClienteDAO();
        this.clientes = new ArrayList<>();
    }

    public void carregarClientes() {
        try {
            clientes = clienteDAO.getAll();
            view.atualizarTabela(clientes);
        } catch (Exception e) {
            view.mostrarMensagem("Erro", "Erro ao carregar clientes: " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void criarCliente() {
        try {
            if (!validarCampos(true)) {
                return;
            }

            Cliente novoCliente = new Cliente(
                view.getNome(),
                view.getSobrenome(),
                Integer.parseInt(view.getRg().replaceAll("[.-]", "")),
                Long.parseLong(view.getCpf().replaceAll("[.-]", "")),
                view.getEndereco()
            );

            clienteDAO.add(novoCliente);
            view.mostrarMensagem("Sucesso", "Cliente Cadastrado com Sucesso", JOptionPane.INFORMATION_MESSAGE);

            carregarClientes();
            view.limparTudo();
            view.notificarAtualizacaoGeral();

        } catch (Exception e) {
            view.mostrarMensagem("Erro", "Erro ao cadastrar cliente: " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void salvarAtualizacaoCliente() {
        if (clienteSelecionado == null) {
            view.mostrarMensagem("Aviso", "Nenhum cliente selecionado para atualizar.", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if (!validarCampos(false)) {
                return;
            }

            clienteSelecionado.setNome(view.getNome());
            clienteSelecionado.setSobrenome(view.getSobrenome());
            clienteSelecionado.setRg(Integer.parseInt(view.getRg().replaceAll("[._-]", "")));
            clienteSelecionado.setEndereco(view.getEndereco());

            clienteDAO.update(clienteSelecionado);
            view.mostrarMensagem("Sucesso", "Cliente atualizado com Sucesso", JOptionPane.INFORMATION_MESSAGE);

            carregarClientes();
            view.limparTudo();
            view.alternarModoPadrao();
            view.notificarAtualizacaoGeral();

        } catch (Exception e) {
            view.mostrarMensagem("Erro", "Erro ao salvar cliente: " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void excluirCliente() {
    if (clienteSelecionado == null) {
        view.mostrarMensagem("Aviso", "Selecione um cliente para excluir.", JOptionPane.WARNING_MESSAGE);
        return;
    }

        List<Veiculo> veiculosLocados = GerenciadorDados.getInstance().getVeiculosLocados();

        for (Veiculo v : veiculosLocados) {
        if (v.getLocacao() != null && v.getLocacao().getCliente().getCpf() == clienteSelecionado.getCpf()) {
            view.mostrarMensagem("Operação Não Permitida", "Este cliente não pode ser excluído pois possui um veículo locado.", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

        int confirmacao = view.pedirConfirmacao("Confirmar Exclusão", "Confirmar Exclusão?\nEssa ação não pode ser desfeita.");
        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                clienteDAO.delete(clienteSelecionado);
                view.mostrarMensagem("Sucesso", "Cliente excluído com sucesso.", JOptionPane.INFORMATION_MESSAGE);
                carregarClientes();
                view.limparTudo();
                view.notificarAtualizacaoGeral();
            } catch (Exception e) {
                view.mostrarMensagem("Erro", "Erro ao excluir cliente: " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    public void selecionarClienteNaTabela(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < clientes.size()) {
            this.clienteSelecionado = clientes.get(rowIndex);
            view.preencherCampos(clienteSelecionado);
            view.alternarBotoesSelecao(true);
        }
    }

    public void limparSelecao() {
        this.clienteSelecionado = null;
        view.limparCampos();
        view.liberarCampos(true);
        view.alternarBotoesSelecao(false);
    }
    
    public void alternarParaModoEdicao() {
        view.alternarModoEdicao();
        view.liberarCampos(true);
        view.desabilitarCpf();
    }

    public void cancelarEdicao() {
        view.alternarModoPadrao();
        limparSelecao();
        view.limparTudo();
    }
    
    private boolean validarCampos(boolean validarCpf) {
        if (view.getNome().isEmpty()) {
            view.mostrarMensagem("Operação Não Concluída", "Preencha o campo NOME.", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (view.getSobrenome().isEmpty()) {
            view.mostrarMensagem("Operação Não Concluída", "Preencha o campo SOBRENOME.", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (view.getRg().replaceAll("[._-]", "").isEmpty()) {
            view.mostrarMensagem("Operação Não Concluída", "Preencha o campo RG.", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (validarCpf && view.getCpf().replaceAll("[._-]", "").isEmpty()) {
            view.mostrarMensagem("Operação Não Concluída", "Preencha o campo CPF.", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (view.getEndereco().isEmpty()) {
            view.mostrarMensagem("Operação Não Concluída", "Preencha o campo ENDEREÇO.", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    
    public List<Cliente> getClientes() {
        return clientes;
    }
}