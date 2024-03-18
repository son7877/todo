package domain;

public class Share {
	private final int personalScheduleId;
	private final int userSharedId;
	
	public Share(int personalScheduleId, int userSharedId) {
		this.personalScheduleId = personalScheduleId;
		this.userSharedId = userSharedId;
	}
	public int getPersonalScheduleId() {
		return personalScheduleId;
	}
	public int getUserSharedId() {
		return userSharedId;
	}
}
