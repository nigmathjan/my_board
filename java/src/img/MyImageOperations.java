package src.img;


import javax.imageio.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;


//Импорт из своих пакетов
import src.utils.*;


public class MyImageOperations
{
	//Функция ресайза картинки, возвращает int массив типа ширина/высота картинки
	public static int[] ResizeImage(File image_file, String mini_file_path)
	{
		BufferedImage img = null;
		
		if((-1 < mini_file_path.indexOf(".jpeg")) || (-1 < mini_file_path.indexOf(".jpg")))
		{
			try 
			{
				img = ImageIO.read(image_file);
				ResizeJpeg(img, mini_file_path);
			} 
			catch (IOException e) { System.out.println("Ошибка в ResizeImage()" + e); }
		}
		else if((-1 < mini_file_path.indexOf(".png")))
		{
			try 
			{
				img = ImageIO.read(image_file);
				ResizePng(img, mini_file_path);
			} 
			catch (IOException e) { System.out.println("Ошибка в ResizeImage()" + e); }
		}
		
		int[] n_img_size = {img.getWidth(), img.getHeight()};
		return n_img_size;
	}
	
	
	//Ресайз jpeg картинки
	private static void ResizeJpeg(BufferedImage sourceImage, String mini_file_path)
	{
		File f = new File(mini_file_path);
		
		Image thumbnail = sourceImage.getScaledInstance(CalculateOptimumWidth(sourceImage.getWidth(), sourceImage.getHeight()), -1, Image.SCALE_SMOOTH);
		BufferedImage bufferedThumbnail = 
		new BufferedImage(thumbnail.getWidth(null), thumbnail.getHeight(null), BufferedImage.TYPE_INT_RGB);
		bufferedThumbnail.getGraphics().drawImage(thumbnail, 0, 0, null);
		
		try
		{
			f.createNewFile();
			ImageIO.write(bufferedThumbnail, "jpeg", f);
		}
		catch( Exception e ) { System.out.println("Ошибка в ResizeJpeg()" + e); }
	}
	
	
	//Ресайз png картинки
	private static void ResizePng(BufferedImage sourceImage, String mini_file_path)
	{
		File f = new File(mini_file_path);
		
		Image thumbnail = sourceImage.getScaledInstance(200, -1, Image.SCALE_SMOOTH);
		BufferedImage bufferedThumbnail = 
		new BufferedImage(thumbnail.getWidth(null), thumbnail.getHeight(null), BufferedImage.TYPE_INT_RGB);
		bufferedThumbnail.getGraphics().drawImage(thumbnail, 0, 0, null);
		
		try
		{
			f.createNewFile();
			ImageIO.write(bufferedThumbnail, "png", f);
		}
		catch( Exception e ) { System.out.println("Ошибка в ResizeJpeg()" + e); }
	}
	
	
	//Вычисление оптимальной ширины миниатюры
	private static int CalculateOptimumWidth(int n_width, int n_height)
	{
		float f_result_width = 0;
		
		if(n_width >= n_height)
		{
			//Если толщина больше высоты, все нормально, сжимаем по ширине как обычно
			f_result_width = MyFinalValues.N_THUMB_IMG_SIZE;
		}
		else
		{
			f_result_width = (float) n_width/((float) n_height/MyFinalValues.N_THUMB_IMG_SIZE);
		}
		
		return (int) f_result_width;
	}
}
