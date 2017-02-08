package master;

import java.io.Serializable;

public class StatusInvoiceBean implements Serializable {
	private int statusInvoiceId;
	private String name;
	private String fromId;
	private String toId;
	public int getStatusInvoiceId() {
		return statusInvoiceId;
	}
	public void setStatusInvoiceId(int statusInvoiceId) {
		this.statusInvoiceId = statusInvoiceId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFromId() {
		return fromId;
	}
	public void setFromId(String fromId) {
		this.fromId = fromId;
	}
	public String getToId() {
		return toId;
	}
	public void setToId(String toId) {
		this.toId = toId;
	}
	
	
}
