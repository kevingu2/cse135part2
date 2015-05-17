package helpers;

public class State {
	private int id;
	private String name;
	private int total;
	public State(int id, String name){
		setId(id);
		setName(name);
	}
	public State(int id, String name, int total){
		setId(id);
		setName(name);
		setTotal(total);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
}
