//Класс для работы с текстом (Распознавание тегов)
package src.txt;


public class MyTextUtils
{
	//Главная функция по обработке текста
	public static String TextDecoration(String s_src)
	{
		String s_result = s_src;
		
		s_result = DeleteBadSymbols(s_result);
		s_result = SearchTags(s_result);
		s_result = AndEndLineSymbols(s_result);
		
		return s_result;
	}
	
	
	//Функция преобразования тегов
	private static String SearchTags(String s_src)
	{
		String s_result = s_src;
		
		s_result = s_result.replace ("[b]", "<b>");
		s_result = s_result.replace ("[/b]", "</b>");
		s_result = s_result.replace ("[i]", "<i>");
		s_result = s_result.replace ("[/i]", "</i>");
		
		return s_result;
	}
	
	
	//Функция добавления переносов строк
	private static String AndEndLineSymbols(String s_src)
	{
		String s_result = s_src;
		
		s_result = s_result.replace ("\n", "<br/>");
		
		return s_result;
	}
	
	
	//Удаление "нехороших" символов
	private static String DeleteBadSymbols(String s_src)
	{
		String s_result = s_src;
		
		s_result = s_result.replace ("<", "");
		s_result = s_result.replace (">", "");
		
		return s_result;
	}
}
