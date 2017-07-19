package MiniTwitter;


import java.util.Hashtable;
import javax.swing.DefaultListModel;

public class TwitterUser implements User, Observer, Group, TwitterElement {
	
	private DefaultListModel<TwitterUser> following;
	private DefaultListModel<TwitterUser> followers;
	private DefaultListModel<TweetMessage> newsFeed;
	private DefaultListModel<TweetMessage> archive;
	private TweetMessage tweet;
	private boolean isGroup;
	private Hashtable<String, Group> children;
	private String name;
	private String ID;
	private long creationTime;
	private long lastUpdate = 0;
	
	public TwitterUser(String name, String ID){
		
		this.name = name;
		this.ID = ID;
	    isGroup = false;
		following = new DefaultListModel<TwitterUser>();
		followers= new DefaultListModel<TwitterUser>();
		newsFeed = new DefaultListModel<TweetMessage>();
		archive = new DefaultListModel<TweetMessage>();
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
		
		return getName() + " - Updated " + getLastUpdate() + " seconds ago";
	}
	
	@Override
	public void update(TwitterUser user) {
		
		newsFeed.addElement(user.tweet);
		
	}

	@Override
	public void follow(TwitterUser user) {
		
		if(!following.contains(user)){
			
			following.addElement(user);
			user.getFollowers().addElement(this);
			
			for(int i = 0; i < user.archive.getSize(); i++){
				
				newsFeed.addElement(user.archive.elementAt(i));
			}
			
		}
		
	}
	
	public void post(String message){
		
		tweet = new TweetMessage(message, this);
		newsFeed.addElement(tweet);
		archive.addElement(tweet);
		AdminUI.getInstance().getArchives().add(tweet);
		notifyFollowers();
		lastUpdate = System.currentTimeMillis();
	}
	
	public long getCreation(){
		
		return (System.currentTimeMillis() - creationTime) / 1000;
	}
	
	public long getLastUpdate() {
		
		if(lastUpdate > 0){
			
			return (System.currentTimeMillis() - lastUpdate) / 1000;
		}
		
		return 0;
	}
	
	public void notifyFollowers(){
		
		for(int i = 0; i < followers.getSize(); i++){
			
			followers.elementAt(i).update(this);
		}
	}
	
	public DefaultListModel<TwitterUser> getFollowers(){
		
		return followers;
	}
	
	public DefaultListModel<TwitterUser> getFollowing(){
		
		return following;
	}
	
	
	public DefaultListModel<TweetMessage> getNewsFeed(){
		
		return newsFeed;
	}
}

