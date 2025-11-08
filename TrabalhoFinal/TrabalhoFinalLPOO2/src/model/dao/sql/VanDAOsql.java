package model.dao.sql;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import model.Van;
import model.dao.interfaces.VanDAO;

public class VanDAOsql implements VanDAO {
    
    private static VanDAOsql vanDaoSql;
    
    private String selectAll;
    private String selectById;
    private String insert;
    private String update;
    private String delete;
    private String deleteAll;
    
    private VanDAOsql(){}
    
    public static VanDAOsql getVanDAOsql(){
        if(vanDaoSql==null){
            vanDaoSql = new VanDAOsql();
            return vanDaoSql;
        } else {
            return vanDaoSql;
        }
    }
    
    @Override
    public List<Van> getAll() throws SQLException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Van getByID() throws SQLException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void add(Van van) throws SQLException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Van van) throws SQLException, IOException {
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
