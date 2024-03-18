package dto;

public class LoginDto {
	private final String uid;
	private final String pw;
	
	public LoginDto(String uid, String pw) {
		this.uid = uid;
		this.pw = pw;
	}
	public String getUid() {
		return uid;
	}
	public String getPw() {
		return pw;
	}
}
