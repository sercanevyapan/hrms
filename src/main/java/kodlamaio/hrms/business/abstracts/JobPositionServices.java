package kodlamaio.hrms.business.abstracts;

import java.util.List;

import kodlamaio.hrms.entities.concretes.JobPosition;

public interface JobPositionServices {
	
	List<JobPosition> getAll();

}
