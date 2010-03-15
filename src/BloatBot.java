import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.jibble.pircbot.*;

import Command.BotCommand;
import Command.ChangeNickCommand;
import Command.CourseCommand;
import Command.DivideByZeroCommand;
import Command.DoSomethingPrettyCommand;
import Command.FMLCommand;
import Command.GodNattCommand;
import Command.HelloCommand;
import Command.QuitCommand;
import Command.ReciteCommand;
import Command.RssCommand;
import Command.TimeCommand;
import Command.dcHubCommand;
import Command.irritateFishbotCommand;

/**
 * 
 * 
 * A simple PircBot bot to show a quick and easy way to add multiple commands to
 * a bot. This allows the main bot class to stay clutter free and each command
 * has a private space to store information.
 * 
 * Each command is created and listed inside of the bot constructor. To see how
 * a command is structured, see the included BotCommand file. To see some simple
 * sample commands, check out QuitCommand and TimeCommand.
 * 
 * In order for the bot to know it is being talked to, a prefix is used. Any
 * text listed after the prefix is used to determine the command name.
 * 
 * NOTE: this requires pircbot.jar from www.jibble.org
 * 
 * @author AMcBain ( http://www.asmcbain.net/ ) @ 2009
 * @modified Zolomon @ 2009
 */
public class BloatBot extends PircBot {

	// Store the commands
	private final List<BotCommand> commands;
	
	// Not prefix triggered commands
	private final List<BotCommand> otherCommands;
	//private String channel;
	private Properties config;
	
	// There's a bug in the getArgument() method for when count < 0 and quotation is true, it doesn't 
	// consider the quote as one argument if there are spaces between the quotes.
	private int maxArgs = 3;

	// The character which tells the bot we're talking to it and not anything
	// else.
	private final String prefix = "bloatbot ";

	public BloatBot() {
		config = getProperties("pbdemo.properties");	
		//channel = config.getProperty("channel");
		
		commands = new ArrayList<BotCommand>();

		// Add all commands
		commands.add(new TimeCommand());
		commands.add(new QuitCommand());
		commands.add(new HelloCommand());
		commands.add(new RssCommand());
		commands.add(new FMLCommand());
		commands.add(new CourseCommand());	
		commands.add(new ReciteCommand());
		commands.add(new DivideByZeroCommand());
		commands.add(new ChangeNickCommand());
		commands.add(new DoSomethingPrettyCommand());
		commands.add(new GodNattCommand());
		commands.add(new dcHubCommand());
		commands.add(new BotCommand(){
			
			public String getCommand(){
				return "commands";
			}
			// The method where each BotCommand implementor will handle the event
			public void handleMessage(PircBot bot, String channel, String sender, String message, String[] args){
				for(BotCommand c : commands){
					bot.sendMessage(channel, c.getCommand());
				}
			}
		});
		// DANGEROUS. DOES NOT SCALE!!!!!!!
		otherCommands = new ArrayList<BotCommand>();
		otherCommands.add(new irritateFishbotCommand());
		// Connect to IRC
		setAutoNickChange(true);
		setName(config.getProperty("nick"));
		try {
			connect(config.getProperty("server"));
			joinChannel(config.getProperty("channel"));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void onMessage(String channel, String sender, String login,
			String hostname, String message) {
		
		// DANGEROUS. DOES NOT SCALE!!!!!!!
		for(BotCommand commands : otherCommands){
			if (message.startsWith(commands.getCommand())){
				
		
			commands.handleMessage(this, channel, sender, 
					message.replace(commands.getCommand(), "").trim(), null);
				}
		}
		// Find out if message was for this bot
		if (message.startsWith(prefix)) {
			message = message.replace(prefix, "");
			
			String[] args = getArguments(message, maxArgs);
			
			// Find out the command given (in a very simplistic way) ... this
			// doesn't scale
			for (BotCommand command : commands) {
				// if the message starts with the commands the BotCommand
				// responds to, remove
				// the command from the message and pass the event along to the
				// BotCommand
				if (message.startsWith(command.getCommand())) {
					command.handleMessage(this, channel, sender, 
							message.replace(command.getCommand(), "").trim(), args);
				}
			}
		}
	}

	public void onConnect() {
		String authBot = config.getProperty("authbot");
		String authMessage = config.getProperty("authmessage");
		String hostmask = config.getProperty("hostmask");

		// auth
		sendRawLine("PRIVMSG " + authBot + " :" + authMessage);
		// mask the host (if set)
		if (hostmask != null && hostmask.trim().length() > 0) {
			sendRawLine(hostmask);
		}
	}

	public void onDisconnect() {
		System.exit(0);
	}

	public Properties getProperties(String filepath) {
		Properties config = new Properties();
		try {
			config.load(new FileInputStream(filepath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Error loading config file: pbdemo.properties");
			e.printStackTrace();
			System.exit(0);
		}

		return config;
	}
	
	public String[] getArguments(String input, int count) {
		return getArguments(input, count, true);
	}

	/**
	 * 
	 * Här hamnar alla login events. Vet ej vad super.onJoin gör så jag kallar den för säkerhets skull.
	 * Gör den inget så gör det ju ignet att vi kallar den heller ^^.
	 * 
	 */
	@Override
	public void onJoin(String channel, String sender, String login, String hostname){
		super.onJoin(channel, sender, login, hostname);
		if(sender.equals("respekt") || sender.equals("rspkt"))
			sendMessage(channel, "Respekt "+ sender +"!");
		if(sender.equals("kd35a"))
			sendMessage(channel, "Welcome " +sender);
		if(sender.equals("Zolomon"))
			sendMessage(channel , "Everybody greet Zoiman!");
		if(sender.equals("bloatbot2"))
			sendMessage(channel, "NOOOOOes, there is two of me! Bloatbots sad ._. ");
		if(sender.equals("Ralleballe"))
			sendMessage(channel, "Welcome "+ sender);
		if(sender.equals("bloatbot"))
			sendMessage(channel, "Hello, I arrive!");
		if(sender.equals("Bobbanovich") || hostname.equals("c-83-233-144-112.cust.bredband2.com")){
			sendMessage(channel, "http://kd35a.se/skit/robin.jpg wahaha");
			sendMessage(channel, (char)1 + "ACTION ollar robin!");
			
		}
		if(hostname.equals("c-83-233-152-86.cust.bredband2.com") && !sender.equals("Ralleballe"))
			sendMessage(channel, "Kolla " + sender + " = Ralleballe! :D (http://sv.wikipedia.org/wiki/Balle_(runristare)) ");

	}
	
	protected void onPrivateMessage(String sender, String login, String hostname, String message){
		super.onPrivateMessage(sender, login, hostname, message);
		sendMessage("#d10", sender+" makes me nervous.");
	}
	public String[] getArguments(String input, int count, boolean quotations) {
		// TODO Fix the quotation bug.
		if(count < 0) {			
			return input.split(" ");
		} else {
			String[] args = new String[count];
			int offset = 0;
			int total = 0;
			int next = 0;

			for(int i = 0; i < count && next != -1; i++) {
				next = input.indexOf(" ", offset);

				if(next == -1) {
					args[i] = input.substring(offset);
				} else {
					args[i] = input.substring(offset, next);

					if(quotations && args[i].startsWith("\"")) {
						int quote = input.indexOf('"', offset + 1);
						
						if(quote == -1) {
							args[i] = input.substring(offset + 1);
							next = -1;
						} else {
							args[i] = input.substring(offset + 1, quote);
							if(quote + 1 < input.length() && input.charAt(quote + 1) == ' ') {
								next = quote + 1;
							} else if(quote + 1 == input.length()) {
								next = -1;
							} else {
								next = quote;
							}
						}
					}
				}
				offset = next + 1;
				total++;
			}

			return (total == count)? args : Arrays.copyOfRange(args, 0, total);
		}
	}
}
