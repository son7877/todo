package domain;

public class Request {
	private final int userSendId;
	private final int userReceiveId;
	public Request(int userSendId, int userReceiveId) {
		this.userSendId = userSendId;
		this.userReceiveId = userReceiveId;
	}
	public int getUserSendId() {
		return userSendId;
	}
	public int getUserReceiveId() {
		return userReceiveId;
	}
}
