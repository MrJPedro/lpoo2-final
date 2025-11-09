package model.dao.interfaces;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import model.Van;

public interface VanDAO {
    public List<Van> getAll() throws SQLException, IOException;
    public Van getByPlaca(String placa) throws SQLException, IOException;
    public void add(Van van) throws SQLException, IOException, Exception;
    public void update(Van van) throws SQLException, IOException, Exception;
    public void delete(Van van) throws SQLException, IOException, Exception;
    public void deleteAll() throws SQLException, IOException;
}