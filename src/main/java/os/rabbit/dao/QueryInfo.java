package os.rabbit.dao;
public class QueryInfo {
	private long total;

	private int pageCode;
	private int pageSize;
	
	public QueryInfo(int pageCode, int pageSize) {
		this.pageCode = pageCode;
		this.pageSize = pageSize;
	}
	public QueryInfo() {
		this.pageCode = 0;
		this.pageSize = 10000;
	}
	
	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}


	public int getPageCode() {
		return pageCode;
	}


	public void setPageCode(int pageCode) {
		this.pageCode = pageCode;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
}
