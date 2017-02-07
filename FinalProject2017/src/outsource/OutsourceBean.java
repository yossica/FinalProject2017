package outsource;

import java.io.Serializable;

public class OutsourceBean implements Serializable {
	private int transactionOutsourceId;
	private int clientId;
	private int employeeId;
	private String startDate;
	private String endDate;
	private int isGross;
	private double fee;
	private String createdBy;
	private String createDate;
	private String changedBy;
	private String changedDate;
}
