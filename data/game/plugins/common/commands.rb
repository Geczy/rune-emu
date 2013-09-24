require 'java'

java_import 'net.scapeemulator.game.GameServer'
java_import 'net.scapeemulator.game.model.World'
java_import 'net.scapeemulator.game.model.object.ObjectType'
java_import 'net.scapeemulator.game.model.mob.Animation'
java_import 'net.scapeemulator.game.model.player.Item'
# Common administrator commands

RuneEmulator::Bootstrap.bind_cmd('window') { |player, args|
  player.interface_set.open_window(args[0].to_i)
}

RuneEmulator::Bootstrap.bind_cmd('item') { |player, args|
  amount = 1
  amount = args[1].to_i if args.length > 1
  player.getInventory().add(Item.new(args[0].to_i, amount));
}

RuneEmulator::Bootstrap.bind_cmd('chatbox') { |player, args|
  player.interface_set.open_chatbox(args[0].to_i)
}

RuneEmulator::Bootstrap.bind_cmd('inventory') { |player, args|
  player.interface_set.open_inventory(args[0].to_i)
}

RuneEmulator::Bootstrap.bind_cmd('text') { |player, args|
  player.set_interface_text(args[0].to_i, args[1].to_i, args[2])
}

RuneEmulator::Bootstrap.bind_cmd('bitstate') { |player, args|
  player.state_set.set_bit_state(args[0].to_i, args[1].to_i)
}

RuneEmulator::Bootstrap.bind_cmd('state') { |player, args|
  player.state_set.set_state(args[0].to_i, args[1].to_i)
}

RuneEmulator::Bootstrap.bind_cmd('obj') { |player, args|
  World::getWorld().getGroundObjects().put(player.position, args[0].to_i, args[1].to_i, ObjectType::PROP)
}

RuneEmulator::Bootstrap.bind_cmd('anim') { |player, args|
  player.playAnimation(Animation.new(args[0].to_i))
}

RuneEmulator::Bootstrap.bind_cmd('reload') { |player, args|
  GameServer::getInstance().reloadPlugins()
}