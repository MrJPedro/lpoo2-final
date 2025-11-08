package model.dao.interfaces;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import model.Motocicleta;


public interface MotocicletaDAO {
    public List<Motocicleta> getAll() throws SQLException, IOException;
    public Motocicleta getByID() throws SQLException, IOException;
    public void add(Motocicleta motocicleta) throws SQLException, IOException;
    public void update(Motocicleta motocicleta) throws SQLException, IOException;
    public void delete(Motocicleta motocicleta) throws SQLException, IOException;
    public void deleteAll() throws SQLException, IOException;
}
