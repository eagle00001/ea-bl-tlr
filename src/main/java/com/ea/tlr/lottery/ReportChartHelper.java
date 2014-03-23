package com.ea.tlr.lottery;

import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.ea.bl.common.utils.DateUtils;
import com.ea.tlr.lottery.dao.LotteryDao;
import com.ea.tlr.lottery.vo.Lottery;

/**
 * @comments
 * @author eagle.daiq
 * @date 2013-12-29
 */
public class ReportChartHelper {
	private final Logger logger = LoggerFactory.getLogger(ReportChartHelper.class);
	private LotteryDao lotteryDao;
	private static ReportChartHelper instance = new ReportChartHelper();
	
	public static ReportChartHelper getInstance(){
		return instance;
	}
	
	private ReportChartHelper (){
		init();
	}
	/**
	 * @return the lotteryDao
	 */
	public LotteryDao getLotteryDao() {
		return lotteryDao;
	}

	/**
	 * @param lotteryDao the lotteryDao to set
	 */
	public void setLotteryDao(LotteryDao lotteryDao) {
		this.lotteryDao = lotteryDao;
	}
	
	private void init(){
			//创建主题样式  
	       StandardChartTheme standardChartTheme=new StandardChartTheme("CN");  
	       //设置标题字体  
	       standardChartTheme.setExtraLargeFont(new Font("隶书",Font.BOLD,20));  
	       //设置图例的字体  
	       standardChartTheme.setRegularFont(new Font("宋书",Font.PLAIN,15));  
	       //设置轴向的字体  
	       standardChartTheme.setLargeFont(new Font("宋书",Font.PLAIN,15));  
	       //应用主题样式  
	       ChartFactory.setChartTheme(standardChartTheme); 
	}
	

	public String generateLineChartByPosition(String startDateStr,String endDateStr,int postion){
		Date startDate = DateUtils.toDefaultDate(startDateStr);
		Date endDate = DateUtils.toDefaultDate(endDateStr);
		List<Lottery> lotteryLst = this.lotteryDao.getSscLotteryByDate(startDate, endDate);
		if(!CollectionUtils.isEmpty(lotteryLst)){
			DefaultCategoryDataset defaultCatDataSet = new DefaultCategoryDataset();
			String positionSer = "NumPostionSer";
			
			for(Lottery lottery:lotteryLst){
				String dateNumberStr = DateUtils.formatDateToString(lottery.getDate(), DateUtils.NUMBER_DATE_FORMAT);
				String x = dateNumberStr+lottery.getSerialNumber();
				String lotteryNum = lottery.getLotteryNumber();
				int length = lotteryNum.length();
				Double y = Double.valueOf(lotteryNum.substring(length-postion,length-postion+1));
				System.out.println("serise number:"+lotteryNum);
				System.out.println("y:"+y);
				System.out.println("X:"+x);
				defaultCatDataSet.addValue(y, positionSer, x);
			}
			
			
			
		   JFreeChart chart = ChartFactory.createLineChart("彩票第"+postion+"位曲线图", "期号", "号码", defaultCatDataSet, PlotOrientation.VERTICAL, true, true, false);
		   CategoryPlot plot = chart.getCategoryPlot();  
		   CategoryAxis domainAxis = plot.getDomainAxis();

		   //CategoryLabelPositions.UP_45 正向旋转45度
		   //CategoryLabelPositions.UP_90 正向旋转90度
		   //CategoryLabelPositions.DOWN_45 反向旋转45度
		   //CategoryLabelPositions.DOWN_90 反向旋转90度

		   domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
		   domainAxis.setCategoryLabelPositionOffset(10);
		   plot.setDomainAxis(domainAxis); 
			   
			try {
//				String fileName = ServletUtilities.saveChartAsPNG(jfreechart, 500, 500, null,ServletActionContext.getRequest().getSession());
				File tempFile = File.createTempFile("SScLineChart", ".jpeg");

				ChartUtilities.saveChartAsJPEG(tempFile, chart, 2000, 500, null);
				return tempFile.getAbsolutePath();
			} catch (IOException e) {
				logger.error("create Chart line error:",e);
			}
		}
		
		return "";
	}
}
