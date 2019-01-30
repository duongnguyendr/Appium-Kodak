package com.cinatic.email;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailHelper {
	public static enum emailObject {
		Subject, From, To, Date, Text
	}

	private static Logger log = LoggerFactory.getLogger(EmailHelper.class);

	private static Email getEmailContent(String host, String email, String password, String folder, String from, String subject) {
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imaps");
		try {
			Session session = Session.getInstance(props, null);
			Store store = session.getStore();
			store.connect(host, email, password);
			Folder inbox = store.getFolder(folder);
			inbox.open(Folder.READ_WRITE);

			Message[] messages = inbox.search(new FlagTerm(new Flags(Flag.SEEN), false));
			System.out.println("messages.length---" + messages.length);

			for (int i = 0, n = messages.length; i < n; i++) {
				Message message = messages[i];
				message.setFlag(Flags.Flag.SEEN, true);
				Object obj = message.getContent();
				Multipart mp = (Multipart) obj;
				BodyPart bp = mp.getBodyPart(0);

				if (message.getFrom()[0].toString().equals(from) && message.getSubject().equals(subject)) {
					log.info("---------------------------------");
					log.info("Email Number " + (i + 1));
					log.info("Subject: " + message.getSubject());
					log.info("From : " + message.getFrom()[0]);
					log.info("To: " + message.getAllRecipients().toString());
					log.info("Received Date: " + message.getReceivedDate());
					log.info("Text: " + bp.getContent().toString());

					Email o = new Email();
					o.setSubject(message.getSubject());
					o.setFrom(message.getFrom()[0].toString());
					o.setTo(message.getAllRecipients().toString());
					o.setReceivedDate(message.getReceivedDate().toString());
					o.setContent(bp.getContent().toString());
					return o;
				}
			}
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		return null;
	}
	
	private static void removeEmailMessage(String host, String email, String password, String subjectToDelete)	{
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imaps");
		try {
			Session session = Session.getInstance(props, null);
			Store store = session.getStore();
			store.connect(host, email, password);
			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_WRITE);

			Message[] messages = inbox.getMessages();

			for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                String subject = message.getSubject();
                if (subject.contains(subjectToDelete)) {
                    message.setFlag(Flags.Flag.DELETED, true);
                    System.out.println("Marked DELETE for message: " + subject);
                }
			}
			 boolean expunge = true;
	         inbox.close(expunge);
	         store.close();
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
	}

	public static Email getGmailContent(String email, String password, String from, String subject) {
		return getEmailContent("imap.gmail.com", email, password, "INBOX", from, subject);
	}

	public static void removeEmailMessage(String email, String password, String subjectToDelete)
	{
		removeEmailMessage("imap.gmail.com", email, password, subjectToDelete);
	}
	
	private static void markEmailAsRead(String host, String email, String password, String folder) {
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imaps");
		try {
			Session session = Session.getInstance(props, null);
			Store store = session.getStore();
			store.connect(host, email, password);
			Folder inbox = store.getFolder(folder);
			inbox.open(Folder.READ_WRITE);

			Message[] messages = inbox.search(new FlagTerm(new Flags(Flag.SEEN), false));
			System.out.println("messages.length---" + messages.length);

			for (int i = 0, n = messages.length; i < n; i++) {
				Message message = messages[i];
				message.setFlag(Flags.Flag.SEEN, true);
			}
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
	}

	public static void markGmailAsRead(String email, String password) {
		markEmailAsRead("imap.gmail.com", email, password, "INBOX");
	}
}
