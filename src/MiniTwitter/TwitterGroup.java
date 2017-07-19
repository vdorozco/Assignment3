package MiniTwitter;


import java.util.Hashtable;

public class TwitterGroup implements Group, TwitterElement{
	
	private boolean isGroup;
	private Hashtable<String, Group> children;
	private String name;
	private String ID;
	private long creationTime;
	
	public TwitterGroup(String name, String ID){
		
		this.name = name;
		this.ID = ID;
		isGroup = true;
		children = new Hashtable<String, Group>();
		creationTime = System.currentTimeMillis();
	}

	@Override
	public void accept(TwitterElementVisitor visitor) {
		
		visitor.visit(this);
	}

	@Override
	public void add(Group child) {
		
		children.put(child.getName(), child);
	}

	//not implemented
	@Override
	public void remove(TwitterUser user) {
		
	}

	@Override
	public Hashtable<String, Group> getChildren() {
	
		return children;
	}

	@Override
	public Group getChild(String name) {
		
		return children.get(name);
	}

	@Override
	public boolean isGroup() {
		
		return isGroup;
	}

	@Override
	public void setGroup(boolean isGroup) {
		
		this.isGroup = isGroup;
		
	}

	@Override
	public String getName() {
		
		return name;
	}

	@Override
	public String getID() {
		
		return ID;
	}
	
	public String toString(){
		
		return getName() + " -Created " + getCreationTime() + " seconds ago.";
	}
	
	public long getCreationTime(){
		
		return (System.currentTimeMillis() - creationTime) / 1000;
	}


}
