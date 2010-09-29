/**
 * 
 */
package org.opensixen.dev.omvc;

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
public class OSXServiceConnectionHandler implements IServiceConnectionHandler {

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

}
