package training;

import java.io.Serializable;
import java.util.List;

public class TrainingBean implements Serializable {
	private int transactionTrainingHeaderId;
	private int clientId;
	private String clientName;
	private String trainingEndDate;
	private String description;
	private int isGross;
	private String createdBy;
	private String createDate;
	private String changedBy;
	private String changedDate;
	
	private List<TrainingDetailBean> detailList;
}
