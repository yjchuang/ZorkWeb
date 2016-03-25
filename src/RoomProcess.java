

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RoomProcess
 */
@WebServlet("/RoomProcess")
public class RoomProcess extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public RoomProcess() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		RoomDb _roomDb = new RoomDb();
		
		String roomDetail = "";
		
		roomDetail = _roomDb.getRoomFromDb(request.getParameter("direction"));
		//update room seen flag
		_roomDb.updateRoomToDb(request.getParameter("direction"), 0, 0);	
		
		if(roomDetail.equalsIgnoreCase("exit"))
			System.exit(0);

		request.setAttribute("message", roomDetail);

		request.getRequestDispatcher("/Room.jsp").forward(request, response);
	}

}
