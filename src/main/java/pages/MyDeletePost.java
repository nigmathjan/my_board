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
import org.springframework.web.bind.annotation.CookieValue;

import pages.util.*;

import base.MyBaseController;
import base.logic.MyThread;
import base.logic.MyPost;
import util.*;


@Controller
public class MyDeletePost
{
	//Удалить пост
	@RequestMapping("/delete_post.cgi")
	public String deletePost
	(
		HttpServletRequest request,
		@CookieValue(value="password", defaultValue="123456") String s_pass,
		Model model
	)
	{
		String s_post = request.getParameter("post_number");
		String s_thread = request.getParameter("thread_number");
		int n_post = 0;
		
		try
		{
			n_post = Integer.parseInt(s_post);
		}
		catch(Exception e) 
		{
		}
		
		if(!isPassCorrect(n_post, s_pass)) 
		{
			return "redirect:/show_thread.cgi?thread_number=" + s_thread;
		}
		else //Если пароли поста и куки совпадают, удаляем
		{
			MyBaseController.deletePost(Integer.parseInt(s_post));
			return "redirect:/show_thread.cgi?thread_number=" + s_thread;
		}
	}
	
	
	//Проверка, совпадают ли пароли
	private boolean isPassCorrect( int n_post_number, String s_pass )
	{
		MyPost mp = MyBaseController.getPostByNumber( n_post_number );
		
		if( (mp.getPost_pass() == null) || (!mp.getPost_pass().equals(s_pass)) )
		{
			return false;
		}
		
		return true;
	}
}
