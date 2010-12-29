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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.logging.Level;

import javax.swing.BoxLayout;
import javax.swing.JTable;
import javax.swing.JTextArea;

import org.adempiere.plaf.AdempierePLAF;
import org.compiere.apps.ADialog;
import org.compiere.swing.CButton;
import org.compiere.swing.CDialog;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CScrollPane;
import org.compiere.util.CLogger;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.jdesktop.swingx.JXTaskPane;
import org.opensixen.dev.omvc.model.Revision;
import org.opensixen.model.ColumnDefinition;
import org.opensixen.omvc.client.ProjectUpdates;
import org.opensixen.omvc.client.Updater;
import org.opensixen.omvc.client.model.ScriptException;
import org.opensixen.swing.BeanTableModel;
import org.opensixen.swing.OTable;

/**
 * 
 * 
 * @author Eloy Gomez 
 * Indeos Consultoria http://www.indeos.es
 * 
 */
public class UpdateDialog extends CDialog {

	private CLogger log = CLogger.getCLogger(getClass());

	private static final long serialVersionUID = 1L;
	private Updater updater;
	private CLabel message;
	private CButton updateBtn;
	private CButton chanBtn;
	private CButton setupBtn;
	
	//Paneles
	private JXTaskPane topPanel = new JXTaskPane();
	

	private OTable updatesTable;

	public UpdateDialog(Updater updater) {
		super();
		this.updater = updater;
		jbInit();
		dynInit();
		pack();
	}

	private void jbInit() {
		
		Font font = AdempierePLAF.getFont_Field().deriveFont(18f);

		setTitle(Msg.translate(Env.getCtx(), "Updates"));
		CPanel container = new CPanel();
		container.setLayout(new BorderLayout(25, 25));
		getContentPane().add(container);

		topPanel.setLayout(new GridBagLayout());
		topPanel.setTitle(Msg.translate(Env.getCtx(), "Update Actions"));
		container.add(topPanel, BorderLayout.NORTH);

		CPanel info = new CPanel();
		CPanel setup = new CPanel();

		info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
		setup.setLayout(new BoxLayout(setup, BoxLayout.Y_AXIS));
		topPanel.add(info);
		topPanel.add(setup);
		topPanel.add( info,new GridBagConstraints( 0,0,1,1,0.0,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets( 2,2,2,2 ),0,0 ));
		

		CLabel label = new CLabel(Msg.translate(Env.getCtx(), "Server"));
		label.setFontBold(true);
		label.setFont(font);
		info.add(label);

		label = new CLabel();
		label.setText(updater.getServerDescription());
		info.add(label);

		chanBtn = new CButton(Msg.translate(Env.getCtx(), "Channels"));
		chanBtn.addActionListener(this);
		setup.add(chanBtn);

		setupBtn = new CButton(Msg.translate(Env.getCtx(), "Setup"));
		setupBtn.addActionListener(this);
		setup.add(setupBtn);

		topPanel.add( setupBtn,new GridBagConstraints( 0,1,1,1,0.0,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets( 10,20,2,20 ),0,0 ));
		topPanel.add( chanBtn,new GridBagConstraints( 0,2,1,1,0.0,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets( 10,20,2,20 ),0,0 ));

		CPanel main = new CPanel();
		main.setLayout(new BorderLayout());
		container.add(main, BorderLayout.CENTER);

		updatesTable = new OTable(Env.getCtx());
		BeanTableModel tableModel = new BeanTableModel(
				new RevisionBeanProvider(updater), getColumnDefinitions());
		updatesTable.setModel(tableModel);
		updatesTable.setupTable();
		updatesTable.setFillsViewportHeight(true);
		// updatesTable.setPreferredScrollableViewportSize(new Dimension(500,
		// 300));
		updatesTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		updatesTable.autoSize(true);
		CScrollPane scrollPane = new CScrollPane(updatesTable);
		main.add(scrollPane, BorderLayout.CENTER);

		CPanel footer = new CPanel();
		footer.setLayout(new GridBagLayout());
		container.add(footer, BorderLayout.SOUTH);

		updateBtn = new CButton();
		updateBtn.setText(Msg.translate(Env.getCtx(), "Update"));
		updateBtn.addActionListener(this);
		updateBtn.setPreferredSize(new Dimension(100,50));
		footer.add( updateBtn,new GridBagConstraints( 0,0,1,1,0.0,0.0,GridBagConstraints.WEST,GridBagConstraints.BOTH,new Insets( 10,20,2,20 ),0,0 ));
		
		//Desactivamos el boton de canales temporalmente
		chanBtn.setVisible(false);

	}

	/**
	 * 
	 */
	private void dynInit() {

	}

	public ColumnDefinition[] getColumnDefinitions() {
		ColumnDefinition[] cols = {
				new ColumnDefinition("revision_ID", "ID", 20,
						DisplayType.Integer),
				new ColumnDefinition("project.name", "Proyecto", 180,
						DisplayType.String),
				new ColumnDefinition("description", "Descripcion", 300,
						DisplayType.String),
				new ColumnDefinition("created", "Fecha", 150, DisplayType.Date) };
		return cols;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.compiere.swing.CDialog#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(setupBtn)) {
			SetupPanel panel = new SetupPanel(null, 0);
			panel.setVisible(true);
		}

		if (e.getSource().equals(updateBtn)) {
			try {
				updater.update();
				ADialog.info(0, this, "Las actualizaciones se han inatalado correctamente.");
				dispose();
			} catch (ScriptException se) {
				ADialog.error(0, this,"No se han podido instalar las actualizaciones. Consulte el log de errores."+ se.getMessage() +se.getCause());
			}

		}

	}

}
