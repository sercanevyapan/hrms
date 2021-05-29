package kodlamaio.hrms.business.concrete;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.CandidateService;
import kodlamaio.hrms.business.abstracts.MernisDemoService;
import kodlamaio.hrms.business.abstracts.VerificationCodeService;
import kodlamaio.hrms.core.adapters.MernisDemo;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.CandidateDao;
import kodlamaio.hrms.dataAccess.abstracts.UserDao;
import kodlamaio.hrms.entities.abstracts.User;
import kodlamaio.hrms.entities.concretes.Candidate;

@Service
public class CandidateManager implements CandidateService {
	
	private CandidateDao candidateDao;
	private UserDao userDao;
	private VerificationCodeService verificationCodeService;
	private MernisDemoService mernisDemoService;

	@Autowired
	public CandidateManager(CandidateDao candidateDao, UserDao userDao, VerificationCodeService verificationCodeService,
			MernisDemoService mernisDemoService) {
		super();
		this.candidateDao = candidateDao;
		this.userDao = userDao;
		this.verificationCodeService = verificationCodeService;
		this.mernisDemoService = mernisDemoService;
	}

	@Override
	public DataResult<List<Candidate>> findAll() {

		return new SuccessDataResult<List<Candidate>>(this.candidateDao.findAll(),
				"Is arayanlar basarili bir sekilde listelendi");
	}

	@Override
	public Result add(Candidate candidate) {

		if (candidate.getFirstname() == null) {
			return new ErrorResult("Lütfen isminizi bos bırakmayınız.");
		}

		else if (candidate.getLastname() == null) {
			return new ErrorResult("Lütfen soyisminizi bos bırakmayınız.");
		}

		else if (candidate.getBirthDate() == null) {
			return new ErrorResult("Lütfen dogum yilini bos bırakmayınız.");
		}

		else if (candidate.getEmail() == null) {
			return new ErrorResult("Lütfen email adresinizi bos birakmayiniz.");
		}

		else if (candidate.getPassword().length() <= 6) {
			return new ErrorResult("Lütfen sifrenizi 6 karakterden az girmeyiniz");
		}

		else if (!mernisDemoService.isValidNationolityIdentity(candidate.getIdentificationNumber())) {

			return new ErrorResult("TC Kimlik dogrulamasi yapilamadi");
		}

		else if (isEmailValid(candidate.getEmail())) {
			candidate.setMailVerify(false);
			User savedUser = this.userDao.save(candidate);

			this.verificationCodeService.createActivationCode(savedUser);
			this.candidateDao.save(candidate);
			return new SuccessResult(candidate.getEmail() + " Adresine doğrulama kodu gönderildi");
		} else {

			return new ErrorResult("Kullanıcı bilgileri hatali");
		}
	}



	@Override
	public DataResult<List<Candidate>> findAllByEmail(String email) {
		return new SuccessDataResult<List<Candidate>>(this.candidateDao.findAllByEmail(email), "Listelendi");
	}

	@Override
	public DataResult<List<Candidate>> findAllByIdentificationNumber(String identificationNumber) {
		return new SuccessDataResult<List<Candidate>>(
				this.candidateDao.findAllByIdentificationNumber(identificationNumber), "Listelendi");
	}

	private final String EMAIL_PATTERN = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+.(com|org|net|edu|gov|mil|biz|info|mobi)(.[A-Z]{2})?$";

	public boolean isEmailValid(String emailInput) {
		Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
		return pattern.matcher(emailInput).find();
	}

}
