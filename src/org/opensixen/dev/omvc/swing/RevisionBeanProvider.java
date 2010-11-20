/**
 * 
 */
package org.opensixen.dev.omvc.swing;

import java.util.ArrayList;
import java.util.List;

import org.opensixen.dev.omvc.model.Revision;
import org.opensixen.interfaces.IBeanProvider;
import org.opensixen.omvc.client.ProjectUpdates;
import org.opensixen.omvc.client.Updater;

/**
 * 
 * 
 * @author Eloy Gomez
 * Indeos Consultoria http://www.indeos.es
 *
 */
public class RevisionBeanProvider implements IBeanProvider {
	
	private Updater updater;
	
	private List<Revision> revisions;

	public RevisionBeanProvider(Updater updater)	{
		this.updater = updater;
	}
	
	/* (non-Javadoc)
	 * @see org.opensixen.interfaces.IBeanProvider#getModel()
	 */
	@Override
	public Object[] getModel() {
		if (revisions == null)	{
			load();
		}
		return revisions.toArray();
	}

	private void load()	{
		
		ArrayList<Revision> rev = new ArrayList<Revision>();
		List<ProjectUpdates> updates = updater.getPendingUpdates();
		
		for (ProjectUpdates update: updates)	{
			rev.addAll(update.getRevisions());
		}
		this.revisions = rev;
	}
		
	/* (non-Javadoc)
	 * @see org.opensixen.interfaces.IBeanProvider#reload()
	 */
	@Override
	public void reload() {
		load();		
	}

	/* (non-Javadoc)
	 * @see org.opensixen.interfaces.IBeanProvider#getModelClass()
	 */
	@Override
	public Class getModelClass() {
		return Revision.class;
	}

}
