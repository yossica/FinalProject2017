package training;

import java.io.Serializable;

public class TrainingDetailBean implements Serializable {
	private int transactionTrainingDetailId;
	private int transactionTrainingHeaderId;
	private String description;
	private double fee;
	private String createdBy;
	private String createDate;
	private String changedBy;
	private String changedDate;

}
