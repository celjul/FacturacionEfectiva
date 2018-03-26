package com.entich.ezfact.services;

import java.util.Map;

import com.entich.ezfact.model.Adjunto;

public interface EmailService {
	@SuppressWarnings("rawtypes")
	void sendEmail(final String email, final Map model,
			final String subject, final String template);
	
	@SuppressWarnings("rawtypes")
	void sendEmail(final String email, final Map model, final String subject,
			final String template, Adjunto... attachments);

	@SuppressWarnings("rawtypes")
	void sendEmail(final String[] email, final Map model,
				   final String subject, final String template);

	@SuppressWarnings("rawtypes")
	void sendEmail(final String[] email, final Map model, final String subject,
				   final String template, Adjunto... attachments);

}
