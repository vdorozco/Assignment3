package MiniTwitter;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;


public class AdminUI implements ActionListener, UIManager, TwitterElement {
	
	private static AdminUI instance = new AdminUI();
	private JFrame frame;
	private JTree tree;
	private DefaultMutableTreeNode selected;
	private DefaultMutableTreeNode root;
	private JScrollPane scrollPane;
	private JButton addUser;
	private JButton addGroup;
	private JButton userView;
	private JButton showUser;
	private JButton showGroup;
	private JButton showMessages;
	private JButton showPercentage;
	private JButton validateUsers;
	private JButton lastUpdate;
	private JButton [] buttons;
	private JTextArea userID;
	private JTextArea groupID;
	private Hashtable<String, Group> elements;
	private List<Group> nodes;
	private ArrayList<TweetMessage> archives;
	private int UGID;
	private int UUID;
	private DefaultMutableTreeNode childNode;
	
	private AdminUI(){
		
		UGID = 1;
		UUID = 1;
		buttons = new JButton[9];
		elements = new Hashtable<String, Group>();
		nodes = new ArrayList<Group>();
		archives = new ArrayList<TweetMessage>();
		root = new DefaultMutableTreeNode("Root");
		buildUI();
	}
	
	public static AdminUI getInstance(){
		
		return instance;
	}
	
	public Hashtable<String, Group> getElements(){
		
		return elements;
	}
	
	public ArrayList<TweetMessage> getArchives(){
		
		return archives;
	}
	
	public void addUser(String name){
		
		TwitterUser child = new TwitterUser(name, UUID++ +"");
		addChildNode(child);
	}
	
	public void addGroup(String name){
		
		TwitterGroup child = new TwitterGroup(name, UGID++ +"");
		addChildNode(child);
	}
	
	
	
	//if a group is not selected store & created node & add node to root
	//make node a child of selected node
	public void addChildNode(Group child){
		
		boolean allowsChildren = child.isGroup();
		elements.put(child.getName(), child);
		nodes.add(child);
		childNode = new DefaultMutableTreeNode(child);
		childNode.setAllowsChildren(allowsChildren);
		root.add(childNode);
		
		if(selected!= null && selected.getAllowsChildren()){
			
			root.remove(childNode);
			selected.add(childNode);
		}
		
		updateWindow();
	}
	
	//close and rebuild window
	public void updateWindow(){
		
		frame.dispose();
		buildUI();
	}

	//Ensure that all user IDS are valid
	public void validateUsers(){
		
		for(Group group: nodes){
			
			if(group.getName().contains(" ")){
				
				System.out.println("Invalid: " + group.getName());
			}
		}
	}
	
	public void lastUpdate(){
		
		TwitterUser lastUpdatedUser = new TwitterUser("dummy", "dummy");
		TwitterUser temp;
		
		for(Group group : nodes){
			
			if(!group.isGroup()){
				
				temp = (TwitterUser)group;
				if(temp.getLastUpdate() < lastUpdatedUser.getLastUpdate())
					lastUpdatedUser = temp;
			}
		}
		
		System.out.println(lastUpdatedUser.getName() + "last updated" + lastUpdatedUser.getLastUpdate() + " seconds ago.");
	}
	
	@Override
	public void accept(TwitterElementVisitor visitor) {
		
		for(Group group: nodes){
			group.accept(visitor);
		}
		
		for(TweetMessage tweet: archives){
			tweet.accept(visitor);
		}
		
		visitor.visit(this);
		
	}

	@Override
	public void manageButtons() {
		
		addUser = new JButton("Add User");
 		addUser.setActionCommand("add user");
 		addUser.setBounds(440, 5, 220, 35);
         addUser.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent e) {
         		if(!elements.containsKey(userID.getText()))
         			addUser(userID.getText());
         	  }
         	});
 		buttons[0] = addUser;
 		
 		addGroup = new JButton("Add Group");
 		addGroup.setActionCommand("add group");
 		addGroup.setBounds(440, 45, 220, 35);
 		addGroup.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent e) {
         		if(!elements.containsKey(groupID.getText()))
         			addGroup(groupID.getText());
         	  }
         	});
 		buttons[1] = addGroup;
 		
 		userView = new JButton("Open User View");
 		userView.setActionCommand("open user view");
 		userView.setBounds(210, 90, 450, 35);
 		userView.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent e) {
         		if(!selected.getAllowsChildren())
         			new UserUI((TwitterUser)selected.getUserObject());
         	  }
         	});
 		buttons[2] = userView;
 		
 		showUser = new JButton("Show User Total");
 		showUser.setActionCommand("shows user total");
 		showUser.setBounds(210, 200, 220, 35);
 		showUser.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent e) {
         		 System.out.print("User total: ");
         		TwitterElementCountVisitor counter = new TwitterElementCountVisitor();
         	    instance.accept(counter);
         	    System.out.println(counter.getUserTotal());
         	  }
         	});
 		buttons[3] = showUser;
 		
 		showGroup = new JButton("Show Group Total");
 		showGroup.setActionCommand("shows group total");
 		showGroup.setBounds(440, 200, 220, 35);
 		showGroup.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent e) {
         		 System.out.print("Group total: ");
         		TwitterElementCountVisitor counter = new TwitterElementCountVisitor();
         	    instance.accept(counter);
         	    System.out.println(counter.getGroupTotal());
         	  }
         	});
 		buttons[4] = showGroup;
 		
 		showMessages = new JButton("Show Messages Total");
 		showMessages.setActionCommand("show messages total");
 		showMessages.setBounds(210, 245, 220, 35);
 		showMessages.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent e) {
         		 System.out.print("Messages total: ");
         		TwitterElementCountVisitor counter = new TwitterElementCountVisitor();
         	    instance.accept(counter);
         	    System.out.println(counter.getMessageTotal());
         	  }
         	});
 		buttons[5] = showMessages;
 		
 		showPercentage = new JButton("Show Positive Percentage");
 		showPercentage.setActionCommand("shows percentage");
 		showPercentage.setBounds(440, 245, 220, 35);
 		showPercentage.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent e) {
         		TwitterElementCountVisitor counter = new TwitterElementCountVisitor();
         	    instance.accept(counter);
         	    System.out.println(100*counter.getPositiveTotal() +"% positivity");
         	  }
         	});
 		buttons[6] = showPercentage;
 		
 		validateUsers = new JButton("Validate Users");
 		validateUsers.setActionCommand("validates users");
 		validateUsers.setBounds(210, 155, 220, 35);
 		validateUsers.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				validateUsers();
				
			}
 			
 			
 		});
 		
 		buttons[7] = validateUsers;
 		
 		lastUpdate = new JButton("Show Last Updated");
 		lastUpdate.setActionCommand("shows last updated");
 		lastUpdate.setBounds(440, 155, 220, 35);
 		lastUpdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				lastUpdate();
				
			}
 			
 		});
 		
 		buttons[8] = lastUpdate;
 		
 		
 		
 		for(JButton button : buttons)
 		{
 			button.setVerticalTextPosition(AbstractButton.CENTER);
 			button.setHorizontalTextPosition(AbstractButton.LEADING);
 			button.addActionListener(this);
 		}
		
	}

	//handles frame build
	@Override
	public void manageFRame() {
		frame = new JFrame("Admin Control Panel");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		
		frame.add(scrollPane);
		frame.add(addUser);
		frame.add(addGroup);
		frame.add(userView);
		frame.add(showUser);
		frame.add(showGroup);
		frame.add(showMessages);
		frame.add(showPercentage);
		frame.add(userID);
		frame.add(groupID);
		frame.add(validateUsers);
		frame.add(lastUpdate);
		
		frame.setSize(685, 325);
		frame.setVisible(true);
		
		
	}

	//manages text area
	@Override
	public void manageTextArea() {
		
		userID = new JTextArea("User ID");
		userID.setBounds(210, 5, 220, 35);
		userID.setFont(new Font("Courier New", Font.PLAIN, 16));
		
		groupID = new JTextArea("Group ID");
		groupID.setBounds(210, 45, 220, 35);
		groupID.setFont(new Font ("Courier New", Font.PLAIN, 16));
		
	}

	//build init
	@Override
	public void buildUI() {
		
		manageTree();
		manageButtons();
		manageTextArea();
		manageFRame();
		
	}
	
	//tree operations
	
	public void manageTree(){
		
		tree = new JTree(root);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				
				selected = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			}
			
		});
		
		scrollPane = new JScrollPane(tree);
		scrollPane.setBounds(5, 5, 200, 275);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}

