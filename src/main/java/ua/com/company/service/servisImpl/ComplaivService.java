package ua.com.company.service.servisImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.company.dao.IcomplainDAO;
import ua.com.company.entity.Complain;
import ua.com.company.entity.User;
import ua.com.company.service.IcomplainService;
import ua.com.company.service.IuserService;

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
		user.addComplain(complain);
		saveMessage(complain);
	}

	@Override
	public List<Complain> findAll() {
		List<Complain> complains = complainDAO.findAllWithAuthor();
		return complains;
	}

	@Override
	public List<Complain> findTodayComplain() {
		String todayDate = new SimpleDateFormat("dd-MM-YYYY").format(new Date());
		String datePattern = "%" + todayDate + "%";
		List<Complain> complains = complainDAO.findTodayComplains(datePattern);
		return complains;
	}

}
