
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
import java.time.ZoneId;
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
            
                "locacao.id_locacao, " +
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
            
                "locacao.id_locacao, " +
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
    
    private String insertVeiculo = 
            "INSERT INTO " +
            "veiculo (id_marca_fk, id_estado_fk, id_categoria_fk, id_locacao_fk, valor_compra, placa, ano) " +
            "VALUES " +
            "(?, ?, ?, ?, ?, ?, ?)";
    
    private String insertAutomovel = 
            "INSERT INTO " +
            "automovel (placa_fk, id_modelo_fk) "+
            "VALUES " +
            "(?, ?)";
    
    private String updateVeiculo =
            "UPDATE veiculo " +
            "SET " +
                "id_marca = ?, " +
                "id_estado = ?, " +
                "id_categoria = ?, " +
                "id_locacao = ?, " +
                "valor_compra = ?, " +
                "ano = ? " +
            "WHERE " +
                "placa = ?";
    
    private String updateAutomovel = 
            "UPDATE automovel " +
            "SET id_modelo_fk = ? " +
            "WHERE placa_fk = ?";
    
    // O setup do BD inclui "ON DELETE CASCADE", o que garante
    // que quando um registro é deletado na tabela veiculo, todos
    // os registros na tabela automovel que referenciam a placa desse
    // mesmo veículo também são deletadas
    private String delete = 
            "DELETE FROM veiculo " +
            "WHERE placa = ?";
    
    // Deleta todos os registros na tabela veiculo cuja placa se encontra na tabela automovel,
    // o que, como descrito no comentário acima, garante que todos os registros na tabela automovel
    // também sejam excluídos
    private String deleteAll = 
            "DELETE veiculo " +
            "FROM veiculo " +
                "JOIN automovel " +
                    "ON veiculo.placa = automovel.placa_fk ";
            
    
    /*
    private String selectPlaca =
            "SELECT placa " +
            "FROM veiculo " +
            "WHERE placa = ?";
    
    private String selectMarca =
            "SELECT id_marca " +
            "FROM marca_veiculo " +
            "WHERE nome_marca = ?";
    
    private String selectModeloAutomovel =
            "SELECT id_modelo " +
            "FROM modelo_automovel " +
            "WHERE nome_modelo = ?";
    
    
    private String selectLocacao =
            "SELECT id_locacao " +
            "FROM locacao " +
            "WHERE id_locacao = ?";
    
    
    private String insertLocacao = 
            "INSERT INTO " +
            "locacao(id_locacao, dias_locacao, valor_locacao, data_locacao, cpf_cliente_fk) " +
            "VALUES " +
            "(?, ?, ?, ?, ?)";
    */
    
    
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
                int idLocacao = rs.getInt("id_locacao");
                int diasLocacao = rs.getInt("dias_locacao");
                double valorLocacao = rs.getDouble("valor_locacao");
                //LocalDate dataLocacao = rs.getDate("data_locacao").toLocalDate();
                
                
                Date dataLocacaoDate = rs.getDate("data_locacao");
                LocalDate dataLocacao = null;
                if (dataLocacaoDate != null){
                    dataLocacao = LocalDate.ofInstant(
                            dataLocacaoDate.toInstant(),
                            ZoneId.systemDefault());
                }
                Locacao locacao = new Locacao(idLocacao, diasLocacao, valorLocacao, dataLocacao, cliente);
                
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
                PreparedStatement st = con.prepareStatement(this.selectByPlaca);
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
                
                int idLocacao = rs.getInt("id_locacao");
                int diasLocacao = rs.getInt("dias_locacao");
                double valorLocacao = rs.getDouble("valor_locacao");
                //LocalDate dataLocacao = rs.getDate("data_locacao").toLocalDate();
                
                Date dataLocacaoDate = rs.getDate("data_locacao");
                LocalDate dataLocacao = null;
                if (dataLocacaoDate != null){
                    dataLocacao = LocalDate.ofInstant(
                            dataLocacaoDate.toInstant(),
                            ZoneId.systemDefault());
                }
                
                Locacao locacao = new Locacao(idLocacao, diasLocacao, valorLocacao, dataLocacao, cliente);
                
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
    public void add(Automovel automovel) throws SQLException, IOException, Exception {
        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement stVeiculo = con.prepareStatement(this.insertVeiculo);
                PreparedStatement stAutomovel = con.prepareStatement(this.insertAutomovel);
                )
        {
            Automovel existente = this.getByPlaca(automovel.getPlaca());
            
            if(existente!=null){
                throw new Exception("Veículo [Placa: " + automovel.getPlaca() + "] já cadastrado no BD!!");
            }
            
            stVeiculo.setInt(1, automovel.getMarca().ID_MARCA);
            stVeiculo.setInt(2, automovel.getEstado().ID_ESTADO);
            stVeiculo.setInt(3, automovel.getCategoria().ID_CATEGORIA);
            stVeiculo.setInt(4, automovel.getLocacao().getIdLocacao());
            stVeiculo.setDouble(5, automovel.getValorDeCompra());
            stVeiculo.setString(6, automovel.getPlaca());
            stVeiculo.setInt(7, automovel.getAno());
            
            stAutomovel.setString(1, automovel.getPlaca());
            stAutomovel.setInt(2, automovel.getModelo().ID_MODELO_AUTOMOVEL);
            
            stVeiculo.executeUpdate();
            stAutomovel.executeUpdate();
        }
    }
    
    @Override
    public void update(Automovel automovel) throws SQLException, IOException, Exception {
        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement stVeiculo = con.prepareStatement(this.updateVeiculo);
                PreparedStatement stAutomovel = con.prepareStatement(this.updateAutomovel);
                )
        {
            Automovel existente = this.getByPlaca(automovel.getPlaca());
            
            if(existente==null){
                throw new Exception("Veículo [Placa: " + automovel.getPlaca() + " ] não existe no BD!!");
            }
            
            stVeiculo.setInt(1, automovel.getMarca().ID_MARCA);
            stVeiculo.setInt(2, automovel.getEstado().ID_ESTADO);
            stVeiculo.setInt(3, automovel.getCategoria().ID_CATEGORIA);
            stVeiculo.setInt(4, automovel.getLocacao().getIdLocacao());
            stVeiculo.setDouble(5, automovel.getValorDeCompra());
            stVeiculo.setInt(6, automovel.getAno());
            stVeiculo.setString(7,automovel.getPlaca());
            
            stAutomovel.setInt(1, automovel.getModelo().ID_MODELO_AUTOMOVEL);
            stAutomovel.setString(2, automovel.getPlaca());
            
            stVeiculo.executeUpdate();
            stAutomovel.executeUpdate();
        }
    }

    @Override
    public void delete(Automovel automovel) throws SQLException, IOException, Exception {
         try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement st = con.prepareStatement(this.delete);
                )
        {
            Automovel existente = this.getByPlaca(automovel.getPlaca());
            
            if(existente==null){
                throw new Exception("Veículo [Placa: " + automovel.getPlaca() + " ] não existe no BD!!");
            }
            
            st.setString(1, automovel.getPlaca());            
            st.executeUpdate();
        }
    }

    @Override
    public void deleteAll() throws SQLException, IOException {
        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement st = con.prepareStatement(this.deleteAll);
                )
        {            
            st.executeUpdate();
        }
    }
    
}
