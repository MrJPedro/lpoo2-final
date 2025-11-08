
package model.dao.sql;

import enums.Categoria;
import enums.Estado;
import enums.Marca;
import enums.ModeloAutomovel;
import java.io.IOException;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import model.Cliente;
import misc.Locacao;
import model.Automovel;
import model.dao.ConnectionFactory;
import model.dao.interfaces.AutomovelDAO;

public class AutomovelDAOsql implements AutomovelDAO{

    private static AutomovelDAOsql automovelDaoSql;
    
    private String selectAll = 
            "SELECT " +
            
                "marca_veiculo.id_marca, " +
                "marca_veiculo.nome_marca AS marca, " +
            
                "estado_veiculo.id_estado, " +
                "estado_veiculo.nome_estado AS estado, " +
            
                "categoria_veiculo.id_categoria, " +
                "categoria_veiculo.nome_categoria AS categoria, " +
            
                "modelo_automovel.id_modelo, " +
                "modelo_automovel.nome_modelo AS modelo, " +
            
                "locacao.dias_locacao, " +
                "locacao.valor_locacao, " +
                "locacao.data_locacao, " +
            
                "cliente.nome, " +
                "cliente.sobrenome, " +
                "cliente.rg, " +
                "cliente.cpf, " +
                "cliente.endereco, " +
            
                "veiculo.valor_compra, " +
                "veiculo.placa, " +
                "veiculo.ano " +
            
            "FROM automovel " +
                "JOIN veiculo " +
                    "ON automovel.placa_fk = veiculo.placa " +
                "JOIN estado_veiculo " +
                    "ON veiculo.id_estado_fk = estado_veiculo.id_estado " +
                "JOIN categoria_veiculo " +
                    "ON veiculo.id_categoria_fk = categoria_veiculo.id_categoria " +
                "JOIN marca_veiculo " +
                    "ON veiculo.id_marca_fk = marca_veiculo.id_marca " +
                "JOIN modelo_automovel " +
                    "ON automovel.id_modelo_fk = modelo_automovel.id_modelo " +
                "LEFT JOIN locacao " +
                    "ON veiculo.id_locacao_fk = locacao.id_locacao " +
                "LEFT JOIN cliente " +
                    "ON locacao.cpf_cliente_fk = cliente.cpf";
    
    private String selectByPlaca = 
            "SELECT " +
            
                "marca_veiculo.id_marca, " +
                "marca_veiculo.nome_marca AS marca, " +
            
                "estado_veiculo.id_estado, " +
                "estado_veiculo.nome_estado AS estado, " +
            
                "categoria_veiculo.id_categoria, " +
                "categoria_veiculo.nome_categoria AS categoria, " +
            
                "modelo_automovel.id_modelo, " +
                "modelo_automovel.nome_modelo AS modelo, " +
            
                "locacao.dias_locacao, " +
                "locacao.valor_locacao, " +
                "locacao.data_locacao, " +
            
                "cliente.nome, " +
                "cliente.sobrenome, " +
                "cliente.rg, " +
                "cliente.cpf, " +
                "cliente.endereco, " +
            
                "veiculo.valor_compra, " +
                "veiculo.placa, " +
                "veiculo.ano " +
            
            "FROM automovel " +
                "JOIN veiculo " +
                    "ON automovel.placa_fk = veiculo.placa " +
                "JOIN estado_veiculo " +
                    "ON veiculo.id_estado_fk = estado_veiculo.id_estado " +
                "JOIN categoria_veiculo " +
                    "ON veiculo.id_categoria_fk = categoria_veiculo.id_categoria " +
                "JOIN marca_veiculo " +
                    "ON veiculo.id_marca_fk = marca_veiculo.id_marca " +
                "JOIN modelo_automovel " +
                    "ON automovel.id_modelo_fk = modelo_automovel.id_modelo " +
                "LEFT JOIN locacao " +
                    "ON veiculo.id_locacao_fk = locacao.id_locacao " +
                "LEFT JOIN cliente " +
                    "ON locacao.cpf_cliente_fk = cliente.cpf " +
            "WHERE veiculo.placa = ?";
    
    private String insert = 
            "INSERT INTO ";
    private String update;
    private String delete;
    private String deleteAll;
    
    private String selectPlaca =
            "SELECT placa " +
            "FROM veiculo " +
            "WHERE placa = ?";
    
    /*
    private String selectMarca =
            "SELECT id_marca " +
            "FROM marca_veiculo " +
            "WHERE nome_marca = ?";
    
    private String selectModeloAutomovel =
            "SELECT id_modelo " +
            "FROM modelo_automovel " +
            "WHERE nome_modelo = ?";
    */
    
    private String selectLocacao =
            "SELECT id_locacao " +
            "FROM locacao " +
            "WHERE id_locacao = ?";
    
    
    private String insertLocacao = 
            "INSERT INTO " +
            "locacao(id_locacao, dias_locacao, valor_locacao, data_locacao, cpf_cliente_fk) " +
            "";
    
    
    
    private AutomovelDAOsql(){}
    
    public static AutomovelDAOsql getAutomovelDAOsql(){
        if(automovelDaoSql==null){
            automovelDaoSql = new AutomovelDAOsql();
            return automovelDaoSql;
        } else {
            return automovelDaoSql;
        }
    }
    
    @Override
    public List<Automovel> getAll() throws SQLException, IOException{
        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement st = con.prepareStatement(this.selectAll);
            )
        {
            ResultSet rs = st.executeQuery();
            List<Automovel> lista = new ArrayList<>();
            
            while(rs.next()){
                //    
                int idMarca = rs.getInt("id_marca");
                String nomeMarca = rs.getString("marca");
                Marca marca = Marca.valueOf(nomeMarca);
                
                int idEstado = rs.getInt("id_estado");
                String nomeEstado = rs.getString("estado");
                Estado estado = Estado.valueOf(nomeEstado);
                
                int idCategoria = rs.getInt("id_categoria");
                String nomeCategoria = rs.getString("categoria");
                Categoria categoria = Categoria.valueOf(nomeCategoria);
                
                int idModeloAutomovel = rs.getInt("id_modelo");
                String nomeModeloAutomovel = rs.getString("modelo");
                ModeloAutomovel modeloAutomovel = ModeloAutomovel.valueOf(nomeModeloAutomovel);
                
                String nomeCliente = rs.getString("nome");
                String sobreNomeCliente = rs.getString("sobrenome");
                int rgCliente = rs.getInt("rg");
                long cpfCliente = rs.getLong("cpf");
                String enderecoCliente = rs.getString("endereco");
                Cliente cliente = new Cliente(
                        nomeCliente,
                        sobreNomeCliente,
                        rgCliente,
                        cpfCliente,
                        enderecoCliente
                    );
                
                int diasLocacao = rs.getInt("dias_locacao");
                double valorLocacao = rs.getDouble("valor_locacao");
                LocalDate dataLocacao = rs.getDate("data_locacao").toLocalDate();
                Locacao locacao = new Locacao(diasLocacao, valorLocacao, dataLocacao, cliente);
                
                double valorCompraVeiculo = rs.getDouble("valor_compra");
                String placaVeiculo = rs.getString("placa");
                int anoVeiculo = rs.getInt("ano");
                
                Automovel automovel = new Automovel(
                        marca,
                        estado,
                        categoria,
                        modeloAutomovel,
                        locacao,
                        valorCompraVeiculo,
                        placaVeiculo,
                        anoVeiculo
                );
                
                lista.add(automovel);
            }
            return lista;
        }
    }

    @Override
    public Automovel getByPlaca(String placa) throws SQLException, IOException {
        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement st = con.prepareStatement(this.selectAll);
            )
        {
            st.setString(1, placa);
            ResultSet rs = st.executeQuery();
            Automovel automovel = null;
            
            if(rs.next()){
                int idMarca = rs.getInt("id_marca");
                String nomeMarca = rs.getString("marca");
                Marca marca = Marca.valueOf(nomeMarca);
                
                int idEstado = rs.getInt("id_estado");
                String nomeEstado = rs.getString("estado");
                Estado estado = Estado.valueOf(nomeEstado);
                
                int idCategoria = rs.getInt("id_categoria");
                String nomeCategoria = rs.getString("categoria");
                Categoria categoria = Categoria.valueOf(nomeCategoria);
                
                int idModeloAutomovel = rs.getInt("id_modelo");
                String nomeModeloAutomovel = rs.getString("modelo");
                ModeloAutomovel modeloAutomovel = ModeloAutomovel.valueOf(nomeModeloAutomovel);
                
                String nomeCliente = rs.getString("nome");
                String sobreNomeCliente = rs.getString("sobrenome");
                int rgCliente = rs.getInt("rg");
                long cpfCliente = rs.getLong("cpf");
                String enderecoCliente = rs.getString("endereco");
                Cliente cliente = new Cliente(
                        nomeCliente,
                        sobreNomeCliente,
                        rgCliente,
                        cpfCliente,
                        enderecoCliente
                    );
                
                int diasLocacao = rs.getInt("dias_locacao");
                double valorLocacao = rs.getDouble("valor_locacao");
                LocalDate dataLocacao = rs.getDate("data_locacao").toLocalDate();
                Locacao locacao = new Locacao(diasLocacao, valorLocacao, dataLocacao, cliente);
                
                double valorCompraVeiculo = rs.getDouble("valor_compra");
                String placaVeiculo = rs.getString("placa");
                int anoVeiculo = rs.getInt("ano");
                
                automovel = new Automovel(
                        marca,
                        estado,
                        categoria,
                        modeloAutomovel,
                        locacao,
                        valorCompraVeiculo,
                        placaVeiculo,
                        anoVeiculo
                );
                
                if(rs.next()){
                    automovel = null;
                    System.gc();
                }
                
            }
            return automovel;
        }
    }
    
    @Override
    public void add(Automovel automovel) throws SQLException, IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(Automovel automovel) throws SQLException, IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(Automovel automovel) throws SQLException, IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteAll() throws SQLException, IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
