package swing;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Cliente;
import misc.Veiculo;
import java.time.ZoneId;

public class Tabela<T> extends AbstractTableModel {

	private String[] nomesColunas;
	private List<T> lista;

	Tabela(String[] nomesColunas, List<T> lista) {
		this.nomesColunas = nomesColunas;
		this.lista = lista;
	}

	@Override
	public int getRowCount() {
		if (lista == null)
			return 0;
		else
			return lista.size();
	}

	@Override
	public int getColumnCount() {
		return nomesColunas.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		T item = lista.get(rowIndex);
		String coluna = nomesColunas[columnIndex];

		if (item instanceof Cliente) {
			Cliente cliente = (Cliente) item;
			switch (coluna) {
				case "NOME":
					return cliente.getNome();
				case "SOBRENOME":
					return cliente.getSobrenome();
				case "RG":
					String rgStr = String.format("%09d", cliente.getRg());
					return rgStr.substring(0, 2) + "." + rgStr.substring(2, 5) + "." + rgStr.substring(5, 8) + "-"
							+ rgStr.substring(8, 9);
				case "CPF":
					String cpfStr = String.format("%011d", cliente.getCpf());
					return cpfStr.substring(0, 3) + "." + cpfStr.substring(3, 6) + "." + cpfStr.substring(6, 9) + "-"
							+ cpfStr.substring(9, 11);
				case "ENDEREÇO":
					return cliente.getEndereco();
				default:
					return "-";
			}
		} else if (item instanceof Veiculo) {
			Veiculo veiculo = (Veiculo) item;
			switch (coluna) {
				case "MARCA":
					return veiculo.getMarca();
				case "MODELO":
					return veiculo.getModelo();
				case "ESTADO":
					return veiculo.getEstado();
				case "CATEGORIA":
					return veiculo.getCategoria();
				case "PLACA":
					String placaStr = veiculo.getPlaca().substring(0, 3) + "-" + veiculo.getPlaca().substring(3);
					return placaStr;
				case "ANO":
					return veiculo.getAno();
				case "PRECO DIARIA":
					String valorStr = String.format("R$%.2f", veiculo.getValorDiariaLocacao());
					valorStr = valorStr.replace(".", ",");
					return valorStr;
				case "NOME CLIENTE":
					if (veiculo.getLocacao() != null && veiculo.getLocacao().getCliente() != null) {
						return veiculo.getLocacao().getCliente().getNome();
					}
					return "-";
				case "DATA":
					if (veiculo.getLocacao() != null && veiculo.getLocacao().getData() != null) {
						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
						return formatter.format(java.util.Date.from(veiculo.getLocacao().getData().atStartOfDay(ZoneId.systemDefault()).toInstant()));
					}
					return "-";
				case "DIAS":
					if (veiculo.getLocacao() != null)
						return veiculo.getLocacao().getDias();
					return "-";
				case "PRECO VENDA":
					String valorStr2 = String.format("R$%.2f", veiculo.getValorParaVenda());
					valorStr2 = valorStr2.replace(".", ",");
					return valorStr2;
				case "VALOR LOCACAO":
					if (veiculo.getLocacao() != null) {
						String valorStr3 = String.format("R$%.2f", veiculo.getLocacao().getValor());
						valorStr3 = valorStr3.replace(".", ",");
						return valorStr3;
					}
					return "-";
				default:
					return "-";
			}
		}

		return "-";
	}

	public void atualizarTabela(List<T> novaLista) {
		this.lista = novaLista;
		fireTableDataChanged();
	}

	public List<T> getDados() {
		return lista;
	}
}