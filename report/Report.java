package report;

import java.time.LocalDateTime;

public class Report {
	private String reportTitle;
	private String reportContent;
	private String fromId;
	private String toId;
	private LocalDateTime reportDate;

	public Report(String reportTitle, String reportContent, String fromId, String toId, LocalDateTime reportDate) {
		this.reportTitle = reportTitle;
		this.reportContent = reportContent;
		this.fromId = fromId;
		this.toId = toId;
		this.reportDate = reportDate;
	}

	public Report() {
		// TODO Auto-generated constructor stub
	}

	public String getReportTitle() {
		return reportTitle;
	}

	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}

	public String getReportContent() {
		return reportContent;
	}

	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
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

	public LocalDateTime getReportDate() {
		return reportDate;
	}

	public void setReportDate(LocalDateTime reportDate) {
		this.reportDate = reportDate;
	}

	@Override
	public String toString() {
		return "Report [reportTitle=" + reportTitle + ", reportContent=" + reportContent + ", fromId=" + fromId
				+ ", toId=" + toId + ", reportDate=" + reportDate + "]";
	}
	
}