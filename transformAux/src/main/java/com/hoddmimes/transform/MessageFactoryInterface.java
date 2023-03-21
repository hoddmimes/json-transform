package com.hoddmimes.transform;

public interface MessageFactoryInterface {
	public MessageInterface getMessageInstance(String pJsonMessageString);
}
