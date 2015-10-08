#!/usr/bin/python
"""
Base class to define a sender. Every mail service will have one sender module.
Each sender will extend the BaseSender class.

@Author Diptesh Chatterjee
"""
from Email import Email

class BaseSender:
    """
    Pass it an email structure and depending on the actual implementation of the
    particular sender, send the email via sender. Return output of send request
    to caller.
    """
    def send(self, email):
        pass

