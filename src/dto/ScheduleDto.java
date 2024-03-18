package dto;

public class ScheduleDto {
	private final String title;
	private final String date;
	private final int userId;
	
	public ScheduleDto(String title, String date, int userId) {
		this.title = title;
		this.date = date;
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public String getDate() {
		return date;
	}
	public int getUserId() {
		return userId;
	}
}