package domain;

public class User {
	private final int id;
	private final String uid;
	private String pw;
	private final String name;
	
	public User(int id, String uid, String pw, String name) {
		this.id = id;
		this.uid = uid;
		this.pw = pw;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public String getUid() {
		return uid;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	
//	public User(String uid, String name, String friends, String personalTODOIds, String groupTODOIds, String sharedTODOIds) {
//		this.uid = uid;
//		this.name = name;
//		this.friends = friends;
//		this.personalTODOIds = personalTODOIds;
//		this.groupTODOIds = groupTODOIds;
//		this.sharedTODOIds = sharedTODOIds;
//	}
//	public String getUid() {
//		return uid;
//	}
//	public String getName() {
//		return name;
//	}
//	public String getFriends() {
//		return friends;
//	}
//	public String getPersonalTODOIds() {
//		return personalTODOIds;
//	}
//	public String getGroupTODOIds() {
//		return groupTODOIds;
//	}
//	public String getSharedTODOIds() {
//		return sharedTODOIds;
//	}
}
