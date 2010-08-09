package org.opensixen.dev.omvc;

import org.opensixen.dev.omvc.interfaces.IRemoteCentralizedIDGenerator;
import org.opensixen.osgi.interfaces.ICentralizedIDGenerator;
import org.osgi.framework.ServiceReference;

public class CentralizedIDGenerator implements ICentralizedIDGenerator {
		
	private static IRemoteCentralizedIDGenerator generator;
	
	private void register()	{		
		Activator.register();
		ServiceReference ref = Activator.getContext().getServiceReference(IRemoteCentralizedIDGenerator.class.getName());
		generator = (IRemoteCentralizedIDGenerator) Activator.getContext().getService(ref);
		
		if (generator == null)	{
			throw new RuntimeException("No se puede registrar el generador remoto.");
		}
		
	}
	
	@Override
	public int getNextID(String tableName, String description) {
		if (generator == null)	{
			register();
		}
		
		return generator.getNextID(tableName);
	}

}
