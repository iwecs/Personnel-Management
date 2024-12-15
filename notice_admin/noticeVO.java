package notice_admin;

import java.time.LocalDateTime;

public class noticeVO {
	private String title;
	private String content;
	private int writer_id;
	private LocalDateTime write_Date;
	private String noticeType;
	public noticeVO(String title, String content, int writer_id, LocalDateTime write_Date, String noticeType) {
		super();
		this.title = title;
		this.content = content;
		this.writer_id = writer_id;
		this.write_Date = write_Date;
		this.noticeType = noticeType;
	}
	public noticeVO() {
		// TODO Auto-generated constructor stub
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getWriter_id() {
		return writer_id;
	}
	public void setWriter_id(int writer_id) {
		this.writer_id = writer_id;
	}
	public LocalDateTime getWrite_Date() {
		return write_Date;
	}
	public void setWrite_Date(LocalDateTime write_Date) {
		this.write_Date = write_Date;
	}
	public String getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}
	@Override
	public String toString() {
		return "noticeVO [title=" + title + ", content=" + content + ", writer_id=" + writer_id + ", write_Date="
				+ write_Date + ", noticeType=" + noticeType + "]";
	}
}