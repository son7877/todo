package domain;

public class GroupSchedule extends Schedule {
	private final int id;
	
	public GroupSchedule(int id, String name, String date) {
		super(name, date);
		this.id = id;
	}
	public int getId() {
		return id;
	}
}
