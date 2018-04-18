package ua.com.company.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ua.com.company.entity.DetailUserInfo;

public interface IdetailUserInfoDAO extends JpaRepository<DetailUserInfo, Integer> {

	@Query("SELECT d FROM DetailUserInfo d LEFT JOIN FETCH d.history WHERE d.id=(:id)")
	DetailUserInfo findUserOrderHistory(@Param("id") Integer user_id);
}
