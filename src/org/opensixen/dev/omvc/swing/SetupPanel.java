package org.opensixen.dev.omvc.swing;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

import org.compiere.apps.ADialog;
import org.compiere.apps.AEnv;
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

public class SetupPanel extends CDialog {

	private static final long serialVersionUID = 1L;
	
	private CTextField fHost = new CTextField();
	private CTextField fUser = new CTextField();
	private CPassword fPass = new CPassword();
	
	private CButton bOk, bCancel;
	
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
		mainPane.setLayout(new GridLayout(0, 2));		
		mainPane.add(new CLabel(Msg.getMsg(Env.getCtx(), "Host")));
		mainPane.add(fHost);
		mainPane.add(new CLabel(Msg.getMsg(Env.getCtx(), "Username")));
		mainPane.add(fUser);
		mainPane.add(new CLabel(Msg.getMsg(Env.getCtx(), "Password")));
		mainPane.add(fPass);
			
		
		CPanel btnPane = new CPanel();
		panel.add(btnPane);
		bOk = new CButton(Msg.getMsg(Env.getCtx(), "Ok"));
		bOk.addActionListener(this);
		btnPane.add(bOk);
		bCancel = new CButton(Msg.getMsg(Env.getCtx(), "Cancel"));
		bCancel.addActionListener(this);
		btnPane.add(bCancel);
	
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(bCancel))	{
			dispose();
		}
		if (e.getSource().equals(bOk))	{
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


