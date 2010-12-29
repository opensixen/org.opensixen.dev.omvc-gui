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

package org.opensixen.dev.omvc.swing;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

import org.compiere.apps.ADialog;
import org.compiere.apps.AEnv;
import org.compiere.apps.ConfirmPanel;
import org.compiere.model.MSysConfig;
import org.compiere.model.Query;
import org.compiere.swing.CButton;
import org.compiere.swing.CDialog;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CPassword;
import org.compiere.swing.CTextField;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.opensixen.model.POFactory;
import org.opensixen.model.QParam;
import org.opensixen.omvc.client.proxy.RevisionDownloaderProxy;

/**
 * 
 * 
 * 
 * @author Eloy Gomez
 * Indeos Consultoria http://www.indeos.es
 *
 */
public class SetupPanel extends CDialog {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Descripción de campos
	 */
	private CTextField fHost = new CTextField();
	private CTextField fUser = new CTextField();
	private CPassword fPass = new CPassword();
	
	//Paneles
	private ConfirmPanel confirmPanel = new ConfirmPanel(true);
	
	private CLogger log = CLogger.getCLogger(getClass());
	
	public SetupPanel(Frame frame, int WindowNo)	{
		super(frame, Msg.getMsg(Env.getCtx(), Msg.getMsg(Env.getCtx(), "Configure the OMVC Server")), true);
		log.config("Configure the OMVC Server");	
		jbInit();
		load();		
		AEnv.positionCenterWindow(frame, this);
	}
	
	private void jbInit() {
		CPanel panel = new CPanel();
		getContentPane().add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		CPanel mainPane = new CPanel();
		panel.add(mainPane);

		mainPane.setLayout(new GridBagLayout());
		mainPane.add( new CLabel(Msg.getMsg(Env.getCtx(), "Host")),new GridBagConstraints( 0,0,1,1,0.0,0.0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets( 2,2,2,2 ),0,0 ));
		mainPane.add( fHost,new GridBagConstraints( 1,0,1,1,0.3,0.0,GridBagConstraints.WEST,GridBagConstraints.BOTH,new Insets( 2,2,2,20 ),0,0 ));

		mainPane.add( new CLabel(Msg.getMsg(Env.getCtx(), "Username")),new GridBagConstraints( 0,1,1,1,0.0,0.0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets( 2,2,2,2 ),0,0 ));
		mainPane.add( fUser,new GridBagConstraints( 1,1,1,1,0.3,0.0,GridBagConstraints.WEST,GridBagConstraints.BOTH,new Insets( 2,2,2,20 ),0,0 ));

		mainPane.add( new CLabel(Msg.getMsg(Env.getCtx(), "Password")),new GridBagConstraints( 0,2,1,1,0.0,0.0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets( 2,2,2,2 ),0,0 ));
		mainPane.add( fPass,new GridBagConstraints( 1,2,1,1,0.3,0.0,GridBagConstraints.WEST,GridBagConstraints.BOTH,new Insets( 2,2,2,20 ),0,0 ));
			
		
		CPanel btnPane = new CPanel();
		btnPane.setLayout(new BorderLayout());
		panel.add(btnPane);
		btnPane.add(confirmPanel,BorderLayout.EAST);
		confirmPanel.addActionListener(this);
	
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))	{
			dispose();
		}
		if (e.getActionCommand().equals(ConfirmPanel.A_OK))	{
			saveInput();
			dispose();
		}
		
		
	}

	private void saveInput() {				
		updateSysConfig("DICTIONARY_ID_WEBSITE",fHost.getText());
		updateSysConfig("DICTIONARY_ID_USER", fUser.getText());
		updateSysConfig("DICTIONARY_ID_PASSWORD", new String(fPass.getPassword()));
	}
	
	private boolean updateSysConfig(String name, String value)	{
		MSysConfig config = POFactory.get(MSysConfig.class, new QParam(MSysConfig.COLUMNNAME_Name, name));
		config.setValue(value);
		return config.save();		
	}

	private void load()	{
		fHost.setText(MSysConfig.getValue("DICTIONARY_ID_WEBSITE"));
		fUser.setText(MSysConfig.getValue("DICTIONARY_ID_USER"));
		fPass.setText(MSysConfig.getValue("DICTIONARY_ID_PASSWORD"));
	}	
}


