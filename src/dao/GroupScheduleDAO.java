package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import domain.GroupSchedule;
import domain.User;
import dto.GroupScheduleDto;
import dto.ScheduleDto;
import global.DbInit;

public class GroupScheduleDAO {
	public List<GroupSchedule> getGroupSchedule(int userId) {
		List<GroupSchedule> groupSchedules = new ArrayList<>();
		try (Connection conn = DbInit.getConnection()){
			String sql = "SELECT gs.* FROM group_schedule AS gs, participant AS p WHERE gs.id = p.group_schedule_id and p.user_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String date = rs.getString("date");
				groupSchedules.add(new GroupSchedule(id, title, date));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return groupSchedules;
	}
	public List<User> getPatricipant(int groupScheduleId) {
		List<User> participants = new ArrayList<>();
		try (Connection conn = DbInit.getConnection()){
			String sql = "SELECT u.* FROM participant AS p, user AS u where p.user_id = u.id and group_schedule_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, groupScheduleId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String uid = rs.getString("uid");
				String pw = rs.getString("password");
				String name = rs.getString("name");
				participants.add(new User(id, uid, pw, name));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return participants;
	}
	public void changeGroupSchedule(ScheduleDto dto) {
		try (Connection conn = DbInit.getConnection()){
			conn.setAutoCommit(false);
			String sql = "UPDATE group_schedule SET title = ?, date = ? WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getTitle());
			ps.setString(2, dto.getDate());
			ps.setInt(3, dto.getUserId());
			int rs = ps.executeUpdate();
			if(rs != 1) conn.rollback();
			else conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("성공적으로 수정하였습니다");
	}
	public void removeGroupSchedule(int groupScheduleId) {
		try (Connection conn = DbInit.getConnection()){
			conn.setAutoCommit(false);
			String sql = "DELETE FROM group_schedule WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, groupScheduleId);
			int rs = ps.executeUpdate();
			if(rs != 1) conn.rollback();
			else conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("성공적으로 삭제하였습니다");
	}
	
	
	
	public List<String> getUnavailableParticipantsList(GroupScheduleDto groupDto) {
		String date = groupDto.getDto().getDate();
		Set<Integer> friendIds = groupDto.getIdSet();
		List<String> nameList = new ArrayList<>();
		int len = friendIds.size();
		try (Connection conn = DbInit.getConnection()){
			String sql = "SELECT u.name FROM personal_schedule AS ps, user AS u "
					+ "WHERE u.id = ps.user_id and ps.date = ? and ps.user_id IN (";
			for(int i = 0; i < len; i++) sql += "?,";
			sql = sql.replaceAll(".$", "") + ")";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, date);
			int index = 2;
			for(Integer i : friendIds) {
				ps.setInt(index++, i);
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				nameList.add(rs.getString("name"));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return nameList;
	}
	public void addGroupSchedule(GroupScheduleDto groupDto) {
		String title = groupDto.getDto().getTitle();
		String date = groupDto.getDto().getDate();
		int scheduleId = getGroupId();
		saveGroupSchedule(scheduleId, title, date);
		Set<Integer> userIds = groupDto.getIdSet();
		userIds.add(groupDto.getDto().getUserId());
		saveParticipant(scheduleId, userIds);
		
	}
	private int getGroupId() {
		int last = 0;
		try (Connection conn = DbInit.getConnection()){
			String sql = "SELECT id FROM group_schedule";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				last = rs.getInt("id");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return last+1;
	}
	private void saveGroupSchedule(int id, String title, String date) {
		try (Connection conn = DbInit.getConnection()){
			conn.setAutoCommit(false);
			String sql = "INSERT INTO group_schedule VALUES (?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, title);
			ps.setString(3, date);
			int rs = ps.executeUpdate();
			if(rs != 1) conn.rollback();
			else conn.commit();
			System.out.println("단체 일정을 추가하였습니다");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void saveParticipant(int scheduleId, Set<Integer> userIds) {
		int len = userIds.size();
		try (Connection conn = DbInit.getConnection()){
			conn.setAutoCommit(false);
			String sql = "INSERT INTO participant VALUES ";
			for(Integer id : userIds) {
				sql += "("+scheduleId+", ?),";
			}
			sql = sql.replaceAll(".$", "");
			PreparedStatement ps = conn.prepareStatement(sql);
			int index = 1;
			for(Integer i : userIds) ps.setInt(index++, i.intValue());
			int rs = ps.executeUpdate();
			if(rs != len) conn.rollback();
			else conn.commit();
			System.out.println("참여자 목록을 추가하였습니다");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static GroupScheduleDAO groupSchedulDAO;
	public static GroupScheduleDAO getInstance() {
		if(groupSchedulDAO == null) groupSchedulDAO = new GroupScheduleDAO();
		return groupSchedulDAO;
	}
	private GroupScheduleDAO() { }
}
