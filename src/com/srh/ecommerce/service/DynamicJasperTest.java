package com.srh.ecommerce.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.srh.ecommerce.model.Orders;
import com.srh.ecommerce.model.OrdersMapper;

public class DynamicJasperTest {

	public static void main(String[] args) {

		ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");

		NamedParameterJdbcTemplate namedJdbcTemplate = (NamedParameterJdbcTemplate) appContext.getBean("namedParameterJdbcTemplate");

		String sql = "SELECT * FROM ORDERS WHERE ORDER_NUMBER = :orderNumber";

		Map<String, String> queryMap = new HashMap<String, String>();

		queryMap.put("orderNumber", "OR 14");

		List<Orders> orders = namedJdbcTemplate.query(sql, queryMap, new OrdersMapper());
		
		//Order Number,Product Name,Order date,Delivery date,Price,Order Status

		JasperReportBuilder report = DynamicReports.report();//a new report
		report
		.columns(
				Columns.column("Order Number", "orderNumber", DataTypes.stringType()),
				Columns.column("Product Name", "productName", DataTypes.stringType()),
				Columns.column("Order date", "orderDate", DataTypes.stringType()),
				Columns.column("Delivery date", "deliveryDate", DataTypes.stringType()),
				Columns.column("Price", "price", DataTypes.stringType()),
				Columns.column("Order Status", "orderStatus", DataTypes.stringType()))
				
				
				.title(//title of the report
						Components.text("SimpleReportExample")
						.setHorizontalAlignment(HorizontalAlignment.CENTER))
						.pageFooter(Components.pageXofY())//show page number on the page footer
						.setDataSource(orders);

		try {
			//show the report
			report.show();

			//export the report to a pdf file
			report.toPdf(new FileOutputStream("c:/report.pdf"));
		} catch (DRException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}


	}

}
