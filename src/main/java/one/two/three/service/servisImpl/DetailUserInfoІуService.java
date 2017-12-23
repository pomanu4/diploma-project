package one.two.three.service.servisImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import one.two.three.DAO.IdetailUserInfoDAO;
import one.two.three.entity.DetailUserInfo;
import one.two.three.service.IdetailUserInfo;

@Service
@Transactional
public class DetailUserInfoІуService implements IdetailUserInfo {
	
	@Autowired
	IdetailUserInfoDAO duiDAO;

	@Override
	public void saveUserDetailInfo(DetailUserInfo detailUserInfo) {
		duiDAO.save(detailUserInfo);
		
	}

	@Override
	public DetailUserInfo finduserOrderHistory(int user_id) {
		DetailUserInfo detailUserInfo = duiDAO.findUserOrderHistory(user_id);
		return detailUserInfo;
	}
	
	

}
