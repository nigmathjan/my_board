package base;


import java.sql.SQLException;
import base.logic.MyServiceTable;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import base.util.HiberUtil;


public class MyServiceTableController
{
	//Проверка на корректность пароля администратора
	public static boolean isAdminPassCorrect( String s_pass )
	{
		return false;
	}
}
