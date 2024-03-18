package service;

import java.util.ArrayList;
import java.util.List;
import dao.GroupScheduleDAO;
import dao.PersonalScheduleDAO;
import domain.GroupSchedule;
import domain.PersonalSchedule;
import domain.User;
import dto.UserDto;
import dto.GroupScheduleDto;
import dto.PersonalScheduleDto;
import dto.ScheduleDto;
import dto.ShareDto;

public class ScheduleService {

	public List<PersonalSchedule> getMySchedule(int userId) {
		List<PersonalSchedule> mySchedules = personalDAO.getMyPersonalSchedule(userId);
		return mySchedules;
	}
	public void removePersonalSchedule(int personalScheduleId) {
		personalDAO.removePersonalSchedule(personalScheduleId);
	}
	public void changePersonalSchedule(PersonalScheduleDto dto) {
		personalDAO.changePersonalSchedule(dto);
	}
	public List<UserDto> getMyFriend(int userId) {
		List<User> friends = personalDAO.getMyFriend(userId);
		List<UserDto> dtoList = new ArrayList<>();
		for(User friend : friends) {
			dtoList.add(new UserDto(friend.getId(), friend.getUid(), friend.getName()));
		}
		return dtoList;
	}
	public void shareMySchedule(ShareDto share) {
		boolean isShareable = personalDAO.isShareable(share);
		if(isShareable) personalDAO.shareMySchedule(share);
		else System.out.println("이미 공유한 친구입니다");
	}
	public void addPersonalSchedule(ScheduleDto dto) {
		personalDAO.addPersonalSchedule(dto);
	}
	public List<GroupSchedule> getGroupSchedule(int userId) {
		List<GroupSchedule> groupSchedules = groupDAO.getGroupSchedule(userId);
		return groupSchedules;
	}
	public List<UserDto> getParticipant(int groupScheduleId) {
		List<User> participants = groupDAO.getPatricipant(groupScheduleId);
		List<UserDto> participantList = new ArrayList<>();
		for(User user : participants) {
			participantList.add(new UserDto(user.getId(), user.getUid(), user.getName()));
		}
		return participantList;
	}
	public void changeGroupSchedule(ScheduleDto dto) {
		groupDAO.changeGroupSchedule(dto);
	}
	public void removeGroupSchedule(int groupScheduleId) {
		groupDAO.removeGroupSchedule(groupScheduleId);
	}
	
	
	
	public List<String> getUnavailableParticipantsList(GroupScheduleDto groupDto) {
		List<String> unvailableList = groupDAO.getUnavailableParticipantsList(groupDto);
		return unvailableList;
	}
	public void addGroupSchedule(GroupScheduleDto groupDto) {
		groupDAO.addGroupSchedule(groupDto);
	}
	
	
	private final PersonalScheduleDAO personalDAO;
	private final GroupScheduleDAO groupDAO;
	private static ScheduleService scheduleService;
	public static ScheduleService getInstance() {
		if(scheduleService == null) scheduleService = new ScheduleService();
		return scheduleService;
	}
	private ScheduleService() {
		personalDAO = PersonalScheduleDAO.getInstance();
		groupDAO = GroupScheduleDAO.getInstance();
	}
	
}
