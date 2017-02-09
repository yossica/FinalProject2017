package training;

import java.io.Serializable;

public class TrainingDetailBean implements Serializable {
	private int transactionTrainingDetailId;
	private int transactionTrainingHeaderId;
	private String description;
	private double fee;
	private int isSettlement;
	private String createdBy;
	private String createdDate;
	private String changedBy;
	private String changedDate;

}
