#!/usr/bin/python
#Author: Diptesh Chatterjee
"""
Class overriding BaseSender to send emails through MailGun.
"""
import requests
from email import Email

class MailgunSender(BaseSender):
    def __init__(self):
        self.address = 'https://api.mailgun.com/v3/sandbox434e53f77df14be581b568a4da667854.mailgun.org/messages'
        self.auth = ('api', 'key-2a3ad9bec734fd11093f7191a2395b4f')

    def __formatToHtml(self, text):
        newlinesReplaced = text.replace('\n', '<br>')
        return '<html>{}</html>'.format(newlinesReplaced)

    def __createPostPayload(self, email):
        data = dict()
        fromValue = ''
        if email.hasSender():
            fromValue = '{} <{}>'.format(email.getSender(),
                    email.getSenderEmail())
        else:
            fromValue = '<{}>'.format(email.getSenderEmail())
        data['from'] = fromValue
        data['to'] = email.getRecepients()
        if email.hasCCList():
            data['cc'] = email.getCCList()
        if email.hasBCCList():
            data['bcc'] = email.getBCCList()
        data['subject'] = email.getSubject()
        data['text'] = email.getBody()
        if email.getFormat() == 'html':
            data['text'] = __formatToHtml(data['text'])
        return data

    def __getAttachmentList(self, email):
        attachments = []
        if email.hasAttachments():
            files = email.getAttachments()
            for fileName in files:
                attachments.append(('attachment', fileName))
        return attachments

    def send(self, email):
        payload = self.__createPostPayload(email)
        attachments = self.__getAttachmentList(email)
        if len(attachments) == 0:
            return requests.post(self.address, auth=self.auth, data=payload)
        else:
            return requests.post(self.address, auth=self.auth,
                    files=attachments, data=payload)
