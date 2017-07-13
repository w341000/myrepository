package bean.privilege;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

/**
 * 权限组
 */
@Entity 
public class PrivilegeGroup {
	/**标识属性**/
	@Id @Column(length=36) //指定主键id,使用hibernate的uuid方法进行生成
	@GenericGenerator(name="UUID",strategy="uuid")
	@GeneratedValue(generator="UUID")
	private String groupid;
	
	/**权限名称**/
	@Column(length=20,nullable=false)
	private String name;
	/**使用了该权限组的员工**/
	@ManyToMany(mappedBy="groups")@Cascade({CascadeType.REFRESH})
	private Set<Employee> employees=new HashSet<Employee>();
	
	/**该组具有的权限**/
	//抓取策略,立即加载
	@ManyToMany(fetch=FetchType.EAGER) @Cascade({CascadeType.REFRESH})
	@JoinTable(name="ps",inverseJoinColumns={//inverseJoinColumns定义中间表与关心被维护的的关系映射
			@JoinColumn(name="module",referencedColumnName="module"),//中间表中的module字段引用了关系被维护端的module字段
			@JoinColumn(name="privilege",referencedColumnName="privilege")}
	,joinColumns={@JoinColumn(name="group_id")}) //中间表中的group_id字段引用了关系维护端的主键字段,并且group_id是主键
	private Set<SystemPrivilege> privileges=new HashSet<SystemPrivilege>();
	public PrivilegeGroup(){}
	public PrivilegeGroup(String groupid) {
		this.groupid=groupid;
	}
	public Set<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<SystemPrivilege> getPrivileges() {
		return privileges;
	}
	public void setPrivileges(Set<SystemPrivilege> privileges) {
		this.privileges = privileges;
	}
	/**
	 * 往组里添加权限
	 * @param privilege
	 */
	public void addSystemPrivilege(SystemPrivilege privilege){
		this.privileges.add(privilege);
	}
	
}
