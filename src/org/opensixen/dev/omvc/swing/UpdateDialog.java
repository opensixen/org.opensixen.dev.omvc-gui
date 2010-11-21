/**
 * 
 */
package org.opensixen.dev.omvc.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.logging.Level;

import javax.swing.BoxLayout;
import javax.swing.JTable;
import javax.swing.JTextArea;

import org.compiere.apps.ADialog;
import org.compiere.swing.CButton;
import org.compiere.swing.CDialog;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CScrollPane;
import org.compiere.util.CLogger;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.opensixen.dev.omvc.model.Revision;
import org.opensixen.model.ColumnDefinition;
import org.opensixen.omvc.client.ProjectUpdates;
import org.opensixen.omvc.client.Updater;
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

	private OTable updatesTable;

	public UpdateDialog( Updater updater)  {
		super();
		this.updater = updater; 
		jbInit();
		dynInit();
		pack();
	}	

	private void jbInit()	{
		setTitle("Updates");
		CPanel container = new CPanel();
		container.setLayout(new BorderLayout(25, 25));
		getContentPane().add(container);	

		CPanel top = new CPanel();
		//top.setLayout(new GridLayout(1, 2));
		container.add(top, BorderLayout.NORTH);
		
		CPanel info = new CPanel();
		CPanel setup = new CPanel();
		
		info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
		setup.setLayout(new BoxLayout(setup, BoxLayout.Y_AXIS));
		top.add(info);
		top.add(setup);
		
		
		
		CLabel label = new CLabel("Actualiza sus canales.");
		label.setFontBold(true);
		info.add(label);
		
		label = new CLabel();
		label.setText("Server: " + updater.getServerDescription());
		info.add(label);
		
		chanBtn = new CButton("Canales");
		chanBtn.addActionListener(this);
		setup.add(chanBtn);
		
		setupBtn = new CButton("Setup");
		setupBtn.addActionListener(this);
		setup.add(setupBtn);
		
		
		
		CPanel main = new CPanel();
		main.setLayout(new BorderLayout());
		container.add(main, BorderLayout.CENTER);

		updatesTable = new OTable(Env.getCtx());	
		BeanTableModel tableModel = new BeanTableModel(new RevisionBeanProvider(updater), getColumnDefinitions());
		updatesTable.setModel(tableModel);
		updatesTable.setupTable();
		updatesTable.setFillsViewportHeight(true);
		//updatesTable.setPreferredScrollableViewportSize(new Dimension(500, 300));
		updatesTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		updatesTable.autoSize(true);
		CScrollPane scrollPane = new CScrollPane(updatesTable);
		main.add(scrollPane, BorderLayout.CENTER);
		
		CPanel footer = new CPanel();
		footer.setLayout(new BoxLayout(footer, BoxLayout.X_AXIS));
		container.add(footer, BorderLayout.SOUTH);
		
		updateBtn = new CButton();
		updateBtn.setText("Update");
		updateBtn.addActionListener(this);
		
		footer.add(updateBtn);
	
	}

	/**
	 * 
	 */
	private void dynInit() {
		
		
		
	}

	public ColumnDefinition[] getColumnDefinitions() {
		ColumnDefinition[] cols = {
				new ColumnDefinition("revision_ID", "ID", 20, DisplayType.Integer),
				new ColumnDefinition("project.name", "Proyecto", 180, DisplayType.String),
				new ColumnDefinition("description", "Descripcion", 300, DisplayType.String),
				new ColumnDefinition("created", "Fecha", 150, DisplayType.Date)};
		return cols;
	}

	/* (non-Javadoc)
	 * @see org.compiere.swing.CDialog#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(setupBtn))		{
			SetupPanel panel = new SetupPanel(null, 0);
			panel.setVisible(true);
		}
		
		if (e.getSource().equals(updateBtn))		{
			if (updater.update())	{
				ADialog.info(0, this, "Las actualizaciones se han inatalado correctamente.");
				dispose();
			}
			else {
				ADialog.error(0, this, "No se han podido instalar las actualizaciones. Consulte el log de errores.");
			}
		}
		
	}
	
	
	

}
