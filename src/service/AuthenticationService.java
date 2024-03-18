package service;

import dao.AuthenticationDAO;
import dto.LoginDto;
import dto.InfoDto;

public class AuthenticationService {
	
	/*
	 *로그인 성공하면 id리턴, 아이디가 없으면 0 리턴, 비밀번호가 틀리면 -1리턴 
	 */
	public int longin(LoginDto dto) {
		boolean isUidExist = authenticationDAO.isUidExist(dto.getUid());
		if (isUidExist) {
			int checkPw = authenticationDAO.checkPw(dto);
			return checkPw;
		}else return 0;
		
	}
	public static boolean isUidAvailable(String uid) {
		return AuthenticationDAO.isUidAvailable(uid);
	}
	/*
	 * 회원가입 성공하면 1리턴, 실패하면 0리턴
	 */
	public static int SignUp(InfoDto dto) {
		return AuthenticationDAO.signUp(dto);
	}

	
	
	
	private static AuthenticationService authenticationService;
	private final AuthenticationDAO authenticationDAO;
	public static AuthenticationService getInstance() {
		if(authenticationService == null) authenticationService = new AuthenticationService();
		return authenticationService;
	}
	private AuthenticationService() {
		authenticationDAO = AuthenticationDAO.getInstance();
	}
}
