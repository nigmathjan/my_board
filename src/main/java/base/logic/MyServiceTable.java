package base.logic;


import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import java.util.Set;


//Класс - сервисная таблица
@Entity
@Table( name = "service_table" )
public class MyServiceTable
{
	@Id
	@Column( name = "id" )
	private Integer id;
	
	@Column( name = "admin_pass" )
	private String admin_pass;
	
	
	public Integer getId() { return id; }
	public String getAdmin_pass() { return admin_pass; }
	
	
	public void setId( Integer id ) { this.id = id; }
	public void setAdmin_pass( String admin_pass ) { this.admin_pass = admin_pass; }
}
