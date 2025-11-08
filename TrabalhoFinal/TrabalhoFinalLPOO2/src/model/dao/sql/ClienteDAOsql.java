package model.dao.sql;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Cliente;
import model.dao.ConnectionFactory;
import model.dao.interfaces.ClienteDAO;


public class ClienteDAOsql implements ClienteDAO {
    
    private static ClienteDAOsql clienteDaoSql;
    
    private String selectAll = 
            "SELECT * " +
            "FROM cliente";
    
    private String selectByCpf = 
            "SELECT * " +
            "FROM cliente " +
            "WHERE cpf = ?";
    
    private String insert = 
            "INSERT INTO cliente(cpf, nome, sobrenome, rg, endereco) " +
            "VALUES (?, ?, ?, ?, ?)";
    
    private String update = 
            "UPDATE cliente " +
            "SET " +
                "nome = ?, " +
                "sobrenome = ?, " +
                "rg = ?, " +
                "endereco = ? " +
            "WHERE cpf = ? ";
    
    private String delete = 
            "DELETE FROM cliente " +
            "WHERE cpf = ?";
    
    private String deleteAll = 
            "DELETE FROM cliente";
    
    private ClienteDAOsql(){}
    
    public static ClienteDAOsql getClienteDAOsql(){
        if(clienteDaoSql==null){
            clienteDaoSql = new ClienteDAOsql();
        }
        return clienteDaoSql;
    }
    
    @Override
    public List<Cliente> getAll() throws SQLException, IOException {
        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement st = con.prepareStatement(selectAll);)
        {
            ResultSet rs = st.executeQuery();
            List<Cliente> lista = new ArrayList<>();
            
            while(rs.next()){
                long cpfCliente = rs.getLong("cpf");
                String nomeCliente = rs.getString("nome");
                String sobreNomeCliente = rs.getString("sobrenome");
                int rgCliente = rs.getInt("rg");
                String enderecoCliente = rs.getString("endereco");
                
                Cliente cliente = new Cliente(
                    nomeCliente,
                    sobreNomeCliente,
                    rgCliente,
                    cpfCliente,
                    enderecoCliente
                );
                        
                lista.add(cliente);
            }
            
            return lista;
        }
    }

    @Override
    public Cliente getByCpf(Long cpf) throws SQLException, IOException {
        try(
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement st = con.prepareStatement(selectByCpf);)
        {
            st.setLong(1, cpf);
            ResultSet rs = st.executeQuery();
            Cliente cliente = null;
            
            if(rs.next()){
                long cpfCliente = rs.getLong("cpf");
                String nomeCliente = rs.getString("nome");
                String sobreNomeCliente = rs.getString("sobrenome");
                int rgCliente = rs.getInt("rg");
                String enderecoCliente = rs.getString("endereco");
                
                cliente = new Cliente(
                    nomeCliente,
                    sobreNomeCliente,
                    rgCliente,
                    cpfCliente,
                    enderecoCliente
                );
                
                if(rs.next()){
                    cliente = null;
                    System.gc();
                }
            }
            return cliente;
        }
    }

    @Override
    public void add(Cliente cliente) throws SQLException, IOException {
        try(
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement st = con.prepareStatement(insert))
        {
            st.setLong(1, cliente.getCpf());
            st.setString(2, cliente.getNome());
            st.setString(3, cliente.getSobrenome());
            st.setInt(4, cliente.getRg());
            st.setString(5, cliente.getEndereco());
            st.executeUpdate();
        }
    }

    @Override
    public void update(Cliente cliente) throws SQLException, IOException {
        try(
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement st = con.prepareStatement(update))
        {
            st.setLong(5, cliente.getCpf());
            st.setString(1, cliente.getNome());
            st.setString(2, cliente.getSobrenome());
            st.setInt(3, cliente.getRg());
            st.setString(4, cliente.getEndereco());
            st.executeUpdate();
        }
    }

    @Override
    public void delete(Cliente cliente) throws SQLException, IOException {
        try(
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement st = con.prepareStatement(delete))
        {
            st.setLong(1, cliente.getCpf());
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
