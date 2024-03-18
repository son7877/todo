package dto;

public class ShareDto {
	private final int scheduleId;
	private final int userId;
	public ShareDto(int scheduleId, int userId) {
		this.scheduleId = scheduleId;
		this.userId = userId;
	}
	public int getScheduleId() {
		return scheduleId;
	}
	public int getUserId() {
		return userId;
	}
}
