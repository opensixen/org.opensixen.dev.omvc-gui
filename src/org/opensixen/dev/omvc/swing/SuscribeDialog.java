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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.logging.Level;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;

import org.compiere.apps.ConfirmPanel;
import org.compiere.dbPort.Convert;
import org.compiere.swing.CButton;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CDialog;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTextArea;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.opensixen.dev.omvc.Activator;
import org.opensixen.dev.omvc.model.Project;
import org.opensixen.dev.omvc.model.Revision;
import org.opensixen.dev.omvc.model.Script;
import org.opensixen.model.MRevision;
import org.opensixen.model.POFactory;
import org.opensixen.model.QParam;
import org.opensixen.omvc.client.Updater;
import org.opensixen.omvc.client.model.ProjectComboBoxModel;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class SuscribeDialog extends CDialog {

	private CPanel PPanel = new CPanel();
	private CPanel southPanel = new CPanel();
	private ConfirmPanel confirmPanel = new ConfirmPanel(true);
	//private JButton bOk;
	//private JButton bCancel;
	private CComboBox fProject;
	private JLabel lProject;
	private CTextArea fDescription;
	private CLabel lDescription;
	private CPanel mainPane;
	private CLabel lHeader;
	
	private CLogger log = CLogger.getCLogger(getClass());
	private ProjectComboBoxModel model;
	private String MSG_STATUS;
	private CButton fSetupOMVC;

		
	public SuscribeDialog(JFrame frame) {
		super(frame);
		try	{
			initGUI();
		}
		catch(Exception ex) {
			log.log(Level.SEVERE, ex.getMessage());
		}
		
	}
	
	private void initGUI(){
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(PPanel,BorderLayout.CENTER);
		getContentPane().add(confirmPanel,BorderLayout.SOUTH);
		confirmPanel.addActionListener(this);
		PPanel.setLayout(new GridBagLayout());
		
		lHeader = new CLabel();
		lHeader.setText(Msg.getMsg(Env.getCtx(), "Project Subscribe"));
		lHeader.setFont(new java.awt.Font("Dialog",1,14));
		
		//Description
		lDescription = new CLabel();
		lDescription.setText(Msg.getMsg(Env.getCtx(), "Description"));
		
		fDescription = new CTextArea();
		fDescription.setEditable(false);
		lDescription.setLabelFor(fDescription);
		
		//Proyecto
		lProject = new CLabel();
		lProject.setText(Msg.getMsg(Env.getCtx(), "Project"));
		
		model = new ProjectComboBoxModel();
		fProject = new CComboBox(model);
		lProject.setLabelFor(fProject);
		
		PPanel.add( lHeader,new GridBagConstraints( 1,0,1,1,0.0,0.0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets( 2,2,10,20 ),0,0 ));

		PPanel.add( lProject,new GridBagConstraints( 0,1,1,1,0.0,0.0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets( 2,2,2,2 ),0,0 ));
		PPanel.add( fProject,new GridBagConstraints( 1,1,1,1,0.3,0.0,GridBagConstraints.WEST,GridBagConstraints.BOTH,new Insets( 2,2,2,20 ),0,0 ));
		PPanel.add( lDescription,new GridBagConstraints( 0,2,1,1,0.0,0.0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets( 20,2,2,2 ),0,0 ));
		PPanel.add( fDescription,new GridBagConstraints( 1,2,GridBagConstraints.RELATIVE,GridBagConstraints.RELATIVE,1.0,1.0,GridBagConstraints.WEST,GridBagConstraints.BOTH,new Insets( 20,2,2,10 ),0,0 ));
		this.setSize(400, 250);
		
	}

	
	
	private void setupInit() {
		CPanel panel = new CPanel();
		getContentPane().add(panel);		
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		CPanel msgPane = new CPanel();		
		panel.add(msgPane);
		CLabel msg = new CLabel();
		msg.setText(Msg.getMsg(Env.getCtx(), "The OMVC Server is not configured"));
		msg.setFontBold(true);
		msgPane.add(msg);
		
		CPanel setupPane = new CPanel();
		panel.add(setupPane);
		setupPane.setLayout(new GridLayout(0, 2));
		
		setupPane.add(new CLabel(Msg.getMsg(Env.getCtx(), "Configure the OMVC Server")));
		fSetupOMVC = new CButton();
		fSetupOMVC.setText(Msg.getMsg(Env.getCtx(), "Setup"));
		fSetupOMVC.addActionListener(this);
		setupPane.add(fSetupOMVC);
		this.setSize(400, 250);

	}

	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))	{
			dispose();
		}
		
		if (e.getActionCommand().equals(ConfirmPanel.A_OK))	{			
			
			if (suscribe())	{
				dispose();
			}
		}
		
		if (e.getSource().equals(fSetupOMVC))	{
			SetupPanel panel = new SetupPanel(null, 0);
			panel.setVisible(true);
			dispose();
		}
	}
	
	private boolean suscribe()	{
		Project project = model.getSelectedProject();
		MRevision rev = POFactory.get(MRevision.class, new QParam(MRevision.COLUMNNAME_Project_ID, project.getProject_ID()));
		if (rev != null)	{
			fDescription.setText(Msg.getMsg(Env.getCtx(), "Already Subscribed to this project"));
			return false;
		}
		
		rev = new MRevision(Env.getCtx(), 0, null);
		rev.setProject_ID(project.getProject_ID());
		rev.setName(project.getName());
		rev.setDescription(project.getName());
		rev.setRevision(0);
		rev.setIsAudited(true);
		return rev.save();

	}
}
