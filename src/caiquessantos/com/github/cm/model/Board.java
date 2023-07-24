package caiquessantos.com.github.cm.model;

/**
 * 
 * Classe para fazer a chamdada dos métodos da classe 'Field' para associar os vizinhos de uma casa, marcar e desmarcar uma casa, reiniciar o tabuleiro, 
 * abrir uma casa, criar os campos e Sortear as minas.
 * 
 * @author Caique Santos Santana
 * @version 1.0
 * @since Release 01 da aplicação
 * */

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import caiquessantos.com.github.cm.exeptions.ExplosionException;

public class Board {
	
	private int linhas;
	private int colunas;
	private int minas;
	
	private final List<Field> campos = new ArrayList<>();
	
	/**
	 * Construtor da classe 'Board' que faz a chamada dos métodos criaCampos, associarVizinhos e SortearMinas logo quando é executado.
	 * 
	 * @param linha - linha o tabuleiro.
	 * @param coluna = coluna do tabuleiro.
	 * @param minas - minas do tabuleiro.
	 * 
	 * 
	 * */
	public Board(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
		
		criarCampos();
		associarVizinhos();
		sortearMinas();
	}
	
	/**
	 * Método que chama o método de abrir uma casa de acordo com a linha e coluna desejada.
	 * 
	 * @author Caíque Santos Santana
	 * @param linha - linha desejada.
	 * @param coluna - coluna desejada. 
	 * */
	public void abrir(int linha, int coluna) {
		try {
			campos.parallelStream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna).findFirst().ifPresent(c -> c.abrir());
		} catch (ExplosionException e) {
			campos.forEach(c -> c.setAberto(true));
			throw e;
		}
	}
	
	/**
	 * Método que chama o método de marcar ou desmarcar uma casa de acordo com a linha e coluna desejada.
	 * 
	 * @author Caíque Santos Santana
	 * @param linha - linha desejada.
	 * @param coluna - coluna desejada. 
	 * */
	public void marcarEdesmarcar(int linha, int coluna) {
		campos.parallelStream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna).findFirst().ifPresent(c -> c.marcarEdesmarcar());
	}
	
	/**
	 * Método para criar os campos linha a linha e coluna a coluna do tabuleiro.
	 * 
	 * @author Caíque Santos Santana
	 * */
	private void criarCampos(){
		for (int linha = 0; linha < linhas; linha++) {
			for (int coluna = 0; coluna < colunas; coluna++) {
				campos.add(new Field(linha, coluna));
			}
		}	
	}
	
	/**
	 * Método para associar cada endereço de linha e coluna como vizinho de uma casa específica, trocando de casa a cada iteracão.
	 * 
	 * @author Caíque Santos Santana
	 * */
	private void associarVizinhos() {
		for (Field field1 : campos) {
			for (Field field2 : campos) {
				field1.adicionarVizinho(field2);
			}
		}
	}
	
	/**
	 * Método para sortear as casas que as minas serão geradas chamando o mkétodo minar() de acordo com o valor aleatoriamente gerado.
	 * 
	 * @author Caíque Santos Santana
	 * */
	private void  sortearMinas() {
		
		long minasColocadas = 0;
		Predicate<Field> minado = f ->f.isMinado();
		
		do {
			int aleatorio = (int) (Math.random() * campos.size());
			campos.get(aleatorio).minar();
			minasColocadas = campos.stream().filter(minado).count();
		} while (minasColocadas < minas);
	}
	
	/**
	 * Método para chamar o método que testa se o objetivo foi alcançado.
	 * 
	 * @author Caíque Santos Santana
	 * */
	public boolean objetivoAlcancado() {
		return campos.stream().allMatch(c -> c.objetivoAlcancado());
	}
	
	/**
	 * Método para chamar o método de reiniciar o tabuleiro.
	 * 
	 * @author Caíque Santos Santana
	 * */
	public void reiniciar() {
		campos.stream().forEach(r -> r.reiniciar());
		sortearMinas();
	}
	
	/**
	 * Método para gerar a interface para o usuário.
	 * 
	 * @author Caíque Santos Santana
	 * */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("  ");
		for (int i = 0; i < colunas; i++) {
			sb.append(" ");
			sb.append(i);
			sb.append(" ");
		}
		sb.append("\n");
		
		int i = 0;
		for (int linha = 0; linha < linhas; linha++) {
			sb.append(linha);
			sb.append(" ");
			for (int coluna = 0; coluna < colunas; coluna++) {
				sb.append(" ");
				sb.append(campos.get(i));
				sb.append(" ");
				i++;
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	
	

}
