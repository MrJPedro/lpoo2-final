package controller;

import enums.Categoria;
import enums.Estado;
import enums.Marca;
import enums.ModeloAutomovel;
import enums.ModeloMotocicleta;
import enums.ModeloVan;
import javax.swing.JOptionPane;
import misc.Veiculo;
import model.Automovel;
import model.Motocicleta;
import model.Van;
import model.dao.DaoFactory;
import model.dao.interfaces.AutomovelDAO;
import model.dao.interfaces.MotocicletaDAO;
import model.dao.interfaces.VanDAO;
import model.manager.GerenciadorDados;
import swing.PainelComprarVeiculo;

public class ComprarVeiculoController {

    private final PainelComprarVeiculo view;

    public ComprarVeiculoController(PainelComprarVeiculo view) {
        this.view = view;
    }

    public void comprarVeiculo() {
        if (!validarCampos()) {
            return;
        }

        Marca marca = view.getMarcaSelecionada();
        Object modelo = view.getModeloSelecionado();
        Estado estado = view.getEstadoSelecionado();
        Categoria categoria = view.getCategoriaSelecionada();
        String placa = view.getPlaca().toUpperCase();
        int ano = view.getAno();
        double preco = view.getPreco();

        try {
            if (modelo instanceof ModeloAutomovel) {
                AutomovelDAO dao = DaoFactory.getAutomovelDAO();
                Veiculo automovel = new Automovel(marca, estado, categoria, (ModeloAutomovel) modelo, null, preco, placa, ano);
                dao.add((Automovel) automovel);
            } else if (modelo instanceof ModeloVan) {
                VanDAO dao = DaoFactory.getVanDAO();
                Veiculo van = new Van(marca, estado, categoria, (ModeloVan) modelo, null, preco, placa, ano);
                dao.add((Van) van);
            } else if (modelo instanceof ModeloMotocicleta) {
                MotocicletaDAO dao = DaoFactory.getMotocicletaDAO();
                Veiculo motocicleta = new Motocicleta(marca, estado, categoria, (ModeloMotocicleta) modelo, null, preco, placa, ano);
                dao.add((Motocicleta) motocicleta);
            }

            view.mostrarMensagem("Sucesso", "Veículo comprado com sucesso", JOptionPane.INFORMATION_MESSAGE);
            
            GerenciadorDados.getInstance().recarregarDadosDoBanco();
            
            view.notificarAtualizacaoGeral(); 
            
            view.limparCampos();

        } catch (Exception e) {
            view.mostrarMensagem("Erro", "Erro ao comprar veículo: " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private boolean validarCampos() {
        if (view.getPlaca().replaceAll("[_-]", "").length() != 7) {
            view.mostrarMensagem("Operação Não Concluída", "Informe a placa completa.", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (view.getAnoAsString().isEmpty()) {
            view.mostrarMensagem("Operação Não Concluída", "Preencha o campo ANO.", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (view.getAno() > 2026 || view.getAno() < 1980) {
            view.mostrarMensagem("Operação Não Concluída", "Ano inválido.", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (view.getPreco() < 1000) {
            view.mostrarMensagem("Operação Não Concluída", "O preço deve ser no mínimo R$1.000,00.", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public void atualizarModelosConformeMarca() {
        Marca selecionada = view.getMarcaSelecionada();
        view.limparModelos();

        for (ModeloAutomovel modelo : ModeloAutomovel.values()) {
            if (modelo.marca == selecionada) {
                view.adicionarModelo(modelo);
            }
        }
        for (ModeloVan modelo : ModeloVan.values()) {
            if (modelo.marca == selecionada) {
                view.adicionarModelo(modelo);
            }
        }
        for (ModeloMotocicleta modelo : ModeloMotocicleta.values()) {
            if (modelo.marca == selecionada) {
                view.adicionarModelo(modelo);
            }
        }
    }
}