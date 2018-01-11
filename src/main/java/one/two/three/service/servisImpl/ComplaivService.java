package one.two.three.service.servisImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import one.two.three.DAO.IcomplainDAO;
import one.two.three.entity.Complain;
import one.two.three.entity.User;
import one.two.three.service.IcomplainService;
import one.two.three.service.IuserService;

@Service
@Transactional
public class ComplaivService implements IcomplainService {
	
	@Autowired
	private IcomplainDAO complainDAO;
	@Autowired
	private IuserService userService;

	@Override
	public void saveMessage(Complain message) {
		complainDAO.save(message);
		
	}

	@Override
	public void addComplain(int userId, String message) {
		String date = new SimpleDateFormat("hh:mm--dd-MM-YYYY").format(new Date());
		User user = userService.oneUserWithComplain(userId);
		Complain complain = new Complain();
		complain.setMessage(message);
		complain.setDate(date);
		user.addBlame(complain);
		saveMessage(complain);
	}

	@Override
	public List<Complain> findAll() {
		List<Complain> cl = complainDAO.findAllWithAuthor();
		return cl;
	}

	@Override
	public List<Complain> findTodayComplain() {
		String todayDate = new SimpleDateFormat("dd-MM-YYYY").format(new Date());
		String datePattern = "%"+todayDate+"%";
		List<Complain> cl = complainDAO.findTodayComplains(datePattern);		
		return cl;
	}
	
	

}
