/**
 * 
 */
package org.opensixen.dev.omvc;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.compiere.model.MSysConfig;
import org.opensixen.riena.client.proxy.ServiceConnection;
import org.opensixen.riena.interfaces.IServiceConnectionHandler;

/**
 * 
 * 
 * @author Eloy Gomez
 * Indeos Consultoria http://www.indeos.es
 *
 */
public class OSXServiceConnectionHandler implements IServiceConnectionHandler, CallbackHandler {

	private ServiceConnection connection;
	
	/* (non-Javadoc)
	 * @see org.opensixen.riena.interfaces.IServiceConnectionHandler#getServiceConnection()
	 */
	@Override
	public ServiceConnection getServiceConnection() {
		// TODO Auto-generated method stub
		
		if (connection == null)	{
			connection = new ServiceConnection();
			connection.setUrl(MSysConfig.getValue("DICTIONARY_ID_WEBSITE"));
		}
		return connection;
	}

	/* (non-Javadoc)
	 * @see javax.security.auth.callback.CallbackHandler#handle(javax.security.auth.callback.Callback[])
	 */
	@Override
	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException {
	
		((NameCallback) callbacks[0]).setName(MSysConfig.getValue("DICTIONARY_ID_USER"));
		((PasswordCallback) callbacks[1]).setPassword(MSysConfig.getValue("DICTIONARY_ID_PASSWORD").toCharArray());
		
	}

	
	
	
}
