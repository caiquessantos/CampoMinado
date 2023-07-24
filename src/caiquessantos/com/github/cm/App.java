package caiquessantos.com.github.cm;

import caiquessantos.com.github.cm.model.Board;
import caiquessantos.com.github.cm.vision.BoardConsol;

public class App {

	public static void main(String[] args) {
		Board B = new Board(10,10,10);	
		new BoardConsol(B);

	}

}
