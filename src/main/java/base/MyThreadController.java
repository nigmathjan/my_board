package base;


import java.sql.SQLException;
import base.logic.MyThread;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import base.util.HiberUtil;
import java.util.List;
import java.util.ArrayList;


public class MyThreadController
{
	//Создать тред
	public static void createNewThread( int n_thread_number, String s_time ) throws SQLException
	{
		MyThread mt = new MyThread();
		mt.setThread_number(n_thread_number);
		mt.setLast_update(s_time);
		
		Session session = HiberUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.saveOrUpdate(mt);
		session.getTransaction().commit();
		session.close();
	}
	
	
	//Обновить дату треда (дата обновляется при добавлении в тред нового поста)
	public static void updateThread( int n_thread_number, String s_time ) throws SQLException
	{
		MyThread mt = new MyThread();
		mt.setThread_number(n_thread_number);
		mt.setLast_update(s_time);
		
		Session session = HiberUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.saveOrUpdate(mt);
		session.getTransaction().commit();
		session.close();
	}
	
	
	//Удалить тред
	public static void deleteThread( int n_thread_number ) throws SQLException
	{
		MyThread mt = new MyThread();
		mt.setThread_number(n_thread_number);
		
		Session session = HiberUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.delete(mt);
		session.getTransaction().commit();
		session.close();
	}
	
	
	//Получить все треды
	public static List<MyThread> getAllThreadsSortByTime() throws SQLException
	{
		List<MyThread> result_list = null;
		
		Session session = HiberUtil.getSessionFactory().openSession();
		result_list = session.createQuery("from MyThread mt order by mt.last_update desc").list();
		session.close();
		
		return result_list;
	}
}
