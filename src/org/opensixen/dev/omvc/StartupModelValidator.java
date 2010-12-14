 /******* BEGIN LICENSE BLOCK *****
 * Versión: GPL 2.0/CDDL 1.0/EPL 1.0
 *
 * Los contenidos de este fichero están sujetos a la Licencia
 * Pública General de GNU versión 2.0 (la "Licencia"); no podrá
 * usar este fichero, excepto bajo las condiciones que otorga dicha 
 * Licencia y siempre de acuerdo con el contenido de la presente. 
 * Una copia completa de las condiciones de de dicha licencia,
 * traducida en castellano, deberá estar incluida con el presente
 * programa.
 * 
 * Adicionalmente, puede obtener una copia de la licencia en
 * http://www.gnu.org/licenses/gpl-2.0.html
 *
 * Este fichero es parte del programa opensiXen.
 *
 * OpensiXen es software libre: se puede usar, redistribuir, o
 * modificar; pero siempre bajo los términos de la Licencia 
 * Pública General de GNU, tal y como es publicada por la Free 
 * Software Foundation en su versión 2.0, o a su elección, en 
 * cualquier versión posterior.
 *
 * Este programa se distribuye con la esperanza de que sea útil,
 * pero SIN GARANTÍA ALGUNA; ni siquiera la garantía implícita 
 * MERCANTIL o de APTITUD PARA UN PROPÓSITO DETERMINADO. Consulte 
 * los detalles de la Licencia Pública General GNU para obtener una
 * información más detallada. 
 *
 * TODO EL CÓDIGO PUBLICADO JUNTO CON ESTE FICHERO FORMA PARTE DEL 
 * PROYECTO OPENSIXEN, PUDIENDO O NO ESTAR GOBERNADO POR ESTE MISMO
 * TIPO DE LICENCIA O UNA VARIANTE DE LA MISMA.
 *
 * El desarrollador/es inicial/es del código es
 *  FUNDESLE (Fundación para el desarrollo del Software Libre Empresarial).
 *  Indeos Consultoria S.L. - http://www.indeos.es
 *
 * Contribuyente(s):
 *  Eloy Gómez García <eloy@opensixen.org> 
 *
 * Alternativamente, y a elección del usuario, los contenidos de este
 * fichero podrán ser usados bajo los términos de la Licencia Común del
 * Desarrollo y la Distribución (CDDL) versión 1.0 o posterior; o bajo
 * los términos de la Licencia Pública Eclipse (EPL) versión 1.0. Una 
 * copia completa de las condiciones de dichas licencias, traducida en 
 * castellano, deberán de estar incluidas con el presente programa.
 * Adicionalmente, es posible obtener una copia original de dichas 
 * licencias en su versión original en
 *  http://www.opensource.org/licenses/cddl1.php  y en  
 *  http://www.opensource.org/licenses/eclipse-1.0.php
 *
 * Si el usuario desea el uso de SU versión modificada de este fichero 
 * sólo bajo los términos de una o más de las licencias, y no bajo los 
 * de las otra/s, puede indicar su decisión borrando las menciones a la/s
 * licencia/s sobrantes o no utilizadas por SU versión modificada.
 *
 * Si la presente licencia triple se mantiene íntegra, cualquier usuario 
 * puede utilizar este fichero bajo cualquiera de las tres licencias que 
 * lo gobiernan,  GPL 2.0/CDDL 1.0/EPL 1.0.
 *
 * ***** END LICENSE BLOCK ***** */

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
