package org.fortumo;

public class RequestDTO {
	@Override
	public String toString() {
		return "RequestDTO [msgId=" + msgId + ", sender=" + sender + ", receiver=" + receiver + ", timestamp="
				+ timestamp + ", operator=" + operator + ", text=" + text + "]";
	}

	private String msgId;
	private String sender;
	private String receiver;
	private String timestamp;
	private String operator;
	private String text;
	private String keyword;
	private String transactionId;
	private String input;
	private String merchantUrl;
	private String messageFromMerchant;

	public RequestDTO(String msgId, String sender, String receiver, String timestamp, String operator, String text) {
		super();
		this.msgId = msgId;
		this.sender = sender;
		this.receiver = receiver;
		this.timestamp = timestamp;
		this.operator = operator;
		this.text = text;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getMerchantUrl() {
		return merchantUrl;
	}

	public void setMerchantUrl(String merchantUrl) {
		this.merchantUrl = merchantUrl;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getMessageFromMerchant() {
		return messageFromMerchant;
	}

	public void setMessageFromMerchant(String messageFromMerchant) {
		this.messageFromMerchant = messageFromMerchant;
	}

	public String getMsgId() {
		if (msgId == null)
			msgId = "";
		return msgId;
	}

	public String getSender() {
		if (sender == null)
			sender = "";
		return sender;
	}

	public String getReceiver() {
		if (receiver == null)
			receiver = "";
		return receiver;
	}

	public String getTimestamp() {
		if (timestamp == null)
			timestamp = "";
		return timestamp;
	}

	public String getOperator() {
		if (operator == null)
			operator = "";
		return operator;
	}

	public String getText() {
		if (text == null)
			text = "";
		return text;
	}

}
