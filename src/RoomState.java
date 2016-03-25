import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RoomState {
	static Connection con = null;
	
	public RoomState()
	{
		
	}
	
	public String getRoomStateFromDb()
	{
		Statement stmt = null;
		ResultSet rs = null;
		
		String _roomName = "";

		String sql = "select roomname from roomstate where roomname=";
		
		
//System.out.println("roomName================="+_roomName +"==========");
		try{
			con = connectToRoomStateDb();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				_roomName = rs.getString("roomname");
			}
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}

		return _roomName;
	}
	

	public void updateRoomStateInDb(String _roomName)
	{
		int count=0;

		String update_sql = "update roomstate SET roomname = ?";

		try{
			con = connectToRoomStateDb();

			PreparedStatement pstmt = con.prepareStatement(update_sql);
			pstmt.setString(1, _roomName);

			count = pstmt.executeUpdate();
			if (count == 0)
				System.out.println("No record found");;

		}catch (SQLException e) {

			e.printStackTrace();
		}
		finally {
			try {
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}


	public static Connection connectToRoomStateDb()
	{
	
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:ora1/ora1@localhost:1521:orcl");
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e){
			e.printStackTrace();
		}

		return con;
	}
}

