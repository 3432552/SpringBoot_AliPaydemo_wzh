package com.example.demo.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.Properties;

/**
 * @ClassName: PropConfig
 * @Description: TODO(配置文件设置)
 * @author 君泓 junying.wjy
 * @date 2018年7月17日 下午4:23:20
 *
 */
public final class AlipayConfig {

	public static final String ALIPAY_DEMO = "alipay_demo";
    public static final String ALIPAY_DEMO_VERSION = "alipay_demo_JAVA_20180907104657";

	/**
	 * 配置文件加载
	 */
	private static Properties prop;

	/**
	 * 配置文件名称
	 */
	public static String CONFIG_FILE = "Alipay-Config.properties";

	/**
	 * 配置文件相对路径
	 */
	public static String ALIPAY_CONFIG_PATH = File.separator + "etc" + File.separator + CONFIG_FILE;

	/**
	 * 项目路径
	 */
	public static String PROJECT_PATH = "";

	private static Log logger = LogFactory.getLog(AlipayConfig.class);

	/**
	 * 初始化配置值
	 */
	public static void initPropertis() {
		prop = new Properties();
		try {
			synchronized (prop) {
				InputStream inputStream = AlipayConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE);
				prop.load(inputStream);
				inputStream.close();
			}
		} catch (IOException e) {
			logger.error("日志 =============》： 配置文件Alipay-Config.properties找不到");
			e.printStackTrace();
		}
	}

	/**
	 * 获取配置文件信息
	 * 
	 * @return
	 */
	public static Properties getProperties() {
		if (prop == null)
			initPropertis();
		return prop;
	}


	/**
	 * 配置信息写入配置文件
	 */
	public static void writeConfig() {
	    System.out.println("ALIPAY_CONFIG_PATH:"+ALIPAY_CONFIG_PATH);
		File file = new File(ALIPAY_CONFIG_PATH);
		if (file.exists()) {
			file.setReadable(true);
			file.setWritable(true);
		}
		String lineText = null;
		BufferedReader bufferedReader = null;
		BufferedWriter bw = null;
		StringBuffer stringBuffer = new StringBuffer();
		try {
			bufferedReader = new BufferedReader(new FileReader(ALIPAY_CONFIG_PATH));
			while ((lineText = bufferedReader.readLine()) != null) {

				if (lineText.startsWith("APP_ID")) {
					lineText = "APP_ID = " + prop.getProperty("APP_ID");
				} else if (lineText.startsWith("RSA2_PRIVATE_KEY")) {
					lineText = "RSA2_PRIVATE_KEY = " + prop.getProperty("RSA2_PRIVATE_KEY");
				} else if (lineText.startsWith("RSA2_PUBLIC_KEY")) {
					lineText = "RSA2_PUBLIC_KEY = " + prop.getProperty("RSA2_PUBLIC_KEY");
				} else if (lineText.startsWith("ALIPAY_RSA2_PUBLIC_KEY")) {
					lineText = "ALIPAY_RSA2_PUBLIC_KEY = " + prop.getProperty("ALIPAY_RSA2_PUBLIC_KEY");
				} else if (lineText.startsWith("NOTIFY_URL")) {
					lineText = "NOTIFY_URL = " + prop.getProperty("NOTIFY_URL");
				} else if (lineText.startsWith("RETURN_URL")) {
					lineText = "RETURN_URL = " + prop.getProperty("RETURN_URL");
				} else if (lineText.startsWith("SANDBOX_BUYER_EMAIL")) {
					lineText = "SANDBOX_BUYER_EMAIL = " + prop.getProperty("SANDBOX_BUYER_EMAIL");
				} else if (lineText.startsWith("SANBOX_BUYER_LOGON_PWD")) {
					lineText = "SANBOX_BUYER_LOGON_PWD = " + prop.getProperty("SANBOX_BUYER_LOGON_PWD");
				} else if (lineText.startsWith("SANBOX_BUYER_PAY_PWD")) {
					lineText = "SANBOX_BUYER_PAY_PWD = " + prop.getProperty("SANBOX_BUYER_PAY_PWD");
				} else if (lineText.startsWith("SANDBOX_SELLER_ID")) {
					lineText = "SANDBOX_SELLER_ID = " + prop.getProperty("SANDBOX_SELLER_ID");
				} else if (lineText.startsWith("SANDBOX_SELLER_EMAIL")) {
					lineText = "SANDBOX_SELLER_EMAIL = " + prop.getProperty("SANDBOX_SELLER_EMAIL");
				} else if (lineText.startsWith("SANDBOX_SELLER_LOGON_PWD")) {
					lineText = "SANDBOX_SELLER_LOGON_PWD = " + prop.getProperty("SANDBOX_SELLER_LOGON_PWD");
				} else if (lineText.startsWith("ALIPAY_GATEWAY_URL")) {
					lineText = "ALIPAY_GATEWAY_URL = " + prop.getProperty("ALIPAY_GATEWAY_URL");
				} else if (lineText.startsWith("CHARSET")) {
					lineText = "CHARSET = " + prop.getProperty("CHARSET");
				} else if (lineText.startsWith("FORMAT")) {
					lineText = "FORMAT = " + prop.getProperty("FORMAT");
				} else if (lineText.startsWith("SIGNTYPE")) {
					lineText = "SIGNTYPE = " + prop.getProperty("SIGNTYPE");
				}

				stringBuffer.append(lineText).append("\r\n");
			}
			bufferedReader.close();
			bw = new BufferedWriter(new FileWriter(ALIPAY_CONFIG_PATH));
			bw.write(stringBuffer.toString());
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @ClassName: Status
	 * @Description: TODO(上传公钥回执状态码)
	 * @author 君泓 junying.wjy
	 * @date 2018年7月23日 下午4:40:47
	 *
	 */
	public final static class Status {

		/**
		 * 上传成功
		 */
		public final static String UPLOAD_SUCCESS = "UPLOAD_SUCCESS";

		/**
		 * 上传失败
		 */
		public final static String UPLOAD_FAILED = "UPLOAD_FAILED";

		/**
		 * 同样的公钥
		 */
		public final static String NOT_COVER = "NOT_COVER";

	}
}