package helpers;

public class Customer {
	private String name;
	private int id;
	private int total;
	public Customer(String name, int id){
		this.setName(name);
		this.setId(id);
	}
	public Customer(String name, int id, int total){
		this.setName(name);
		this.setId(id);
		this.setTotal(total);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
}
