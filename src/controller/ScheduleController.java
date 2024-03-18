package controller;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import domain.GroupSchedule;
import domain.PersonalSchedule;
import dto.UserDto;
import dto.GroupScheduleDto;
import dto.PersonalScheduleDto;
import dto.ScheduleDto;
import dto.ShareDto;
import service.ScheduleService;

public class ScheduleController {
	public void start() {
		while(true) {
			System.out.println("------------------------------일정관리-------------------------------\n");
			System.out.println("   1.개인일정 조회    2.단체일정 조회    3.개인일정 생성    4.단체일정 생성    5.뒤로가기");
			String input = sc.nextLine();
			
			if(input.equals("1")) showMySchedule();
			else if(input.equals("2")) showGroupSchedule();
			else if(input.equals("3")) addPersonalSchedule();
			else if(input.equals("4")) addGroupSchedule();
			else if(input.equals("5")) break;
			else System.out.println("다시 입력해주세요");
		}
	}
	private void showMySchedule() {
		List<PersonalSchedule> mySchedules = scheduleService.getMySchedule(userId);
		int len = mySchedules.size();
		if(len == 0) {
			System.out.println("개인일정이 없습니다");
			return;
		}
		for(int i = 0; i < len; i++) {
			PersonalSchedule schedule = mySchedules.get(i);
			System.out.println((i+1) + "번 일정      제목 : " + schedule.getTitle() + "     날짜 : " + schedule.getDate());
		}
		System.out.println("\n     작업할 일정의 번호를 입력하세요");
		int index = 0;
		while(true) {
			String inputIndex = sc.nextLine();
			try {
				index = Integer.parseInt(inputIndex);
				if(index <= len && index > 0) break;
				System.out.println("목록 내의 숫자를 입력해주세요");
			}catch (NumberFormatException e) {
				System.out.println("숫자를 입력하세요");
			}
		}
		System.out.println("\n   1.일정 삭제    2.일정 수정    3.일정 공유 ");
		while(true) {
			String inputwork = sc.nextLine();
			if(inputwork.equals("1")) { removePersonalSchedule(mySchedules.get(index-1).getId()); break; }
			else if(inputwork.equals("2")) { changePersonalSchedule(mySchedules.get(index-1)); break; }
			else if(inputwork.equals("3")) { sharePersonalSchedule(mySchedules.get(index-1)); break; }
			else System.out.println("다시 입력해주세요");
		}
	}
	private void removePersonalSchedule(int personalScheduleId) {
		scheduleService.removePersonalSchedule(personalScheduleId);
	}
	private void changePersonalSchedule(PersonalSchedule schedule) {
		System.out.println("기존 제목 : " + schedule.getTitle());
		System.out.println("수정할 제목 입력 : ");
		String title = sc.nextLine();
		System.out.println("기존 날짜 : " + schedule.getDate());
		System.out.println("수정할 날짜 입력 : ");
		String date = sc.nextLine();
		
		PersonalScheduleDto dto = new PersonalScheduleDto(schedule.getId(), title, date);
		scheduleService.changePersonalSchedule(dto);
	}
	private void sharePersonalSchedule(PersonalSchedule schedule) {
		List<UserDto> dtoList = scheduleService.getMyFriend(userId);
		System.out.println("----친구 목록----");
		int len = dtoList.size();
		if(len == 0) {
			System.out.println("친구가 없습니다");
			return;
		}
		for(int i = 0; i < len; i++) {
			UserDto friend = dtoList.get(i);
			System.out.println((i+1) + "번 친구    이름 : " + friend.getName() + "    아이디 : " + friend.getUid());
		}
		System.out.println("공유할 친구의 번호를 입력하세요");
		int index = 0;
		while(true) {
			String inputIndex = sc.nextLine();
			try {
				index = Integer.parseInt(inputIndex);
				if(index <= len && index > 0) break;
				System.out.println("목록 내의 숫자를 입력해주세요");
			}catch (NumberFormatException e) {
				System.out.println("숫자를 입력하세요");
			}
		}
		ShareDto share = new ShareDto(schedule.getId(), dtoList.get(index-1).getId());
		scheduleService.shareMySchedule(share);
	}
	private void addPersonalSchedule() {
		ScheduleDto dto = make();
		scheduleService.addPersonalSchedule(dto);
	}
	private ScheduleDto make() {
		System.out.print("TO-DO 제목 : ");
		String title = sc.nextLine();
		System.out.print("TO-DO 날짜  : ");
		String date = sc.nextLine();
		return new ScheduleDto(title, date, userId);
	}
	
	
	
	
	private void showGroupSchedule() {
		List<GroupSchedule> groupSchedules = scheduleService.getGroupSchedule(userId);
		int len = groupSchedules.size();
		if(len == 0) {
			System.out.println("단체일정이 없습니다");
			return;
		}
		for(int i = 0; i < len; i++) {
			GroupSchedule schedule = groupSchedules.get(i);
			System.out.println((i+1) + "번 일정      제목 : " + schedule.getTitle() + "     날짜 : " + schedule.getDate());
		}
		System.out.println("\n     작업할 일정의 번호를 입력하세요");
		int index = 0;
		while(true) {
			String inputIndex = sc.nextLine();
			try {
				index = Integer.parseInt(inputIndex);
				if(index <= len && index > 0) break;
				System.out.println("목록 내의 숫자를 입력해주세요");
			}catch (NumberFormatException e) {
				System.out.println("숫자를 입력하세요");
			}
		}
		System.out.println("\n   1.참여명단 보기    2.일정 수정    3.일정 삭제 ");
		while(true) {
			String inputwork = sc.nextLine();
			if(inputwork.equals("1")) { showParticipant(groupSchedules.get(index-1).getId()); break; }
			else if(inputwork.equals("2")) { changeGroupSchedule(groupSchedules.get(index-1)); break; }
			else if(inputwork.equals("3")) { removeGroupSchedule(groupSchedules.get(index-1)); break; }
			else System.out.println("다시 입력해주세요");
		}
	}
	private void showParticipant(int groupScheduleId) {
		List<UserDto> participants = scheduleService.getParticipant(groupScheduleId);
		System.out.println("----참여 명단----");
		int len = participants.size();
		if(len == 0) {
			System.out.println("친구가 없습니다");
			return;
		}
		for(int i = 0; i < len; i++) {
			UserDto friend = participants.get(i);
			System.out.println((i+1) + "번 친구    이름 : " + friend.getName() + "    아이디 : " + friend.getUid());
		}
	}
	private void changeGroupSchedule(GroupSchedule groupSchedule) {

		System.out.println("기존 제목 : " + groupSchedule.getTitle());
		System.out.println("수정할 제목 입력 : ");
		String title = sc.nextLine();
		System.out.println("기존 날짜 : " + groupSchedule.getDate());
		System.out.println("수정할 날짜 입력 : ");
		String date = sc.nextLine();
		
		ScheduleDto dto = new ScheduleDto(title, date, groupSchedule.getId());
		scheduleService.changeGroupSchedule(dto);
	}
	private void removeGroupSchedule(GroupSchedule groupSchedule) {
		scheduleService.removeGroupSchedule(groupSchedule.getId());
	}
	private void addGroupSchedule() {
		ScheduleDto dto = make();
		List<UserDto> dtoList = scheduleService.getMyFriend(userId);
		Set<Integer> friendIdSet = new HashSet<>();
		System.out.println("----친구 목록----");
		int len = dtoList.size();
		for(int i = 0; i < len; i++) {
			UserDto friend = dtoList.get(i);
			System.out.println((i+1) + "번 친구    이름 : " + friend.getName() + "    아이디 : " + friend.getUid());
		}
		System.out.println("일정을 함께할 친구의 번호를 하나씩 입력하세요\n완료시 아무 문자나 입력하시면 됩니다");
		int index = 0;
		while(true) {
			String inputIndex = sc.nextLine();
			try {
				index = Integer.parseInt(inputIndex);
				if(index <= len && index > 0) {
					friendIdSet.add(dtoList.get(index-1).getId());
					System.out.println("친구 " + index + " 추가!!");
				}
				else System.out.println("목록 내의 숫자를 입력해주세요");
			}catch (NumberFormatException e) { break; }
		}
		GroupScheduleDto groupDto = new GroupScheduleDto(dto, friendIdSet);
		if(friendIdSet.size() > 0) {
			List<String> unvailableList = scheduleService.getUnavailableParticipantsList(groupDto);
			if(!unvailableList.isEmpty()) {
				System.out.println(unvailableList.toString() + " 해당 인원이 원하시는 날짜에 일정이 있습니다 그래도 진행하시나요?");
				System.out.println("   1. 생성           2. 취소");
				while(true) {
					String input = sc.nextLine();
					
					if(input.equals("1")) break;
					else if(input.equals("2")) return;
					else System.out.println("다시 입력해주세요");
				}
			}
		}
		scheduleService.addGroupSchedule(groupDto);
		
	}
	
	
	
	
	
	private final ScheduleService scheduleService;
	private final Scanner sc;
	private final int userId;
	private static ScheduleController scheduleController;
	
	public static ScheduleController getInstance(Scanner sc, int id) {
		if(scheduleController == null) scheduleController = new ScheduleController(sc, id);
		return scheduleController;
	}
	private ScheduleController(Scanner sc, int id) {
		this.sc = sc;
		this.userId = id;
		scheduleService = ScheduleService.getInstance();
	}
}
