package controller;

import java.util.List;
import java.util.Scanner;

import domain.PersonalSchedule;
import domain.Request;
import dto.RequestDto;
import dto.UserDto;
import service.FriendshipService;

public class FriendshipController {
	
	public void start() {
		while(true) {
			System.out.println("------------------------------친구관리-------------------------------\n");
			System.out.println("   1.친구추가    2.요청관리    3.친구목록    4.뒤로가기");
			String input = sc.nextLine();
			
			if(input.equals("1")) sendRequest();
			else if(input.equals("2")) manageRequest();
			else if(input.equals("3")) showFriend();
			else if(input.equals("4")) break;
			else System.out.println("다시 입력해주세요");
		}
	}
	private void sendRequest() {
		System.out.print("친구의 ID : ");
		String uid = sc.nextLine();
		RequestDto dto = new RequestDto(userId, uid);
		int sendResult = friendshipService.sendRequest(dto);
		if(sendResult == 1) System.out.println("친구요청을 전송하였습니다");
		else if(sendResult == 0) System.out.println("입력하신 아이다가 존재하지 않습니다");
		else if(sendResult == -1) System.out.println("내 아이디를 입력하였습니다");
		else if(sendResult == -2) System.out.println("해당 유저와는 이미 친구입니다");
	}
	private void manageRequest() {
		List<UserDto> requestUserList = friendshipService.getRequestUser(userId);
		while(true) {
			int len = requestUserList.size();
			if(len == 0) {
				System.out.println("친구 요청이 없습니다");
				break;
			}
			for(int i = 0; i < len; i++) {
				UserDto sender = requestUserList.get(i);
				System.out.println((i+1) + "번  요청     이름 : " + sender.getName() + "   ID : " + sender.getUid());
			}
			System.out.println("\n  처리할 요청의 번호를 입력하세요 (종료하려면 0을 입력하세요)");
			int index = 0;
			while(true) {
				String inputIndex = sc.nextLine();
				try {
					index = Integer.parseInt(inputIndex);
					if(index <= len && index >= 0) break;
					System.out.println("목록 내의 숫자를 입력해주세요");
				}catch (NumberFormatException e) {
					System.out.println("숫자를 입력하세요");
				}
			}
			if(index == 0) break;
			Request request = new Request(requestUserList.get(index-1).getId(), userId);
			System.out.println("\n   1.수락    2.거절 ");
			while(true) {
				String inputwork = sc.nextLine();
				if(inputwork.equals("1")) { 
					acceptRequest(request);
					requestUserList.remove(index-1);
					break; 
				}
				else if(inputwork.equals("2")) { 
					rejectRequest(request);
					requestUserList.remove(index-1);
					break; 
				}
				else System.out.println("다시 입력해주세요");
			}
		}
	}
	private void acceptRequest(Request request) {
		int acceptResult = friendshipService.acceptRequest(request);
		if(acceptResult == 1) System.out.println("요청을 수락하였습니다");
		else System.out.println("요청 처리가 실패하였습니다");
	}
	private void rejectRequest(Request request) {
		int rejectResult = friendshipService.rejectRequest(request);
		if(rejectResult == 1) System.out.println("요청을 거절하였습니다");
		else System.out.println("요청 처리가 실패하였습니다");
	}
	private void showFriend() {
		List<UserDto> friendList = friendshipService.getFriendList(userId);
		while(true) {
			System.out.println("----친구 목록----");
			int len = friendList.size();
			if(len == 0) {
				System.out.println("친구가  없습니다");
				break;
			}
			for(int i = 0; i < len; i++) {
				UserDto friend = friendList.get(i);
				System.out.println((i+1) + "번 친구    이름 : " + friend.getName() + "    아이디 : " + friend.getUid());
			}
			System.out.println("작업할 친구의 번호를 입력하세요 (뒤로 가려면 0을 입력하세요)");
			int index = 0;
			while(true) {
				String inputIndex = sc.nextLine();
				try {
					index = Integer.parseInt(inputIndex);
					if(index <= len && index >= 0) break;
					System.out.println("목록 내의 숫자를 입력해주세요");
				}catch (NumberFormatException e) {
					System.out.println("숫자를 입력하세요");
				}
			}
			if(index == 0) return;
			System.out.println("--------------------------------------\n   1.친구일정 확인    2.친구 삭제 ");
			while(true) {
				String inputwork = sc.nextLine();
				if(inputwork.equals("1")) { 
					showSharedSchedule(friendList.get(index-1).getId());
					System.out.println("진행하려면 아무 텍스트나 입력하세요");
					sc.nextLine();
					break; 
				}
				else if(inputwork.equals("2")) { 
					removeFriend(friendList.get(index-1).getId());
					friendList.remove(index-1);
					break; 
				}
				else System.out.println("다시 입력해주세요");
			}
		}
	}
	private void showSharedSchedule(int friendId) {
		Request ids = new Request(friendId, userId);
		List<PersonalSchedule> mySchedules = friendshipService.getSharedSchedule(ids);
		int len = mySchedules.size();
		for(int i = 0; i < len; i++) {
			PersonalSchedule schedule = mySchedules.get(i);
			System.out.println((i+1) + "번 일정      제목 : " + schedule.getTitle() + "     날짜 : " + schedule.getDate());
		}
	}
	private void removeFriend(int friendId) {
		Request ids = new Request(friendId, userId);
		int removeResult = friendshipService.removeFriend(ids);
		if(removeResult == 1) System.out.println("성공적으로 삭제하였습니다");
		else System.out.println("삭제 작업이 실패하였습니다");
	}
	
	
	
	
	
	private static FriendshipController friendshipController;
	private final FriendshipService friendshipService;
	private final int userId;
	private final Scanner sc;
	public static FriendshipController getInstance(Scanner sc, int userId) {
		if(friendshipController == null) friendshipController = new FriendshipController(sc, userId);
		return friendshipController;
	}
	private FriendshipController(Scanner sc, int userId) {
		friendshipService = FriendshipService.getInstance();
		this.userId = userId;
		this.sc = sc;
	}
}
