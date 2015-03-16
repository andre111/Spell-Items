package me.andre111.items;

public class Reward {
	private int level;
	private boolean repeating;
	private boolean reset;
	
	private String item;

	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public boolean isRepeating() {
		return repeating;
	}
	public void setRepeating(boolean repeating) {
		this.repeating = repeating;
	}
	public boolean isReset() {
		return reset;
	}
	public void setReset(boolean reset) {
		this.reset = reset;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
}
