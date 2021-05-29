package kodlamaio.hrms.business.concrete;

import java.security.SecureRandom;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.VerificationCodeService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.UserDao;
import kodlamaio.hrms.dataAccess.abstracts.VerificationCodeDao;
import kodlamaio.hrms.entities.abstracts.User;
import kodlamaio.hrms.entities.concretes.VerificationCode;


@Service
public class VerificationCodeManager implements VerificationCodeService {

	VerificationCodeDao verificationCodeDao;
	UserDao userDao;

	@Autowired
	public VerificationCodeManager(VerificationCodeDao verificationCodeDao, UserDao userDao) {
		super();
		this.verificationCodeDao = verificationCodeDao;
		this.userDao = userDao;
	}

	@Override
	public DataResult<List<VerificationCode>> findAllByCode() {

		return new SuccessDataResult<List<VerificationCode>>(this.verificationCodeDao.findAll(),"Basariyla listelendi");
	}

	String generatedCode = "";

	@Override
	public String createActivationCode(User user) {

		for (int i = 0; i == 0; i = 0) {
			generatedCode = randomCodeGenarator(20); 
			if (findByCode(generatedCode) == null) {
				break;
			}
		}
		VerificationCode verificationCode = new VerificationCode();
		verificationCode.setId(user.getId());
		verificationCode.setCode(generatedCode);
		verificationCodeDao.save(verificationCode);
		return generatedCode;
	}

	@Override
	public Result activateUser(String activationCode) {
		
		VerificationCode verificationCode = verificationCodeDao.findByCode(activationCode);
		verificationCode.setVerified(true);
		verificationCodeDao.save(verificationCode);
		return new SuccessResult("Kullanici aktivasyonu gerceklesti");

	}

	@Override
	public VerificationCode findByCode(String code) {

		return this.verificationCodeDao.findByCode(code);
	}

	private final String whatsUp = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private SecureRandom mixer = new SecureRandom();

	private String randomCodeGenarator(int lenght) {
		StringBuilder randomValueConstructor = new StringBuilder(lenght);
		for (int i = 0; i < lenght; i++) {
			randomValueConstructor.append(whatsUp.charAt(mixer.nextInt(whatsUp.length())));
		}
		return randomValueConstructor.toString();
	}
}
