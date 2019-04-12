package cn.kungreat.mail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailApplicationTests {

	@Value("${spring.mail.username}")
	private String mailAddress;

	@Autowired
	private JavaMailSender javaMailSender;
	@Test
	public void simple() {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(mailAddress);
		simpleMailMessage.setTo("qepau886@163.com");
		simpleMailMessage.setSubject("test");
		simpleMailMessage.setText("这是一个测试邮件");
		simpleMailMessage.setSentDate(new Date());
		javaMailSender.send(simpleMailMessage);
	}

	@Test
	public void htmlText(){
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
		try {
			mimeMessageHelper.setFrom(mailAddress);
			mimeMessageHelper.setTo("qepau886@163.com");
			mimeMessageHelper.setSubject("html");
			mimeMessageHelper.setText("<h1>HTML...</h1>",true);
			javaMailSender.send(mimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@Test  //带附件
	public void attach(){
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
			mimeMessageHelper.setFrom(mailAddress);
			mimeMessageHelper.setTo("qepau886@163.com");
			mimeMessageHelper.setSubject("带附件");
			mimeMessageHelper.setText("<h1>HTML...</h1>",true);
			mimeMessageHelper.addAttachment("附件",new File("D:/upload/a.PNG"));
			javaMailSender.send(mimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@Test  //内联附件
	public void inLineAttach(){
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
			mimeMessageHelper.setFrom(mailAddress);
			mimeMessageHelper.setTo("qepau886@163.com");
			mimeMessageHelper.setSubject("内联附件");
			mimeMessageHelper.setText("<h1>HTML...<img src=\"cid:img\" /></h1>",true);
			// cid:name   要和下边contentid 一样
			mimeMessageHelper.addInline("img",new File("D:/upload/a.PNG"));
			javaMailSender.send(mimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}