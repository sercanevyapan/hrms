package kodlamaio.hrms.business.abstracts;

import java.util.List;

import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.abstracts.User;
import kodlamaio.hrms.entities.concretes.Candidate;

public interface CandidateService {
	
DataResult<List<Candidate>> findAllByEmail(String email); 
	
	DataResult<List<Candidate>> findAllByIdentificationNumber(String identificationNumber);
	
	DataResult<List<Candidate>> findAll(); //Tüm isbasvurusu yapanlarin listesi

	Result add(Candidate candidate); 

}
