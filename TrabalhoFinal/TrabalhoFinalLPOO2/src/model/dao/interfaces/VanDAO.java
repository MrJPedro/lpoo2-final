package model.dao.interfaces;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import model.Van;


public interface VanDAO {
    public List<Van> getAll() throws SQLException, IOException;
    public Van getByID() throws SQLException, IOException;
    public void add(Van van) throws SQLException, IOException;
    public void update(Van van) throws SQLException, IOException;
    public void delete(Van van) throws SQLException, IOException;
    public void deleteAll() throws SQLException, IOException;
}
