

import java.util.List;

import Exceptions.NotEmptyBoxException;
import Exceptions.TurnException;

public class TicTacToeAI {
	
	private static Player max;

	public static Action bestMove(State s) {
		Action result = null;
		double currentValue = Double.NEGATIVE_INFINITY;
		max = s.getTurn();
		List<Action> actions = s.actions();
		for (Action a : actions) {
			try {
				double value = minimax(s.result(a), Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, s.getTurn().equalsTo("X") ? Player.O : Player.X);
				if (value > currentValue) {
					result = a;
					currentValue = value;
				}
			} catch (TurnException | NotEmptyBoxException e) {
			}
		}
		return result;
	}

	private static double minimax(State s, double alpha, double beta, Player p) {
		if (s.gameEnded()) {
			return eval(s);
		}
		if (max.equals(p)) { // Max Value
			double value = Double.NEGATIVE_INFINITY;
			for (Action a : s.actions()) {
				try {
					double actionValue = minimax(s.result(a), alpha, beta, s.getTurn().equalsTo("X") ? Player.O : Player.X);
					value = Math.max(value, actionValue);
					if (value >= beta) {
						return value;
					}
					alpha = Math.max(alpha, value);
				} catch (TurnException | NotEmptyBoxException e) {
				}
			}
			return value;
		} else { // Min Value
			double value = Double.POSITIVE_INFINITY;
			for (Action a : s.actions()) {
				try {
					double actionValue = minimax(s.result(a), alpha, beta, s.getTurn().equalsTo("X") ? Player.O : Player.X);
					value = Math.min(value, actionValue);
					if (value <= alpha) {
						return value;
					}
					beta = Math.min(beta, value);
				} catch (TurnException | NotEmptyBoxException e) {
				}
			}
			return value;
		}
	}

	private static double eval(State s) {
		if (s.wins(Player.X)) {
			return max.equalsTo("X") ? 1.0 : -1.0;
		} else if (s.wins(Player.O)) {
			return max.equalsTo("O") ? 1.0 : -1.0;
		} else return 0.0;
	}
}
