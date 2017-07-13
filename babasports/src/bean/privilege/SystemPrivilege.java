package bean.privilege;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
/**
 * 系统权限
 */
@Entity
public class SystemPrivilege {

	/**标识属性**/
	@Id //@EmbeddedId 也可以
	private SystemPrivilegePK id;
	/**权限名称**/
	@Column(length=20,nullable=false)
	private String name;
	/**权限被分配在的权限组**/
	@ManyToMany(mappedBy="privileges") @Cascade({CascadeType.REFRESH})
	private Set<PrivilegeGroup> groups=new HashSet<PrivilegeGroup>();
	
	public SystemPrivilege(){}
	
	public SystemPrivilege(SystemPrivilegePK id) {
		super();
		this.id = id;
	}
	/**
	 * 
	 * @param module 模块
	 * @param privilege 权限
	 * @param name 名称
	 */
	public SystemPrivilege(String module, String privilege,String name) {
		super();
		this.id = new SystemPrivilegePK(module, privilege);
		this.name = name;
	}
	public SystemPrivilegePK getId() {
		return id;
	}
	public void setId(SystemPrivilegePK id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Set<PrivilegeGroup> getGroups() {
		return groups;
	}

	public void setGroups(Set<PrivilegeGroup> groups) {
		this.groups = groups;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SystemPrivilege other = (SystemPrivilege) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
}
