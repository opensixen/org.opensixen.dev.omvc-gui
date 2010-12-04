/**
 * 
 */
package org.opensixen.dev.omvc;

import java.io.IOException;
import java.util.ArrayList;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.compiere.model.MSysConfig;
import org.opensixen.riena.client.proxy.ServiceConnection;
import org.opensixen.riena.interfaces.IConnectionChangeListener;
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
	
	private ArrayList<IConnectionChangeListener> connectionChangeListeners = new ArrayList<IConnectionChangeListener>();
	
	private static OSXServiceConnectionHandler instance;
	
	public static OSXServiceConnectionHandler getInstance()	{
		if (instance == null)	{
			instance = new OSXServiceConnectionHandler();
		}
		return instance;
	}
	
	/**
	 * Private constructor
	 */
	public OSXServiceConnectionHandler()	{
		
	}
	
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

	/* (non-Javadoc)
	 * @see org.opensixen.riena.interfaces.IServiceConnectionHandler#addConnectionChangeListener(org.opensixen.riena.interfaces.IConnectionChangeListener)
	 */
	@Override
	public void addConnectionChangeListener(IConnectionChangeListener listener) {
		connectionChangeListeners.add(listener);
		
	}

	/* (non-Javadoc)
	 * @see org.opensixen.riena.interfaces.IServiceConnectionHandler#removeConnectionChangeListener(org.opensixen.riena.interfaces.IConnectionChangeListener)
	 */
	@Override
	public void removeConnectionChangeListener(IConnectionChangeListener listener) {
		if (connectionChangeListeners.contains(listener))	{
			connectionChangeListeners.remove(listener);
		}		
	}	
	
	public void fireConnectionChange()	{
		connection = null;
		for (IConnectionChangeListener listener : connectionChangeListeners)	{
			listener.fireConnectionChange();
		}
	}	
	
}
