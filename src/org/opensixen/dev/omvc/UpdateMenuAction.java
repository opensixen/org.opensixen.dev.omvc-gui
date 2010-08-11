package org.opensixen.dev.omvc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import org.compiere.apps.AEnv;
import org.opensixen.omvc.client.Updater;
import org.opensixen.osgi.AbstractMenuAction;
import org.opensixen.osgi.interfaces.IMenuAction;

public class UpdateMenuAction extends AbstractMenuAction implements IMenuAction, ActionListener {

	@Override
	public void addAction(JMenuBar menuBar) {
		JMenu menu = getMenu(menuBar, "Tools");
		
		if (menu == null)	{
			return;
		}
		
		AEnv.addMenuItem("Update", "Update", null, menu, this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Updater updater = new Updater();
		updater.update();
		
	}

}
