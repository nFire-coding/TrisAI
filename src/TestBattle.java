

import Exceptions.NotEmptyBoxException;
import Exceptions.TurnException;

public class TestBattle {

	public static void main(String[] args) throws TurnException, NotEmptyBoxException, InterruptedException {
		int aima = 0;
		int mine = 0;
		int draw = 0;

		for (int i = 0; i < 500; i++) {
			// Aima X %%% Io O
			State s = new State();
			while (!s.gameEnded()) {
				if (s.getTurn().equalsTo("X")) {
					AIma ai = new AIma(s, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1);
					Action a = ai.makeDecision(s);
					s = s.result(a);
				} else {
					Action a = TicTacToeAI.bestMove(s);
					s = s.result(a);
				}
			}
			if (s.wins(Player.X)) {
				aima++;
			} else if (s.wins(Player.O)) {
				mine++;
			} else {
				draw++;
			}
			
			System.out.println("" + (i * 2) + "=>\tAima: " + aima + "\tIo: " + mine + "\tDraw: " + draw);

			// Aima O %%% Io X
			s = new State();
			while (!s.gameEnded()) {
				if (s.getTurn().equalsTo("O")) {
					AIma ai = new AIma(s, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1);
					Action a = ai.makeDecision(s);
					s = s.result(a);
				} else {
					Action a = TicTacToeAI.bestMove(s);
					s = s.result(a);
				}
			}
			if (s.wins(Player.X)) {
				mine++;
			} else if (s.wins(Player.O)) {
				aima++;
			} else {
				draw++;
			}
			
			System.out.println("" + (i * 2 + 1) + "=>\tAima: " + aima + "\tIo: " + mine + "\tDraw: " + draw);
		}
		
		System.out.println("Aima: " + aima + "\tIo: " + mine + "\tDraw: " + draw);
	}
}
