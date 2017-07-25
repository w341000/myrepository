package bos.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import bos.dao.IWorkbillDao;
import bos.dao.base.impl.BaseDaoImpl;
import bos.domain.Workbill;
import bos.utils.PageBean;
@Repository
public class WorkbillDaoImpl extends BaseDaoImpl<Workbill> implements
		IWorkbillDao {


}
