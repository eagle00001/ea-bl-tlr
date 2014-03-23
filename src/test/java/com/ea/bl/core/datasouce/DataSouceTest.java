package com.ea.bl.core.datasouce;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DataSouceTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassPathXmlApplicationContext cxt = new ClassPathXmlApplicationContext("applicationContext-test.xml");
//		testOracleDb(cxt);
		testMysqlDb(cxt);
	}
	
	
	private static void testMysqlDb(ClassPathXmlApplicationContext cxt) {
		DataSource ds = (DataSource)cxt.getBean("dataSource_mysql");
		try {
			Connection conn = ds.getConnection();
			if(conn!=null)
				System.out.println("mysql dataSouce connection is created.");
			else
				System.out.println("mysql dataSouce connection is error.");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				ds.getConnection().close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param cxt
	 */
	private static void testOracleDb(ClassPathXmlApplicationContext cxt) {
		DataSource ds = (DataSource)cxt.getBean("dataSource_default");
		try {
			Connection conn = ds.getConnection();
			if(conn!=null)
				System.out.println("dataSouce connection is created.");
			else
				System.out.println("dataSouce connection is error.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
