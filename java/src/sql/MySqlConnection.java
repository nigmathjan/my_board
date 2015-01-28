package src.sql;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class MySqlConnection
{
	//Определить, треды с какими номерами будут на странице с номером n_page, при отображении n_how_many_threads_on_page тредов на странице 
	public static int[] GiveMeThreadsOnPageN(int n_page, int n_how_many_threads_on_page)
	{
		int[] n_result = null;
		int[] n_all_threads = GiveMeThreadsNumbers();
		
		//Опреляем, не последнюю страницу ли запросил пользователь
		if(n_page == HowManyPages(n_how_many_threads_on_page))
		{
			//Узнаем сколько тредов, на последней странице, и это число устанавливаем как размерность массива под треды
			n_result = new int[HowManyThreadsInBase()%n_how_many_threads_on_page];
			
			int j = 0;
			for(int i = (n_page - 1)*n_how_many_threads_on_page; i < ((n_page - 1)*n_how_many_threads_on_page) + (HowManyThreadsInBase()%n_how_many_threads_on_page); i++)
			{
				n_result[j] = n_all_threads[i]; j++;
			}
		}
		else //Если не последнюю
		{
			n_result = new int[n_how_many_threads_on_page];
			int j = 0;
			
			for(int i = (n_page - 1)*n_how_many_threads_on_page; i < n_page*n_how_many_threads_on_page; i++)
			{
				n_result[j] = n_all_threads[i]; j++;
			}
		}
		
		return n_result;
	}
	
	
	//Вычислить количество страниц на доске, при отображении n_threads тредов на ней
	public static int HowManyPages(int n_threads)
	{
		int n_result = HowManyThreadsInBase()/n_threads;
		
		if((HowManyThreadsInBase()%n_threads) != 0)
		{
			n_result++;
		}
		
		return n_result;
	}
	
	
	//Получить первый пост треда
	public static String[] GiveMeFirstPostOfThread(int n_thread_number)
	{
		String[] s_result = new String[10];
		String s_sql = "SELECT * FROM posts WHERE post_number = '" + n_thread_number + "';";
		
		try
		{
			ResultSet myrs = ReturnResultSet(s_sql, false);
			while(myrs.next())
			{
				s_result[0] = myrs.getString("post_number");
				s_result[1] = myrs.getString("thread_number");
				s_result[2] = myrs.getString("post_date");
				s_result[3] = myrs.getString("post_name");
				s_result[4] = myrs.getString("post_theme");
				s_result[5] = myrs.getString("post_text");
				s_result[6] = myrs.getString("image_name");
				s_result[7] = myrs.getString("image_size");
				s_result[8] = myrs.getString("image_width");
				s_result[9] = myrs.getString("image_height");
			}
		}
		catch( Exception e ) { System.out.println("Ошибка в GiveMeFirstPostOfThread()\n" + e); }
		
		return s_result;
	}
	
	
	//Получить последние n постов треда
	public static ArrayList<String[]> GiveMeSomeLastPostsFromThread(int n_thread_number, int n_how_many_posts)
	{
		ArrayList<String[]> s_result = new ArrayList<String[]>();
		String s_sql = "SELECT * FROM posts WHERE thread_number = '" + n_thread_number + "' AND post_number <> '" + n_thread_number + "' ORDER BY post_number DESC LIMIT " + n_how_many_posts + ";";
		
		try
		{
			ResultSet myrs = ReturnResultSet(s_sql, false);
			while(myrs.next())
			{
				String[] s_buf = new String[10];
				
				s_buf[0] = myrs.getString("post_number");
				s_buf[1] = myrs.getString("thread_number");
				s_buf[2] = myrs.getString("post_date");
				s_buf[3] = myrs.getString("post_name");
				s_buf[4] = myrs.getString("post_theme");
				s_buf[5] = myrs.getString("post_text");
				s_buf[6] = myrs.getString("image_name");
				s_buf[7] = myrs.getString("image_size");
				s_buf[8] = myrs.getString("image_width");
				s_buf[9] = myrs.getString("image_height");
				
				s_result.add(s_buf);
			}
		}
		catch( Exception e ) { System.out.println("Ошибка в GiveMeSomeLastPostsFromThread()\n" + e); }
		
		//Разварачиваем ArrayList задом наперед
		ArrayList<String[]> s_correct_result = new ArrayList<String[]>();
		
		for(int i = 0; i < s_result.size(); i++)
		{
			s_correct_result.add(s_result.get(s_result.size() - (i + 1)));
		}
		
		return s_correct_result;
	}
	
	
	//Процедура, возвращающая массив с номерами тредов
	//Треды отсортированы по дате последнего поста
	public static int[] GiveMeThreadsNumbers()
	{
		int[] thread_numbers = new int[HowManyThreadsInBase()];
		String s_sql = "SELECT thread_number FROM threads ORDER BY last_update DESC;";
		
		try
		{
			int i = 0;
			ResultSet myrs = ReturnResultSet(s_sql, false);
			while(myrs.next())
			{
				thread_numbers[i] = myrs.getInt("thread_number");
				i++;
			}
		}
		catch( Exception e ) { System.out.println("Ошибка в GiveMeThreadsNumbers()\n" + e); }
		
		return thread_numbers;
	}
	
	
	//Узнаем, сколько тредов на борде
	public static int HowManyThreadsInBase()
	{
		int n_threads = 0;
		String s_sql = "SELECT COUNT(*) FROM threads;";
		
		try
		{
			ResultSet myrs = ReturnResultSet(s_sql, false);
			while(myrs.next())
			{
				n_threads = myrs.getInt("COUNT(*)");
			}
		}
		catch( Exception e ) { System.out.println("Ошибка в HowManyThreadsInBase()\n" + e); }
		
		return n_threads;
	}
	
	
	//Узнать, сколько постов в треде n_thread
	public static int HowManyPostsInThread(int n_thread)
	{
		int n_posts = 0;
		String s_sql = "SELECT COUNT(*) FROM posts WHERE thread_number=" + n_thread + ";";
		
		try
		{
			ResultSet myrs = ReturnResultSet(s_sql, false);
			while(myrs.next())
			{
				n_posts = myrs.getInt("COUNT(*)");
			}
		}
		catch( Exception e ) { System.out.println("Ошибка в HowManyPostsInThread()\n" + e); }
		
		return n_posts;
	}
	
	
	//Узнать сколько постов будет скрыто при отображении n_visible_posts под опппостом (на страничке с тредами)
	public static int HowManyHiddenPostsInThread(int n_thread, int n_visible_posts)
	{
		return HowManyPostsInThread(n_thread) - n_visible_posts - 1;
	}
	
	
	//Вывод всех постов без разделения по тредам
	public static ArrayList<String[]> ShowAllPosts()
	{
		ArrayList<String[]> result_al = new ArrayList<String[]>();
		
		String s_sql = "SELECT post_name, post_date, post_text FROM posts;";
		
		try
		{
			ResultSet myrs = ReturnResultSet(s_sql, false);
			
			while(myrs.next())
			{
				String[] s_buf = new String[3];
				
				s_buf[0] = myrs.getString("post_name");
				s_buf[1] = myrs.getString("post_date");
				s_buf[2] = myrs.getString("post_text");
				
				result_al.add(s_buf);
			}
		}
		catch( Exception e ) { System.out.println("Ошибка в ShowAllPosts()\n" + e); }
		
		return result_al;
	}
	
	
	//Создать тред
	//Параметр - массив
	//Элемент 0 - Имя постера
	//Элемент 1 - Тема поста
	//Элемент 2 - Текст поста
	//Элемент 3 - Имя картинки поста
	//Элемент 4 - Размер картинки
	//Элемент 5 - Ширина картинки
	//Элемент 6 - Высота картинки
	public static void CreateThread(String[] s_param)
	{
		String s_sql = 
			"CALL create_thread(" +
			"'" + s_param[0] + 
			"', '" + s_param[1] + 
			"', '" + s_param[2] + 
			"', '" + s_param[3] + 
			"', '" + s_param[4] + 
			"', '" + s_param[5] + 
			"', '" + s_param[6] + 
			"');";
		
		try
		{
			ResultSet myrs = ReturnResultSet(s_sql, true);
		}
		catch( Exception e ) { System.out.println("Ошибка в CreateThread()\n" + e);  }
	}
	
	
	//Написать в тред
	//Создать тред
	//Параметр - массив
	//Элемент 0 - Номер треда
	//Элемент 1 - Имя постера
	//Элемент 2 - Тема поста
	//Элемент 3 - Текст поста
	//Элемент 4 - Имя картинки поста
	//Элемент 5 - Размер картинки
	//Элемент 6 - Ширина картинки
	//Элемент 7 - Высота картинки
	public static void SendPostInThread(String[] s_param)
	{
		String s_sql = 
			"CALL add_post(" +
			"'" + s_param[0] + 
			"', '" + s_param[1] + 
			"', '" + s_param[2] + 
			"', '" + s_param[3] + 
			"', '" + s_param[4] + 
			"', '" + s_param[5] + 
			"', '" + s_param[6] + 
			"', '" + s_param[7] + 
			"');";
		
		try
		{
			ResultSet myrs = ReturnResultSet(s_sql, true);
		}
		catch( Exception e ) { System.out.println("Ошибка в SendPostInThread()\n" + e);  }
	}
	
	
	//Узнать, сколько всего постов на борде
	public static int HowManyPostsInBase()
	{
		int n_result = 0;
		String s_sql = "SELECT last_post_number FROM service_table;";
		
		try
		{
			ResultSet myrs = ReturnResultSet(s_sql, false);
			
			while(myrs.next())
			{
				n_result = myrs.getInt("last_post_number");
			}
		}
		catch( Exception e ) { System.out.println("Ошибка в HowManyPostsInBase()\n" + e); }
		
		return n_result;
	}
	
	
	//Получить ResultSet
	private static ResultSet ReturnResultSet(String s_sql, boolean b_flag) throws SQLException, ClassNotFoundException
	{
		ResultSet return_result_set = null;
		Connection c = null;
		Statement stmt = null;
		
		Class.forName("com.mysql.jdbc.Driver");
		c = DriverManager.getConnection(url, user, password);
		stmt = c.createStatement();
		
		if(!b_flag) { return_result_set = stmt.executeQuery(s_sql); }
		else { stmt.executeUpdate(s_sql); }
		
		return return_result_set;
	}
	
	
	//Поля
	private static String bd_name = "my_board";
	private static String bd_address = "127.0.0.1";
	private static String user = "root";
	private static String password = "123456";
	private static String url = "jdbc:mysql://" + bd_address + "/" + bd_name + "?characterEncoding=utf8";
} 
