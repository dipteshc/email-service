#!/usr/bin/python

"""
Class defining the structure of an Email. This class is immutable and should
only be created using an EmailBuilder object. I know of no other way to make
classes immutable in Python.

@author Diptesh Chatterjee
"""
class Email:
    def __init__(self, sender="", to=[], cc=[], bcc=[], subject="", body="",
            attachments=[]):
        self.__sender__ = sender
        self.__to__ = to
        self.__cc__ = cc
        self.__bcc__ = bcc
        self.__subject__ = subject
        self.__body__ = body
        self.__attachments__ = attachments

    def getSender(self):
        return self.__sender__

    def getRecepients(self):
        return self.__to__

    def getCCList(self):
        return self.__cc__

    def getBCCList(self):
        return self.__bcc__

    def getSubject(self):
        return self.__subject__

    def getBody(self):
        return self.__body__

    def getAttachments(self):
        return self.__attachments__

"""
Class EmailBuilder implements a builder pattern for Email. Since Email is an
immutable class, I chose to use Builder pattern.
Usage:
    builder = EmailBuilder()
              .setSender('foo@example.com')
              .addReceiver('bar@anotherexample.com')
              .addReceiver('foobar@somedomain.com')
              .
              .
              .
              .create()

"""

class EmailBuilder:
    def __init__(self):
        self.__sender__ = ""
        self.__receivers__ = []
        self.__cc__ = []
        self.__bcc__ = []
        self.__subject__ = ""
        self.__body__ = ""
        self.__attachments__ = []

    def create(self):
        return Email(self.__sender, self.__receivers__, self.__cc__,
                self.__bcc__, self.__subject__, self.__body__,
                self.__attachments__)

    def setSender(self, sender):
            self.__sender__ = sender

    def addReceiver(self, receiver):
            self.__receivers__.append(receiver)

    def addCC(self, cc):
            self.__cc__.append(cc)

    def addBCC(self, bcc):
            self.__bcc__.append(bcc)

    def addAttachment(self, attachment):
            self.__attachments__.append(attachment)

    def setSubject(self, subject):
            self.subject = subject

    def setBody(self, body):
            self.body = body

