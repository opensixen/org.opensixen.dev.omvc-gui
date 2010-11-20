package org.opensixen.dev.omvc;

import java.util.ArrayList;

import org.compiere.model.MSysConfig;
import org.compiere.util.CLogger;
import org.eclipse.riena.communication.core.IRemoteServiceRegistration;
import org.eclipse.riena.communication.core.factory.ProxyFactory;
import org.eclipse.riena.communication.core.factory.Register;
import org.opensixen.dev.omvc.interfaces.IRemoteCentralizedIDGenerator;
import org.opensixen.dev.omvc.model.RienaTools;
import org.opensixen.omvc.client.proxy.OMVCAuthServiceProxy;
import org.opensixen.omvc.client.proxy.RemoteConsoleProxy;
import org.opensixen.omvc.client.proxy.RevisionDownloaderProxy;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;


/**
 * Client Activator
 * 
 * 
 * @author Eloy Gomez
 *
 */
public class Activator implements BundleActivator {

	private static BundleContext context;
		
	private static CLogger s_log = CLogger.getCLogger(Activator.class);
	
	@Override
	public void start(BundleContext context) throws Exception {	
		Activator.context = context;
		OSXServiceConnectionHandler handler = new OSXServiceConnectionHandler();

		// Set connection Handlers
		RemoteConsoleProxy.setServiceConnectionHandler(handler);
		RevisionDownloaderProxy.setServiceConnectionHandler(handler);
		OMVCAuthServiceProxy.setServiceConnectionHandler(handler);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public static BundleContext getContext()	{
		return context;
	}	
}
	
	
	
