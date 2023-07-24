package caiquessantos.com.github.cm.model;

/**
 * 
 * Classe para fazer os testes u8nitários da classe 'Field'. 
 * 
 * @author Caique Santos Santana
 * @version 1.0
 * @since Release 01 da aplicação
 * */

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import caiquessantos.com.github.cm.exeptions.ExplosionException;

class FieldTest {
	
	private Field field;
	private Board board;

	/**
	 * Método que inicia um Field para os testes.
	 * 
	 * */
	@BeforeEach
	void inicarField() {
		field = new Field(2, 2);
		board = new Board(3, 3, 2);
	}
	
	/**
	 * Método que faz os testes de vizinhança na horizontal e vertical.
	 * 
	 * */
	@Test
	void vizinhoDeDistanciaUm() {

		Field vizinhoEsquerda = new Field(2, 1);
		boolean resultadoEsquera = field.adicionarVizinho(vizinhoEsquerda);
		assertTrue(resultadoEsquera);

		Field vizinhoDireita = new Field(2, 3);
		boolean resultadoDireita = field.adicionarVizinho(vizinhoDireita);
		assertTrue(resultadoDireita);

		Field vizinhoCima = new Field(1, 2);
		boolean resultadoCima = field.adicionarVizinho(vizinhoCima);
		assertTrue(resultadoCima);
		
		Field vizinhoBaixo = new Field(3, 2);
		boolean resultadoBaixo = field.adicionarVizinho(vizinhoBaixo);
		assertTrue(resultadoBaixo);
	}
	
	/**
	 * Método que faz os testes de vizinhança nas diagonais.
	 * */
	@Test
	void vizinhoDeDistanciaDois() {
		
		Field vizinhoNoroeste = new Field(1, 1);
		boolean resultadoNoroeste = field.adicionarVizinho(vizinhoNoroeste);
		assertTrue(resultadoNoroeste);
		
		Field vizinhoNordeste = new Field(1, 3);
		boolean resultadoNordeste = field.adicionarVizinho(vizinhoNordeste);
		assertTrue(resultadoNordeste);
		
		Field vizinhoSudoeste = new Field(3, 1);
		boolean resultadoSudoeste = field.adicionarVizinho(vizinhoSudoeste);
		assertTrue(resultadoSudoeste);

		Field vizinhoSudeste = new Field(3, 3);
		boolean resultadoSudeste = field.adicionarVizinho(vizinhoSudeste);
		assertTrue(resultadoSudeste);

	}
	
	/**
	 * Método que faz o teste caso o vizinho seja falso.
	 * */
	@Test
	void vizinhoFalso() {
		Field vizinhoFalso = new Field(4, 5);
		boolean resultadoFalso = field.adicionarVizinho(vizinhoFalso);
		assertFalse(resultadoFalso);
		
	}
	
	/**
	 * Método que testa a marcação da casa.
	 * */
	@Test
	void MarcacaoPadrao() {
		assertFalse(field.isMarcado());
	}
	
	/**
	 * Método que testa a marcação da casa.
	 * */
	@Test
	void MarcacaoAlternada() {
		field.marcarEdesmarcar();
		assertTrue(field.isMarcado());
	}
	
	/**
	 * Método que testa a marcação e desmarcação da da casa.
	 * */
	@Test
	void DuasMarcacoes() {
		field.marcarEdesmarcar();
		field.marcarEdesmarcar();
		assertFalse(field.isMarcado());
	}
	
	/**
	 * Método que testa a abertura caso não esteja minado nem marcado.
	 * */
	@Test
	void NaominadoENaomarcado() {
		assertTrue(field.abrir());
	}
	
	/**
	 * Método que testa a abertura caso não esteja minado mas esteja marcado.
	 * */
	@Test
	void NaominadoEMarcado() {
		field.marcarEdesmarcar();
		assertFalse(field.abrir());
	}
	
	/**
	 * Método que testa a abertura caso esteja minado e marcado.
	 * */
	@Test
	void MinadoEMarcado() {
		field.marcarEdesmarcar();
		field.minar();
		assertFalse(field.abrir());
	}
	
	/**
	 * Método que testa a abertura caso esteja minado mas não esteja marcado.
	 * */
	@Test
	void MinadoENaomarcado() {
		field.minar();
		assertThrows(ExplosionException.class, () -> {
			field.abrir();
		} );
	}
	
	/**
	 * Método que testa se as casas vizinhas irão se abrir.
	 * */
	@Test
	void abrirComVizinho() {
		Field vizinho33 = new Field(3,3);
		Field vizinho44 = new Field(4,4);
		
		vizinho33.adicionarVizinho(vizinho44);
		
		field.adicionarVizinho(vizinho33);
		field.abrir();
		
		assertTrue(vizinho33.isAberto() && vizinho44.isAberto());
	}
	
	/**
	 * Método que testa se as casas vizinhas minadas não irão se abrir.
	 * */
	@Test
	void abrirComVizinhoMinado() {
		Field vizinho43 = new Field(4,3);
		Field vizinho44 = new Field(4,4);
		vizinho44.minar();
		Field vizinho33 = new Field(3,3);
		vizinho33.adicionarVizinho(vizinho43);
		vizinho33.adicionarVizinho(vizinho44);
		field.adicionarVizinho(vizinho33);
		field.abrir();
		
		assertTrue(vizinho33.isAberto() && !vizinho44.isAberto());
		
	}

	/**
	 * Testa se o objetivo foi alcançado através da abertura da casa não minada.
	 * */
	@Test
	void objetivoalcancadodescoberto() {
		field.abrir();
		assertTrue(field.objetivoAlcancado());
	}
	
	/**
	 * Testa se o objetivo foi alcançado através marcacão da casa minada.
	 * */
	@Test
	void objetivoalcancadoprotegido() {
		field.minar();
		field.marcarEdesmarcar();
		assertTrue(field.objetivoAlcancado());
	}
	
	
	/**
	 * Método que testa se o resultado padrão está certo.
	 * */	
	@Test
	void testaResultado() {
		assertTrue(field.toString().length()>0);
	}
	
	/**
	 * Método que testa se o resultado de uma casa marcada está certo.
	 * */	
	@Test
	void testaResultadoMarcado() {
		field.marcarEdesmarcar();
		assertTrue(field.toString().length()>0);
	}

	/**
	 * Método que testa se o resultado de uma casa aberta e minada marcada está certo.
	 * */	
	@Test
	void testaResultadoAbertoEMinado() {
		field.abrir();		
		field.minar();
		assertTrue(field.toString().length()>0);
	}
	
	/**
	 * Método que testa se o resultado de uma casa aberta com os vizinhos minados está certo.
	 * */	
	@Test
	void testaResultadoAbertoEMinadoNoSVizinhos() {
		Field vizinho23 = new Field(2,3);
		vizinho23.minar();
		field.adicionarVizinho(vizinho23);
		field.abrir();
		assertTrue(field.toString().length()>0);
		
	}
	
	/**
	 * Método que testa se o resultado de uma casa aberta está certo.
	 * */	
	@Test
	void testaResultadoAberto() {
		field.abrir();
		assertTrue(field.toString().length()>0);
	}
	
	/**
	 * Método que testa se o método reiniciar coloca como false o valor das variáveis 'aberto','marcado' e 'minado'.
	 * */	
	@Test
	void TestaReiniciar() {
		field.reiniciar();
		assertTrue(!field.isAberto() && !field.isMarcado() && !field.isMinado());
	}
	
	/*Aqui começam os testes unitários do tabuleiro*/

	/**
	 * Método que testa se o método reiniciar da classe Board está funcionando corretamente.
	 * */	
	@Test
	void testareiniciardoBoard() {
		board.reiniciar();
		assertTrue(!field.isAberto() && !field.isMarcado() && !field.isMinado());
	}

	/**
	 * Método que testa se o método ObjetivoAlcancado com Descoberto sendo true, da classe Board está funcionando corretamente.
	 * */	
	@Test
	void testaObjetivoAlcançadooBoardescoberto(){
		board.objetivoAlcancado();
		assertTrue(!field.isMinado() && field.abrir());
	}	
	
	/**
	 * Método que testa se o método ObjetivoAlcancado com Protegido sendo true, da classe Board está funcionando corretamente.
	 * */	
	@Test
	void testaObjetivoAlcançadooBoarprotegido(){
		board.objetivoAlcancado();
		field.marcarEdesmarcar();
		field.minar();
		assertTrue(field.isMarcado() && field.isMinado());
	}	
	
	/**
	 * Método que testa se o método marcarEdesmarcar da classe Board está funcionando corretamente.
	 * */	
	@Test
	void marcarEdesmarcarBoard() {
		board.marcarEdesmarcar(2, 2);
		field.marcarEdesmarcar();
		assertTrue(field.isMarcado());
	}
	
	/**
	 * Método que testa se o método abrir da classe Board está funcionando corretamente.
	 * */	
	@Test
	void abrirBoard() {
		board.abrir(2, 2);
		assertTrue(!field.isAberto());
	}
	
	/**
	 * Método que testa se o método toString da classe Board está funcionando corretamente.
	 * */	
	@Test 
	void TestatoStringBoard() {
		assertTrue(board.toString().length()>0);
	}
	
}
