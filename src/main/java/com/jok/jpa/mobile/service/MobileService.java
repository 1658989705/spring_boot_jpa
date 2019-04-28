package com.jok.jpa.mobile.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jok.jpa.mobile.dao.MobileDao;
import com.jok.jpa.mobile.domain.Mobile;

@Service
@Transactional
public class MobileService {
	@Autowired
	public MobileDao mobileDao;
	
	public void add(Mobile moble) {
		this.mobileDao.save(moble);
	}

}
