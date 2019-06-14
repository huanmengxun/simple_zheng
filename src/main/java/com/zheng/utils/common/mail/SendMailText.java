package com.zheng.utils.common.mail;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.zheng.utils.common.MyComUtils;
import com.zheng.utils.handle.MyException;

//https://www.cnblogs.com/ysocean/p/7666061.html
public class SendMailText {
//	发件地址
	static String emitAddress = "17627796854@163.com";
	static String emailAccount="17627796854";
	static String emailPassword="**";

	/**
	 * 获得创建一封邮件的实例对象
	 * 
	 * @param session
	 * @return
	 * @throws MessagingException
	 * @throws AddressException
	 */
	public static MimeMessage getMimeMessage(Session session, String acceptAddress) throws Exception {
		// 1.创建一封邮件的实例对象
		MimeMessage msg = new MimeMessage(session);
		// 2.设置发件人地址
		msg.setFrom(new InternetAddress(emitAddress));
		/**
		 * 3.设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行 MimeMessage.RecipientType.TO:发送
		 * MimeMessage.RecipientType.CC：抄送 MimeMessage.RecipientType.BCC：密送
		 */
		msg.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(acceptAddress));
		// 4.设置邮件主题
		msg.setSubject("邮件主题(包含图片和附件)", "UTF-8");
		// 下面是设置邮件正文
		// msg.setContent("简单的纯文本邮件！", "text/html;charset=UTF-8");
		
		// 5. 创建图片"节点"
		MimeBodyPart image = new MimeBodyPart();
		// 读取本地文件
		DataHandler dh = new DataHandler(new FileDataSource("src\\mailTestPic.png"));
		// 将图片数据添加到"节点"
		image.setDataHandler(dh);
		// 为"节点"设置一个唯一编号（在文本"节点"将引用该ID）
		image.setContentID("mailTestPic");

		// 6. 创建文本"节点"
		MimeBodyPart text = new MimeBodyPart();
		// 这里添加图片的方式是将整个图片包含到邮件内容中, 实际上也可以以 http 链接的形式添加网络图片
		
		text.setContent(
				"这是一张图片<br/><a href='http://www.cnblogs.com/ysocean/p/7666061.html'><img src='cid:mailTestPic'/></a>",
				"text/html;charset=UTF-8");
		// 7. （文本+图片）设置 文本 和 图片"节点"的关系（将 文本 和 图片"节点"合成一个混合"节点"）
		MimeMultipart mm_text_image = new MimeMultipart();
		mm_text_image.addBodyPart(text);
		mm_text_image.addBodyPart(image);
		mm_text_image.setSubType("related"); // 关联关系

		// 8. 将 文本+图片 的混合"节点"封装成一个普通"节点"
		// 最终添加到邮件的 Content 是由多个 BodyPart 组成的 Multipart, 所以我们需要的是 BodyPart,
		// 上面的 mailTestPic 并非 BodyPart, 所有要把 mm_text_image 封装成一个 BodyPart
		MimeBodyPart text_image = new MimeBodyPart();
		text_image.setContent(mm_text_image);

		// 9. 创建附件"节点"
		MimeBodyPart attachment = new MimeBodyPart();
		// 读取本地文件
		DataHandler dh2 = new DataHandler(new FileDataSource("src\\mailTestDoc.docx"));
		// 将附件数据添加到"节点"
		attachment.setDataHandler(dh2);
		// 设置附件的文件名（需要编码）
		attachment.setFileName(MimeUtility.encodeText(dh2.getName()));

		// 10. 设置（文本+图片）和 附件 的关系（合成一个大的混合"节点" / Multipart ）
		MimeMultipart mm = new MimeMultipart();
		mm.addBodyPart(text_image);
		mm.addBodyPart(attachment); // 如果有多个附件，可以创建多个多次添加
		mm.setSubType("mixed"); // 混合关系

		// 11. 设置整个邮件的关系（将最终的混合"节点"作为邮件的内容添加到邮件对象）
		msg.setContent(mm);
		// 设置邮件的发送时间,默认立即发送
		msg.setSentDate(new Date());

		return msg;
	}

	public static void main(String[] args) throws Exception {
		sendMailText( "ceshi", "测试","aa@163.com","aa@qq.com");
	}
	/**
	 * 功能描述：
	 *
	 * @param emitAddress   发件人地址
	 * @param acceptAddress 收件人地址
	 * @throws Exception
	 */
	public static void sendMailText( String subject, String content,String... acceptAddress) throws Exception {
		if(MyComUtils.isEmpty(acceptAddress)) {
			throw new MyException("未填写发送人地址",true);
		}
		// 2、创建定义整个应用程序所需的环境信息的 Session 对象
		Session session = Session.getInstance(smtp163Prop());
		// 设置调试信息在控制台打印出来
//		session.setDebug(true);
		MimeMessage msg = loadEmailMsg(session, subject, content, acceptAddress);
		
		// 4、根据session对象获取邮件传输对象Transport
		Transport transport = session.getTransport();
		// 设置发件人的账户名和密码
		transport.connect(emailAccount, emailPassword);
		// 发送邮件，并发送到所有收件人地址，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
		transport.sendMessage(msg, msg.getAllRecipients());
		// 如果只想发送给指定的人，可以如下写法
		// transport.sendMessage(msg, new Address[]{new InternetAddress("xxx@qq.com")});
		// 5、关闭邮件连接
		transport.close();
	}

	protected static MimeMessage loadEmailMsg(Session session, String subject, String content, String... acceptAddress) {
		// 3、创建邮件的实例对象
		MimeMessage msg = new MimeMessage(session);
		// 设置发件人地址
		try {
			msg.setFrom(new InternetAddress(emitAddress));
//			 设置收件人地址（可以增加多个收件人、抄送、密送），
//			即下面这一行代码书写多行 MimeMessage.RecipientType.TO:发送
//			  MimeMessage.RecipientType.CC：抄送 
//			MimeMessage.RecipientType.BCC：密送
//			msg.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(acceptAddress));
			StringBuilder builder=new StringBuilder();
			for(String address:acceptAddress) {
				builder.append(address).append(",");
			}
			String address=builder.toString();
			address=address.length()>0?address.substring(0, address.length()-1):address;
			msg.setRecipients(MimeMessage.RecipientType.TO, address);
			// 设置邮件主题
			msg.setSubject(subject, "UTF-8");
			// 设置邮件正文
			msg.setContent(content, "text/html;charset=UTF-8");

			// 设置邮件的发送时间,默认立即发送
			msg.setSentDate(new Date());
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return msg;
	}

	/**
	 * 功能描述： 163 smtp邮件协议
	 * 
	 * @return
	 */
	protected static Properties smtp163Prop() {
		// 连接邮件服务器的参数配置
		Properties props = new Properties();
		// 设置用户的认证方式
		props.setProperty("mail.smtp.auth", "true");
		// 设置传输协议
		props.setProperty("mail.transport.protocol", "smtp");
		// 设置发件人的SMTP服务器地址
		props.setProperty("mail.smtp.host", "smtp.163.com");
		return props;
	}

}