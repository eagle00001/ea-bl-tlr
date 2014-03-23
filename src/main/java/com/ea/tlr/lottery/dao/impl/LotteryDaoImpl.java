package com.ea.tlr.lottery.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.util.CollectionUtils;

import com.ea.bl.common.utils.DateUtils;
import com.ea.tlr.lottery.dao.LotteryDao;
import com.ea.tlr.lottery.vo.Lottery;
import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * @comments
 * @author eagle.daiq
 * @date 2013-12-1
 */
public class LotteryDaoImpl extends SqlMapClientTemplate implements LotteryDao {

	/* (non-Javadoc)
	 * @see com.ea.tlr.lottery.dao.LotteryDao#getSscLotteryByDate(java.util.Date, java.util.Date)
	 */
	@Override
	public List<Lottery> getSscLotteryByDate(Date startDate, Date endDate) {
		if(startDate==null || endDate==null)
			return null;
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("startDate", DateUtils.formatDateToDefaultString(startDate));
		params.put("endDate", DateUtils.formatDateToDefaultString(endDate));
		return this.queryForList("getSscLotteryByDate", params);
	}

	/* (non-Javadoc)
	 * @see com.ea.tlr.lottery.dao.LotteryDao#insertSscLottery(java.util.List)
	 */
	@Override
	public boolean insertSscLottery(final List<Lottery> sscLotteryList) {
		if(CollectionUtils.isEmpty(sscLotteryList))
			return false;
		int code = (Integer) this.execute(new SqlMapClientCallback() {
			
			@Override
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				executor.startBatch();
                for(Lottery lottery:sscLotteryList){
                	executor.insert("saveLottery", lottery);
                }
                return executor.executeBatch();
			}
		});
		return code>0?true:false;
	}

}
