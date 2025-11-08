package model.dao.interfaces;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import model.Automovel;


public interface AutomovelDAO {
    public List<Automovel> getAll()throws SQLException, IOException;
    public Automovel getByPlaca(String placa) throws SQLException, IOException;
    public void add(Automovel automovel) throws SQLException, IOException;
    public void update(Automovel automovel) throws SQLException, IOException;
    public void delete(Automovel automovel) throws SQLException, IOException;
    public void deleteAll() throws SQLException, IOException;
}
