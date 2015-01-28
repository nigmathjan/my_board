package src;


import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


//Импорт своих пакетов
import src.html.err.*;
import src.html.*;
import src.sql.*;
import src.img.*;

 
public class MyMakeNewThread extends HttpServlet
{
	//Массив под хранение текстовых полей, которые впоследствии запишем в базу как поля нового треда/сообщения
	String[] s_args = new String[7];
	
	
	//Начальная инициализация массива пустыми значениями
	private void sArgsArrayInit()
	{
		//Чистим массив параметров
		for(int i = 0; i < s_args.length; i++)
		{
			s_args[i] = "0";
		}
		s_args[4] = "no_image"; //Вместо адреса картинки, пишем что ее нет
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		sArgsArrayInit();
		
		//проверяем является ли полученный запрос multipart/form-data
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) 
		{
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		
		// Создаём класс фабрику
		DiskFileItemFactory factory = new DiskFileItemFactory();
		 
		// Максимальный буфера данных в байтах,
		// при его привышении данные начнут записываться на диск во временную директорию
		// устанавливаем один мегабайт
		factory.setSizeThreshold(1024*1024);
		// устанавливаем временную директорию
		File tempDir = (File)getServletContext().getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(tempDir);
		 
		//Создаём сам загрузчик
		ServletFileUpload upload = new ServletFileUpload(factory);
		//максимальный размер данных который разрешено загружать в байтах
		//по умолчанию -1, без ограничений. Устанавливаем 10 мегабайт.
		upload.setSizeMax(1024 * 1024 * 10);
		 
		try 
		{
			List items = upload.parseRequest(request);
			Iterator iter = items.iterator();
			
			while (iter.hasNext()) 
			{
				FileItem item = (FileItem) iter.next();
				 
				if (item.isFormField()) 
				{
					//если принимаемая часть данных является полем формы
					processFormField(item);
				} 
				else
				{
					//в противном случае рассматриваем как файл
					processUploadedFile(item);
				}
			}
			
			//Если картинки нет
			if(-1 < s_args[4].indexOf("no_image"))
			{
				response.setContentType("text/html; charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println(MyErrorPagesCreator.ErrorImageNotFound());
			}
			else
			{
				//Вызываем метод создания треда
				MySqlConnection.CreateThread(s_args);
				
				response.setContentType("text/html; charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println("<meta http-equiv=\"Refresh\" content=\"0;url=my_board\">");
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
	}
	
	
	//Выбираем, как будем сохранять файл
	private void processUploadedFile(FileItem item)
	{
		//Какой тип файла нам передали?
		if(-1 < item.getName().indexOf(".png"))
		{
			WriteFileOnDisk(item, ".png");
		}
		else if((-1 < item.getName().indexOf(".jpg")) || (-1 < item.getName().indexOf(".jpeg")))
		{
			WriteFileOnDisk(item, ".jpeg");
		}
	}
	
	
	//Запись файла на диск
	public void WriteFileOnDisk(FileItem item, String file_resolution)
	{
		String file_name = Integer.toString(MySqlConnection.HowManyPostsInBase() + 1) + file_resolution;
		//Имя оригинального файла
		String path = getServletContext().getRealPath("/src/" + file_name);
		//Имя файла миниатюры
		String mini_path = getServletContext().getRealPath("/thumb/" + file_name);
		
		//Записываем имя файла в поле массива, который передадим процедуры добавления поста в базу
		//Также вычисляем и записываем размер
		s_args[3] = file_name;
		s_args[4] = Long.toString(item.getSize()/1024);
		
		
		File uploadetFile = null;
		uploadetFile = new File(path);
		
		//записываем в него данные
		try
		{
			uploadetFile.createNewFile();
			item.write(uploadetFile);
			
			int[] n_img_params = MyImageOperations.ResizeImage(uploadetFile, mini_path);
			s_args[5] = Integer.toString(n_img_params[0]);
			s_args[6] = Integer.toString(n_img_params[1]);
		}
		catch( Exception e ) { System.out.println("Ошибка в WriteFileOnDisk()" + e); }
	}
	
	
	//Отлавливаем текстовы параметры, и пишем их в массив (который впоследствии прольем в базу)
	private void processFormField(FileItem item)
	{
		try
		{
			if(item.getFieldName().equals("name_field"))
			{
				s_args[0] = new String(item.getString().getBytes("ISO-8859-1"),"UTF-8");
			}
			if(item.getFieldName().equals("theme_field"))
			{
				s_args[1] = new String(item.getString().getBytes("ISO-8859-1"),"UTF-8");
			}
			if(item.getFieldName().equals("text_field"))
			{
				s_args[2] = new String(item.getString().getBytes("ISO-8859-1"),"UTF-8");
			}
		}
		catch( Exception e ) { System.out.println("Ошибка в MyMakeNewThread.processFormField()" + e); }
	}
}
