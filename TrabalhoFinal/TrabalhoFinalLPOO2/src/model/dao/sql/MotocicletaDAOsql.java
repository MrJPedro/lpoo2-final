
package model.dao.sql;

import enums.Categoria;
import enums.Estado;
import enums.Marca;
import enums.ModeloMotocicleta;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import misc.Locacao;
import model.Motocicleta;
import model.dao.ConnectionFactory;
import model.dao.DaoFactory;
import model.dao.interfaces.LocacaoDAO;
import model.dao.interfaces.MotocicletaDAO;

public class MotocicletaDAOsql implements MotocicletaDAO {

    private static MotocicletaDAOsql motocicletaDaoSql;

    private String selectAll = 
        "SELECT v.*, mt.id_modelo_fk, m.nome_marca, e.nome_estado, c.nome_categoria, mm.nome_modelo " +
        "FROM veiculo v " +
        "JOIN motocicleta mt ON v.placa = mt.placa_fk " +
        "JOIN marca_veiculo m ON v.id_marca_fk = m.id_marca " +
        "JOIN estado_veiculo e ON v.id_estado_fk = e.id_estado " +
        "JOIN categoria_veiculo c ON v.id_categoria_fk = c.id_categoria " +
        "JOIN modelo_motocicleta mm ON mt.id_modelo_fk = mm.id_modelo";

    private String selectByPlaca = selectAll + " WHERE v.placa = ?";

    private String insertVeiculo = 
            "INSERT INTO " +
            "veiculo (placa, id_marca_fk, id_estado_fk, id_categoria_fk, id_locacao_fk, valor_compra, ano) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private String insertMotocicleta = 
            "INSERT INTO motocicleta (placa_fk, id_modelo_fk) VALUES (?, ?)";

    private String updateVeiculo =
        "UPDATE veiculo SET id_marca_fk = ?, id_estado_fk = ?, id_categoria_fk = ?, id_locacao_fk = ?, valor_compra = ?, ano = ? WHERE placa = ?";
    
    private String updateMotocicleta = 
            "UPDATE motocicleta SET id_modelo_fk = ? WHERE placa_fk = ?";
            
    private String delete = "DELETE FROM veiculo WHERE placa = ?";

    private String deleteAll = "DELETE v FROM veiculo v JOIN motocicleta mt ON v.placa = mt.placa_fk";

    private MotocicletaDAOsql() {}

    public static MotocicletaDAOsql getMotocicletaDAOsql() {
        if (motocicletaDaoSql == null) {
            motocicletaDaoSql = new MotocicletaDAOsql();
        }
        return motocicletaDaoSql;
    }
    
    private Motocicleta getMotocicletaFromResultSet(ResultSet rs) throws SQLException, IOException {
        Marca marca = Marca.valueOf(rs.getString("nome_marca").toUpperCase());
        Estado estado = Estado.valueOf(rs.getString("nome_estado").toUpperCase());
        Categoria categoria = Categoria.valueOf(rs.getString("nome_categoria").toUpperCase());
        ModeloMotocicleta modelo = ModeloMotocicleta.valueOf(rs.getString("nome_modelo").toUpperCase());
        double valorCompra = rs.getDouble("valor_compra");
        String placa = rs.getString("placa");
        int ano = rs.getInt("ano");

        Motocicleta motocicleta = new Motocicleta(marca, estado, categoria, modelo, null, valorCompra, placa, ano);
        
        long idLocacao = rs.getLong("id_locacao_fk");
        if (!rs.wasNull()) {
            LocacaoDAO locacaoDAO = DaoFactory.getLocacaoDAO();
            Locacao locacao = locacaoDAO.getById(idLocacao);
            motocicleta.setLocacao(locacao);
        }

        return motocicleta;
    }
    
    @Override
    public List<Motocicleta> getAll() throws SQLException, IOException {
        List<Motocicleta> lista = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement st = con.prepareStatement(selectAll);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                lista.add(getMotocicletaFromResultSet(rs));
            }
        }
        return lista;
    }

    @Override
    public Motocicleta getByPlaca(String placa) throws SQLException, IOException {
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement st = con.prepareStatement(selectByPlaca)) {
            st.setString(1, placa);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return getMotocicletaFromResultSet(rs);
                }
            }
        }
        return null;
    }

    @Override
    public void add(Motocicleta motocicleta) throws SQLException, IOException, Exception {
        Connection con = null;
        try {
            con = ConnectionFactory.getConnection();
            con.setAutoCommit(false);

            if (getByPlaca(motocicleta.getPlaca()) != null) {
                throw new Exception("Motocicleta com a placa " + motocicleta.getPlaca() + " já existe.");
            }
            
            try (PreparedStatement stVeiculo = con.prepareStatement(insertVeiculo)) {
                stVeiculo.setString(1, motocicleta.getPlaca());
                stVeiculo.setInt(2, motocicleta.getMarca().ID_MARCA);
                stVeiculo.setInt(3, motocicleta.getEstado().ID_ESTADO);
                stVeiculo.setInt(4, motocicleta.getCategoria().ID_CATEGORIA);
                stVeiculo.setNull(5, java.sql.Types.INTEGER);
                stVeiculo.setDouble(6, motocicleta.getValorDeCompra());
                stVeiculo.setInt(7, motocicleta.getAno());
                stVeiculo.executeUpdate();
            }

            try (PreparedStatement stMotocicleta = con.prepareStatement(insertMotocicleta)) {
                stMotocicleta.setString(1, motocicleta.getPlaca());
                stMotocicleta.setInt(2, motocicleta.getModelo().ID_MODELO_MOTOCICLETA);
                stMotocicleta.executeUpdate();
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
    public void update(Motocicleta motocicleta) throws SQLException, IOException, Exception {
        if (getByPlaca(motocicleta.getPlaca()) == null) {
            throw new Exception("Motocicleta com a placa " + motocicleta.getPlaca() + " não encontrada.");
        }
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stVeiculo = con.prepareStatement(updateVeiculo);
             PreparedStatement stMotocicleta = con.prepareStatement(updateMotocicleta)) {

            stVeiculo.setInt(1, motocicleta.getMarca().ID_MARCA);
            stVeiculo.setInt(2, motocicleta.getEstado().ID_ESTADO);
            stVeiculo.setInt(3, motocicleta.getCategoria().ID_CATEGORIA);
            if (motocicleta.getLocacao() != null) {
                stVeiculo.setLong(4, motocicleta.getLocacao().getIdLocacao());
            } else {
                stVeiculo.setNull(4, java.sql.Types.BIGINT);
            }
            stVeiculo.setDouble(5, motocicleta.getValorDeCompra());
            stVeiculo.setInt(6, motocicleta.getAno());
            stVeiculo.setString(7, motocicleta.getPlaca());
            stVeiculo.executeUpdate();

            stMotocicleta.setInt(1, motocicleta.getModelo().ID_MODELO_MOTOCICLETA);
            stMotocicleta.setString(2, motocicleta.getPlaca());
            stMotocicleta.executeUpdate();
        }
    }

    @Override
    public void delete(Motocicleta motocicleta) throws SQLException, IOException, Exception {
        if (getByPlaca(motocicleta.getPlaca()) == null) {
            throw new Exception("Motocicleta com a placa " + motocicleta.getPlaca() + " não encontrada.");
        }
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement st = con.prepareStatement(delete)) {
            st.setString(1, motocicleta.getPlaca());
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