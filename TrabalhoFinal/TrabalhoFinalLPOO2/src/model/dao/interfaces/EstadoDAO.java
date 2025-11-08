package model.dao.interfaces;

import enums.Estado;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public interface EstadoDAO {
    public List<Estado> getAll()throws SQLException, IOException;
    public Estado getByNome() throws SQLException, IOException;
    /*
    public void add(Estado estado) throws SQLException, IOException;
    public void update(Estado estado) throws SQLException, IOException;
    public void delete() throws SQLException, IOException;
    public void deleteAll() throws SQLException, IOException;
    */
}
