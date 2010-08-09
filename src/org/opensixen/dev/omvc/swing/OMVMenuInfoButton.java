package org.opensixen.dev.omvc.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.compiere.swing.CButton;
import org.compiere.util.Env;
import org.compiere.util.Ini;

import org.opensixen.osgi.interfaces.IMenuInfoComponent;

public class OMVMenuInfoButton implements IMenuInfoComponent, ActionListener {

	private static CButton devBtn;
	
	private static Color devStatusColor_OFF;
	private static Color devStatusColor_ON = new Color(255, 0, 0);	
	
	public OMVMenuInfoButton() {
		devBtn = new CButton();
		devBtn.setActionCommand("DevBtn");
		devBtn.addActionListener(this);
		devBtn.setIcon(Env.getImageIcon("devTools24.png"));
		devBtn.setMargin(new Insets(0, 0, 0, 0));
		devBtn.setText("Developer Tools");
		devStatusColor_OFF = devBtn.getBackground();
		
		if (Env.getAD_Client_ID(Env.getCtx()) != 0)	{
			devBtn.setEnabled(false);
		}

	}

	@Override
	public Component getComponent() {
		return devBtn;
	}
	
	
	
	public static void update() {
		if (Ini.isPropertyBool(Ini.P_ADEMPIERESYS))	{
			devBtn.setText("Desactivar DevMode");
			devBtn.setBackground(devStatusColor_ON);
			devBtn.setText("DevMode ACTIVO.");
		}
		else {
			devBtn.setText("Activar DevMode");
			devBtn.setBackground(devStatusColor_OFF);
			devBtn.setText("DevMode INACTIVO.");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {				
		boolean value = !Ini.isPropertyBool(Ini.P_ADEMPIERESYS);
		Ini.setProperty(Ini.P_ADEMPIERESYS, value);
		Ini.setProperty(Ini.P_LOGMIGRATIONSCRIPT, value);
		update();
	}

	@Override
	public void updateInfo() {
		update();
		
	}

}
