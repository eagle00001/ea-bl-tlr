package com.ea.bl.core.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.DataSourceFactory;

public class TomcatDataSource extends AbstractDataSource {

	private DataSource ds;
	
	@Override
	public Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

	@Override
	public Connection getConnection(String username, String password)
			throws SQLException {
		return ds.getConnection(username, password);
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return ds.getLogWriter();
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		this.ds.setLogWriter(out);
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		this.ds.setLoginTimeout(seconds);
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return this.ds.getLoginTimeout();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return this.ds.unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return this.ds.isWrapperFor(iface);
	}

	@Override
	protected long getMaxWait() {
		return this.ds.getPoolProperties().getMaxWait();
	}

	@Override
	protected int getMaxActive() {
		return this.ds.getPoolProperties().getMaxActive();
	}

	@Override
	protected void setProperties(Properties paramProps) {
		 if (this.ds == null) {
		      this.ds = new DataSource(DataSourceFactory.parsePoolProperties(paramProps));
		 } else {
			this.ds.close();
			this.ds.setDbProperties(paramProps);
			this.ds.setPoolProperties(DataSourceFactory.parsePoolProperties(paramProps));
		 }
	}

	@Override
	protected void close() {
		this.ds.close();
	}

}
