package one.two.three.service;

import one.two.three.entity.DetailUserInfo;

public interface IdetailUserInfo {
	
	public void saveUserDetailInfo(DetailUserInfo detailUserInfo);
	
	public DetailUserInfo finduserOrderHistory(int user_id);
}
