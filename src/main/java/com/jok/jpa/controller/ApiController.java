package com.jok.jpa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jok.jpa.computer.domain.Computer;
import com.jok.jpa.computer.service.ComputerService;
import com.jok.jpa.mobile.domain.Mobile;

@RestController
@RequestMapping("api")
public class ApiController {
	@Autowired
	public ComputerService computerService;
	@GetMapping("/add")
	public String add() {
		Computer computer = new Computer();
		computer.setName("小米pro");
		computer.setPrice(7000.00);
		
		Mobile mobile = new Mobile();
		mobile.setName("iphone X");
		mobile.setPrice(10000.00);
		computerService.add(computer,mobile);
		return "success";
	}
	@GetMapping("/getlist")
	public ResponseEntity<?>  getList(String name){
		List<Computer> list = computerService.getList(name);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/getLikeList")
	public ResponseEntity<?>  getLikeList(String name){
		List<Computer> list = computerService.getLikeList(name);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

}
