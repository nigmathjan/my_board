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
import util.*;


@Controller
public class MyShowBoard
{
	//Вывод страницы доски
	@RequestMapping("/show_board.cgi")
	public String showBoard
	(
		HttpServletRequest request,
		@CookieValue(value="password", defaultValue="123456") String s_pass,
		Model model
	)
	{
		String s_page = request.getParameter("page");
		int n_page = 0;
		
		try
		{
			n_page = Integer.parseInt(s_page);
		}
		catch( Exception e )
		{
			model.addAttribute("err", "Ошибка! Неверный индекс страницы доски");
			return "WEB-INF/jsp/err_page.jsp";
		}
		
		if( !isPageExists(n_page, MyFinalValues.N_THREADS_ON_PAGE) )
		{
			model.addAttribute("err", "Ошибка! Страницы с номером " + Integer.toString(n_page) + " нет!");
			return "WEB-INF/jsp/err_page.jsp";
		}
		
		model.addAttribute("banner", MyHtmlCreator.drawBanner());
		model.addAttribute("create_thread_form", MyHtmlCreator.drawThreadForm(s_pass));
		model.addAttribute("threads_area", drawThreadsPage(n_page));
		model.addAttribute("page_locator", MyHtmlCreator.createNavigationString(MyFinalValues.N_THREADS_ON_PAGE));
		model.addAttribute("footer", MyHtmlCreator.drawFooter());
		
		return "WEB-INF/jsp/board_page.jsp";
	}
	
	
	//Рисуем страницу с тредами
	//n_page - номер страницы с тредами
	public String drawThreadsPage( int n_page )
	{
		String s_result = "";
		int n_threads = MyBaseController.howManyThreadsOnBoard();
		
		//Получаем все треды на нужной странице
		List<MyThread> mt_list = MyBaseController.getThreadsOnPage(n_page, MyFinalValues.N_THREADS_ON_PAGE);
		for( MyThread mt_index : mt_list )
		{
			s_result += 
			"<div class=\"thread\">\n" +
			MyHtmlCreator.drawOPPost
				(
					MyBaseController.getOPPost(mt_index.getThread_number()), 
					MyBaseController.howManyPostsInThread(mt_index.getThread_number()) - MyFinalValues.N_POSTS_BELOW_OP
				);
			
			//Получаем несколько последних постов треда
			List<MyPost> mp_list = MyBaseController.getSomeLastPostsFromThread(mt_index.getThread_number(), MyFinalValues.N_POSTS_BELOW_OP);
			for( MyPost post_iter : mp_list )
			{
				s_result += MyHtmlCreator.drawPost(post_iter);
			}
			
			s_result += "</div>\n";
		}
		
		return s_result;
	}
	
	
	//Проверка, есть ли такая страница на доске
	private boolean isPageExists( int n_page, int n_threads_on_page )
	{
		int n_threads = MyBaseController.howManyThreadsOnBoard();
		
		if( (n_page * n_threads_on_page) > n_threads )
		{
			return false;
		}
		else
		{
			return true;
		}
	}
}
