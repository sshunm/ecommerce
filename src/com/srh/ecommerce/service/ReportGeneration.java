package com.srh.ecommerce.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;

import com.srh.ecommerce.model.AdminSearch;
import com.srh.ecommerce.model.Orders;

public class ReportGeneration {

	private static final String COMMA = ",";

	private static final String NEW_LINE = "\n";

	private static final String FILE_TITLE = "Order report for the ";

	private static final String FILE_HEADER = "Order Number,Product Name,Order date,Delivery date,Price,Order Status";

	public String writeCsvFile(List<Object[]> orders, AdminSearch adminSearch, String userType) {

		String title = "";

		if( !adminSearch.getCompanyName().equalsIgnoreCase("") )
			title = "company " + adminSearch.getCompanyName();
		else if( !adminSearch.getUserName().equalsIgnoreCase("") )
			title = "customer " + adminSearch.getUserName();
		else if( userType.equalsIgnoreCase("A") )
			title = "year 2016";


		String fileName = "";


		FileWriter fileWriter = null;

		try {

			Date date = new Date();

			fileName = System.getProperty("user.home") +"/_OR_Orders_" + date.getTime() + ".csv";

			fileWriter = new FileWriter(fileName);

			fileWriter.append(FILE_TITLE.toString() + title);			

			fileWriter.append(NEW_LINE);		
			fileWriter.append(NEW_LINE);


			fileWriter.append(FILE_HEADER.toString());

			fileWriter.append(NEW_LINE);		
			fileWriter.append(NEW_LINE);
			fileWriter.append(NEW_LINE);

			for( Object[] obj : orders ){

				fileWriter.append(obj[0].toString());
				fileWriter.append(COMMA);
				fileWriter.append(obj[1].toString());
				fileWriter.append(COMMA);
				fileWriter.append(obj[2].toString());
				fileWriter.append(COMMA);
				fileWriter.append(obj[3].toString());
				fileWriter.append(COMMA);
				fileWriter.append(obj[4].toString());
				fileWriter.append(COMMA);
				fileWriter.append(obj[5].toString());
				fileWriter.append(NEW_LINE);

			}		

			System.out.println("CSV file was created successfully !!!");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}


		return fileName;
	}


	public String generateDynamicJasperReport(List<Orders> orders, AdminSearch adminSearch, String userType) throws DRException, FileNotFoundException{
		
		String title = FILE_TITLE;

		if( !adminSearch.getCompanyName().equalsIgnoreCase("") )
			title = title + "company " + adminSearch.getCompanyName();
		else if( !adminSearch.getUserName().equalsIgnoreCase("") )
			title = title + "customer " + adminSearch.getUserName();
		else if( userType.equalsIgnoreCase("A") )
			title = title + "year 2016";
		
		String fileName = System.getProperty("user.home") +"/_OR_Orders_" + (new Date()).getTime() + ".pdf";
		
		


		JasperReportBuilder report = DynamicReports.report();//a new report
		
		report
		.columns(
				Columns.column("Order Number", "orderNumber", DataTypes.stringType()),
				Columns.column("Product Name", "productName", DataTypes.stringType()),
				Columns.column("Order date", "orderDate", DataTypes.stringType()),
				Columns.column("Unit price", "price", DataTypes.stringType()),
				Columns.column("No. of Items", "numberOfItems", DataTypes.integerType()).setHorizontalAlignment(HorizontalAlignment.LEFT),
				Columns.column("Status", "orderStatus", DataTypes.stringType()),
				Columns.column("Delivery date", "deliveryDate", DataTypes.stringType()))
				
				.title(
						Components.text(title).setDimension(100, 50)
						.setHorizontalAlignment(HorizontalAlignment.CENTER))
						.pageFooter(Components.pageXofY())
						.setDataSource(orders);

		report.toPdf(new FileOutputStream(fileName));

		return fileName;
	}


}


