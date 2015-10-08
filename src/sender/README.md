How to add a new email sender:
1. Create class to extend BaseSender
2. Override the send method with actual sender code

How to build a new Email instance:
Although Email has a public constructor, it should not be used. One should
always create an instance of EmailBuilder and use that to construct an instance
of Email.
