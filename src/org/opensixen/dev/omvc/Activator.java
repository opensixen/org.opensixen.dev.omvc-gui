package org.opensixen.dev.omvc;

import org.compiere.util.CLogger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;


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
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub		
	}
	
	public static BundleContext getContext()	{
		return context;
	}	
}
	
	
	
