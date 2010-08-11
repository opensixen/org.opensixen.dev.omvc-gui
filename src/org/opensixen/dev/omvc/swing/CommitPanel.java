package org.opensixen.dev.omvc.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.logging.Level;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

import org.compiere.apps.AEnv;
import org.compiere.apps.Preference;
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
import org.opensixen.dev.omvc.model.ServiceRegistrationException;

public class CommitPanel extends CDialog {

	private IRevisionUploader uploader;
	
	private static final long serialVersionUID = 1L;
	private CLogger log = CLogger.getCLogger(getClass());
	private CComboBox fProject;
	private CTextArea fDescription;
	private CPanel panel = new CPanel();
	
	private CButton fSetupOMVC, fCommit, fCancel;
	
	public CommitPanel (Frame frame, int WindowNo)	{
		super(frame, Msg.getMsg(Env.getCtx(), Msg.getMsg(Env.getCtx(), "Commit Revision")), true);
		log.config("Commit Revision");
		try	{
			uploader = Activator.getUploader();
			jbInit();
		}
		catch (ServiceRegistrationException e)	{
			log.severe("No se puede registrar con el servidor de revisiones");
			setupInit();
		}
		catch(Exception ex) {
			log.log(Level.SEVERE, ex.getMessage());
		}
		load();
		panel.setPreferredSize(new Dimension(400, 300));
		AEnv.positionCenterWindow(frame, this);
	}
	
	

	private void jbInit() {
		getContentPane().add(panel);
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		CPanel topPane = new CPanel();
		panel.add(topPane, BorderLayout.PAGE_START);
		
		topPane.setLayout(new FlowLayout());
		CLabel label = new CLabel(Msg.getMsg(Env.getCtx(), "Envia la revision al servidor OMVC"));
		topPane.add(label);
		
		CPanel mainPane = new CPanel();
		
		//mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));
		panel.add(mainPane, BorderLayout.CENTER);
		
		label = new CLabel();
		label.setText(Msg.getMsg(Env.getCtx(), "Description"));
		mainPane.add(label);
		
		fDescription = new CTextArea();
		Dimension dDescription = new Dimension(300, 80);
		fDescription.setPreferredSize(dDescription);
		fDescription.setMaximumSize(dDescription);
		mainPane.add(fDescription);
		
		label = new CLabel();
		label.setText(Msg.getMsg(Env.getCtx(), "Project"));
		mainPane.add(label);
		
		fProject = new CComboBox(new ProjectComboBoxModel(uploader));
		Dimension dProject = new Dimension(300, 30);
		fProject.setPreferredSize(dProject);
		fProject.setMaximumSize(dProject);
		mainPane.add(fProject);
		
		CPanel setupPane = new CPanel();
		panel.add(setupPane, BorderLayout.PAGE_END);
		setupPane.setLayout(new GridLayout(0, 2));
		
		setupPane.add(new CLabel(Msg.getMsg(Env.getCtx(), "Configure the OMVC Server")));
		fSetupOMVC = new CButton();
		fSetupOMVC.setText(Msg.getMsg(Env.getCtx(), "Setup"));
		fSetupOMVC.addActionListener(this);
		setupPane.add(fSetupOMVC);
		
		fCommit = new CButton();
		fCommit.setText(Msg.getMsg(Env.getCtx(), "Commit"));
		fCommit.addActionListener(this);
		setupPane.add(fCommit);
				
		fCancel = new CButton();
		fCancel.setText(Msg.getMsg(Env.getCtx(), "Cancel"));
		fCancel.addActionListener(this);
		setupPane.add(fCancel);
		

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

	}
	
	private void load() {
	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(fSetupOMVC))	{
			SetupPanel panel = new SetupPanel(null, 0);
			panel.setVisible(true);
		}
	}	
	
	

}
