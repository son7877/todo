package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import domain.PersonalSchedule;
import domain.User;
import dto.PersonalScheduleDto;
import dto.ScheduleDto;
import dto.ShareDto;
import global.DbInit;

public class PersonalScheduleDAO {
	
	public List<PersonalSchedule> getMyPersonalSchedule(int userId){
		List<PersonalSchedule> mySchedules = new ArrayList<>();
		try (Connection conn = DbInit.getConnection()){
			String sql = "SELECT * FROM personal_schedule where user_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String date = rs.getString("date");
				int user_id = rs.getInt("user_id");
				mySchedules.add(new PersonalSchedule(id, title, date, user_id));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mySchedules;
	}
	public void removePersonalSchedule(int personalScheduleId) {
		try (Connection conn = DbInit.getConnection()){
			conn.setAutoCommit(false);
			String sql = "DELETE FROM personal_schedule WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, personalScheduleId);
			int rs = ps.executeUpdate();
			if(rs != 1) conn.rollback();
			else conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("성공적으로 삭제하였습니다");
	}
	public void changePersonalSchedule(PersonalScheduleDto dto) {
		try (Connection conn = DbInit.getConnection()){
			conn.setAutoCommit(false);
			String sql = "UPDATE personal_schedule SET title = ?, date = ? WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getTitle());
			ps.setString(2, dto.getDate());
			ps.setInt(3, dto.getId());
			int rs = ps.executeUpdate();
			if(rs != 1) conn.rollback();
			else conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("성공적으로 수정하였습니다");
	}
	public List<User> getMyFriend(int userId) {
		List<User> friends = new ArrayList<>();
		try (Connection conn = DbInit.getConnection()){
			String sql = "SELECT * FROM user WHERE id IN (SELECT user_2_id FROM friend WHERE user_1_id = ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String uid = rs.getString("uid");
				String pw = rs.getString("password");
				String name = rs.getString("name");
				friends.add(new User(id, uid, pw, name));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return friends;
	}
	public boolean isShareable(ShareDto share) {
		try (Connection conn = DbInit.getConnection()){
			String sql = "SELECT * FROM share WHERE personal_schedule_id = ? and user_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, share.getScheduleId());
			ps.setInt(2, share.getUserId());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return false;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public void shareMySchedule(ShareDto share) {
		try (Connection conn = DbInit.getConnection()){
			conn.setAutoCommit(false);
			String sql = "INSERT INTO share VALUES (?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, share.getScheduleId());
			ps.setInt(2, share.getUserId());
			int rs = ps.executeUpdate();
			if(rs != 1) conn.rollback();
			else conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("성공적으로 공유하였습니다");
	}
	public void addPersonalSchedule(ScheduleDto dto) {
		try (Connection conn = DbInit.getConnection()){
			conn.setAutoCommit(false);
			String sql = "INSERT INTO personal_schedule(title, date, user_id) VALUES (?, ?, ?)";
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
		System.out.println("일정을 추가하였습니다");
	}
	
	
	
	private static PersonalScheduleDAO personalSchedulDAO;
	public static PersonalScheduleDAO getInstance() {
		if(personalSchedulDAO == null) personalSchedulDAO = new PersonalScheduleDAO();
		return personalSchedulDAO;
	}
	private PersonalScheduleDAO() { }
}
