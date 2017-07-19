package MiniTwitter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class UserUI implements UIManager {
	
	private TwitterUser user;
	private JFrame frame;
	private JButton followUser;
	private JButton postTweet;
	private JTextArea userID;
	private JTextArea tweetMsg;
	private JList<TwitterUser> following;
	private JList<TweetMessage> newsFeed;
	//Refresh only when following a new user or creating new instance.
	private JScrollPane followingScrollPane;
	private JScrollPane newsFeedScrollPane;
	
	public UserUI(final TwitterUser user){
		
		this.user = user;
		buildUI();
	}
	
	//finish
	//handle button and action listener
	@Override
	public void manageButtons() {
		
		followUser = new JButton("Follow User");
		followUser.setVerticalTextPosition(AbstractButton.CENTER);
		followUser.setHorizontalTextPosition(AbstractButton.LEADING);
		followUser.setActionCommand("follows user");
		followUser.setBounds(160, 5, 150, 35);
		followUser.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
			
				if(!userID.getText().equals(user.getName()) &&
						AdminUI.getInstance().getElements().containsKey(userID.getText())){
					user.follow((TwitterUser)AdminUI.getInstance().getElements().get(userID.getText()));
				}
			}
			
		});
		
		postTweet = new JButton("Tweet");
		postTweet.setVerticalTextPosition(AbstractButton.CENTER);
 		postTweet.setHorizontalTextPosition(AbstractButton.LEADING);
 		postTweet.setActionCommand("posts tweet");
 		postTweet.setBounds(160, 170, 150, 35);
 		postTweet.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent e) {
         	    user.post(tweetMsg.getText());
          	  }
          	});
		
	}

	@Override
	public void manageFRame() {
		
		frame = new JFrame(user.getName() + "'s profile - Created" + 
		user.getCreation() + " seconds ago");
 		frame.setLayout(null);
 		frame.add(userID);
 		frame.add(followUser);
 		frame.add(tweetMsg);
 		frame.add(postTweet);
 		frame.add(followingScrollPane);
 		frame.add(newsFeedScrollPane);
 		
  		frame.setSize(330, 370);
  		frame.setVisible(true);
		
		
		
	}

	@Override
	public void manageTextArea() {
		
		userID = new JTextArea("User ID");
 		userID.setBounds(5, 5, 150, 35);
 		
 		tweetMsg = new JTextArea("Tweet Message");
  		tweetMsg.setBounds(5, 170, 150, 35);
  		tweetMsg.setLineWrap(true);
		
	}
	
	public void refresh(){
		
		following = new JList<TwitterUser>(user.getFollowing());
 		followingScrollPane = new JScrollPane(following);
 		followingScrollPane.setBounds(5, 45, 305, 120);
 		
 		newsFeed = new JList <TweetMessage>(user.getNewsFeed());
 		newsFeedScrollPane = new JScrollPane(newsFeed);
 		newsFeedScrollPane.setBounds(5, 210, 305, 120);
		
	}

	@Override
	public void buildUI() {
		
		manageButtons();
		manageTextArea();
		refresh();
		manageFRame();
		
	}

}

