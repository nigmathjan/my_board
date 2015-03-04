package pages;
 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.List;
import java.util.ArrayList;

import pages.util.*;
import base.MyBaseController;
import base.logic.MyThread;
import base.logic.MyPost;


@Controller
public class MyShowThread
{
	//Вывод треда
	@RequestMapping("/show_thread.cgi")
	public String showThread
	(
		HttpServletRequest request,
		@CookieValue(value="password", defaultValue="123456") String s_pass,
		Model model
	)
	{
		int n_thread_number = Integer.parseInt(request.getParameter("thread_number"));
		if(!isThreadExists(n_thread_number))
		{
			model.addAttribute("err", "Ошибка! Тред №" + Integer.toString(n_thread_number) + " не существует!");
			return "WEB-INF/jsp/err_page.jsp";
		}
		else
		{
			model.addAttribute("thread", drawThread(n_thread_number));
			model.addAttribute("thread_form", MyHtmlCreator.drawPostForm(n_thread_number, s_pass));
			
			return "WEB-INF/jsp/thread_page.jsp";
		}
	}
	
	
	//Функция создания треда
	private String drawThread( int n_thread_number )
	{
		String s_result = "";
		
		s_result += MyHtmlCreator.drawOPPost( MyBaseController.getOPPost(n_thread_number ), 0 );
		
		List<MyPost> all_thread_posts = MyBaseController.getAllPostsFromThreadWithoutFirst( n_thread_number );
		
		for( MyPost mp_index : all_thread_posts )
		{
			s_result += MyHtmlCreator.drawPost(mp_index);
		}
		
		return s_result;
	}
	
	
	//Есть ли такой тред?
	private boolean isThreadExists( int n_thread_number )
	{
		List<MyThread> mt_list =  MyBaseController.getAllThreads();
		
		for( MyThread mt_index : mt_list )
		{
			if( mt_index.getThread_number() == n_thread_number )
			{
				return true;
			}
		}
		
		return false;
	}
	
}
