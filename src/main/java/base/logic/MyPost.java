package base.logic;


import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import java.util.Set;


//Класс - таблица постов
@Entity
@Table( name = "post" )
public class MyPost
{
	@Id
	@Column( name = "post_number" )
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	private Integer post_number;
	
	@Column( name = "thread_number" )
	private Integer thread_number;
	
	@Column( name = "post_date" )
	private String post_date;
	
	@Column( name = "post_name" )
	private String post_name;
	
	@Column( name = "post_theme" )
	private String post_theme;
	
	@Column( name = "post_text" )
	private String post_text;
	
	@Column( name = "post_pass" )
	private String post_pass;
	
	@Column( name = "image_name" )
	private String image_name;
	
	@Column( name = "image_size" )
	private Long image_size;
	
	@Column( name = "image_width" )
	private Integer image_width;
	
	@Column( name = "image_height" )
	private Integer image_height;
	
	
	public Integer getPost_number() { return post_number; }
	public Integer getThread_number() { return thread_number; }
	public String getPost_date() { return post_date; }
	public String getPost_name() { return post_name; }
	public String getPost_theme() { return post_theme; }
	public String getPost_text() { return post_text; }
	public String getPost_pass() { return post_pass; }
	public String getImage_name() { return image_name; }
	public Long getImage_size() { return image_size; }
	public Integer getImage_width() { return image_width; }
	public Integer getImage_height() { return image_height; }
	
	
	public void setPost_number( Integer post_number ) { this.post_number = post_number; }
	public void setThread_number( Integer thread_number ) { this. thread_number = thread_number; }
	public void setPost_date( String post_date ) { this.post_date = post_date; }
	public void setPost_name( String post_name ) { this.post_name = post_name; }
	public void setPost_theme( String post_theme ) { this.post_theme = post_theme; }
	public void setPost_text( String post_text ) { this.post_text = post_text; }
	public void setPost_pass( String post_pass ) { this.post_pass = post_pass; }
	public void setImage_name( String image_name ) { this.image_name = image_name; }
	public void setImage_size( Long image_size ) { this.image_size = image_size; }
	public void setImage_width( Integer image_width ) { this.image_width = image_width; }
	public void setImage_height( Integer image_height ) { this.image_height = image_height; }
}
