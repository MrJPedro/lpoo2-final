package misc;

import swing.Janela;
// ===== Remover essas importações, pois foram utilizadas para testes =====
import enums.*;
import java.util.Arrays;
import java.util.List;
import model.Automovel;
import model.dao.DaoFactory;
import model.dao.interfaces.AutomovelDAO;

// ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== =====

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//new Janela();
                
                try {
                    AutomovelDAO aDao = DaoFactory.getAutomovelDAO();
                    Automovel a = aDao.getByPlaca("GGG1033");
                    System.out.println(a.getPlaca());
                } catch (Exception e){
                    System.out.println(e);
                }
                
	}

}

//João Gabriel Fanti Alves
//João Pedro Gonçalves Cardoso
//Kauan Kelvin Alves Macedo
