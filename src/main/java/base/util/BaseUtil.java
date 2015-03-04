package base.util;


import java.util.Date;
import java.text.SimpleDateFormat;


public class BaseUtil
{
	//Текущая дата-время в SQL формате
	public static String getCurrentSqlTime()
	{
		String s_time = "";
		
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		s_time = sdf.format(dt);
		
		return s_time;
	}
} 
