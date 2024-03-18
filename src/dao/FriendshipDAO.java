package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import domain.PersonalSchedule;
import domain.Request;
import domain.User;
import global.DbInit;

public class FriendshipDAO {

	public int getId(String friendUid) {
		try (Connection conn = DbInit.getConnection()){
			String sql = "SELECT id FROM user where uid = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, friendUid);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				return id;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	public boolean isFriend(Request request) {
		try (Connection conn = DbInit.getConnection()){
			String sql = "SELECT * FROM friend where user_1_id = ? and user_2_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, request.getUserSendId());
			ps.setInt(2,  request.getUserReceiveId());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return true;
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	public void sendRequest(Request request) {
		try (Connection conn = DbInit.getConnection()){
			conn.setAutoCommit(false);
			String sql = "INSERT INTO request (sender_id, receiver_id) VALUES (?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, request.getUserSendId());
			ps.setInt(2, request.getUserReceiveId());
			int rs = ps.executeUpdate();
			if(rs != 1) conn.rollback();
			else conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public List<User> getRequestUser(int userId) {
		List<User> requestUserList = new ArrayList<>();
		try (Connection conn = DbInit.getConnection()){
			String sql = "SELECT u.* FROM user AS u, request AS r WHERE u.id = r.sender_id and r.receiver_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String uid = rs.getString("uid");
				String pw = rs.getString("password");
				String name = rs.getString("name");
				requestUserList.add(new User(id, uid, pw, name));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requestUserList;
	}
	
	
	public int addFriend(Request request) {
		try (Connection conn = DbInit.getConnection()){
			conn.setAutoCommit(false);
			String sql = "INSERT INTO friend (user_1_id, user_2_id) VALUES (?, ?), (?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, request.getUserSendId());
			ps.setInt(2, request.getUserReceiveId());
			ps.setInt(3, request.getUserReceiveId());
			ps.setInt(4, request.getUserSendId());
			int rs = ps.executeUpdate();
			if(rs != 2) {
				conn.rollback();
				return 0;
			}
			else {
				conn.commit();
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	public int deleteRequest(Request request) {
		try (Connection conn = DbInit.getConnection()){
			conn.setAutoCommit(false);
			String sql = "DELETE FROM request WHERE sender_id = ? and receiver_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, request.getUserSendId());
			ps.setInt(2, request.getUserReceiveId());
			int rs = ps.executeUpdate();
			if(rs != 1) {
				conn.rollback();
				return 0;
			}
			else {
				conn.commit();
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	public List<User> getFriendList(int userId) {
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
	public List<PersonalSchedule> getSharedSchedule(Request ids) {
		List<PersonalSchedule> sharedSchedules = new ArrayList<>();
		try (Connection conn = DbInit.getConnection()){
			String sql = "SELECT * FROM personal_schedule AS ps, share AS s WHERE ps.id = s.personal_schedule_id and s.user_id = ? and ps.user_id = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, ids.getUserReceiveId());
			ps.setInt(2, ids.getUserSendId());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String date = rs.getString("date");
				int user_id = rs.getInt("user_id");
				sharedSchedules.add(new PersonalSchedule(id, title, date, user_id));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sharedSchedules;
	}
	public int removeShare(Request ids) {
		try (Connection conn = DbInit.getConnection()){
			conn.setAutoCommit(false);
			String sql = "DELETE FROM share WHERE user_id = ? and personal_schedule_id IN "
					+ "(SELECT id FROM (SELECT ps.id FROM personal_schedule AS ps, share AS s "
					+ "WHERE ps.id = s.personal_schedule_id and s.user_id = ? and ps.user_id = ?) AS t)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, ids.getUserReceiveId());
			ps.setInt(2, ids.getUserReceiveId());
			ps.setInt(3, ids.getUserSendId());
			int rs = ps.executeUpdate();
			if(rs == 0) {
				conn.rollback();
				return 0;
			}
			else {
				conn.commit();
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	public int removeFriend(Request ids) {
		try (Connection conn = DbInit.getConnection()){
			conn.setAutoCommit(false);
			String sql1 = "DELETE FROM friend WHERE user_1_id = ? and user_2_id = ?";
			PreparedStatement ps1 = conn.prepareStatement(sql1);
			ps1.setInt(1, ids.getUserReceiveId());
			ps1.setInt(2, ids.getUserSendId());
			int rs1 = ps1.executeUpdate();
			if(rs1 != 1) {
				conn.rollback();
				return 0;
			}
			String sql2 = "DELETE FROM friend WHERE user_1_id = ? and user_2_id = ?";
			PreparedStatement ps2 = conn.prepareStatement(sql2);
			ps2.setInt(1, ids.getUserSendId());
			ps2.setInt(2, ids.getUserReceiveId());
			int rs2 = ps2.executeUpdate();
			if(rs2 != 1) {
				conn.rollback();
				return 0;
			}
			else {
				conn.commit();
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	
	private static FriendshipDAO friendshipDAO;
	public static FriendshipDAO getInstance() {
		if(friendshipDAO == null) friendshipDAO = new FriendshipDAO();
		return friendshipDAO;
	}
	private FriendshipDAO() {}
}
