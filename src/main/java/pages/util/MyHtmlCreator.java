package pages.util;


import base.MyBaseController;
import base.logic.MyThread;
import base.logic.MyPost;
import base.logic.MyServiceTable;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;


public class MyHtmlCreator
{
	//Рисуем оппост
	public static String drawOPPost( MyPost post, int n_hidden_posts )
	{
		String s_hidden = "";
		if( n_hidden_posts > 0)
		{
			s_hidden = "			<span class=\"oppost_footer\">Скрыто " + Integer.toString(n_hidden_posts) + " постов.</span><br/>\n";
		}
		
		String s_result =
			"<hr/>\n" +
			"	<table class=\"oppost\">\n" +
			"		<tr>\n" +
			"			<td>" + post.getPost_number() + "  </td>\n" +
			"			<td class=\"post_name\">" + post.getPost_name() + "  </td>\n" +
			"			<td class=\"post_theme\">" + post.getPost_theme() + "  </td>\n" +
			"			<td class=\"post_date\">" + post.getPost_date() + "  </td>\n" +
			"			<td> [ <a href=\"show_thread.cgi?thread_number=" + post.getPost_number() + "\">Ответ в тред</a> ]</td>\n" +
			"			<td> [ <a href=\"delete_thread.cgi?thread_number=" + post.getThread_number() + "\"> Удалить </a> ] </td>\n" +
			"		</tr>\n" +
			"	</table>\n" +
			"	<table class=\"oppost\">\n" +
			"		<tr>\n" +
			"			<td>Файл <a href=\"src/" + post.getImage_name() + "\">" + post.getImage_name() + "</a> ( " + post.getImage_height()+ "х" + post.getImage_width() + ", " + post.getImage_size() + " КВ ) </td>\n" +
			"		</tr>\n" +
			"	</table>\n" +
			"			<a target=\"_blank\" href=\"src/" + post.getImage_name() + "\"><img align=\"left\" hspace=\"10\" src=\"thumb/" + post.getImage_name() + "\"></a>\n" +
			"			<span>" + post.getPost_text() + "</span><br/>\n" + s_hidden;
		
		return s_result;
	}
	
	
	//Рисуем баннер
	public static String drawBanner()
	{
		String s_result = 
		"<div class=\"centered_text\"><img src=\"src/banners/banner.svg\"></div>\n";
		
		return s_result;
	}
	
	
	//Рисуем пост
	public static String drawPost( MyPost post )
	{
		if(-1 < post.getImage_name().indexOf("no_image"))
		{
			return drawPostWithoutImage(post);
		}
		else
		{
			return drawPostWithImage(post);
		}
	}
	
	
	//Рисуем пост с картинкой
	public static String drawPostWithImage( MyPost post )
	{
		String s_result = 
			"<div class=\"post\">\n" +
			"	<table class=\"oppost\">\n" +
			"		<tr>\n" +
			"			<td>" + post.getPost_number() + "  </td>\n" +
			"			<td class=\"post_name\">" + post.getPost_name() + "  </td>\n" +
			"			<td class=\"post_theme\">" + post.getPost_theme() + "  </td>\n" +
			"			<td class=\"post_date\">" + post.getPost_date() + "  </td>\n" + 
			"			<td> [ <a href=\"delete_post.cgi?post_number=" + post.getPost_number() + "&thread_number=" + post.getThread_number() + "\">Удалить</a> ] </td>" +
			"		</tr>\n" +
			"	</table>\n" +
			"	<table>\n" +
			"		<tr>" +
			"			<td>Файл <a href=\"src/" + post.getImage_name() + "\">" + post.getImage_name() + "</a> ( " + post.getImage_height()+ "х" + post.getImage_width() + ", " + post.getImage_size() + " КВ ) </td>\n" +
			"		</tr>" +
			"	</table>\n" +
			"			<a target=\"_blank\" href=\"src/" + post.getImage_name() + "\"><img align=\"left\" hspace=\"10\" src=\"thumb/" + post.getImage_name() + "\"></a>\n" +
			"			<span>" + post.getPost_text() + "</span>\n" +
			"</div>\n" +
			"<br />\n";
			
		return s_result;
	}
	
	
	//Рисуем пост без картинки
	public static String drawPostWithoutImage( MyPost post )
	{
		String s_result = 
			"<div class=\"post\">\n" +
			"	<table class=\"oppost\">\n" +
			"		<tr>\n" +
			"			<td>" + post.getPost_number() + "  </td>\n" +
			"			<td class=\"post_name\">" + post.getPost_name() + "  </td>\n" +
			"			<td class=\"post_theme\">" + post.getPost_theme() + "  </td>\n" +
			"			<td class=\"post_date\">" + post.getPost_date() + "  </td>\n" + 
			"			<td> [ <a href=\"delete_post.cgi?post_number=" + post.getPost_number() + "&thread_number=" + post.getThread_number() + "\">Удалить</a> ] </td>" +
			"		</tr>\n" +
			"	</table>\n" +
			"			<span>" + post.getPost_text() + "</span>\n" +
			"</div>\n" +
			"<br />\n";
			
		return s_result;
	}
	
	
	//Рисуем форму создания треда
	public static String drawThreadForm( String s_pass )
	{
		String s_html = 
			"<div>\n" +
			"	<form action=\"create_thread.cgi\" method=\"post\" enctype=\"multipart/form-data\">\n" +
			"		<table align=\"center\">\n" +
			"			<tr><td class=\"create_thread_form\">Тема</td><td><input type=\"text\" name=\"theme_field\"></td></tr>\n" +
			"			<tr><td class=\"create_thread_form\">Имя</td><td><input type=\"text\" name=\"name_field\" value=\"Anonymous\"></td></tr>\n" +
			"			<tr><td class=\"create_thread_form\">Сообщение</td><td><textarea name=\"text_field\" rows=\"5\" cols=\"60\"></textarea></td></tr>\n" +
			"			<tr><td class=\"create_thread_form\">Файл</td><td><input type=\"file\" name=\"file\" size=\"35\" /></td></tr>\n" +
			"			<tr><td class=\"create_thread_form\">Пароль для удаления</td><td><input type=\"password\" name=\"pass_field\" value=\"" + s_pass + "\"><td></tr>" +
			"			<tr><td lass=\"create_thread_form\"></td><td><input type=\"submit\" value=\"Создать тред\"></td></tr>\n" +
			"		</table>\n" +
			"	</form>\n" +
			"</div>\n";
			
		return s_html;
	}
	
	
	//Рисуем форму создания поста
	public static String drawPostForm( int n_thread_number, String s_pass )
	{
		String s_html = 
			"<div class=\"create_post.cgi\">\n" +
			"	<form action=\"create_post.cgi\" method=\"post\" enctype=\"multipart/form-data\">\n" +
			"	<input type=\"hidden\" name=\"thread_number\" value=\"" + n_thread_number + "\">\n" +
			"		<table align=\"center\">\n" +
			"			<tr><td>Тема</td><td><input type=\"text\" name=\"theme_field\"></td></tr>\n" +
			"			<tr><td>Имя</td><td><input type=\"text\" name=\"name_field\" value=\"Anonymous\"></td></tr>\n" +
			"			<tr><td>Сообщение</td><td><textarea name=\"text_field\" rows=\"5\" cols=\"60\"></textarea></td></tr>\n" +
			"			<tr><td>Файл</td><td><input type=\"file\" name=\"file\" size=\"35\" /></td></tr>\n" +
			"			<tr><td>Пароль для удаления</td><td><input type=\"password\" name=\"pass_field\" value=\"" + s_pass + "\"><td></tr>" +
			"			<tr><td><input type=\"submit\" value=\"Написать в тред\"></td></tr>\n" +
			"		</table>\n" +
			"	</form>\n" +
			"</div>\n" +
			"<hr/>\n" +
			"<div class=\"centered_text\"><p> [ <a href=\"show_board.cgi?page=0\">К доске</a> ] </p></div>\n";
			
		return s_html;
	}
	
	
	//Рисуем полоску навигации по страницам доски
	public static String createNavigationString( int n_threads )
	{
		String s_result =
		"	Перейти к странице: ";
		
		for(int i = 0; i <= (n_threads / 3); i++)
		{
			s_result += " [ " + "<a href=\"show_board.cgi?page=" + i + "\">" + i + "</a>" + " ] ";
		}
		
		return s_result;
	}
	
	
	//Рисуем футер
	public static String drawFooter()
	{
		String s_result = 
		"JAVA + Spring MVC + Hibernate ORM";
		
		return s_result;
	}
}
