
public enum Player {
	X("X"), O("O"), EMPTY(" ");
	
	private String s;
	
	private Player(String s) {
		this.s = s;
	}
	
	public String toString() {
		return this.s;
	}
	
	public boolean equalsTo(String s) {
		return s == this.s;
	}

}
