package domain;

public class Participant {
	private final int groupScheduleId;
	private final int sharedUserId;
	
	public Participant(int groupScheduleId, int sharedUserId) {
		this.groupScheduleId = groupScheduleId;
		this.sharedUserId = sharedUserId;
	}
	public int getGroupScheduleId() {
		return groupScheduleId;
	}
	public int getSharedUserId() {
		return sharedUserId;
	}
}
