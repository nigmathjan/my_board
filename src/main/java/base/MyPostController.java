package base;


import java.sql.SQLException;
import base.logic.MyPost;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import base.util.HiberUtil;
import java.util.List;
import java.util.ArrayList;
import org.hibernate.criterion.Restrictions;


public class MyPostController
{
	//Добавить новый пост в таблицу
	public static void addNewPost( MyPost post ) throws SQLException
	{
		Session session = HiberUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.saveOrUpdate(post);
		session.getTransaction().commit();
		session.close();
	}
	
	
	//Удалить пост из таблицы (по номеру поста)
	public static void deletePost( int n_post_number ) throws SQLException
	{
		MyPost mp = new MyPost();
		mp.setPost_number( n_post_number );
		
		Session session = HiberUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.delete(mp);
		session.getTransaction().commit();
		session.close();
	}
	
	
	//Сколько постов в треде? (SQL)
	public static int howManyPostsInThread( int n_thread_number ) throws SQLException
	{
		Session session = HiberUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Number num_result = (Number) session.createSQLQuery
			(
				"SELECT COUNT(*) FROM post WHERE thread_number=" + n_thread_number + ";"
			).uniqueResult();
			
		session.getTransaction().commit();
		session.close();
		
		return num_result.intValue();
	}
	
	
	//Получить все посты треда (SQL)
	public static List<MyPost> getAllPostsFromThread( int n_thread_number ) throws SQLException
	{
		List<MyPost> result_list = null;
		
		Session session = HiberUtil.getSessionFactory().openSession();
		session.beginTransaction();
		result_list = session.createCriteria(MyPost.class)
			.add(Restrictions.eq("thread_number", n_thread_number))
			.list();
			
		session.getTransaction().commit();
		session.close();
		
		return result_list;
	}
	
	
	//Узнать максимальный номер поста (SQL)
	public static int maxPostNumber() throws SQLException
	{
		Session session = HiberUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Number num_result = (Number) session.createSQLQuery
			(
				"SELECT MAX(post_number) FROM post;"
			).uniqueResult();
			
		session.getTransaction().commit();
		session.close();
		
		return num_result.intValue();
	}
	
	
	//Удалить все посты треда (HQL)
	public static void deleteAllPostsFromThread( int n_thread_number ) throws SQLException
	{
		MyPost mp = new MyPost();
		mp.setThread_number(n_thread_number);
		
		Session session = HiberUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.createQuery("DELETE MyPost WHERE thread_number = :tn")
			.setParameter("tn", n_thread_number)
			.executeUpdate();

		session.getTransaction().commit();
		session.close();
	}
	
	
	//Получить пост по id
	public static MyPost getPostById( int n_post_number ) throws SQLException
	{
		MyPost mp = new MyPost();
		
		Session session = HiberUtil.getSessionFactory().openSession();
		mp = (MyPost) session.get(MyPost.class, n_post_number);
		session.close();
		
		return mp;
	}
}
