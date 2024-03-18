package controller;

import java.util.Scanner;

import domain.User;
import dto.InfoDto;
import service.AuthenticationService;
import service.MainService;

public class MainController {
	public void start() {
		while(true) {
			System.out.println("------------------------------메인화면-------------------------------\n");
			System.out.println("   1.친구관리    2.일정관리    3.개인정보 수정    4.로그아웃    5.종료");
			String input = sc.nextLine();
			
			if(input.equals("1")) friendshipController.start();
			else if(input.equals("2")) scheduleController.start();
			else if(input.equals("3")) changeMyInfo();
			else if(input.equals("4")) break;
			else if(input.equals("5")) {
				System.out.println("앱을 종료합니다");
				System.exit(0);
			}
			else System.out.println("다시 입력해주세요");
		}
	}
	private void changeMyInfo() {
		InfoDto myInfo = mainService.getMyInfo(userId);
		String uid = myInfo.getUid();
		String pw = myInfo.getPw();
		String name = myInfo.getName();
		while(true) {
			System.out.println("------------------------------개인정보 수정-------------------------------\n");
			System.out.println("   1.ID 수정    2.PW 수정    3.이름 수정    4.완료 ");
			String input = sc.nextLine();
			
			if(input.equals("1")) uid = change(uid);
			else if(input.equals("2")) {
				System.out.println("기존 PW : " + pw);
				System.out.print("PW : ");
				pw = sc.nextLine();
			}
			else if(input.equals("3")) {
				System.out.println("기존 이름 : " + name);
				System.out.print("이름 : ");
				name = sc.nextLine();
			}
			else if(input.equals("4")) break;
			else System.out.println("다시 입력해주세요");
		}
		
		User info = new User(userId, uid, pw, name);
		int changeResult = mainService.changeInfo(info);
		if(changeResult == 1) System.out.println("정보를 수정하였습니다");
		else System.out.println("정보 수정 실패ㅠㅠ");
	}
	private String change(String uid) {
		String newUid;
		while(true) {
			System.out.println("기존 ID : " + uid);
			System.out.print("ID : ");
			newUid = sc.nextLine();
			boolean uidAvailable = AuthenticationService.isUidAvailable(newUid);
			if(uidAvailable) break;
			else System.out.println("사용 불가능한 아이디입니다");
		}
		return newUid;
	}
	
	
	
	private final Scanner sc;
	private final int userId;
	private final MainService mainService;
	private final FriendshipController friendshipController;
	private final ScheduleController scheduleController;
	
	public MainController(Scanner sc, int userId) {
		mainService = MainService.getInstance();
		friendshipController = FriendshipController.getInstance(sc, userId);
		scheduleController = ScheduleController.getInstance(sc, userId);
		this.sc = sc;
		this.userId = userId;
	}
}
