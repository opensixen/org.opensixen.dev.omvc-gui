package org.opensixen.dev.omvc.swing;
import java.awt.BorderLayout;
import java.awt.GridLayout;
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
import org.opensixen.dev.omvc.interfaces.IRevisionUploader;
import org.opensixen.dev.omvc.model.Revision;
import org.opensixen.dev.omvc.model.Script;
import org.opensixen.dev.omvc.model.ServiceRegistrationException;
import org.opensixen.model.MRevision;
import org.opensixen.model.POFactory;
import org.opensixen.model.QParam;
import org.opensixen.omvc.client.Updater;

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
public class CommitDialog extends CDialog {

	private JPanel jPanel1;
	private JButton bOk;
	private JButton bCancel;
	private CComboBox cComboBox1;
	private JLabel lProject;
	private CTextArea fDescription;
	private JLabel lDescription;
	private JPanel mainPane;
	private JLabel lHeader;
	private IRevisionUploader uploader;
	
	private CLogger log = CLogger.getCLogger(getClass());
	private ProjectComboBoxModel model;
	private String MSG_STATUS;
	private CButton fSetupOMVC;

	/**
	* Auto-generated main method to display this JDialog
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame();
				CommitDialog inst = new CommitDialog(frame);
				inst.setVisible(true);
			}
		});
	}
	
	public CommitDialog(JFrame frame) {
		super(frame);
		try	{
			uploader = Activator.getUploader();
			initGUI();
		}
		catch (ServiceRegistrationException e)	{
			log.severe("No se puede registrar con el servidor de revisiones");
			Activator.unregister();
			setupInit();
		}
		catch(Exception ex) {
			log.log(Level.SEVERE, ex.getMessage());
		}
		
	}
		
	private void initGUI() {
		try {
			{
				BorderLayout thisLayout = new BorderLayout();
				getContentPane().setLayout(thisLayout);
				{
					jPanel1 = new JPanel();
					getContentPane().add(jPanel1, BorderLayout.NORTH);
					{
						lHeader = new JLabel();
						jPanel1.add(lHeader);
						lHeader.setText("Enviar revision al servidor");
						lHeader.setFont(new java.awt.Font("Dialog",1,14));
					}
				}
				{
					mainPane = new JPanel();
					getContentPane().add(mainPane, BorderLayout.CENTER);
					GroupLayout mainPaneLayout = new GroupLayout((JComponent)mainPane);
					mainPane.setLayout(mainPaneLayout);
					mainPane.setPreferredSize(new java.awt.Dimension(378, 245));
					//START >>  lDescription
					lDescription = new JLabel();
					lDescription.setText("Desciption");
					lDescription.setBounds(70, 42, 70, 14);
					//END <<  lDescription
					//START >>  fDescription
					fDescription = new CTextArea();
					fDescription.setBounds(182, 14, 147, 70);
					//END <<  fDescription
					//START >>  lProject
					lProject = new JLabel();
					lProject.setText("Project");
					lProject.setBounds(84, 98, 49, 14);
					//END <<  lProject
					//START >>  cComboBox1
					model = new ProjectComboBoxModel(uploader);
					cComboBox1 = new CComboBox(model);
					cComboBox1.setBounds(182, 105, 77, 21);
					//END <<  cComboBox1
					//START >>  bCancel
					bCancel = new JButton();
					bCancel.addActionListener(this);
					bCancel.setText("Cancel");
					//END <<  bCancel
					//START >>  bOk
					bOk = new JButton();
					bOk.addActionListener(this);
					bOk.setText("Ok");
					//END <<  bOk
					mainPaneLayout.setHorizontalGroup(mainPaneLayout.createSequentialGroup()
						.addContainerGap(33, 33)
						.addGroup(mainPaneLayout.createParallelGroup()
						    .addComponent(lDescription, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
						    .addGroup(GroupLayout.Alignment.LEADING, mainPaneLayout.createSequentialGroup()
						        .addGap(21)
						        .addComponent(lProject, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)))
						.addGap(35)
						.addGroup(mainPaneLayout.createParallelGroup()
						    .addGroup(mainPaneLayout.createSequentialGroup()
						        .addComponent(fDescription, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE)
						        .addGap(0, 0, Short.MAX_VALUE))
						    .addGroup(GroupLayout.Alignment.LEADING, mainPaneLayout.createSequentialGroup()
						        .addGroup(mainPaneLayout.createParallelGroup()
						            .addGroup(GroupLayout.Alignment.LEADING, mainPaneLayout.createSequentialGroup()
						                .addGap(0, 58, Short.MAX_VALUE)
						                .addComponent(bOk, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
						            .addGroup(GroupLayout.Alignment.LEADING, mainPaneLayout.createSequentialGroup()
						                .addComponent(cComboBox1, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
						                .addGap(51)))
						        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						        .addComponent(bCancel, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
						        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 0, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(39, 39));
					mainPaneLayout.setVerticalGroup(mainPaneLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(mainPaneLayout.createParallelGroup()
						    .addComponent(fDescription, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
						    .addGroup(GroupLayout.Alignment.LEADING, mainPaneLayout.createSequentialGroup()
						        .addGap(26)
						        .addComponent(lDescription, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
						        .addGap(44)))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(mainPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						    .addComponent(cComboBox1, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						    .addComponent(lProject, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
						.addGap(31)
						.addGroup(mainPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						    .addComponent(bCancel, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						    .addComponent(bOk, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(24, 24));
				}
			}
			this.setSize(400, 250);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		if (e.getSource().equals(bCancel))	{
			dispose();
		}
		
		if (e.getSource().equals(bOk))	{
			// Antes de hacer commit, hay que actualizarse
			Updater updater = new Updater();
			if (!updater.update())	{
				log.severe("No se puede actualiar a la ultima revision antes de enviar nada.");
				return;
			}
			
			if (commit())	{
				dispose();
			}
		}
		
		if (e.getSource().equals(fSetupOMVC))	{
			SetupPanel panel = new SetupPanel(null, 0);
			panel.setVisible(true);
			dispose();
		}
	}
	
	private boolean commit()	{
		Revision rev = new Revision();		
		rev.setProject(model.getSelectedProject());
		rev.setDescription(fDescription.getText());
		
		Script pgScript = Script.getScript(Script.ENGINE_POSTGRESQL, Convert.getPgFileName());
		if (pgScript == null)	{
			MSG_STATUS = "No se encuentra el script para PostgreSQL.";
			log.severe(MSG_STATUS);
			return false;
		}
		rev.addScript(pgScript);
		
		Script oraScript = Script.getScript(Script.ENGINE_ORACLE, Convert.getOrFileName());
		if (oraScript == null)	{
			MSG_STATUS = "No se encuentra el script para Oracle.";
			log.severe(MSG_STATUS);
			return false;
		}
		rev.addScript(oraScript);
		
		if (!uploader.testService())	{
			MSG_STATUS = "No se puede conectar con el servidor.";
			log.severe(MSG_STATUS);
			return false;
		}
		
		// Enviamos la revision
		int revision_ID = uploader.uploadRevison(rev); 
		if (revision_ID == -1)	{
			MSG_STATUS = "No se puedo enviar la revision!";
			log.severe(MSG_STATUS);
			return false;
		}

		// Guardamos la revision actual
		MRevision m_revision = POFactory.get(MRevision.class, new QParam(MRevision.COLUMNNAME_Project_ID, rev.getProject().getProject_ID()));
		m_revision.setRevision(revision_ID);
		m_revision.save();
		
		// Limpiamos los scripts para dejar paso a una nueva revision
		Convert.resetLogMigrationScript();
		return true;
	}
}
