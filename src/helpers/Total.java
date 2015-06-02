package helpers;

public class Total {
	private int total;
	private int pid;
	private int sid;
	public Total(int total, int pid, int sid){
		this.total=total;
		this.pid=pid;
		this.sid=sid;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
}
