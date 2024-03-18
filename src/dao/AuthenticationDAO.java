package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dto.LoginDto;
import dto.InfoDto;
import global.DbInit;

public class AuthenticationDAO {

	public boolean isUidExist(String uid) {
		try (Connection conn = DbInit.getConnection()){
			String sql = "SELECT * FROM user WHERE uid = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, uid);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return true;
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public int checkPw(LoginDto dto) {
		try (Connection conn = DbInit.getConnection()){
			String sql = "SELECT * FROM user WHERE uid = ? and password = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getUid());
			ps.setString(2, dto.getPw());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				int id = rs.getInt("id");
				return id;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	public static boolean isUidAvailable(String uid) {
		try (Connection conn = DbInit.getConnection()){
			String sql = "SELECT * FROM user WHERE uid = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, uid);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {System.out.println(rs.getString("uid"));return false;}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public static int signUp(InfoDto dto) {
		try (Connection conn = DbInit.getConnection()){
			conn.setAutoCommit(false);
			String sql = "INSERT INTO user(uid, password, name) VALUES (?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getUid());
			ps.setString(2, dto.getPw());
			ps.setString(3, dto.getName());
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
	
	
	
	private static AuthenticationDAO authenticationDAO;
	public static AuthenticationDAO getInstance() {
		if(authenticationDAO == null) authenticationDAO = new AuthenticationDAO();
		return authenticationDAO;
	}
	private AuthenticationDAO() {}
}
