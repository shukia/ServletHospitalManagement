package com.te.hospitalmanagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Discharge
 */
@WebServlet("/Discharge")
public class Discharge extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Discharge() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		
		double total = 0;
		Connection c = GetConnection.getConnection();
		PrintWriter out = response.getWriter();
		String pid = request.getParameter("pid");
		int days = Integer.valueOf(request.getParameter("days"));
		int daycost = Integer.valueOf(request.getParameter("daycost"));
		String medData = request.getParameter("mc");
		String[] mcs = medData.split(";");
		String sq1 = "delete from patient where pid = "+pid;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			c.createStatement().executeUpdate(sq1);
			StringBuilder sb = new StringBuilder();
			for(String mc:mcs){
				String mid = mc.split(",")[0];
				int count = Integer.valueOf(mc.split(",")[1]);

				String sql_name = "select name from medicine where mid = "+mid;
				ResultSet rs_name = c.createStatement().executeQuery(sql_name);
				rs_name.next();
				String name = rs_name.getString("name");
				sb.append( name + " ");

				String sql_price = "select price from medicine where name = "+name;
				System.out.println(sql_price);
				c.createStatement().execute(sql_price);
				ResultSet rs = c.createStatement().executeQuery(sql_price);
				rs.next();
				double price = Double.valueOf(rs.getString("price"));
				total += (price * count);
			}
			
			total += (days * daycost);
			response.setContentType("text/html");
			out.println("<h1>Medicine names: " + sb.toString() + "</h1>");
			out.println("<h2>TOTAL MONEY TO PAY IS:<br><br><br></h1>");
			out.println("<h3>= "+total+"</h3>");
			
			
		} catch (SQLException e) {
			out.println("<br><br><br><h1 align=center><font color=\"red\">TRY AGAIN! Some problem<br></font></h1>");  
			e.printStackTrace();} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
