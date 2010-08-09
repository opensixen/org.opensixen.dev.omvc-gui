package org.opensixen.dev.omvc;

public class ServiceRegistrationException extends RuntimeException {

	public ServiceRegistrationException(String msg) {
		super (msg);
	}

	public ServiceRegistrationException(String msg, Exception e) {
		super (msg,e);
	}

	private static final long serialVersionUID = 1L;

}
