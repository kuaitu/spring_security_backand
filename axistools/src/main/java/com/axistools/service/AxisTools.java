package com.axistools.service;

import cn.hutool.core.util.XmlUtil;
import com.axistools.config.AxisConfig;
import com.axistools.utils.LogUtil;
import com.axistools.utils.LogicException;
import com.axistools.utils.SM4Utils;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.utils.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.encoding.XMLType;
import java.net.URL;
import java.util.*;

public class AxisTools {
	
	Integer countGetGuid = null;
	Integer countGetkey = null;
	Integer countGetData = null;

	public AxisTools(){
		countGetGuid = 0;
		countGetkey = 0;
		countGetData = 0;
	}
	
	private String getAuthorizationGuid(){
		if(AxisConfig.guid != null 
				&& AxisConfig.interval != null 
				&& AxisConfig.nextGuidDate != null 
				&& (new Date()).compareTo(AxisConfig.nextGuidDate) < 0){
			setSm4Key(AxisConfig.guid);
			setGuidTime();
			return AxisConfig.guid;
		}
		String guid  = getGuid();
		return guid;
	}
	
	private String getGuid(){
        Service service = new Service();
        try {
        	
            Call call = (Call) service.createCall();
            call.setSOAPActionURI("http://tempuri.org/Authorization");
            call.setTimeout(new Integer(60000));
            call.setTargetEndpointAddress(new URL(AxisConfig.webServiceUrl));
            //你需要远程调用的方法
            call.setOperationName(new QName(AxisConfig.targetNamespace,"Authorization"));
            //方法参数，如果没有参数请无视
            call.addParameter(new QName(AxisConfig.targetNamespace,"account"), XMLType.XSD_STRING, ParameterMode.IN);
            call.addParameter(new QName(AxisConfig.targetNamespace,"passWord"), XMLType.XSD_STRING, ParameterMode.IN);
            //设置返回类型，对方接口返回的json，我就用string接收了,自定义类型另贴一个代码
            call.setReturnType(XMLType.XSD_STRING);
            //调用方法并传递参数，没有参数的话： call.invoke(new Object[] { null});
            String result = (String) call.invoke(new Object[]{AxisConfig.account,AxisConfig.passWord});
            
            if(result.substring(1, 6).equals("Error")){
            	handlingErrors(result);
            	throw new LogicException("系统异常，请刷新后重试！");
            }else{
                LogUtil.info("获取guid：" + result);
                
                //设置guid和失效时间
                AxisConfig.guid = result;
                setGuidTime();
                
                //根据新的guid设置密钥
                setSm4Key(result);
                
                return result;
            }
            
        } catch (Exception e) {
        	LogUtil.error("获取guid出错：" + e.getMessage());
        	throw new LogicException("系统异常，请刷新后重试！");
        }
	}
	
	private void setGuidTime(){
		if(AxisConfig.interval != null){
			long currentTime = System.currentTimeMillis() + AxisConfig.interval;
	        Date date = new Date(currentTime);
	        LogUtil.info("获取guid过期时间：" + date);
	        AxisConfig.nextGuidDate = date;
		}
	}
	
	private void setSm4Key(String guid){
		if(!StringUtils.isEmpty(SM4Utils.secretKey) 
				&& AxisConfig.expirationDate != null 
				&& (new Date()).compareTo(AxisConfig.expirationDate) <= 0){
    		return ;
    	}
		setKey(guid);
	}
	
	private  void setKey(String guid){
		if (StringUtils.isEmpty(guid)){
			return;
		}

        Service service = new Service();
        try {
            Call call = (Call) service.createCall();
            call.setSOAPActionURI("http://tempuri.org/getSm4Key");
            call.setTimeout(new Integer(60000));
            call.setTargetEndpointAddress(new URL(AxisConfig.webServiceUrl));
            //你需要远程调用的方法
            call.setOperationName(new QName(AxisConfig.targetNamespace,"getSm4Key"));
            //方法参数，如果没有参数请无视
            call.addParameter(new QName(AxisConfig.targetNamespace,"guid"), XMLType.XSD_STRING, ParameterMode.IN);
            //设置返回类型，对方接口返回的json，我就用string接收了,自定义类型另贴一个代码
            call.setReturnType(XMLType.XSD_STRING);
            //调用方法并传递参数，没有参数的话： call.invoke(new Object[] { null});
            String result = (String) call.invoke(new Object[]{guid});
            
            if(result.substring(1, 6).equals("Error")){
            	handlingErrors(result);
            	throw new LogicException("系统异常，请刷新后重试！");
            }else{
            	LogUtil.info("获取secretKey：" + result);
                
                //设置密钥和过期时间
                SM4Utils.secretKey = result;
                SM4Utils.iv = result;
                AxisConfig.expirationDate = getDayEnd();
            }
        } catch (Exception e) {
        	LogUtil.error("获取secretKey出错：" + e.getMessage());
        	throw new LogicException("获取secretKey出错，请刷新后重试！");
        }
	}
	
	public String queryDecHeadDetailData(String type, String value){
		//guid获取
		String guid = getAuthorizationGuid();
		//guid = "b7c4a61c-e3a6-44f1-b7fd-ad02b43be9f8";
		
        Service service = new Service();
        try {
            Call call = (Call) service.createCall();
            call.setSOAPActionURI("http://tempuri.org/queryDecData");
            call.setTimeout(new Integer(60000));
            call.setTargetEndpointAddress(new URL(AxisConfig.webServiceUrl));
            //你需要远程调用的方法
            call.setOperationName(new QName(AxisConfig.targetNamespace,"queryDecData"));
            //方法参数，如果没有参数请无视
            call.addParameter(new QName(AxisConfig.targetNamespace,"requestInfo"), XMLType.XSD_STRING, ParameterMode.IN);
            //设置返回类型，对方接口返回的json，我就用string接收了,自定义类型另贴一个代码
            call.setReturnType(XMLType.XSD_STRING);
            
            
            //设置查询的参数
            Map<String, Object> requestMap = new HashMap<>();
            requestMap.put("guid", guid);
            requestMap.put("type", type);
            requestMap.put("value", value);
            String plainText = XmlUtil.mapToXmlStr(requestMap, "Request");
            LogUtil.info("查询请求的报文requestInfo：" + plainText);
            
            //请求加密
            SM4Utils sm4 = new SM4Utils();
            String cipherText = sm4.encryptData_ECB(plainText);
            LogUtil.info("查询请求的报文(加密)requestInfo：" + cipherText);
            //LogUtil.info("请求密文:" + cipherText);
            
            //调用方法并传递参数，没有参数的话： call.invoke(new Object[] { null});
            String resultSm4 = (String) call.invoke(new Object[]{cipherText});
            LogUtil.info("返回结果的报文(加密)：" + resultSm4);
            if(resultSm4.substring(1, 6).equals("Error")){
            	handlingErrors(resultSm4);
            	if(countGetData < 3){
					countGetData ++;
            		return queryDecHeadDetailData(type,value);
            	}else{
            		throw new LogicException("报关单数据获取异常，请刷新后重试！");
            	}
            }else{
            	//返回解密
                String result = sm4.decryptData_ECB(resultSm4);
                LogUtil.info("返回结果的报文(解密)xml：" + result);
                return result;
            }
        } catch (Exception e) {
        	LogUtil.error("获取报关单数据出错：" + e.getMessage());
        	throw new LogicException("系统异常，请刷新后重试！");
        }
	}
	
	private void handlingErrors(String aString){
		//处理错误报文
		LogUtil.info("返回的错误xml：" + aString);
		Document document = null;
		try {
			document = DocumentHelper.parseText(aString);
		} catch (DocumentException e) {
			LogUtil.error("获取xml出错：" + e.getMessage());
			throw new LogicException("解析xml异常，请刷新后重试！");
		}
		Element root = document.getRootElement();
		String errorCode = root.attributeValue("code");
		String errorMsg = root.getStringValue();
		LogUtil.info("返回的错误码：" + errorCode);
		if(!StringUtils.isEmpty(errorCode)){
			switch (errorCode) {
			case "00":
				LogUtil.error(errorMsg + " : " + AxisConfig.guid);
				if (countGetGuid < 6) {
					countGetGuid ++;
					getGuid();
				}
				break;
			case "05":
			case "104":
			case "103":
			case "01":
			case "400":
				LogUtil.error(errorMsg);
				break;
			case "101":
				LogUtil.error(errorMsg);
				String guid = null;
				if (countGetGuid < 6) {
					countGetGuid ++;
					guid = getGuid();
				}
				setKey(guid);
				break;
			case "102":
				LogUtil.error(errorMsg + " : "  + AxisConfig.guid);
				if (countGetGuid < 6) {
					countGetGuid ++;
					getGuid();
				}
				break;

			default:
				LogUtil.error("未知错误，错误信息：" + errorMsg);
				break;
			}
		}
	}
	
	private Date getDayEnd() {
		//第二天零点换密码，重新获取
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}
}
