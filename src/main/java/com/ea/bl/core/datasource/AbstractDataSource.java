package com.ea.bl.core.datasource;

import java.util.Properties;

import javax.sql.DataSource;

public abstract class AbstractDataSource implements DataSource {
	  protected abstract long getMaxWait();

	  protected abstract int getMaxActive();

	  protected abstract void setProperties(Properties paramProps);

	  protected abstract void close();
}
