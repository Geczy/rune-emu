package net.scapeemulator.game.command;

import net.scapeemulator.game.model.player.Player;

import java.util.HashMap;
import java.util.Map;

public final class CommandDispatcher {

    private final Map<String, CommandHandler> handlers = new HashMap<>();

    public CommandDispatcher() {
        bind(new TeleportCommandHandler());
        bind(new EmptyCommandHandler());
        bind(new PositionCommandHandler());
        bind(new MasterCommandHandler());
    }

    public void bind(CommandHandler handler) {
        handlers.put(handler.getName(), handler);
    }
    
    public void unbindAll() {
        handlers.clear();
    }

    public void handle(Player player, String command) {
        String[] parts = command.split(" ");

        String name = parts[0];
        String[] arguments = new String[parts.length - 1];
        System.arraycopy(parts, 1, arguments, 0, arguments.length);

        CommandHandler handler = handlers.get(name);
        if (handler != null) {
            handler.handle(player, arguments);
        }
    }
}
