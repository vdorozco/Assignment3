package MiniTwitter;

public class TwitterElementCountVisitor implements TwitterElementVisitor{
	
	private int userTotal = 0;
	private int groupTotal = 0;
	private int messageTotal = 0;
	private int positiveTotal = 0;
	private String [] buzzwords = {"good", "great", "fun", "excellent", "awesome", "happy"};

	@Override
	public void visit(AdminUI admin) {
		
		
	}

	@Override
	public void visit(TwitterUser user) {
		
		userTotal++;
		
	}

	@Override
	public void visit(TwitterGroup group) {
		
		groupTotal++;
		
	}

	@Override
	public void visit(TweetMessage message) {
		
		messageTotal++;
		
		for(String str: buzzwords){
			
			if(message.getMessage().contains(str))
				positiveTotal++;
		}
		
	}
	
	public int getUserTotal(){	
		
		return userTotal;
	}
	
	public int getGroupTotal(){
		
		return groupTotal;
	}
	
	public int getMessageTotal(){
		
		return messageTotal;
	}
	
	public double getPositiveTotal(){
		
		return positiveTotal/messageTotal;
	}
}

