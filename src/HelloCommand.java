import org.jibble.pircbot.PircBot;

public class HelloCommand implements BotCommand {

	@Override
	public String getCommand() {
		return "hello";
	}

	@Override
	public void handleMessage(PircBot bot, String channel, String sender, String message, String[] args) {
	
		if(args[1].equals("buddy")){
			bot.sendMessage(channel, sender + ": Howdy partner!");
		}else{
			bot.sendMessage(channel, sender + ": Silence, infidel! I KILL YOU.");
		}
	}
}
