package dto;

public class UserDto {
	private final int id;
	private final String uid;
	private final String name;
	
	public UserDto(int id, String uid, String name) {
		this.id = id;
		this.uid = uid;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public String getUid() {
		return uid;
	}
	public String getName() {
		return name;
	}
}
