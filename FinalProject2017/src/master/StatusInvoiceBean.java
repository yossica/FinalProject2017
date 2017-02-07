package master;

import java.io.Serializable;

public class StatusInvoiceBean implements Serializable {
	private int statusInvoiceId;
	private String name;
	private String from;
	private String to;
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
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	
}
