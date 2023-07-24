package caiquessantos.com.github.cm.vision;

/**
 * 
 * Classe responsável por executar o jogo, realizando todo seu ciclo corretamente, inclusive as impressões no console.
 * 
 * @author Caique Santos Santana
 * @version 1.0
 * @since Release 01 da aplicação
 * */

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
import caiquessantos.com.github.cm.exeptions.ExitExeption;
import caiquessantos.com.github.cm.exeptions.ExplosionException;
import caiquessantos.com.github.cm.model.Board;

public class BoardConsol {
	
	private Board board;
	private Scanner entrada = new Scanner(System.in);
	
	/**
	 * Construtor da classe.
	 * 
	 * @author Caíque Santos Santana
	 * @param board - O tabuleiro que será usado
	 */
	public BoardConsol(Board board) {
		this.board = board;
		
		executarJogo();
	}
	
	/**
	 * Métido respon´savel por fazer a execução, reexecução e finalização do jogo.
	 * 
	 * @author Caíque Santos Santana
	 */
	private void executarJogo() {
		try {
			boolean continuar = true;
			
			while(continuar) {
				CiclodoJogo();
				System.out.print("\nDeseja continua? (S/n) ");
				String resposta = entrada.nextLine();
				
				if ("n".equalsIgnoreCase(resposta)) {
					System.out.println("\nTCHAU!!!");
					continuar = false;
				} else {
					board.reiniciar();
				}
			}
		
		} catch (ExitExeption e) {
			
			System.out.println("\nTCHAU!!!");
	
		} finally {
			
			entrada.close();
		
		}
	}
	
	/**
	 * Método responsável pelo ciclo o jogo, sendo esse que coleta a informação da casa que se deseja jogar e a jogada que deverá ser executada.
	 * 
	 * @author Caíque Santos Santana
	 */
	private void CiclodoJogo() {
		try {
			while (!board.objetivoAlcancado()) {
				System.out.println();
				System.out.println(board);
				
				String digitado = capturarValorDigitado("\nInforme (x,y): ");
				
				Iterator<Integer> xy = Arrays.stream(digitado.split(",")).map(e -> Integer.parseInt(e.trim())).iterator();
				digitado = capturarValorDigitado("\n1- Abrir a casa | 2- Marcar a casa: ");
				
				if ("1".equals(digitado)) {
					board.abrir(xy.next(), xy.next());
				} else if ("2".equals(digitado)) {
					board.marcarEdesmarcar(xy.next(), xy.next());					
				}
			}
			System.out.println("\nVOCÊ GANHOU!!!\n");
			System.out.println(board);
		} catch (ExplosionException e) {
			System.out.println("\nVOCÊ PERDEU!!!\n");
			System.out.println(board);
		}
	}
	
	/**
	 * Método responsável pela captura de valores digitados pelo o usuário.
	 * 
	 * @author Caíque Santos Santana
	 */
	private String capturarValorDigitado(String texto) {
		System.out.print(texto);
		String digitado = entrada.nextLine();
		
		if ("sair".equalsIgnoreCase(digitado)) {
		 throw new ExitExeption();
		}
		
		return digitado;
	}
}
