package service.privilege.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bean.privilege.SystemPrivilege;
import service.privilege.SystemPrivilegeService;
import dao.impl.BaseDaoImpl;
@Service("systemPrivilegeService") @Transactional
public class SystemPrivilegeServiceImpl extends BaseDaoImpl<SystemPrivilege> implements SystemPrivilegeService {

	@Override
	public void saves(List<SystemPrivilege> privileges){
		for(SystemPrivilege privilege:privileges){
			super.save(privilege);
		}
		
	}
}
