package top.qingrang.pojo;

public class UserRole {
	private Long id;

	private Long uid;

	private Long rid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getRid() {
		return rid;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	@Override
	public String toString() {
		return "UserRole{" +
				"id=" + id +
				", uid=" + uid +
				", rid=" + rid +
				'}';
	}
}