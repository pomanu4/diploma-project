package one.two.three.service;

import java.util.List;

import one.two.three.entity.Complain;

public interface IcomplainService {
	
	public void saveMessage(Complain message);
	
	public void addComplain(int userId, String message);
	
	public List<Complain> findAll();
	
	public List<Complain> findTodayComplain();

}
