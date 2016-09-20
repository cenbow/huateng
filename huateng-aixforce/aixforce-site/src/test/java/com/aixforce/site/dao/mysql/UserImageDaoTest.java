package com.aixforce.site.dao.mysql;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.aixforce.site.model.UserImage;

public class UserImageDaoTest extends BaseSpringTest{
	@Autowired
	private UserImageDao dao;
//	@Test
	public void testFindById() {
		fail("Not yet implemented");
	}

//	@Test
	public void testCountOf() {
		fail("Not yet implemented");
	}

//	@Test
	public void testFindByUserId() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreate() {
		UserImage ug = new UserImage();
		ug.setCreatedAt(new Date());
		ug.setFileName("test_file_name1");
		ug.setFileSize(100000);
		ug.setUserId(1L);
		dao.create(ug);
	}

//	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

//	@Test
	public void testDeleteByUserId() {
		fail("Not yet implemented");
	}

//	@Test
	public void testTotalSizeByUserId() {
		fail("Not yet implemented");
	}

}
