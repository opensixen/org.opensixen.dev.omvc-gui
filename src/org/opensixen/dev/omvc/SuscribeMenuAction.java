package org.opensixen.dev.omvc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.compiere.apps.AEnv;
import org.compiere.util.Env;
import org.opensixen.dev.omvc.swing.CommitDialog;
import org.opensixen.dev.omvc.swing.SuscribeDialog;
import org.opensixen.osgi.AbstractMenuAction;
import org.opensixen.osgi.interfaces.IMenuAction;

public class SuscribeMenuAction  extends AbstractMenuAction implements IMenuAction,ActionListener {
	JMenuItem mSuscribe;
	
	@Override
	public void addAction(JMenuBar menuBar) {
		if (Env.getAD_Client_ID(Env.getCtx()) != 0)	{
			return;
		}
		JMenu menu = getMenu(menuBar, "Tools");
		
		if (menu == null)	{
			return;
		}
		
		mSuscribe = AEnv.addMenuItem("Suscribe Project", "Suscribe Project", null, menu, this);		
	}

	public void actionPerformed(ActionEvent e) {
		SuscribeDialog dialog = new SuscribeDialog(null);
		dialog.setVisible(true);
	}

}
