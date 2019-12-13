package com.axistools.service;

import com.axistools.utils.LogUtil;
import org.apache.axis.utils.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class AxisTest {

	public static void main(String[] args) {
//		//设置查询的参数
//        Map<String, Object> requestMap = new HashMap<>();
//        requestMap.put("guid", "123456");
//        requestMap.put("type", "entryid");
//        requestMap.put("value", "1234567890");
//        String plainText = XmlUtil.mapToXmlStr(requestMap, "Request");
//        System.out.println(plainText);
//        
//        //请求加密
//        SM4Utils sm4 = new SM4Utils();
//        String cipherText = sm4.encryptData_ECB(plainText);
//        System.out.println(cipherText);
		
		/*AxisConfig.targetNamespace = "http://tempuri.org/";
		AxisConfig.webServiceUrl = "http://220.160.52.51:85/query_dec/Dec_query.asmx?wsdl";
		AxisConfig.account = "dzka_dec";
		AxisConfig.passWord = "dzka@1234";
		String ret = AxisTools.queryDecHeadDetailData("ENTRYID","350120170017703020");
		System.out.println("========" + ret);
		System.out.println(AxisTools.getDayEnd());*/
		
		String aString = "<Error code=\"00\">guid无效</Error>";
		System.out.println(aString.substring(1, 6));
		if(aString.substring(1, 6).equals("Error")){
        	System.out.println("出错：" + aString);
        }else{
        	System.out.println("正确：" + aString);
        }
		Document document = null;
		try {
			document = DocumentHelper.parseText(aString);
			//System.out.println("document"+document);
			} catch (DocumentException e) {
				LogUtil.error("获取xml出错：" + e.getMessage());
			}
		Element root = document.getRootElement();
		String errorCode = root.attributeValue("code");
		String errorMsg = root.getStringValue();
		System.out.println(errorCode);
		System.out.println(errorMsg);
		if(!StringUtils.isEmpty(errorCode)){
			switch (errorCode) {
			case "00":
				;
				break;

			default:
				break;
			}
		}
	}

}
