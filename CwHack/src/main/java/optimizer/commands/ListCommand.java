package optimizer.commands;

import optimizer.Client;
import optimizer.command.Command;
import optimizer.command.exception.CommandException;
import optimizer.command.exception.CommandSyntaxException;
import optimizer.utils.ChatUtils;

public class ListCommand extends Command
{

	public ListCommand()
	{
		super("list", "List all available features", new String[]{});
	}

	@Override
	public void execute(String[] command) throws CommandException
	{
		if (command.length != 0)
			throw new CommandSyntaxException("argument number not matching");
		for (String feature : Client.CWHACK.getFeatures().getAllFeatureNames())
		{
			ChatUtils.info(Client.CWHACK.getFeatures().getFeature(feature).getName());
		}
	}
}
