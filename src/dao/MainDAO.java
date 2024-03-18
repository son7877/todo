package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import domain.User;
import dto.InfoDto;
import global.DbInit;

public class MainDAO {
	public static InfoDto getMyInfo(int userId) {
		try (Connection conn = DbInit.getConnection()){
			String sql = "SELECT * FROM user WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String uid = rs.getString("uid");
				String pw = rs.getString("password");
				String name = rs.getString("name");
				return new InfoDto(uid, pw, name);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public int changeInfo(User info) {
		try (Connection conn = DbInit.getConnection()){
			conn.setAutoCommit(false);
			String sql = "UPDATE user SET uid = ?, password = ?, name = ? WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, info.getUid());
			ps.setString(2, info.getPw());
			ps.setString(3, info.getName());
			ps.setInt(4, info.getId());
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

	
	
	
	private static MainDAO mainDAO;
	public static MainDAO getInstance() {
		if(mainDAO == null) mainDAO = new MainDAO();
		return mainDAO;
	}
	private MainDAO() {}
}
