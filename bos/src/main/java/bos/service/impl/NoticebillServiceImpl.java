package bos.service.impl;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bos.crm.CustomerService;
import bos.dao.IDecidedzoneDao;
import bos.dao.INoticebillDao;
import bos.dao.IWorkbillDao;
import bos.domain.Decidedzone;
import bos.domain.Noticebill;
import bos.domain.Staff;
import bos.domain.User;
import bos.domain.Workbill;
import bos.service.INoticebillService;
@Service @Transactional
public class NoticebillServiceImpl implements INoticebillService {
	@Resource
	private INoticebillDao noticebillDao;
	@Resource
	private IDecidedzoneDao decidedzoneDao;
	@Resource
	private IWorkbillDao workbillDao;
	@Resource
	private CustomerService proxy;

	@Override
	public void save(Noticebill model) {
		noticebillDao.save(model);//持久对象
		//获取取件地址
		String pickaddress = model.getPickaddress();
		//根据取件地址查询定区id --从crm查询
		String dId = proxy.findDecidedzoneIdByPickaddress(pickaddress);
		if(dId!=null){
			//查询到定区id,进行自动分单
			Decidedzone decidedzone = decidedzoneDao.findById(dId);
			Staff staff = decidedzone.getStaff();
			model.setStaff(staff); //业务通知单关联匹配到的取派员
			model.setOrdertype("自动");
			//需要为取派员创建一个工单
			Workbill workbill=new Workbill();
			workbill.setAttachbilltimes(0);//追单次数
			workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));//系统时间
			workbill.setNoticebill(model);//工单关联业务通知单
			workbill.setPickstate("未取件");//取件状态
			workbill.setRemark(model.getRemark());
			workbill.setStaff(staff);//关联取派员
			workbill.setType("新单");
			
			workbillDao.save(workbill);//保存工单
			
			//调用短信服务,给取派员发送短信
		}else{
			//没有查询到定区id,转为人工分单
			model.setOrdertype("人工");
		}
	}

}
