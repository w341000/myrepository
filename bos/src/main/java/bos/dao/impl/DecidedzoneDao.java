package bos.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import bos.dao.IDecidedzoneDao;
import bos.dao.base.impl.BaseDaoImpl;
import bos.domain.Decidedzone;
import bos.utils.PageBean;
@Repository
public class DecidedzoneDao extends BaseDaoImpl<Decidedzone> implements	IDecidedzoneDao {

}
