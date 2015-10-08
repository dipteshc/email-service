#!/usr/bin/python
#Author: Diptesh Chatterjee
"""
Class overriding BaseSender to send emails through Sendgrid.
"""
import os
import requests
import sendgrid
from email import Email

class SendgridSender(BaseSender):
    def __init__(self):
        self.client = sendgrid.SendGridClient('dipteshc', 'pikachu123')

    def __formatToHtml(self, text):
        newlinesReplaced = text.replace('\n', '<br>')
        return '<html>{}</html>'.format(newlinesReplaced)

    def __createMail(self, email):
        msg = sendgrid.Mail()
        msg.set_from(email.getSenderEmail())
        if email.hasSender():
            msg.set_from_name(email.getSender())
        for recepient in email.getRecepients():
            msg.add_to(recepient)
        for cc in email.getCCList():
            msg.add_cc(cc)
        for bcc in email.getBCCList():
            msg.add_bcc(bcc)
        msg.set_subject(email.getSubject())
        for attachment in email.getAttachments():
            msg.add_attachment(os.path.basename(attachment), attachment)
        if email.getFormat() == 'text':
            msg.set_text(email.getBody())
        else:
            msg.set_html(self.__formatToHtml(email.getBody()))
        return msg

    def send(self, email):
        msg = createMail(email)
        return self.client.send(msg)

