
package model.dao.sql;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import misc.Locacao;
import model.Cliente;
import model.dao.ConnectionFactory;
import model.dao.DaoFactory;
import model.dao.interfaces.ClienteDAO;
import model.dao.interfaces.LocacaoDAO;

public class LocacaoDAOsql implements LocacaoDAO {

    private static LocacaoDAOsql locacaoDaoSql;

    private ClienteDAO clienteDao;

    private String selectAll = "SELECT * " +
            "FROM locacao";

    private String selectById = "SELECT * " +
            "FROM locacao " +
            "WHERE id_locacao = ?";

    private String insert = "INSERT INTO locacao(dias_locacao, valor_locacao, data_locacao, cpf_cliente_fk) " +
            "VALUES (?, ?, ?, ?)";

    private String update = "UPDATE locacao " +
            "SET " +
            "dias_locacao = ?, " +
            "valor_locacao = ?, " +
            "data_locacao = ?, " +
            "cpf_cliente_fk = ? " +
            "WHERE id_locacao = ? ";

    private String delete = "DELETE FROM locacao " +
            "WHERE id_locacao = ?";

    private String deleteAll = "DELETE FROM locacao";

    private LocacaoDAOsql() {
        this.clienteDao = DaoFactory.getClienteDAO();
    }

    public static LocacaoDAOsql getLocacaoDAOsql() {
        if (locacaoDaoSql == null) {
            locacaoDaoSql = new LocacaoDAOsql();
        }
        return locacaoDaoSql;
    }

    @Override
    public List<Locacao> getAll() throws SQLException, IOException {
        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement st = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            ResultSet rs = st.executeQuery();
            List<Locacao> lista = new ArrayList<>();

            while (rs.next()) {
                long idLocacao = rs.getLong("id_locacao");
                int diasLocacao = rs.getInt("dias_locacao");
                double valorLocacao = rs.getDouble("valor_locacao");

                Date dataLocacaoDate = rs.getDate("data_locacao");
                LocalDate dataLocacao = null;
                if (dataLocacaoDate != null) {
                    dataLocacao = dataLocacaoDate.toLocalDate();
                }

                long cpfCliente = rs.getLong("cpf_cliente_fk");

                Cliente clienteLocacao = clienteDao.getByCpf(cpfCliente);

                Locacao locacao = new Locacao(idLocacao, diasLocacao, valorLocacao, dataLocacao, clienteLocacao);

                lista.add(locacao);
            }

            return lista;
        }
    }

    @Override
    public Locacao getById(long idLocacao) throws SQLException, IOException {
        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement st = con.prepareStatement(selectById);) {
            st.setLong(1, idLocacao);
            ResultSet rs = st.executeQuery();
            Locacao locacao = null;

            if (rs.next()) {
                int diasLocacao = rs.getInt("dias_locacao");
                double valorLocacao = rs.getDouble("valor_locacao");

                Date dataLocacaoDate = rs.getDate("data_locacao");
                LocalDate dataLocacao = null;
                if (dataLocacaoDate != null) {
                    dataLocacao = dataLocacaoDate.toLocalDate();
                }

                long cpfCliente = rs.getLong("cpf_cliente_fk");

                Cliente clienteLocacao = clienteDao.getByCpf(cpfCliente);

                locacao = new Locacao(idLocacao, diasLocacao, valorLocacao, dataLocacao, clienteLocacao);
            }
            return locacao;
        }
    }

    @Override

    public void add(Locacao locacao) throws SQLException, IOException {
        String sql = "INSERT INTO locacao(dias_locacao, valor_locacao, data_locacao, cpf_cliente_fk) VALUES (?, ?, ?, ?)";

        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1, locacao.getDias());
            st.setDouble(2, locacao.getValor());
            st.setDate(3, Date.valueOf(locacao.getData()));
            st.setLong(4, locacao.getCliente().getCpf());

            st.executeUpdate();

            try (ResultSet generatedKeys = st.getGeneratedKeys()) {
                if (generatedKeys.next()) {

                    long idGerado = generatedKeys.getLong(1);
                    locacao.setIdLocacao(idGerado);

                } else {
                    throw new SQLException("Falha ao criar locação, nenhum ID obtido.");
                }
            }
        }
    }

    @Override
    public void update(Locacao locacao) throws SQLException, IOException {
        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement st = con.prepareStatement(update)) {
            st.setInt(1, locacao.getDias());
            st.setDouble(2, locacao.getValor());

            st.setDate(3, Date.valueOf(locacao.getData()));

            st.setLong(4, locacao.getCliente().getCpf());
            st.setLong(5, locacao.getIdLocacao());
            st.executeUpdate();
        }
    }

    @Override
    public void delete(Locacao locacao) throws SQLException, IOException {
        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement st = con.prepareStatement(delete)) {
            st.setLong(1, locacao.getIdLocacao());
            st.executeUpdate();
        }
    }

    @Override
    public void deleteAll() throws SQLException, IOException {
        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement st = con.prepareStatement(deleteAll)) {
            st.executeUpdate();
        }
    }

}