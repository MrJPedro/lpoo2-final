package model.dao.sql;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
    
    private String selectAll = 
            "SELECT * " +
            "FROM locacao";
    
    private String selectById = 
            "SELECT * " +
            "FROM locacao " +
            "WHERE id_locacao = ?";
    
    private String insert = 
            "INSERT INTO locacao(id_locacao, dias_locacao, valor_locacao, data_locacao, cpf_cliente_fk) " +
            "VALUES (?, ?, ?, ?, ?)";
    
    private String update = 
            "UPDATE locacao " +
            "SET " +
                "dias_locacao = ?, " +
                "valor_locacao = ?, " +
                "data_locacao = ?, " +
                "cpf_cliente = ? " +
            "WHERE id_locacao = ? ";
    
    private String delete = 
            "DELETE FROM locacao " +
            "WHERE id_locacao = ?";
    
    private String deleteAll = 
            "DELETE FROM locacao";
    
    private LocacaoDAOsql(){}
    
    public static LocacaoDAOsql getLocacaoDAOsql(){
        
        if(locacaoDaoSql==null){
            locacaoDaoSql = new LocacaoDAOsql();
            locacaoDaoSql.clienteDao = DaoFactory.getClienteDAO();
        }
        return locacaoDaoSql;
    }
    
    @Override
    public List<Locacao> getAll() throws SQLException, IOException {
        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement st = con.prepareStatement(selectAll);)
        {
            ResultSet rs = st.executeQuery();
            List<Locacao> lista = new ArrayList<>();
            
            while(rs.next()){
                int idLocacao = rs.getInt("id_locacao");
                int diasLocacao = rs.getInt("dias_locacao");
                double valorLocacao = rs.getDouble("valor_locacao");
                
                Date dataLocacaoDate = rs.getDate("data_locacao");
                LocalDate dataLocacao = null;
                if(dataLocacaoDate!=null){
                    dataLocacao = LocalDate.ofInstant(
                            dataLocacaoDate.toInstant(),
                            ZoneId.systemDefault());
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
    public Locacao getById(int idLocacao) throws SQLException, IOException {
        try(
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement st = con.prepareStatement(selectById);)
        {
            st.setInt(1,idLocacao);
            ResultSet rs = st.executeQuery();
            Locacao locacao = null;
            
            if(rs.next()){
                int diasLocacao = rs.getInt("dias_locacao");
                double valorLocacao = rs.getDouble("valor_locacao");
                
                Date dataLocacaoDate = rs.getDate("data_locacao");
                LocalDate dataLocacao = null;
                if(dataLocacaoDate!=null){
                    dataLocacao = LocalDate.ofInstant(
                            dataLocacaoDate.toInstant(),
                            ZoneId.systemDefault());
                }
                
                long cpfCliente = rs.getLong("cpf_cliente_fk");
                
                Cliente clienteLocacao = clienteDao.getByCpf(cpfCliente);
                
                locacao = new Locacao(idLocacao, diasLocacao, valorLocacao, dataLocacao, clienteLocacao);
                
                
                if(rs.next()){
                    locacao = null;
                    System.gc();
                }
            }
            return locacao;
        }
    }

    @Override
    public void add(Locacao locacao) throws SQLException, IOException {
        try(
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement st = con.prepareStatement(insert))
        {
            st.setLong(1, Instant.now().toEpochMilli());
            st.setInt(2, locacao.getDias());
            st.setDouble(3, locacao.getValor());
            
            Date dataLocacao = Date.valueOf(locacao.getData());
            st.setDate(4, dataLocacao);
            st.setLong(5, locacao.getCliente().getCpf());
            st.executeUpdate();
        }
    }

    @Override
    public void update(Locacao locacao) throws SQLException, IOException {
        try(
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement st = con.prepareStatement(update))
        {
            st.setInt(1, locacao.getDias());
            st.setDouble(2, locacao.getValor());
            
            Date dataLocacao = Date.valueOf(locacao.getData());
            st.setDate(3, dataLocacao);
            
            st.setLong(4, locacao.getCliente().getCpf());
            st.setInt(5, locacao.getIdLocacao());
            st.executeUpdate();
        }
    }

    @Override
    public void delete(Locacao locacao) throws SQLException, IOException {
        try(
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement st = con.prepareStatement(delete))
        {
            st.setInt(1, locacao.getIdLocacao());
            st.executeUpdate();
        }
    }

    @Override
    public void deleteAll() throws SQLException, IOException {
        try(
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement st = con.prepareStatement(deleteAll))
        {
            st.executeUpdate();
        }
    }
    
}
