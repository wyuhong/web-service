package com.test;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;


public class Client {
    private static final QName SERVICE_NAME = new QName(
    					"http://www.wuxianedu.com/ws/weather", "WeatherService");
    private static final QName PORT_NAME = new QName(
    					"http://www.wuxianedu.com/ws/weather", "WeatherServicePort");

    private static final String WSDL_LOCATION = "http://localhost:8084/WeatherService?wsdl";

    public static void main(String args[]) throws Exception {
        URL wsdlURL = new URL(WSDL_LOCATION);
        Service service = Service.create(wsdlURL, SERVICE_NAME);
        WeatherService ws = service.getPort(PORT_NAME, WeatherService.class);  
        
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = format.parse("2016-12-20");
		Date date2 = format.parse("2016-12-21");
		Date date3 = format.parse("2016-12-22");
		List<Date> dates = new ArrayList<Date>();
		dates.add(date1);
		dates.add(date2);
		dates.add(date3);
		List<TemperatureInfo> obj = ws.getWeathers("杭州", dates);
		for(TemperatureInfo info :obj){
			System.out.print(info.getCity() + "\t"
					+ format.format(info.getDate()) + "\t"
					+ info.getMax() + "\t"
					+ info.getMin()+ "\t"
					+ info.getAverage()+ "\t"
					+ info.getDesc());
			System.out.print("\n");
		}
    }
}
