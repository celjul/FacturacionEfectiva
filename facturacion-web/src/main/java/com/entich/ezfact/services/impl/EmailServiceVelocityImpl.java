package com.entich.ezfact.services.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sendinblue.Sendinblue;
import org.apache.commons.codec.binary.Base64;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.entich.ezfact.model.Adjunto;
import com.entich.ezfact.services.EmailService;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class EmailServiceVelocityImpl implements EmailService {

	@Autowired
	JavaMailSender javaMailSender;
	
	@Autowired
	VelocityEngine velocityEngine;

	@Autowired
	private ConfigurationServiceImp configurationServiceImp;

    private static final Logger LOG = LoggerFactory
            .getLogger(EmailServiceVelocityImpl.class);
	
	@SuppressWarnings("rawtypes")
	@Override
	public void sendEmail(final String email, final Map model, final String subject,
			final String template) {
		Sendinblue http = new Sendinblue(configurationServiceImp.getSendingblueHost(),configurationServiceImp.getSendingblueKey());
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@SuppressWarnings("unchecked")
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				/*message.setTo(email);*/
				message.setBcc(email);
				message.setSubject(subject);
				//message.setFrom("hola@facturacionefectiva.com");
				message.setFrom(configurationServiceImp.getEmailDirOrigenEmail());
				String text = VelocityEngineUtils
						.mergeTemplateIntoString(velocityEngine, 
								template, "UTF8", model);
				message.setText(text, true);
			}
		};
		this.javaMailSender.send(preparator);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void sendEmail(final String[] email, final Map model, final String subject,
						  final String template) {
		Sendinblue http = new Sendinblue(configurationServiceImp.getSendingblueHost(),configurationServiceImp.getSendingblueKey());
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@SuppressWarnings("unchecked")
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				/*message.setTo(email);*/
				message.setBcc(email);
				message.setSubject(subject);
				//message.setFrom("hola@facturacionefectiva.com");
				message.setFrom(configurationServiceImp.getEmailDirOrigenEmail());
				String text = VelocityEngineUtils
						.mergeTemplateIntoString(velocityEngine,
								template, "UTF8", model);
				message.setText(text, true);
			}
		};
		this.javaMailSender.send(preparator);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void sendEmail(final String email, final Map model, final String subject,
			final String template, final Adjunto... attachments) {
		Sendinblue http = new Sendinblue(configurationServiceImp.getSendingblueHost(),configurationServiceImp.getSendingblueKey());
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@SuppressWarnings("unchecked")
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
				/*message.setTo(email);*/
				message.setBcc(email);
				message.setSubject(subject);
				//message.setFrom("hola@facturacionefectiva.com");
				message.setFrom(configurationServiceImp.getEmailDirOrigenEmail());
				String text = VelocityEngineUtils
						.mergeTemplateIntoString(velocityEngine, 
								template, "UTF-8", model);
				message.setText(text, true);
				
				for (Adjunto attach : attachments) {
					message.addAttachment(attach.getNombre(), attach.getSource());
				}
			}
		};
		this.javaMailSender.send(preparator);
	}

    @SuppressWarnings("rawtypes")
    @Override
    public void sendEmail(final String email[], final Map model, final String subject,
                          final String template, final Adjunto... attachments) {
        Sendinblue http = new Sendinblue(configurationServiceImp.getSendingblueHost(),configurationServiceImp.getSendingblueKey());
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @SuppressWarnings("unchecked")
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
				//message.setTo(email);
                message.setBcc(email);
                message.setSubject(subject);
                message.setFrom(configurationServiceImp.getEmailDirOrigenEmail());
                String text = VelocityEngineUtils
                        .mergeTemplateIntoString(velocityEngine,
                                template, "UTF-8", model);
                message.setText(text, true);

                for (Adjunto attach : attachments) {
                    message.addAttachment(attach.getNombre(), attach.getSource());
                }
            }
        };
        this.javaMailSender.send(preparator);
    }


}