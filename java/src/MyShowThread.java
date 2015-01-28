package src;


import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.ArrayList;

//Импорт своих пакетов
import src.html.*;
import src.sql.*;
import src.img.*;


public class MyShowThread extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String s_thread_number = request.getParameter("thread_number");
		out.println(MyHtmlCreator.DrawThread(Integer.parseInt(s_thread_number), 1));
	}
}
