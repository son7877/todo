package domain;

public class Friend {
	private final int user1Id;
	private final int user2Id;
	private final String createAt;
	
	public Friend(int user1Id, int user2Id, String createAt) {
		this.user1Id = user1Id;
		this.user2Id = user2Id;
		this.createAt = createAt;
	}
	public int getUser1Id() {
		return user1Id;
	}
	public int getUser2Id() {
		return user2Id;
	}
	public String getCreateAt() {
		return createAt;
	}
}
