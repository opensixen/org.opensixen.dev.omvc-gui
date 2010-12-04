/**
 * 
 */
package org.opensixen.dev.omvc;

import java.net.ConnectException;

import org.compiere.model.MClient;
import org.compiere.model.MSysConfig;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.opensixen.omvc.client.proxy.OMVCAuthServiceProxy;
import org.opensixen.omvc.client.proxy.RemoteConsoleProxy;
import org.opensixen.omvc.client.proxy.RevisionDownloaderProxy;
import org.opensixen.osgi.interfaces.IModelValidator;

/**
 * Model validator for OMVC
 * 
 * - Register Auth server on login
 * 
 * 
 * @author Eloy Gomez Indeos Consultoria http://www.indeos.es
 * 
 */
public class StartupModelValidator implements IModelValidator {

	private CLogger log = CLogger.getCLogger(getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.model.ModelValidator#initialize(org.compiere.model.
	 * ModelValidationEngine, org.compiere.model.MClient)
	 */
	@Override
	public void initialize(ModelValidationEngine engine, MClient client) {
		engine.addModelChange(MSysConfig.Table_Name, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.model.ModelValidator#getAD_Client_ID()
	 */
	@Override
	public int getAD_Client_ID() {
		return Env.getAD_Client_ID(Env.getCtx());
	}

	/**
	 * Register Auth Proxy
	 * 
	 * @see org.compiere.model.ModelValidator#login(int, int, int)
	 */
	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID) {
		try {
			// Register Auth proxy
			OSXServiceConnectionHandler handler = OSXServiceConnectionHandler.getInstance();

			// Set connection Handlers
			RemoteConsoleProxy.getInstance().setServiceConnectionHandler(handler);
			RevisionDownloaderProxy.getInstance().setServiceConnectionHandler(handler);
			OMVCAuthServiceProxy.getInstance().setServiceConnectionHandler(	handler);
			OMVCAuthServiceProxy.getInstance().register();
		} catch (Exception e) {
			log.warning("No se puede contectar con el servidor OMVC");
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.model.ModelValidator#modelChange(org.compiere.model.PO,
	 * int)
	 */
	@Override
	public String modelChange(PO po, int type) throws Exception {
		if (type == ModelValidator.CHANGETYPE_NEW
				|| type == ModelValidator.CHANGETYPE_CHANGE) {
			OSXServiceConnectionHandler.getInstance().fireConnectionChange();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.model.ModelValidator#docValidate(org.compiere.model.PO,
	 * int)
	 */
	@Override
	public String docValidate(PO po, int timing) {
		// TODO Auto-generated method stub
		return null;
	}

}
