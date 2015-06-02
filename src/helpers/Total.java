package helpers;

public class Total {
	private Integer total;
	private Integer pid;
	private Integer sid;
	public Total(Integer total, Integer pid, Integer sid){
		this.total=total;
		this.pid=pid;
		this.sid=sid;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
}
