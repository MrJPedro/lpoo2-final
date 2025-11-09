package model.dao.interfaces;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import misc.Locacao;

public interface LocacaoDAO {
    public List<Locacao> getAll()throws SQLException, IOException;
    public Locacao getById(long id) throws SQLException, IOException;
    public void add(Locacao locacao) throws SQLException, IOException;
    public void update(Locacao locacao) throws SQLException, IOException;
    public void delete(Locacao locacao) throws SQLException, IOException;
    public void deleteAll() throws SQLException, IOException;
}
