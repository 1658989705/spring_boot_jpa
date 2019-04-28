package com.jok.jpa.computer.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jok.jpa.computer.dao.ComputerDao;
import com.jok.jpa.computer.domain.Computer;
import com.jok.jpa.mobile.dao.MobileDao;
import com.jok.jpa.mobile.domain.Mobile;

@Service

public class ComputerService {
	@Autowired
	public ComputerDao computerDao;
	
	@Autowired
	public MobileDao mobileDao;
	
	@Transactional
	public void add(Computer computer) {
		this.computerDao.save(computer);
	}
	@Transactional
	public void add(Computer computer, Mobile mobile) {
		this.computerDao.save(computer);
		this.mobileDao.save(mobile);
	}
	public List<Computer> getList(String name) {
		List<Computer> list = computerDao.findByNameLike(name);
		return list;
	}
	public List<Computer> getLikeList(String name) {
		List<Computer> list = computerDao.findByNameLike(name);
		return list;
	}

}
