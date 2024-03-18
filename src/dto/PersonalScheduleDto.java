package dto;

public class PersonalScheduleDto {
	private final int id;
	private final String title;
	private final String date;
	
	public PersonalScheduleDto(int id, String title, String date) {
		this.id = id;
		this.title = title;
		this.date = date;
	}
	public int getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getDate() {
		return date;
	}
}
