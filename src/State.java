
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Exceptions.NotEmptyBoxException;
import Exceptions.TurnException;

public class State {

	private String[][] board;
	private Player turn;
	private int numTurns;

	public State() {
		this.board = new String[3][3];

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				this.board[i][j] = Player.EMPTY.toString();
			}
		}

		this.turn = Player.X;
		this.numTurns = 0;
	}

	public State(String[][] board, Player turn, int numTurns) {
		this.board = board.clone();
		this.turn = turn;
		this.numTurns = numTurns;
	}

	public String[][] getBoard() {
		return board;
	}

	public void setTurn(Player turn) {
		this.turn = turn;
	}

	public Player getTurn() {
		return turn;
	}

	public void setNumTurns(int numTurns) {
		this.numTurns = numTurns;
	}

	public State result(Action a) throws TurnException, NotEmptyBoxException {
		if (!a.getPlayer().equalsTo(this.turn.toString())) {
			throw new TurnException("Non è il turno di " + a.getPlayer() + " bensì di " + this.turn.toString());
		} else if (!this.board[a.getRow()][a.getCol()].equals(Player.EMPTY.toString())) {
			throw new NotEmptyBoxException(
					"La casella (" + (a.getRow() + 1) + "," + (a.getCol() + 1) + ") non è vuota");
		}
		State result = this.clone();
		result.setNumTurns(this.numTurns + 1);
		result.setTurn(this.turn.equalsTo("X") ? Player.O : Player.X);
		result.getBoard()[a.getRow()][a.getCol()] = a.getPlayer().toString();
		return result;
	}

	public boolean wins(Player p) {
		int consecutiveX = 0;
		if (numTurns >= 5) {
			// Check orizzontale
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (this.board[i][j].equals(p.toString())) {
						consecutiveX++;
					} else {
						consecutiveX = 0;
						break;
					}
				}
				if (consecutiveX == 3) {
					return true;
				}
			}
			consecutiveX = 0;

			// Check verticale
			for (int j = 0; j < 3; j++) {
				for (int i = 0; i < 3; i++) {
					if (this.board[i][j].equals(p.toString())) {
						consecutiveX++;
					} else {
						consecutiveX = 0;
						break;
					}
				}
				if (consecutiveX == 3) {
					return true;
				}
			}

			// Check diagonali
			if ((this.board[0][0].equals(p.toString()) && this.board[1][1].equals(p.toString())
					&& this.board[2][2].equals(p.toString()))
					|| (this.board[0][2].equals(p.toString()) && this.board[1][1].equals(p.toString())
							&& this.board[2][0].equals(p.toString()))) {
				return true;
			}
		}
		return false;
	}

	public boolean gameEnded() {
		return boardIsFull() || wins(Player.X) || wins(Player.O);
	}
	
	public boolean boardIsFull() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (this.board[i][j].equals(Player.EMPTY.toString())) {
					return false;
				}
			}
		}
		return true;
	}


	public List<Action> actions() {
		ArrayList<Action> result = new ArrayList<>();
		State mock = this.clone();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Action a = new Action(i, j, turn);
				try {
					mock.result(a);
					result.add(a);
				} catch (Exception e) {
				}
			}
		}

		return result;
	}

	protected State clone() {
		String[][] newBoard = new String[3][3];
		for (int i = 0; i < 3; i++) {
			newBoard[i] = Arrays.copyOf(this.board[i], 3);
		}
		return new State(newBoard, this.turn, this.numTurns);
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(" " + this.board[0][0] + " | " + this.board[0][1] + " | " + this.board[0][2]);
		result.append("\n---+---+---");
		result.append("\n " + this.board[1][0] + " | " + this.board[1][1] + " | " + this.board[1][2]);
		result.append("\n---+---+---");
		result.append("\n " + this.board[2][0] + " | " + this.board[2][1] + " | " + this.board[2][2]);
		return result.toString();
	}

	public static void main(String[] args) throws TurnException, NotEmptyBoxException, InterruptedException {
		int xCount = 0;
		int oCount = 0;
		int draw = 0;

		for (int i = 0; i < 10; i++) {
			State s = new State();
			while (!s.gameEnded()) {
				Action a = null;
				a = TicTacToeAI.bestMove(s);
				s = s.result(a);
			}
			if (s.wins(Player.X)) {
				xCount++;
			} else if (s.wins(Player.O)) {
				oCount++;
			} else {
				draw++;
			}
			System.out.println(i);
		}

		System.out.println("Vittorie di X: " + xCount + "\tVittorie di O: " + oCount + "\tPareggi: " + draw);
	}

}
