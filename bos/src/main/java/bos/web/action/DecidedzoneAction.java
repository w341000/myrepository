package bos.web.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import bos.domain.Decidedzone;
import bos.service.IDecidedzoneService;
import bos.web.action.base.BaseAction;
@Controller @Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone> {
	//接受分区id
	private String[] subareaid;
	@Resource
	private IDecidedzoneService decidedzoneService;
	public void setSubareaid(String[] subareaid) {
		this.subareaid = subareaid;
	}
	
	/**
	 * 添加定区
	 */
	public String add(){
		decidedzoneService.save(model,subareaid);
		return "list";
	}
	/**
	 * 列出定区已经关联的分区数据
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public String listAssociationSubareaAjax() throws IOException{
		if(StringUtils.isNotBlank(model.getId())){
			Decidedzone decidedzone=decidedzoneService.findById(model.getId());
			List list =new ArrayList(decidedzone.getSubareas());
			String[] excludes=new String[]{"decidedzone","subareas"};
			this.WriteList2Json(list, excludes);
		}
		return NONE;
	}
	/**
	 * 更新操作
	 * @return
	 */
	public String edit(){
		Decidedzone decidedzone = decidedzoneService.findById(model.getId());
		decidedzone.setName(model.getName());
		decidedzone.setStaff(model.getStaff());
		decidedzoneService.update(decidedzone,subareaid);
		return "list";
	}
	/**
	 * 分页查询
	 */
	public String pageQuery() throws IOException{
		decidedzoneService.pageQuery(pageBean);
		String[] excludes = new String[] { "pageSize", "currentPage",
				"detachedCriteria", "subareas", "decidedzones" };
		this.WritePageBean2Json(excludes);
		return NONE;
	}
}
