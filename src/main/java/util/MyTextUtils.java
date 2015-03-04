//Класс для работы с текстом (Распознавание тегов)
package util;


public class MyTextUtils
{
	//Главная функция по обработке текста
	public static String textDecoration(String s_src)
	{
		String s_result = s_src;
		
		s_result = deleteBadSymbols(s_result);
		s_result = searchTags(s_result);
		s_result = addEndLineSymbols(s_result);
		
		return s_result;
	}
	
	
	//Функция преобразования тегов
	private static String searchTags(String s_src)
	{
		String s_result = s_src;
		
		s_result = s_result.replace ("[b]", "<b>");
		s_result = s_result.replace ("[/b]", "</b>");
		s_result = s_result.replace ("[i]", "<i>");
		s_result = s_result.replace ("[/i]", "</i>");
		
		return s_result;
	}
	
	
	//Функция добавления переносов строк
	private static String addEndLineSymbols(String s_src)
	{
		String s_result = s_src;
		
		s_result = s_result.replace ("\n", "<br/>");
		
		return s_result;
	}
	
	
	//Удаление "нехороших" символов
	private static String deleteBadSymbols(String s_src)
	{
		String s_result = s_src;
		
		s_result = s_result.replace ("<", "");
		s_result = s_result.replace (">", "");
		
		return s_result;
	}
}
