package MiniTwitter;

import java.util.Hashtable;

import javax.swing.DefaultListModel;
import javax.swing.tree.DefaultMutableTreeNode;

public interface Group extends TwitterElement {
	
	public void add(Group child);
	public void remove(TwitterUser user);
	public Hashtable<String, Group> getChildren();
	public Group getChild(String name);
	public boolean isGroup();
	public void setGroup(boolean isGroup);	
	public String getName();
	public String getID();
	public String toString();

}
