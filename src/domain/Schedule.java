package domain;

public class Schedule {
	private String title;
	private String date;
	
	public Schedule(String name, String date) {
		this.title = name;
		this.date = date;
	}
	public String getTitle() {
		return title;
	}
	public String getDate() {
		return date;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setDate(String date) {
		this.date = date;
	}
	

}
