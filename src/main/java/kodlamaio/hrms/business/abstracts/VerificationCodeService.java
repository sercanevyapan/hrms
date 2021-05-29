package kodlamaio.hrms.business.abstracts;

import java.util.List;

import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.abstracts.User;
import kodlamaio.hrms.entities.concretes.VerificationCode;



public interface VerificationCodeService {


	VerificationCode findByCode(String code);
	DataResult<List<VerificationCode>> findAllByCode();
	String createActivationCode(User user);
	Result activateUser(String activationCode);
}
