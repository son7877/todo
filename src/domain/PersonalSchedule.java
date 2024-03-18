package domain;

public class PersonalSchedule extends Schedule {
	private final int id;
	private final int userId;
	
	public PersonalSchedule(int id, String name, String date, int userId) {
		super(name, date);
		this.id = id;
		this.userId = userId;
	}
	public int getId() {
		return id;
	}
	public int getUserId() {
		return userId;
	}
	public String toData() {
		String data = "";
		data += id + "\t" + this.getTitle() + "\t" + this.getDate() + "\t" + userId + "\n";
		return data;
	}
}
