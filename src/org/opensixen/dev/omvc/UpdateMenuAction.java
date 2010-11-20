package org.opensixen.dev.omvc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.PrivilegedAction;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import org.compiere.apps.AEnv;

import org.opensixen.dev.omvc.swing.SetupPanel;
import org.opensixen.dev.omvc.swing.UpdateDialog;
import org.opensixen.omvc.client.Updater;
import org.opensixen.omvc.client.proxy.OMVCAuthServiceProxy;
import org.opensixen.omvc.client.proxy.RemoteConsoleProxy;
import org.opensixen.osgi.AbstractMenuAction;
import org.opensixen.osgi.interfaces.IMenuAction;

public class UpdateMenuAction extends AbstractMenuAction implements
		IMenuAction, ActionListener {

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
			SetupPanel panel = new SetupPanel(null, 0);
			panel.setVisible(true);
			return;
		}

		// Run app in secure context
		try {
			Subject.doAs(RemoteConsoleProxy.getLoginContext().getSubject(),
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
