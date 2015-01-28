package src;


import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.ArrayList;

//Импорт своих пакетов
import src.html.*;
import src.sql.*;
import src.img.*;


public class MyShowPage extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String s_page_number = request.getParameter("page_number");
		if(s_page_number == null)
		{
			out.println(MyHtmlCreator.DrawPage(1, 1));
		}
		else
		{
			out.println(MyHtmlCreator.DrawPage(Integer.parseInt(s_page_number), 1));
		}
	}
}
