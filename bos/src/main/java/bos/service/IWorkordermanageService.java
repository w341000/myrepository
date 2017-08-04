package bos.service;

import java.util.List;

import bos.domain.Workordermanage;

public interface IWorkordermanageService {

	public void save(Workordermanage model);

	public List<Workordermanage> findListNotStart();

}
