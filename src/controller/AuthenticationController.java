package controller;

import java.util.Scanner;

import dto.LoginDto;
import dto.InfoDto;
import service.AuthenticationService;

public class AuthenticationController {
	public void start() {
		while(true) {
			System.out.println("------------------------------어플 시작-------------------------------\n");
			System.out.println("   1.로그인    2.회원가입    3.종료");
			String input = sc.nextLine();
			
			if(input.equals("1")) login();
			else if(input.equals("2")) signUp();
			else if(input.equals("3")) {
				System.out.println("앱을 종료합니다");
				return;
			}
			else System.out.println("다시 입력해주세요");
		}
	}
	private void login() {
		System.out.print("ID : ");
		String uid = sc.nextLine();
		System.out.print("PW : ");
		String pw = sc.nextLine();
		LoginDto dto = new LoginDto(uid, pw);
		int loginResult = authenticationService.longin(dto);
		if(loginResult == 0) System.out.println("입력하신 아이디가 없습니다");
		else if(loginResult == -1) System.out.println("비밀번호가 틀렸습니다");
		else {
			System.out.println("성공적으로 로그인 하였습니다");
			MainController main = new MainController(sc, loginResult);
			main.start();
		}
	}
	private void signUp() {
		String uid;
		while(true) {
			System.out.print("ID : ");
			uid = sc.nextLine();
			boolean uidAvailable = AuthenticationService.isUidAvailable(uid);
			if(uidAvailable) break;
			System.out.println("사용 불가능한 아이디입니다");
		}
		System.out.print("PW : ");
		String pw = sc.nextLine();
		System.out.print("이름 : ");
		String name = sc.nextLine();
		InfoDto dto = new InfoDto(uid, pw, name);
		int signUpResult = AuthenticationService.SignUp(dto);
		if(signUpResult == 1) System.out.println("회원가입 완료");
		else System.out.println("회원가입 실패ㅠㅠ");
	}
	
	
	
	private final Scanner sc;
	private static AuthenticationController authenticationController;
	private final AuthenticationService authenticationService;
	
	public static AuthenticationController getInstance() {
		if(authenticationController == null) authenticationController = new AuthenticationController();
		return authenticationController;
	}
	private AuthenticationController() {
		authenticationService = AuthenticationService.getInstance();;
		sc = new Scanner(System.in);
	}
}
