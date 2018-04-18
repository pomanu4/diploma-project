package ua.com.company.service;

import java.util.List;

import ua.com.company.entity.Complain;

public interface IcomplainService {

	public void saveMessage(Complain message);

	public void addComplain(int userId, String message);

	public List<Complain> findAll();

	public List<Complain> findTodayComplain();

}
