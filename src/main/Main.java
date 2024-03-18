package main;

import controller.AuthenticationController;

public class Main {

	public static void main(String[] args) {
		
//		Scanner sc = new Scanner(System.in);
//		int userId = 1;
//		ScheduleController con = ScheduleController.getInstance(sc, userId);
//		con.start();
		
		AuthenticationController todoApp = AuthenticationController.getInstance();
		todoApp.start();
		
//		String date = "2024-03-20";
//		List<Integer> iii = new ArrayList<>();
//		iii.add(1);
//		iii.add(2);
//		iii.add(3);
//		int len = iii.size();
//		try (Connection conn = DbInit.getConnection()){
//			String sql = "SELECT * FROM personal_schedule WHERE date = ? and user_id IN (";
//			for(int i = 0; i < len; i++) sql += "?,";
//			sql = sql.replaceAll(".$", "") + ")";
//			PreparedStatement ps = conn.prepareStatement(sql);
//			ps.setString(1, date);
//			int index = 2;
//			for(Integer i : iii) {
//				ps.setInt(index++, i);
//			}
//			ResultSet rs = ps.executeQuery();
//			while(rs.next()) {
//				int id = rs.getInt("id");
//				String title = rs.getString("title");
//				String datee = rs.getString("date");
//				int user_id = rs.getInt("user_id");
//				System.out.println(id+" "+title+" "+datee+" "+user_id);
//			}
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
	}

}
