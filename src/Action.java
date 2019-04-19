

public class Action {

	private int row;
	private int col;
	private Player player;

	public Action(int row, int col, Player player) {
		if (row < 0 || row >= 3 || col < 0 || col >= 3) {
			throw new IllegalArgumentException("Azione fuori dalla board");
		}
		if (player.equals(Player.EMPTY)) {
			throw new IllegalArgumentException("Azione vuota");
		}
		
		this.row = row;
		this.col = col;
		this.player = player;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public Player getPlayer() {
		return player;
	}

}
