
package model.dao.sql;

import enums.Categoria;
import enums.Estado;
import enums.Marca;
import enums.ModeloVan;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import misc.Locacao;
import model.Van;
import model.dao.ConnectionFactory;
import model.dao.DaoFactory;
import model.dao.interfaces.LocacaoDAO;
import model.dao.interfaces.VanDAO;

public class VanDAOsql implements VanDAO {

    private static VanDAOsql vanDaoSql;

    private String selectAll = 
        "SELECT v.*, vn.id_modelo_fk, m.nome_marca, e.nome_estado, c.nome_categoria, mv.nome_modelo " +
        "FROM veiculo v " +
        "JOIN van vn ON v.placa = vn.placa_fk " +
        "JOIN marca_veiculo m ON v.id_marca_fk = m.id_marca " +
        "JOIN estado_veiculo e ON v.id_estado_fk = e.id_estado " +
        "JOIN categoria_veiculo c ON v.id_categoria_fk = c.id_categoria " +
        "JOIN modelo_van mv ON vn.id_modelo_fk = mv.id_modelo";

    private String selectByPlaca = selectAll + " WHERE v.placa = ?";

    private String insertVeiculo = 
            "INSERT INTO " +
            "veiculo (placa, id_marca_fk, id_estado_fk, id_categoria_fk, id_locacao_fk, valor_compra, ano) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private String insertVan = 
            "INSERT INTO van (placa_fk, id_modelo_fk) VALUES (?, ?)";

        
private String updateVeiculo =
        "UPDATE veiculo SET id_marca_fk = ?, id_estado_fk = ?, id_categoria_fk = ?, id_locacao_fk = ?, valor_compra = ?, ano = ? WHERE placa = ?";


    private String updateVan = 
            "UPDATE van SET id_modelo_fk = ? WHERE placa_fk = ?";
            
    private String delete = "DELETE FROM veiculo WHERE placa = ?";

    private String deleteAll = "DELETE v FROM veiculo v JOIN van vn ON v.placa = vn.placa_fk";

    private VanDAOsql() {}

    public static VanDAOsql getVanDAOsql() {
        if (vanDaoSql == null) {
            vanDaoSql = new VanDAOsql();
        }
        return vanDaoSql;
    }
    
    private Van getVanFromResultSet(ResultSet rs) throws SQLException, IOException {
        Marca marca = Marca.valueOf(rs.getString("nome_marca").toUpperCase());
        Estado estado = Estado.valueOf(rs.getString("nome_estado").toUpperCase());
        Categoria categoria = Categoria.valueOf(rs.getString("nome_categoria").toUpperCase());
        ModeloVan modelo = ModeloVan.valueOf(rs.getString("nome_modelo").toUpperCase());
        double valorCompra = rs.getDouble("valor_compra");
        String placa = rs.getString("placa");
        int ano = rs.getInt("ano");

        Van van = new Van(marca, estado, categoria, modelo, null, valorCompra, placa, ano);

        long idLocacao = rs.getLong("id_locacao_fk");
        if (!rs.wasNull()) {
            LocacaoDAO locacaoDAO = DaoFactory.getLocacaoDAO();
            Locacao locacao = locacaoDAO.getById(idLocacao);
            van.setLocacao(locacao);
        }

        return van;
    }
    
    @Override
    public List<Van> getAll() throws SQLException, IOException {
        List<Van> lista = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement st = con.prepareStatement(selectAll);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                lista.add(getVanFromResultSet(rs));
            }
        }
        return lista;
    }

    @Override
    public Van getByPlaca(String placa) throws SQLException, IOException {
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement st = con.prepareStatement(selectByPlaca)) {
            st.setString(1, placa);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return getVanFromResultSet(rs);
                }
            }
        }
        return null;
    }

    @Override
    public void add(Van van) throws SQLException, IOException, Exception {
        Connection con = null;
        try {
            con = ConnectionFactory.getConnection();
            con.setAutoCommit(false);
            
            if (getByPlaca(van.getPlaca()) != null) {
                throw new Exception("Van com a placa " + van.getPlaca() + " já existe.");
            }
            
            try (PreparedStatement stVeiculo = con.prepareStatement(insertVeiculo)) {
                stVeiculo.setString(1, van.getPlaca());
                stVeiculo.setInt(2, van.getMarca().ID_MARCA);
                stVeiculo.setInt(3, van.getEstado().ID_ESTADO);
                stVeiculo.setInt(4, van.getCategoria().ID_CATEGORIA);
                stVeiculo.setNull(5, java.sql.Types.INTEGER);
                stVeiculo.setDouble(6, van.getValorDeCompra());
                stVeiculo.setInt(7, van.getAno());
                stVeiculo.executeUpdate();
            }

            try (PreparedStatement stVan = con.prepareStatement(insertVan)) {
                stVan.setString(1, van.getPlaca());
                stVan.setInt(2, van.getModelo().ID_MODELO_VAN);
                stVan.executeUpdate();
            }
            
            con.commit();
        } catch (SQLException | IOException e) {
            if (con != null) con.rollback();
            throw e;
        } catch (Exception e) {
            if (con != null) con.rollback();
            throw e;
        } finally {
            if (con != null) con.close();
        }
    }

    @Override
    public void update(Van van) throws SQLException, IOException, Exception {
        if (getByPlaca(van.getPlaca()) == null) {
            throw new Exception("Van com a placa " + van.getPlaca() + " não encontrada.");
        }
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stVeiculo = con.prepareStatement(updateVeiculo);
             PreparedStatement stVan = con.prepareStatement(updateVan)) {
            
            stVeiculo.setInt(1, van.getMarca().ID_MARCA);
            stVeiculo.setInt(2, van.getEstado().ID_ESTADO);
            stVeiculo.setInt(3, van.getCategoria().ID_CATEGORIA);
            if (van.getLocacao() != null) {
                stVeiculo.setLong(4, van.getLocacao().getIdLocacao());
            } else {
                stVeiculo.setNull(4, java.sql.Types.BIGINT);
            }
            stVeiculo.setDouble(5, van.getValorDeCompra());
            stVeiculo.setInt(6, van.getAno());
            stVeiculo.setString(7, van.getPlaca());
            stVeiculo.executeUpdate();

            stVan.setInt(1, van.getModelo().ID_MODELO_VAN);
            stVan.setString(2, van.getPlaca());
            stVan.executeUpdate();
        }
    }

    @Override
    public void delete(Van van) throws SQLException, IOException, Exception {
        if (getByPlaca(van.getPlaca()) == null) {
            throw new Exception("Van com a placa " + van.getPlaca() + " não encontrada.");
        }
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement st = con.prepareStatement(delete)) {
            st.setString(1, van.getPlaca());
            st.executeUpdate();
        }
    }

    @Override
    public void deleteAll() throws SQLException, IOException {
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement st = con.prepareStatement(deleteAll)) {
            st.executeUpdate();
        }
    }
}