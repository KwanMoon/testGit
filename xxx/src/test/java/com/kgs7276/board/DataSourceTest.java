package com.kgs7276.board;

import java.sql.Connection;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations = {
				"file:src/main/webapp/WEB-INF/spring/**/*.xml"
		})
public class DataSourceTest {
	
	private static final Logger logger = LoggerFactory.getLogger(DataSourceTest.class);

	@Inject
	private DataSource ds;
	
	@Test
	public void testDataSource() throws Exception {
		try(Connection conn = ds.getConnection()) {
			//System.out.println(conn);
			logger.info("연결 객체 : "  + conn.toString());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}