package com.srh.ecommerce.service;

import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import com.srh.ecommerce.dao.ECommerceDaoImpl;
import com.srh.ecommerce.model.OrderSpec;
import com.srh.ecommerce.model.Orders;
import com.srh.ecommerce.model.Product;

public class CreatePDFServiceImpl {
	
	public String generateAcknowledgement( List<Orders> orders, OrderSpec spec ) throws IOException, COSVisitorException{
		
		
		PDDocument doc = new PDDocument();
        PDPage page = new PDPage();

        doc.addPage(page);
        
        ECommerceDaoImpl daoImpl = new ECommerceDaoImpl();

        PDPageContentStream content = new PDPageContentStream(doc, page);        

		content.beginText();
        content.setFont(PDType1Font.HELVETICA, 22);
        content.moveTextPositionByAmount(250, 750);
        content.drawString("Confirmation");
        content.endText();
        
        content.beginText();
        content.setFont(PDType1Font.HELVETICA, 16);
        content.moveTextPositionByAmount(60, 700);
        content.drawString("Order number : " + orders.get(0).getOrderNumber() );
        content.endText();
        
        content.beginText();
        content.setFont(PDType1Font.HELVETICA, 16);
        content.moveTextPositionByAmount(60, 670);
        content.drawString("Order Date : " + orders.get(0).getOrderDate() );
        content.endText();
        
        content.beginText();
        content.setFont(PDType1Font.HELVETICA, 16);
        content.moveTextPositionByAmount(60, 640);
        content.drawString("Address : " + spec.getAddress() );
        content.endText();
        
        content.beginText();
        content.setFont(PDType1Font.HELVETICA, 16);
        content.moveTextPositionByAmount(60, 610);
        content.drawString("Payment type : " + spec.getPaymentType() );
        content.endText();
        
        
        /* Loop should start*/
        
        content.beginText();
        content.setFont(PDType1Font.HELVETICA, 12);
        content.moveTextPositionByAmount(20, 550);
        content.drawString("Model Number ");
        content.endText();
        
        content.beginText();
        content.setFont(PDType1Font.HELVETICA, 12);
        content.moveTextPositionByAmount(140, 550);
        content.drawString("Product ");
        content.endText();
        
        content.beginText();
        content.setFont(PDType1Font.HELVETICA, 12);
        content.moveTextPositionByAmount(240, 550);
        content.drawString("Price ");
        content.endText();       

        
        content.beginText();
        content.setFont(PDType1Font.HELVETICA, 12);
        content.moveTextPositionByAmount(320, 550);
        content.drawString(" No. of Items ");
        content.endText();
        
        content.beginText();
        content.setFont(PDType1Font.HELVETICA, 12);
        content.moveTextPositionByAmount(440, 550);
        content.drawString(" Delivery date ");
        content.endText();
        
		int y = 500;
		
        String fileName = "OrderConfirmation_" + orders.get(0).getOrderNumber() + ".pdf"; 
        
		
		for( Orders order : orders ){
			
			Product product = daoImpl.getProductbyId( order.getProductId() );
			
			content.beginText();
	        content.setFont(PDType1Font.HELVETICA, 10);
	        content.moveTextPositionByAmount(20, y);
	        content.drawString(product.getModelNumber());
	        content.endText();
	        
	        content.beginText();
	        content.setFont(PDType1Font.HELVETICA, 10);
	        content.moveTextPositionByAmount(140, y);
	        content.drawString(product.getProductName());
	        content.endText();
	        
	        
	        content.beginText();
	        content.setFont(PDType1Font.HELVETICA, 10);
	        content.moveTextPositionByAmount(240, y);
	        content.drawString(product.getPrice());
	        content.endText();
	        

	        content.beginText();
	        content.setFont(PDType1Font.HELVETICA, 10);
	        content.moveTextPositionByAmount(350, y);
	        content.drawString(String.valueOf(order.getNumberOfItems()));
	        content.endText();
	        
	        content.beginText();
	        content.setFont(PDType1Font.HELVETICA, 10);
	        content.moveTextPositionByAmount(420, y);
	        content.drawString(order.getDeliveryDate());
	        content.endText();
	        
	        y = y - 30;
			
			
		}   
        
        
        content.close();
        doc.save(fileName);
        doc.close();
        
        return System.getProperty("user.dir") + "\\" + fileName;
		
		
		
	}


	

}


