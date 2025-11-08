
package model.dao.sql;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import model.Motocicleta;
import model.dao.interfaces.MotocicletaDAO;


public class MotocicletaDAOsql implements MotocicletaDAO {

    private static MotocicletaDAOsql motocicletaDaoSql;
    
    private String selectAll;
    private String selectById;
    private String insert;
    private String update;
    private String delete;
    private String deleteAll;
    
    private MotocicletaDAOsql(){}
    
    public static MotocicletaDAOsql getMotocicletaDAOsql(){
        if(motocicletaDaoSql==null){
            motocicletaDaoSql = new MotocicletaDAOsql();
            return motocicletaDaoSql;
        } else {
            return motocicletaDaoSql;
        }
    }
    
    @Override
    public List<Motocicleta> getAll() throws SQLException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Motocicleta getByID() throws SQLException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void add(Motocicleta motocicleta) throws SQLException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Motocicleta motocicleta) throws SQLException, IOException {
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
    
}
