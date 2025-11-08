package model.dao.sql;

import enums.Estado;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import model.dao.interfaces.EstadoDAO;
import model.dao.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;



public class EstadoDAOsql implements EstadoDAO {
    
    private static EstadoDAOsql estadoDaoSql;

    private String selectAll = 
            "SELECT " +
            "id_estado, " +
            "nome_estado " +
            "FROM estado_veiculo";
    
    private String selectByNome = 
            "SELECT " +
            "id_estado, " +
            "nome_estado " +
            "FROM estado_veiculo " +
            "WHERE nome_estado = ? ";
    
    private EstadoDAOsql(){}
    
    public static EstadoDAOsql getEstadoDAOsql(){
        if(estadoDaoSql==null){
            estadoDaoSql = new EstadoDAOsql();
            return estadoDaoSql;
        } else {
            return estadoDaoSql;
        }
    }
    
    @Override
    public List<Estado> getAll() throws SQLException, IOException {
        try(
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement st = con.prepareStatement(this.selectAll);
            )
        {
            ResultSet rs = st.executeQuery();
            List<Estado> lista = new ArrayList<>();
            while(rs.next()){
                int idEstado = rs.getInt("id_estado");
                String nomeEstado = rs.getString("nome_estado");
                //Estado estado = new Estado(idEstado, nomeEstado);
                //lista.add(estado);
            }
            
            return lista;
        }
    }

    @Override
    public Estado getByNome() throws SQLException, IOException {
        try(
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement st = con.prepareStatement(this.selectByNome);
            ){
                ResultSet rs = st.executeQuery();
                Estado estado = null;
                if(rs.next()){
                    int idEstado = rs.getInt("id_estado");
                    String nomeEstado = rs.getString("nome_estado");
                    //estado = new Estado(idEstado, nomeEstado);
                    if(rs.next()){
                        estado = null;
                    }
                }
                return estado;
        }
    }

    /*
    @Override
    public void add(Estado estado) throws SQLException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Estado estado) throws SQLException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete() throws SQLException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteAll() throws SQLException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    */
    
}
