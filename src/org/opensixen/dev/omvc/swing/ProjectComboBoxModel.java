package org.opensixen.dev.omvc.swing;

import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

import org.opensixen.dev.omvc.interfaces.IRevisionUploader;
import org.opensixen.dev.omvc.model.Project;

public class ProjectComboBoxModel implements ComboBoxModel {

	private List<Project> projects;
	private Project selected;
	
	
	public ProjectComboBoxModel(IRevisionUploader console) {
		try	{
			projects = console.getProjects();
		}
		catch (Exception e)	{
			e.printStackTrace();
			
		}
	}

	@Override
	public int getSize() {
		return projects.size();
	}

	@Override
	public Object getElementAt(int index) {
		return projects.get(index).getName();
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSelectedItem(Object anItem) {
		for (Project project:projects)	{
			if (project.getName().equals(anItem))	{
				selected = project;
				return;
			}
		}
		
		selected = null;
	}

	@Override
	public Object getSelectedItem() {
		if (selected != null)	{
			return selected.getName();
		}
		else {
			return null;
		}
	}
	
	public Project getSelectedProject()	{
		return selected;
	}

}
