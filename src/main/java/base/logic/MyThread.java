package base.logic;


import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import java.util.Set;
import java.util.HashSet;


//Класс - таблица тредов
@Entity
@Table( name = "thread" )
public class MyThread
{
	@Id
	@Column( name = "thread_number" )
	private Integer thread_number;
	
	@Column( name = "last_update" )
	private String last_update;
	
	
	public Integer getThread_number() { return thread_number; }
	public String getLast_update() { return last_update; }
	
	
	public void setThread_number( Integer thread_number ) { this.thread_number = thread_number; }
	public void setLast_update( String last_update ) { this.last_update = last_update; }
}
