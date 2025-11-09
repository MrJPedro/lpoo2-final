package model.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import enums.Estado;
import misc.Veiculo;
import model.Cliente;
import model.dao.DaoFactory;
import model.dao.interfaces.AutomovelDAO;
import model.dao.interfaces.ClienteDAO;
import model.dao.interfaces.MotocicletaDAO;
import model.dao.interfaces.VanDAO;

public class GerenciadorDados {

    private static GerenciadorDados instance;

    private List<Veiculo> todosVeiculos;
    private List<Cliente> todosClientes;

    private GerenciadorDados() {
        todosVeiculos = new ArrayList<>();
        todosClientes = new ArrayList<>();
        carregarDadosIniciais();
    }

    public static GerenciadorDados getInstance() {
        if (instance == null) {
            instance = new GerenciadorDados();
        }
        return instance;
    }

    public void recarregarDadosDoBanco() {
        carregarTodosVeiculos();
        carregarTodosClientes();
    }

    private void carregarDadosIniciais() {
        try {
            carregarTodosVeiculos();
            carregarTodosClientes();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro crítico ao carregar dados iniciais: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            System.exit(1); // Fecha a aplicação se os dados não puderem ser carregados
        }
    }

    private void carregarTodosVeiculos() {
        todosVeiculos.clear();
        try {
            AutomovelDAO automovelDAO = DaoFactory.getAutomovelDAO();
            MotocicletaDAO motocicletaDAO = DaoFactory.getMotocicletaDAO();
            VanDAO vanDAO = DaoFactory.getVanDAO();

            todosVeiculos.addAll(automovelDAO.getAll());
            todosVeiculos.addAll(motocicletaDAO.getAll());
            todosVeiculos.addAll(vanDAO.getAll());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar veículos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void carregarTodosClientes() {
        todosClientes.clear();
        try {
            ClienteDAO clienteDAO = DaoFactory.getClienteDAO();
            todosClientes.addAll(clienteDAO.getAll());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar clientes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Métodos públicos para obter listas filtradas
    
    public List<Veiculo> getTodosVeiculos() {
        return todosVeiculos;
    }

    public List<Cliente> getTodosClientes() {
        return todosClientes;
    }

    public List<Veiculo> getVeiculosDisponiveis() {
        return todosVeiculos.stream()
                .filter(v -> v.getEstado() == Estado.DISPONIVEL || v.getEstado() == Estado.NOVO)
                .collect(Collectors.toList());
    }

    public List<Veiculo> getVeiculosLocados() {
        return todosVeiculos.stream()
                .filter(v -> v.getEstado() == Estado.LOCADO)
                .collect(Collectors.toList());
    }
    
    public List<Cliente> getClientesAptos() {
        List<Long> cpfsComLocacao = getVeiculosLocados().stream()
                .filter(v -> v.getLocacao() != null && v.getLocacao().getCliente() != null)
                .map(v -> v.getLocacao().getCliente().getCpf())
                .collect(Collectors.toList());

        return todosClientes.stream()
                .filter(c -> !cpfsComLocacao.contains(c.getCpf()))
                .collect(Collectors.toList());
    }
}