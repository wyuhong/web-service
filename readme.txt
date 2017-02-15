用户名：
密码：         checkbox 发送密码到手机

登录
 

A:发布出来一个短信的webservice(别人的厂家)

B:调用这个webservice(我们的单位)
	别人提供给我们的内容：
		1、http://xxxx:xxx/xxxxx?wsdl  http://localhost:8084/WeatherService?wsdl
		2、调用webservice的案例  JaxWS_Client
		
	如何调用：
		1、解析wsdl---->响应的接口和实体类
		2、完成自己端的代码，然后调用对方给定的发短信的方法


WSDL中重要的部分：
1、
<service name="WeatherService">
<port name="WeatherServicePort" binding="tns:WeatherServicePortBinding">
<soap:address location="http://localhost:8084/WeatherService"/>
</port>
</service>

2、
<definitions xxxx targetNamespace="http://www.wuxianedu.com/ws/weather" name="WeatherService">

3、
<portType name="WeatherServiceImpl">
<operation name="getWeathers">
<input wsam:Action="http://www.wuxianedu.com/ws/weather/WeatherServiceImpl/getWeathersRequest" message="tns:getWeathers"/>
<output wsam:Action="http://www.wuxianedu.com/ws/weather/WeatherServiceImpl/getWeathersResponse" message="tns:getWeathersResponse"/>
</operation>
</portType>

webservice发布者的开发步骤：

1、根据需求编制实体类（类的注解@XmlRootElement(name="xxxx")）
	a,在自己编制的实体类上添加注解：@XmlRootElement(name = "实体类名")
2、编制接口(用来发布出去给别人调用的接口(指定的方法)，类的注解@WebService(serviceName,portName),发布出去的方法上注解@WebMethod)
	a,编制一个新的接口(没问题)
	b,复制样例接口上的注解@WebService(serviceName="接口名",portName="接口名+port",targetNamespace="你们公司的域名+/ws/接口名")
	c,编制该接口中的方法，方法上也要复制样例中的注解@WebMethod

3、编制接口的实现类(类的注解参见编制接口上的注解(与接口的注解内容要保持一致))
	a,直接把自己写的接口上的注解复制粘贴过来
	b,实现的方法上添加注解@WebMethod(operationName="getWeathers")
	
4、通过EndPoint.publish(发布的地址,实现类的实例);

5、其他人调用的公共地址：http://192.168.2.106:8084/WeatherService?wsdl

6、作为web服务的发布者，要把如何调用的步骤的代码案例做好，一起发给调用者（调用的demo---->调用者）




webservice调用者的开发步骤

1、拿到发布者给的http://xxxx:xxx/xxxxx?wsdl（询问这个URL的部署的网络环境）

2、http://xxxx:xxx/xxxxx?wsdl拿到浏览器地址栏(一个公网的服务，还有一个是内网的服务，咨询信息中心)

3、解析wsdl:jdk wsimport===>wsimport -d /Users/mac/Downloads -keep -verbose http://localhost:8084/WeatherService?wsdl

wsimport -d d: -keep -verbose http://localhost:8084/WeatherService?wsdl

4、自动生成的只需要拿两样东西：实体类(JDK1.7生成Date类型要多加注意，修改成java.util.Date)，接口(自动生成的内容后面加了Impl，实际使用时需要删除掉Impl，注解上的Impl可以不用删除)

5、写一个场景类Client,调用相应的代码(java.net.URL---javax.xml.Service---发布出来的接口类---调用方法)
	a，成员变量直接复制，相对应的内容自己修改(这里所有常量需要在发布者发布的WSDL中查看)
		private static final QName SERVICE_NAME = new QName("http://www.wuxianedu.com/ws/weather", "WeatherService");
        private static final QName PORT_NAME = new QName("http://www.wuxianedu.com/ws/weather", "WeatherServicePort");
    	private static final String WSDL_LOCATION = "http://localhost:8084/WeatherService?wsdl";
    b, main方法中
    	URL wsdlURL = new URL(WSDL_LOCATION);
        Service service = Service.create(wsdlURL, SERVICE_NAME);
        接口 ws = service.getPort(PORT_NAME, 接口.class);
        ws.getWeather("hangzhou",dates);





A：人事管理系统  java  WEBSERVICE(发布一个服务出来)

B：财务管理系统  .net  (调用A系统发布的这个服务)

C：客户关系管理系统  Android