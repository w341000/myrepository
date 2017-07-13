package bean.privilege;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import bean.user.Gender;

/**
 * 员工实体
 */
@Entity
public class Employee {
	/* 用户名,主键,20位*/
	@Id @Column(length=20)
	private String username;
	/* 密码 20位, 不能为null */
	@Column(length=20,nullable=false)
	private String password;
	/* 真实姓名 8位 不能为null */
	@Column(length=8,nullable=false)
	private String realname;
	/* 性别 5位 不能为null */
	@Enumerated(EnumType.STRING) @Column(length=5,nullable=false)
	private Gender gender;
	/* 学历 10位 */
	@Column(length=10)
	private String degree;
	/* 身份证 必须提供 */
	@OneToOne(optional=false) @JoinColumn(name="card_id") @Cascade({CascadeType.ALL})
	private IDCard idCard ;//一对一,员工作为关系维护端
	/* 毕业院校 20位 */
	@Column(length=20)
	private String school;
	/* 联系电话 18 */
	@Column(length=18)
	private String phone;
	/* 电子邮件 40 */
	@Column(length=40)
	private String email;
	/* 照片 80 */ 
	@Column(length=80)
	private String imageName; //只存放文件名称,而且文件名称采用uuid生成,图片保存在/images/employee/[username]/文件名
	/* 员工在职状态 true为在职,false为离职 */
	@Column(nullable=false)
	private Boolean visible = true;
	/* 员工所在部门 */
	@ManyToOne @Cascade({CascadeType.REFRESH}) @JoinColumn(name="department_id")
	private Department department;//双向一对多,多对一
	/*员工具有的所有权限组*/
	@ManyToMany(fetch=FetchType.EAGER) @Cascade({CascadeType.REFRESH})
	@JoinTable(name="eg",inverseJoinColumns=@JoinColumn(name="group_id")
	,joinColumns={@JoinColumn(name="username")})
	private Set<PrivilegeGroup> groups=new HashSet<PrivilegeGroup>();
	
	@Transient //不需要映射
	public String getImagePath(){
		if(this.username!=null && this.imageName!=null) return "/images/employee/"+this.username+"/"+this.imageName;//路径/images/employee/[username]/文件名
		return null;
	}
	/**
	 * 添加权限组
	 * @param group
	 */
	public void addPrivilegeGroup(PrivilegeGroup group){
		this.groups.add(group);
	}
	
	public Set<PrivilegeGroup> getGroups() {
		return groups;
	}
	public void setGroups(Set<PrivilegeGroup> groups) {
		this.groups = groups;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public IDCard getIdCard() {
		return idCard;
	}
	public void setIdCard(IDCard idCard) {
		this.idCard = idCard;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public Boolean getVisible() {
		return visible;
	}
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
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
		Employee other = (Employee) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	

}
