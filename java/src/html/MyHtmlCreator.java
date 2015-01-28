//Класс по созданию HTML-страничек
package src.html;


import java.util.ArrayList;


//Импорт своих пакетов
import src.html.*;
import src.sql.*;
import src.img.*;



public class MyHtmlCreator
{
	//Сколько показываем тредов на странице
	public static final int N_THREADS_ON_PAGE = 10;
	//Сколько последних тредов под оппостом покажем
	public static final int N_SHOW_SUBPOSTS = 3;
	
	
	//Рисуем страничку с тредами
	public static String DrawPage(int n_page_number, int n_status)
	{
		String s_html = MakeHeader("THE BEST IMAGEBOARD EVER!") + MakeCreateThreadForm();
		
		if(MySqlConnection.HowManyThreadsInBase() > 0) //Если есть треды на доске, рисуем их
		{
			int[] n_threads = MySqlConnection.GiveMeThreadsOnPageN(n_page_number, N_THREADS_ON_PAGE);
			for(int i = 0; i < n_threads.length; i++)
			{
				//Рисуем оппост
				s_html += DrawOpPost(MySqlConnection.HowManyHiddenPostsInThread(n_threads[i], N_SHOW_SUBPOSTS), MySqlConnection.GiveMeFirstPostOfThread(n_threads[i]), n_status);
				
				ArrayList<String[]> s_bottom_posts = MySqlConnection.GiveMeSomeLastPostsFromThread(n_threads[i], N_SHOW_SUBPOSTS);
				for(int j = 0; j < s_bottom_posts.size(); j++)
				{
					s_html += DrawSimplePost(s_bottom_posts.get(j), n_status);
				}
				s_html += "<br/>";
			}
		}
		
		return s_html += MakeBoardFooter();
	}
	
	
	//Рисуем тред
	public static String DrawThread(int n_thread, int n_status)
	{
		String s_html = MakeHeader("THE BEST IMAGEBOARD EVER!") + MakeAnswerInThreadForm(n_thread);
		
		//Рисуем оппост
		s_html += DrawOpPost(0, MySqlConnection.GiveMeFirstPostOfThread(n_thread), n_status);
		
		ArrayList<String[]> s_bottom_posts = MySqlConnection.GiveMeSomeLastPostsFromThread(n_thread, 512);
		for(int j = 0; j < s_bottom_posts.size(); j++)
		{
			s_html += DrawSimplePost(s_bottom_posts.get(j), 1);
		}
		
		
		return s_html += MakeThreadFooter();
	}
	
	
	//Рисуем форму ответа в тред
	public static String MakeAnswerInThreadForm(int n_thread_number)
	{
		String s_html = 
			"<div class=\"thread_form\">\n" +
			"	<form action=\"make_post\" method=\"post\" enctype=\"multipart/form-data\">\n" +
			"		<table align=\"center\">\n" +
			"			<tr><td><input type=\"hidden\" name=\"thread_number\" value=\"" + n_thread_number + "\"> </td></tr>\n" +
			"			<tr><td>Тема</td><td><input type=\"text\" name=\"theme_field\"></td></tr>\n" +
			"			<tr><td>Имя</td><td><input type=\"text\" name=\"name_field\" value=\"Anonymous\"></td></tr>\n" +
			"			<tr><td>Сообщение</td><td><textarea name=\"text_field\" rows=\"5\" cols=\"60\"></textarea></td></tr>\n" +
			"			<tr><td>Файл</td><td><input type=\"file\" name=\"file\" size=\"35\" /></td></tr>\n" +
			"			<tr><td><input type=\"submit\" value=\"Ответить в тред\"></td></tr>\n" +
			"		</table>\n" +
			"	</form>\n" +
			"</div>\n";
			
		return s_html;
	}
	
	
	//Рисуем пост
	//Параметр s_args - данные поста
	//Параметр n_status - для кого рисуем пост (0 - пользователь, 1 - админ)
	public static String DrawSimplePost(String[] s_args, int n_status)
	{
		String s_img = "";
		String s_thumb_img = "";
		String s_admin_bar = "";
		
		//Рисовать админ-опции?
		if(n_status == 1)
		{
			s_admin_bar = "			<td> [ <a href=\"del_post?post_number=" + s_args[0] + "\">Удалить пост</a> ] </td>\n";
		}
		
		//Если картинки нет, не рисуем ее, если есть, рисуем
		if(-1 < s_args[6].indexOf("no_image")) {  }
		else 
		{
			s_img = "			<td colspan=\"4\">Файл <a target=\"_blank\" href=\"src/" + s_args[6] + "\">" + s_args[6] + "</a> ( " + s_args[8] + "х" + s_args[9] + ", " + s_args[7] + " КВ ) </td>\n"; 
			s_thumb_img = "<a target=\"_blank\" href=\"src/" + s_args[6] + "\"><img align=\"left\" hspace=\"10\" src=\"thumb/" + s_args[6] + "\"></a>";
		}
		
		String s_html = 
			"<div class=\"post\">\n" +
			"	<table class=\"oppost\">\n" +
			"		<tr>\n" +
			"			<td>" + s_args[0] + "  </td>\n" +
			"			<td class=\"post_name\">" + s_args[3] + "  </td>\n" +
			"			<td class=\"post_theme\">" + s_args[4] + "  </td>\n" +
			"			<td class=\"post_date\">" + s_args[2] + "  </td>\n" + 
			s_admin_bar +
			"		</tr>\n" +
			"		<tr>\n" +
			s_img +
			"		</tr>\n" +
			"	</table>\n" +
			s_thumb_img + "\n" +
			"			<span>" + s_args[5] + "</span>\n" +
			"</div>\n" +
			"<br />\n";
			
		return s_html;
	}
	
	
	//Рисуем оппост
	//1 Параметр - сколько постов осталось скрыто
	//2 Параметр - данные поста
	public static String DrawOpPost(int n_hidden_posts, String[] s_args, int n_status)
	{
		String s_admin_bar = "";
		String s_hidden = "";
		if( n_hidden_posts > 0)
		{
			s_hidden = "			<span class=\"oppost_footer\">Скрыто " + Integer.toString(n_hidden_posts) + " постов.</span><br/>\n";
		}
		
		//Рисовать админ-опции?
		if(n_status == 1)
		{
			s_admin_bar = "			<td> [ <a href=\"del_post?post_number=" + s_args[0] + "\">Удалить пост</a> ] </td>\n";
		}
		
		String s_html = 
			"<hr/>\n" +
			"	<table class=\"oppost\">\n" +
			"		<tr>\n" +
			"			<td>" + s_args[0] + "  </td>\n" +
			"			<td class=\"post_name\">" + s_args[3] + "  </td>\n" +
			"			<td class=\"post_theme\">" + s_args[4] + "  </td>\n" +
			"			<td class=\"post_date\">" + s_args[2] + "  </td>\n" +
			"			<td>Файл <a href=\"\">" + s_args[6] + "</a> ( " + s_args[8] + "х" + s_args[9] + ", " + s_args[7] + " КВ ) </td>\n" +
			"			<td> [ <a href=\"show_thread?thread_number=" + s_args[0] + "\">Ответ в тред</a> ]</td>\n" +
			s_admin_bar +
			"		</tr>\n" +
			"	</table>\n" +
			"			<a target=\"_blank\" href=\"src/" + s_args[6] + "\"><img align=\"left\" hspace=\"10\" src=\"thumb/" + s_args[6] + "\"></a>\n" +
			"			<span>" + s_args[5] + "</span></br>\n" +
			s_hidden;
		
		return s_html;
	}
	
	
	//Рисуем форму создания треда
	public static String MakeCreateThreadForm()
	{
		String s_html = 
			"<div class=\"thread_form\">\n" +
			"	<form action=\"make_thread\" method=\"post\" enctype=\"multipart/form-data\">\n" +
			"		<table align=\"center\">\n" +
			"			<tr><td>Тема</td><td><input type=\"text\" name=\"theme_field\"></td></tr>\n" +
			"			<tr><td>Имя</td><td><input type=\"text\" name=\"name_field\" value=\"Anonymous\"></td></tr>\n" +
			"			<tr><td>Сообщение</td><td><textarea name=\"text_field\" rows=\"5\" cols=\"60\"></textarea></td></tr>\n" +
			"			<tr><td>Файл</td><td><input type=\"file\" name=\"file\" size=\"35\" /></td></tr>\n" +
			"			<tr><td><input type=\"submit\" value=\"Создать тред\"></td></tr>\n" +
			"		</table>\n" +
			"	</form>\n" +
			"</div>\n";
			
		return s_html;
	}
	
	
	//Рисуем хейдер
	public static String MakeHeader(String s_title)
	{
		String s_html =
			"<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Frameset//EN\" \"http://www.w3.org/TR/html4/frameset.dtd\">\n" +
			"<html>\n" +
			"	<head>\n" +
			"		<title>" + s_title + "</title>\n" +
			"		<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\">\n" +
			"		<link rel=\"stylesheet\" href=\"css/board.css\">\n" +
			"	</head>\n" +
			"	<body>\n";
		
		return s_html;
	}
	
	
	//Рисуем футер для треда
	public static String MakeThreadFooter()
	{
		String s_html = 
		"<hr/>\n" + 
		"<p align=center> [ <a href=\"my_board\">Вернуться на доску</a> ] </p>\n" +
		"<hr/>\n" +
		"<p>Powered by <a href=\"https://www.java.com/\">JAVA</a>\n</p>" +
		"</body>\n" +
		"</html>\n";
		
		return s_html;
	}
	
	
	//Рисуем футер для странички с тредами
	public static String MakeBoardFooter()
	{
		String s_pages = "";
		for(int i = 0; i < MySqlConnection.HowManyPages(N_THREADS_ON_PAGE); i++)
		{
			s_pages +=	
				" [ " +
				"<a href=\"my_board?page_number=" + (i + 1) + "\">" + (i + 1) + "</a>" +
				" ]";
		}
		
		String s_html = 
			"<hr/>\n" +
			"Перейти к странице: " + s_pages + "\n" +
			"<hr/>\n" +
			"Powered by <a href=\"https://www.java.com/\">JAVA</a>\n" +
			"</body>\n" +
			"</html>\n";
		
		return s_html;
	}
}
