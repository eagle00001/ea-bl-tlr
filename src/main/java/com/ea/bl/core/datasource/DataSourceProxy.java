package com.ea.bl.core.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.crypto.SecretKey;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;

import com.ea.bl.common.utils.EncryptionUtils;
import com.ea.bl.common.utils.FileLoadUtils;

/**
 * 数据源代理
 * @author eagle.daiq
 *
 */
public class DataSourceProxy implements DataSource,InitializingBean,BeanNameAware{
	private String beanName;
	private AbstractDataSource dataSouce;
	private Lock lock = new ReentrantLock();
	public static final String DEFAULT_DES_DATASOURCE_KEY = "DESDataSourcecProxy";
	
	private static Logger log = LoggerFactory.getLogger(DataSourceProxy.class);
	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return this.dataSouce.unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return this.dataSouce.isWrapperFor(iface);
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return this.dataSouce.getLogWriter();
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		this.dataSouce.setLogWriter(out);
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		this.dataSouce.setLoginTimeout(seconds);
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return this.dataSouce.getLoginTimeout();
	}

	@Override
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		String dataFile = this.beanName.concat(".properties");
		Properties p = FileLoadUtils.loadFileWithProperties(dataFile);
		if(p==null)
			throw new RuntimeException("The DataSouce file can not be found.["+dataFile+"].");
		if(p.containsKey("password.encrypt")){
			//解密处理
			String encryptPwd = p.getProperty("password.encrypt");
			SecretKey dsKey = EncryptionUtils.createDESSecretKey(DEFAULT_DES_DATASOURCE_KEY);
			String descryptedPwd = EncryptionUtils.decryptByDES(dsKey, encryptPwd);
			p.remove("password.encrypt");
			p.put("password", descryptedPwd);
		}
		String dsClassname = p.getProperty("dataSourceClassName");
		lock.lock();
		try {
			if(this.dataSouce!=null){
				this.dataSouce.close();
			}
			this.dataSouce = (AbstractDataSource)super.getClass().getClassLoader().loadClass(dsClassname).newInstance();
			p.remove("dataSourceClassName");
			this.dataSouce.setProperties(p);
		} catch (Exception e) {
			log.error("init dataSource failed.",e);
			throw new RuntimeException("The value of dataSourceClassName property is wrong in ["+dataFile+"].");
		}finally{
			lock.unlock();
		}
		
	}

	@Override
	public Connection getConnection() throws SQLException {
		return this.dataSouce.getConnection();
	}

	@Override
	public Connection getConnection(String username, String password)
			throws SQLException {
		return this.dataSouce.getConnection(username, password);
	}
	
}
