/**
 * 
 */
package org.opensixen.dev.omvc.swing;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;

import javax.management.RuntimeErrorException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

import org.compiere.apps.AEnv;
import org.compiere.swing.CButton;
import org.compiere.swing.CDialog;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTextField;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.opensixen.osgi.Service;
import org.opensixen.osgi.ServiceQuery;
import org.opensixen.osgi.interfaces.ICommand;

/**
 * @author harlock
 *
 */
public class RunCommandDialog extends CDialog {

	private CTextField fCmd;
	private CButton bRun;
	private CButton bClose;


	public RunCommandDialog(Frame owner) throws HeadlessException {
		super(owner);
		jbInit();
		AEnv.positionCenterWindow(owner, this);
	}
	
	
	private void jbInit()	{
		CPanel panel = new CPanel();
		getContentPane().add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		CPanel mainPane = new CPanel();
		panel.add(mainPane);
		mainPane.setLayout(new GridLayout(0, 2));		
		mainPane.add(new CLabel(Msg.getMsg(Env.getCtx(), "Command")));
		fCmd = new CTextField();
		mainPane.add(fCmd);
		
		CPanel btnPane = new CPanel();
		panel.add(btnPane);
		bRun = new CButton(Msg.getMsg(Env.getCtx(), "Run"));
		bRun.addActionListener(this);
		btnPane.add(bRun);
		bClose = new CButton(Msg.getMsg(Env.getCtx(), "Close"));
		bClose.addActionListener(this);
		btnPane.add(bClose);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(bClose))	{
			dispose();
		}
		if (e.getSource().equals(bRun))	{
			run();
		}
	}


	/**
	 * 
	 */
	private void run() {
		String name = fCmd.getText();
		if (name == null || name.length() == 0)	{
			return;
		}
		ServiceQuery query = new ServiceQuery(ICommand.P_NAME, name);
		ICommand command = 	Service.locate(ICommand.class, query);	
		if (command == null )	{
			return;
		}
		command.prepare();
		try {
			command.doIt();
		}
		catch (Exception e)	{
			throw new RuntimeException(e);
		}
		
	}

}
