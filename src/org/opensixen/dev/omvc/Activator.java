package org.opensixen.dev.omvc;

import org.compiere.model.MSysConfig;
import org.compiere.util.CLogger;
import org.eclipse.riena.communication.core.factory.ProxyFactory;
import org.eclipse.riena.communication.core.factory.Register;
import org.opensixen.dev.omvc.interfaces.IRemoteCentralizedIDGenerator;
import org.opensixen.dev.omvc.interfaces.IRevisionUploader;
import org.opensixen.dev.omvc.interfaces.IRienaService;
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
	private static boolean registered = false;
	private static IRevisionUploader uploader;
	private static IRemoteCentralizedIDGenerator generator;
	
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
	
	
	
	public static IRevisionUploader getUploader() {
		if (!registered)	{
			register();
		}
		return uploader;
	}

	public static IRemoteCentralizedIDGenerator getGenerator() {
		if (!registered)	{
			register();
		}
		return generator;
	}

	public static void register() throws ServiceRegistrationException	{
		if (registered)	{
			return;
		}		
		String server = MSysConfig.getValue("DICTIONARY_ID_WEBSITE");
		if (server.endsWith("/"))	{
			server = server.substring(0, server.lastIndexOf('/'));
		}
		
		// Registramos el Centralized ID Generator
		String url = server + IRevisionUploader.path;		
		uploader = register(IRevisionUploader.class, url);

		// El RevisionUploader
		url = server + IRemoteCentralizedIDGenerator.path;
		generator = register(IRemoteCentralizedIDGenerator.class, url);
		
		registered = true;				
	}

	private static <T extends IRienaService > T register(Class<T> clazz, String url ) throws ServiceRegistrationException	{
		try {
			Register.remoteProxy(clazz).usingUrl(url).withProtocol("hessian").andStart(context);
			ServiceReference ref = Activator.getContext().getServiceReference(clazz.getName());
			T service = (T) context.getService(ref);
			if (service.testService())	{
				s_log.info("Service registered: " + url);
			}
			return service;
		}
		catch (Exception e)	{
			throw new ServiceRegistrationException("No se puede conectar con el servidor en la URL: " + url, e);
		}
	}
	
}
	
	
	