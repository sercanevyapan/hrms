package kodlamaio.hrms.business.concrete;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.JobTitleServices;
import kodlamaio.hrms.dataAccess.abstracts.JobTitleDao;
import kodlamaio.hrms.entities.concretes.JobTitle;

@Service
public class JobTitleManager implements JobTitleServices {
	
	private JobTitleDao productDao;
	
	@Autowired
	public JobTitleManager(JobTitleDao productDao) {
		super();
		this.productDao = productDao;
	}

	@Override
	public List<JobTitle> getAll() {
		// TODO Auto-generated method stub
		return productDao.findAll();
	}

}
