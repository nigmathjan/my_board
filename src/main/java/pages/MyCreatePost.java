package pages;


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
import javax.servlet.ServletContext;
 
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;

import base.logic.MyPost;
import base.MyBaseController;
import pages.util.img.*;
import util.*;


@Controller
public class MyCreatePost
{
	HttpServletRequest global_request = null;
	MyPost global_mp = null;
	
	
	@RequestMapping("/create_post.cgi")
	public String createNewThread
	(
		HttpServletRequest request,
		HttpServletResponse response,
		@CookieValue(value="password", defaultValue="123456") String s_pass,
		Model model
	)
	{
		globalFieldsInit(request);
		
		//проверяем является ли полученный запрос multipart/form-data
		if (!ServletFileUpload.isMultipartContent(request)) { /* ... */ }
		// Создаём класс фабрику
		DiskFileItemFactory factory = new DiskFileItemFactory();
		//Буфер в памяти под файл
		factory.setSizeThreshold(1024*1024);
		// устанавливаем временную директорию
		File tempDir = (File) request.getSession().getServletContext().getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(tempDir);
		//Создаём сам загрузчик
		ServletFileUpload upload = new ServletFileUpload(factory);
		//максимальный размер данных который разрешено загружать в байтах
		upload.setSizeMax(1024 * 1024 * 10);
		
		try
		{
			parsePostRequest(upload.parseRequest(request));
		}
		catch(Exception e) {  }
		
		//Сравнение пароля поста и значения cookie (если не равны, и пользователь ввел новый пароль, переписываем значение cookie)
		if( global_mp.getPost_pass().equals(s_pass) ) { /* ... */ }
		else{ response.addCookie(new Cookie("password", global_mp.getPost_pass())); }
		
		global_mp.setPost_text( MyTextUtils.textDecoration(global_mp.getPost_text()) );
		
		MyBaseController.addNewPost( global_mp );
		return "redirect:/show_thread.cgi?thread_number=" + global_mp.getThread_number();
	}
	
	
	//Начальная инициализация глобального объекта
	private void globalFieldsInit(HttpServletRequest request)
	{
		global_request = request;
		
		global_mp = new MyPost();
		global_mp.setImage_name("no_image");
	}
	
	
	//Функция расшифтовки параметров запроса
	private void parsePostRequest( List items )
	{
		Iterator iter = items.iterator();
		
		while( iter.hasNext() )
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
	}
	
	
	//Выбираем, как будем сохранять файл
	private void processUploadedFile(FileItem item)
	{
		//Какой тип файла нам передали?
		if(-1 < item.getName().indexOf(".png"))
		{
			writeFileOnDisk(item, ".png");
		}
		else if((-1 < item.getName().indexOf(".jpg")) || (-1 < item.getName().indexOf(".jpeg")))
		{
			writeFileOnDisk(item, ".jpeg");
		}
	}
	
	
	//Запись файла на диск
	public void writeFileOnDisk(FileItem item, String file_resolution)
	{
		String file_name = Integer.toString(MyBaseController.maxPostNumber() + 1) + file_resolution;
		//Имя оригинального файла
		String path = global_request.getSession().getServletContext().getRealPath("/src/" + file_name);
		//Имя файла миниатюры
		String mini_path = global_request.getSession().getServletContext().getRealPath("/thumb/" + file_name);
		
		//Записываем имя файла в поля объекта MyPost
		//Также вычисляем и записываем размер
		global_mp.setImage_name(file_name);
		global_mp.setImage_size(item.getSize()/1024);
		
		//Создаем файл для записи на диск
		File uploadetFile = new File(path);
		
		//записываем в него данные
		try
		{
			uploadetFile.createNewFile();
			item.write(uploadetFile);
			
			int[] n_img_params = MyImageOperations.ResizeImage(uploadetFile, mini_path);
			global_mp.setImage_height(n_img_params[0]);
			global_mp.setImage_width(n_img_params[1]);
		}
		catch( Exception e ) { System.out.println("Ошибка в WriteFileOnDisk()" + e); }
	}
	
	
	//Отлавливаем текстовы параметры, и пишем их в объект MyPost
	private void processFormField(FileItem item)
	{
		try
		{
			if(item.getFieldName().equals("thread_number"))
			{
				global_mp.setThread_number(Integer.parseInt(new String(item.getString().getBytes("ISO-8859-1"),"UTF-8")));
			}
			if(item.getFieldName().equals("name_field"))
			{
				global_mp.setPost_name(new String(item.getString().getBytes("ISO-8859-1"),"UTF-8"));
			}
			if(item.getFieldName().equals("theme_field"))
			{
				global_mp.setPost_theme(new String(item.getString().getBytes("ISO-8859-1"),"UTF-8"));
			}
			if(item.getFieldName().equals("text_field"))
			{
				global_mp.setPost_text(new String(item.getString().getBytes("ISO-8859-1"),"UTF-8"));
			}
			if(item.getFieldName().equals("pass_field"))
			{
				global_mp.setPost_pass(new String(item.getString().getBytes("ISO-8859-1"),"UTF-8"));
			}
		}
		catch( Exception e ) { System.out.println("Ошибка в MyMakeNewThread.processFormField()" + e); }
	}
}
