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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.PrivilegedAction;
import java.util.logging.Level;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import org.compiere.apps.AEnv;
import org.compiere.util.CLogger;

import org.opensixen.dev.omvc.swing.SetupPanel;
import org.opensixen.dev.omvc.swing.UpdateDialog;
import org.opensixen.omvc.client.Updater;
import org.opensixen.omvc.client.proxy.RemoteConsoleProxy;
import org.opensixen.osgi.AbstractMenuAction;
import org.opensixen.osgi.interfaces.IMenuAction;

/**
 * 
 * 
 * 
 * @author Eloy Gomez
 * Indeos Consultoria http://www.indeos.es
 *
 */
public class UpdateMenuAction extends AbstractMenuAction implements
		IMenuAction, ActionListener {
	private CLogger log = CLogger.getCLogger(getClass());
	
	@Override
	public void addAction(JMenuBar menuBar) {
		JMenu menu = getMenu(menuBar, "Tools");

		if (menu == null) {
			return;
		}

		AEnv.addMenuItem("Update", "Update", null, menu, this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Updater updater;
		try {
			updater = new Updater();
		}
		catch (Exception ex)	{
			log.log(Level.SEVERE, "Error conectando al servidor.", ex);
			SetupPanel panel = new SetupPanel(null, 0);
			panel.setVisible(true);
			return;
		}

		// Run app in secure context
		try {
			Subject.doAs(RemoteConsoleProxy.getInstance().getLoginContext().getSubject(),
					getRunAction(updater));
		} catch (LoginException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}

	private PrivilegedAction getRunAction(final Updater updater) {
		return new PrivilegedAction() {
			public Object run() {
				UpdateDialog dialog = new UpdateDialog(updater);
				AEnv.showCenterScreen(dialog);
				return null;
			}
		};
	}

}
