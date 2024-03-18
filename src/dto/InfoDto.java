package dto;

public class InfoDto {
	private final String uid;
	private final String pw;
	private final String name;
	
	public InfoDto(String uid, String pw, String name) {
		this.uid = uid;
		this.pw = pw;
		this.name = name;
	}
	public String getUid() {
		return uid;
	}
	public String getPw() {
		return pw;
	}
	public String getName() {
		return name;
	}
}
