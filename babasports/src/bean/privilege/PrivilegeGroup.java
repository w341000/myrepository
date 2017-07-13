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
 * Ȩ����
 */
@Entity 
public class PrivilegeGroup {
	/**��ʶ����**/
	@Id @Column(length=36) //ָ������id,ʹ��hibernate��uuid������������
	@GenericGenerator(name="UUID",strategy="uuid")
	@GeneratedValue(generator="UUID")
	private String groupid;
	
	/**Ȩ������**/
	@Column(length=20,nullable=false)
	private String name;
	/**ʹ���˸�Ȩ�����Ա��**/
	@ManyToMany(mappedBy="groups")@Cascade({CascadeType.REFRESH})
	private Set<Employee> employees=new HashSet<Employee>();
	
	/**������е�Ȩ��**/
	//ץȡ����,��������
	@ManyToMany(fetch=FetchType.EAGER) @Cascade({CascadeType.REFRESH})
	@JoinTable(name="ps",inverseJoinColumns={//inverseJoinColumns�����м������ı�ά���ĵĹ�ϵӳ��
			@JoinColumn(name="module",referencedColumnName="module"),//�м���е�module�ֶ������˹�ϵ��ά���˵�module�ֶ�
			@JoinColumn(name="privilege",referencedColumnName="privilege")}
	,joinColumns={@JoinColumn(name="group_id")}) //�м���е�group_id�ֶ������˹�ϵά���˵������ֶ�,����group_id������
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
	 * ���������Ȩ��
	 * @param privilege
	 */
	public void addSystemPrivilege(SystemPrivilege privilege){
		this.privileges.add(privilege);
	}
	
}
