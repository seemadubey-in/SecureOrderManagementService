package com.soms.service.businessService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;
import com.soms.service.entity.OrderLoad;


public class CSVHelper {
  public static String TYPE = "text/csv";
  public static String FILETYPE = "application/vnd.ms-excel";
  static String[] HEADERs = { "Id", "Status", "TotalPrice", "Date" };

  public static boolean hasCSVFormat(MultipartFile file) {
System.out.println("conect type is: " + file.getContentType());
    if (!TYPE.equals(file.getContentType()) && !FILETYPE.equals(file.getContentType())) {
      return false;
    }

    return true;
  }
  
  public static List<OrderLoad> csVtoOrders(InputStream is) throws NumberFormatException, ParseException{
	  try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		        CSVParser csvParser = new CSVParser(fileReader,
		            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

		     List<OrderLoad> orders = new ArrayList<OrderLoad>();

		      Iterable<CSVRecord> csvRecords = csvParser.getRecords();
 DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
		      for (CSVRecord csvRecord : csvRecords) {
	//	        @SuppressWarnings("deprecation")
		    	  OrderLoad order = new OrderLoad(
		              Long.parseLong(csvRecord.get("Id")),
		              csvRecord.get("Status"),
		              new BigDecimal((csvRecord.get("TotalPrice"))),
		              formatter.parse(csvRecord.get("Date"))
		              //new SimpleDateFormat(csvRecord.get("Date"))
		            );

		        orders.add(order);
		      }

		      return orders;
		    } catch (IOException e) {
		      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		    }
		  }
  }