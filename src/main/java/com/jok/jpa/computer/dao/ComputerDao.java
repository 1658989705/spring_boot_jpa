package com.jok.jpa.computer.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jok.jpa.computer.domain.Computer;

public interface ComputerDao extends JpaRepository<Computer, Integer> {
	  @Query(value = " select id,name,price from computer m where m.name=:name", nativeQuery = true)
	  List<Computer> findByComputerName(@Param("name") String name);
	 
	  // 模糊查询包含 name 的数据
	  List<Computer> findByNameLike(String name);
	 
	  // 模糊查询 不 包含 name 的数据
	  List<Computer> findByNameNotLike(String name);
}
