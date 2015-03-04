package base;


import java.util.List;
import java.util.ArrayList;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import base.logic.MyPost;
import base.logic.MyThread;
import base.util.HiberUtil;
import base.util.BaseUtil;


public class MyBaseController
{
	//Создать тред
	public static synchronized void createNewThread( MyPost mp_arg )
	{
		try
		{
			int n_thread_number = MyPostController.maxPostNumber() + 1;
			String s_time = BaseUtil.getCurrentSqlTime();
			
			MyPost mp = mp_arg;
			
			mp.setThread_number(n_thread_number);
			mp.setPost_date(s_time);
			
			//Создаем запись о новом треде
			MyThreadController.createNewThread(n_thread_number, s_time);
			//Заносим оппост в таблицу постов
			MyPostController.addNewPost(mp);
		}
		catch(Exception e) { e.printStackTrace(); }
	}
	
	
	//Добавить пост в тред
	public static synchronized void addNewPost( MyPost mp_arg )
	{
		try
		{
			String s_time = BaseUtil.getCurrentSqlTime();
			
			MyPost mp = mp_arg;
			mp.setPost_date(s_time);
			
			//Добавляем пост в таблицу постов
			MyPostController.addNewPost(mp);
			//Обновляем дату треда
			MyThreadController.updateThread(mp.getThread_number(), s_time);
		}
		catch(Exception e) { e.printStackTrace(); }
	}
	
	
	//Удалить пост из треда
	public static synchronized void deletePost( int n_post_number )
	{
		try
		{
			MyPostController.deletePost(n_post_number);
		}
		catch(Exception e) { e.printStackTrace(); }
	}
	
	
	//Удалить тред со всеми постами
	public static synchronized void deleteThread( int n_thread_number )
	{
		try
		{
			MyThreadController.deleteThread( n_thread_number );
			MyPostController.deleteAllPostsFromThread( n_thread_number );
		}
		catch(Exception e) { e.printStackTrace(); }
	}
	
	
	//Сколько постов в треде
	public static synchronized int howManyPostsInThread( int n_thread_number )
	{
		int n_result = 0;
		
		try
		{
			n_result = MyPostController.howManyPostsInThread( n_thread_number );
		}
		catch(Exception e) { e.printStackTrace(); }
		
		return n_result;
	}
	
	
	//Получить все посты треда (кроме первого)
	public static synchronized List<MyPost> getAllPostsFromThreadWithoutFirst( int n_thread_numer )
	{
		List<MyPost> result_list = new ArrayList<MyPost>();
		
		try
		{
			result_list = MyPostController.getAllPostsFromThread( n_thread_numer );
			if(result_list.size() > 0)
			{
				result_list.remove(0);
			}
		}
		catch(Exception e) { e.printStackTrace(); }
		
		return result_list;
	}
	
	
	//Получить n_count последних постов треда
	public static synchronized List<MyPost> getSomeLastPostsFromThread( int n_thread_number, int n_count )
	{
		List<MyPost> result_list = new ArrayList<MyPost>();
		List<MyPost> all_posts = null;
		
		try
		{
			//Получим все посты треда
			all_posts = getAllPostsFromThreadWithoutFirst(n_thread_number);
		}
		catch(Exception e) { e.printStackTrace(); }
		
		if( n_count >= all_posts.size() ) { result_list = all_posts; }
		else
		{
			result_list = all_posts.subList(all_posts.size() - (n_count + 1), all_posts.size());
		}
		
		return result_list;
	}
	
	
	//Проверка пароля администратора
	public static boolean isAdminPassCorrect( String s_pass )
	{
		return MyServiceTableController.isAdminPassCorrect( s_pass );
	}
	
	
	//Сколько всего тредов на доске
	public static int howManyThreadsOnBoard()
	{
		return getAllThreads().size();
	}
	
	
	//Получить треды на определенной странице
	public static List<MyThread> getThreadsOnPage( int n_page, int n_threads_on_page )
	{
		List<MyThread> result_list = getAllThreads();
		
		//Вычисляем, какие треды должны находится на отображаемой странице
		//Если мы запросили последнюю страницу
		if((result_list.size() - n_page * n_threads_on_page) < n_threads_on_page)
		{
			result_list = result_list.subList(n_page * n_threads_on_page, result_list.size());
		}
		else //Если запросили не последнюю
		{
			result_list = result_list.subList
				(
					n_page * n_threads_on_page, 
					(n_page * n_threads_on_page) + n_threads_on_page
				);
		}
		
		return result_list;
	}
	
	
	//Получить все треды на доске (сортировка по времени)
	public static List<MyThread> getAllThreads()
	{
		List<MyThread> result_list = null;
		
		try
		{
			result_list = MyThreadController.getAllThreadsSortByTime();
		}
		catch(Exception e) { e.printStackTrace(); }
		
		return result_list;
	}
	
	
	//Получить ОП-пост треда
	public static MyPost getOPPost( int n_thread_number )
	{
		MyPost mp = null;
		
		try
		{
			mp = MyPostController.getPostById(n_thread_number);
		}
		catch(Exception e) { e.printStackTrace(); }
		
		return mp;
	}
	
	
	//Узнать максимальный номер поста
	public static int maxPostNumber()
	{
		int n = 0;
		
		try
		{
			n = MyPostController.maxPostNumber();
		}
		catch(Exception e) { e.printStackTrace(); }
		
		return n;
	}
	
	
	//Получить пост по номеру
	public static MyPost getPostByNumber( int n_post_number )
	{
		MyPost mp = null;
		
		try
		{
			mp = MyPostController.getPostById(n_post_number);
		}
		catch(Exception e) { e.printStackTrace(); }
		
		return mp;
	}
}
