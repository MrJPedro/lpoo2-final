package model.dao;

import model.dao.interfaces.AutomovelDAO;
import model.dao.interfaces.ClienteDAO;
import model.dao.interfaces.EstadoDAO;
import model.dao.interfaces.LocacaoDAO;
import model.dao.interfaces.MotocicletaDAO;
import model.dao.interfaces.VanDAO;
import model.dao.sql.AutomovelDAOsql;
import model.dao.sql.ClienteDAOsql;
import model.dao.sql.EstadoDAOsql;
import model.dao.sql.LocacaoDAOsql;
import model.dao.sql.MotocicletaDAOsql;
import model.dao.sql.VanDAOsql;

public class DaoFactory {
    
    private static DaoFactory daoFactory;
    
    private DaoFactory(){};
    
    public static DaoFactory getDaoFactory(){
        if(daoFactory==null){
            daoFactory = new DaoFactory();
            return daoFactory;
        } else {
            return daoFactory;
        }
    }
    
    public static AutomovelDAO getAutomovelDAO() {
        AutomovelDAO automovelDao = AutomovelDAOsql.getAutomovelDAOsql();
        return automovelDao;
    }
    
    public static MotocicletaDAO getMotocicletaDAO() {
        MotocicletaDAO motocicletaDao = MotocicletaDAOsql.getMotocicletaDAOsql();
        return motocicletaDao;
    }
    
    public static VanDAO getVanDAO() {
        VanDAO vanDao = VanDAOsql.getVanDAOsql();
        return vanDao;
    }
    
    public static EstadoDAO getEstadoDAO(){
        EstadoDAO estadoDao = EstadoDAOsql.getEstadoDAOsql();
        return estadoDao;
    }
    
    public static ClienteDAO getClienteDAO(){
        ClienteDAO clienteDao = ClienteDAOsql.getClienteDAOsql();
        return clienteDao;
    }
    
    public static LocacaoDAO getLocacaoDAO(){
        LocacaoDAO locacaoDao = LocacaoDAOsql.getLocacaoDAOsql();
        return locacaoDao;
    }
}
