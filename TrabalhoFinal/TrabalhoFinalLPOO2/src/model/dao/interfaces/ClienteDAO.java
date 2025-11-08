
package model.dao.interfaces;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import model.Cliente;

public interface ClienteDAO {
    public List<Cliente> getAll()throws SQLException, IOException;
    public Cliente getByCpf(Long cpf) throws SQLException, IOException;
    public void add(Cliente cliente) throws SQLException, IOException;
    public void update(Cliente cliente) throws SQLException, IOException;
    public void delete(Cliente cliente) throws SQLException, IOException;
    public void deleteAll() throws SQLException, IOException;
}
