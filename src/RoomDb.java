import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class RoomDb {
	static Connection con = null;
	
	public RoomDb()
	{
		
	}
	
	public String getRoomFromDb(String _roomName)
	{
		Statement stmt = null;
		ResultSet rs = null;
		int notASecret = 0;
		String _roomDetail = "Your in "+_roomName+". You can go to";
		
		Random rnd = new Random();
		notASecret = rnd.nextInt(4);
		//notASecret = 1;
		
		if(_roomName.equalsIgnoreCase("Vault") == true)
		{
			//System.out.println("==============You are in the Vault=====================");
		}
		
		String sql = "select * from rooms where roomname='" + _roomName + "'";
		
		try{
			con = connectToRoomsDb();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				_roomDetail +=" "+rs.getString("optionroom1");
				if(_roomName.equalsIgnoreCase("Vault") == true)
				{
					if((rs.getInt("roomdiscovered") == 1) || (notASecret == 0))
					{
						//secret discovered, show secret room option
						_roomDetail +=" or "+rs.getString("optionroom2");
					}
					else
					{
						_roomDetail +="";
					}
				}	
				else if(rs.getString("optionroom2")!=null)
					_roomDetail +=" or "+rs.getString("optionroom2");
				if(rs.getString("optionroom3")!=null)
					_roomDetail +=" or "+rs.getString("optionroom3");
				
				_roomDetail +=". You see "+rs.getString("items");
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

		return _roomDetail;
	}
	
//	public String getBookByAuthorFromDb(String _author)
//	{
//		Statement stmt = null;
//		ResultSet rs = null;
//		
//		//Book book = new Book();
//		String _books = "";
//
//		String sql = "select title from books where author='" + _author + "'";
//		
//		try{
//			con = connectToBooksDb();
//			stmt = con.createStatement();
//			rs = stmt.executeQuery(sql);
//			while(rs.next()){
//				_books +="<tr><td>"+rs.getString("title")+"</td></tr>";
//			}
//		}catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				rs.close();
//				stmt.close();
//				con.close();
//			}catch(SQLException e){
//				e.printStackTrace();
//			}
//		}
//
//		return _books;
//	}

	
//	
//
//	public static void insertBookToDb(Book _book)
//	{
//		int count=0;
//
//		String update_sql = "insert into Books (sku, title, author, description, price) values (?, ?, ?, ?, ?)";
//		try{
//			con = connectToBooksDb();
//
//			PreparedStatement pstmt = con.prepareStatement(update_sql);
//
//			pstmt.setString(1, _book.getSku());
//			pstmt.setString(2, _book.getTitle());
//			pstmt.setString(3, _book.getAuthor());
//			pstmt.setString(4, _book.getDescription());
//			pstmt.setDouble(5, _book.getPrice());
//
//
//			count = pstmt.executeUpdate();
//			if (count == 0)
//				System.out.println("No record found");;
//
//		}catch (SQLException e) {
//
//			e.printStackTrace();
//		}
//
//		finally {
//			try {
//				//rs.close();
//				//stmt.close();
//				con.close();
//			}catch(SQLException e){
//				e.printStackTrace();
//			}
//		}
//
//	}

	public void updateRoomToDb(String _roomName, int _roomDiscovered, int _roomSeen)
	{
		int count=0;

		String update_sql = "update rooms SET roomdiscovered = ?, roomseen= ? WHERE roomname = ?";

		try{
			con = connectToRoomsDb();

			PreparedStatement pstmt = con.prepareStatement(update_sql);
			pstmt.setInt(1, _roomDiscovered);
			pstmt.setInt(2, _roomSeen);
			pstmt.setString(3, _roomName);

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


	public static Connection connectToRoomsDb()
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

