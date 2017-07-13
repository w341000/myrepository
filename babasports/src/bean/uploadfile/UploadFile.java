package bean.uploadfile;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 * 存放上传的文件
 * @author Administrator
 *
 */
@Entity
public class UploadFile {
	/**类别id **/
	@Id//指定主键id,生成方式为容器自动选择
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(length=80,nullable=false)
	private String filepath;
	@Temporal(TemporalType.TIMESTAMP)
	private Date uploadtime=new Date();
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public Date getUploadtime() {
		return uploadtime;
	}
	
	public void setUploadtime(Date uploadtime) {
		this.uploadtime = uploadtime;
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
		UploadFile other = (UploadFile) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
