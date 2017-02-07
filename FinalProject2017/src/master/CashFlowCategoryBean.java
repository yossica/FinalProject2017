package master;

import java.io.Serializable;

public class CashFlowCategoryBean implements Serializable {
	private String cashFlowCategoryId;
	private String name;
	private int isDebit;
	private int isEnabled;
	private String createdBy;
	private String createDate;
	private String changedBy;
	private String changedDate;
}
