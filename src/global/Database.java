package global;

import java.util.ArrayList;
import java.util.List;

import domain.Friend;
import domain.GroupSchedule;
import domain.Participant;
import domain.PersonalSchedule;
import domain.Request;
import domain.Share;
import domain.User;

public class Database {
	public final static List<User> USERS = new ArrayList<>();
	public final static List<Friend> FRIENDS = new ArrayList<>();
	public final static List<Request> REQUESTS = new ArrayList<>();
	public final static List<PersonalSchedule> PERSONAL_SCHEDULES = new ArrayList<>();
	public final static List<Share> SHARES = new ArrayList<>();
	public final static List<GroupSchedule> GROUP_SCHEDULES = new ArrayList<>();
	public final static List<Participant> PARTICIPANTS = new ArrayList<>();
}
