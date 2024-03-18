package service;

import java.util.ArrayList;
import java.util.List;

import dao.FriendshipDAO;
import domain.PersonalSchedule;
import domain.Request;
import domain.User;
import dto.RequestDto;
import dto.UserDto;

public class FriendshipService {

	/*
	 * 입력한 아이디가 없으면 0 리턴, 내 아이디를 입력했으면 -1 리턴, 이미 친구면 -2 리턴, 친구요청 보내면 1리턴
	 */
	public int sendRequest(RequestDto dto) {
		int friendId = friendshipDAO.getId(dto.getFriendUid());
		if (friendId != 0) {
			if(dto.getMyId() == friendId) return -1;
			Request request = new Request(dto.getMyId(), friendId);
			boolean isFriend = friendshipDAO.isFriend(request);
			if(!isFriend) {
				friendshipDAO.sendRequest(request);
				return 1;
			}else return -2;
		}else return 0;
	}
	public List<UserDto> getRequestUser(int userId) {
		List<User> requestUserList = friendshipDAO.getRequestUser(userId);
		List<UserDto> dtoList = new ArrayList<>();
		for(User user : requestUserList) dtoList.add(new UserDto(user.getId(), user.getUid(), user.getName()));
		return dtoList;
	}
	public int acceptRequest(Request request) {
		int addResult = friendshipDAO.addFriend(request);
		if(addResult == 0) return 0;
		int deleteResult = friendshipDAO.deleteRequest(request);
		return deleteResult;
	}
	public int rejectRequest(Request request) {
		int deleteResult = friendshipDAO.deleteRequest(request);
		return deleteResult;
	}
	public List<UserDto> getFriendList(int userId) {
		List<User> friendList = friendshipDAO.getFriendList(userId);
		List<UserDto> dtoList = new ArrayList<>();
		for(User user : friendList) dtoList.add(new UserDto(user.getId(), user.getUid(), user.getName()));
		return dtoList;
	}
	public List<PersonalSchedule> getSharedSchedule(Request ids) {
		List<PersonalSchedule> sharedSchedules = friendshipDAO.getSharedSchedule(ids);
		return sharedSchedules;
	}
	public int removeFriend(Request ids) {
		int removeResult1 = friendshipDAO.removeShare(ids);
		if(removeResult1 == 0) return 0;
		int removeResult2 = friendshipDAO.removeFriend(ids);
		return removeResult2;
	}
	
	
	
	
	private static FriendshipService friendshipService;
	private final FriendshipDAO friendshipDAO;
	public static FriendshipService getInstance() {
		if(friendshipService == null) friendshipService = new FriendshipService();
		return friendshipService;
	}
	private FriendshipService() {
		friendshipDAO = FriendshipDAO.getInstance();
	}
}
