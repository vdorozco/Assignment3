package MiniTwitter;

public class TweetMessage implements TwitterElement {
	
	private String message;
	private String poster;
	
	public TweetMessage(String message, TwitterUser user){
		
		this.message = message;
		poster = user.getName();
	}
	
	public String toString(){
		
		return poster + ": " + message;
	}

	@Override
	public void accept(TwitterElementVisitor visitor) {
		
		visitor.visit(this);
		
	}
	
	public String getMessage(){
		
		return message;
	}
	
	

}
