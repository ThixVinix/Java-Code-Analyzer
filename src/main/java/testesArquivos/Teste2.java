package testesArquivos;

public abstract class Teste2 {

	public int variavelGlobal1 = 1;
	
	public Teste2() {
		
		String variavelLocal1 = " /* "; // I see her face... 
		System.out.println(" /* "); /* Diamond 2
		/*
		 * 
		 */
		/**     **///// abra�os fraternos!
		/***/
		//
		//TESTE COMENTARIO
		int variavelLocal2 = 1;
		
		System.out.println("Hello, World!  "
				+ "");
		System.out.println("Hello, World!"); /* Aqui j� era esperado n�o remover o coment�rio!
		VAI DAR MERDA!!!!
		* Merda 2
		  Merda 3
	    * Merda 4	  
		  Merda 5 */
		/*Aqui � para remover!*/
		//////////
		/*
		
		 * sdsadsadasdsad
		Coment�rio ixpertinho
		 **/ 
	}
	
	public static int outroMetodo() { return 1;}
	
	public static int outroMetodo2() { 
		System.out.println();
		return 1;
		}
	
	public static int outroMetodo3() { return 1;}
	
	 synchronized int outroMetodo4() { return 1;}
	 
	 static int outroMetodo5() { return 1;}
	 
	 int outroMetodo6() { return 1;}
	 
//	 abstract void outroMetodo7();   ESTE METODO NAO ESTA SENDO IDENTIFICADO COMO METODO
}


class outraClasse1 {
	/*
	 * 
	 Testando em outras classes!
	 */
}

//class outraClasse {
	/** Coment�rios n�o s�o bem-vindos neste c�digo!*/
//}

//class outraClasse3 {
	//Testando a classe
//}

//class outraClasse4 {
	/*Testando como se n�o houvesse amanh�*/
//}
