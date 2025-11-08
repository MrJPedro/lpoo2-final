package misc;

import swing.Janela;

import enums.*;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//new Janela();
                
                Estado[] estado = Estado.values();
                System.out.println(Arrays.asList(estado).contains(Estado.NOVO));
	}

}

//João Gabriel Fanti Alves
//João Pedro Gonçalves Cardoso
//Kauan Kelvin Alves Macedo
