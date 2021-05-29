package kodlamaio.hrms.business.abstracts;

import kodlamaio.hrms.core.utilities.results.DataResult;
import java.util.List;
import kodlamaio.hrms.entities.abstracts.User;

public interface UserService {
	DataResult<List<User>> getAll();
}
