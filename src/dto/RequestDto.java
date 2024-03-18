package dto;

public class RequestDto {
	private final int myId;
	private final String friendUid;
	
	public RequestDto(int myId, String friendUid) {
		this.myId = myId;
		this.friendUid = friendUid;
	}
	public int getMyId() {
		return myId;
	}
	public String getFriendUid() {
		return friendUid;
	}
}
