require 'java'

java_import 'net.scapeemulator.game.GameServer'
java_import 'net.scapeemulator.game.model.World'
java_import 'net.scapeemulator.game.model.object.ObjectType'
java_import 'net.scapeemulator.game.model.mob.Animation'
java_import 'net.scapeemulator.game.model.player.Item'
java_import 'net.scapeemulator.game.model.player.SkillSet'
# Common administrator commands

RuneEmulator::Bootstrap.bind_cmd('window') { |player, args|
  player.interface_set.open_window(args[0].to_i)
}

RuneEmulator::Bootstrap.bind_cmd('item') { |player, args|
  amount = 1
  amount = args[1].to_i if args.length > 1
  player.get_inventory.add(Item.new(args[0].to_i, amount));
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
  rot = 0
  rot = args[1].to_i if args.length > 1
  RuneEmulator::OBJECT_LIST.put(player.position, args[0].to_i, rot, ObjectType::PROP)
}

RuneEmulator::Bootstrap.bind_cmd('anim') { |player, args|
  player.playAnimation(Animation.new(args[0].to_i))
}

RuneEmulator::Bootstrap.bind_cmd('reload') { |player, args|
  GameServer::getInstance().reloadPlugins()
}

RuneEmulator::Bootstrap.bind_cmd('master') { |player, args|
  skills = player.get_skill_set
  for id in 0...Skill::AMOUNT_SKILLS
    skills.add_experience(id, SkillSet::MAXIMUM_EXPERIENCE)
  end
}

RuneEmulator::Bootstrap.bind_cmd('empty') { |player, args|
  player.get_inventory.empty
}

RuneEmulator::Bootstrap.bind_cmd('tele') { |player, args|
  h = player.get_position.height
  h = args[2].to_i if args.length > 2
  player.teleport(Position.new(args[0].to_i, args[1].to_i, h));
}

RuneEmulator::Bootstrap.bind_cmd('pos') { |player, args|
  player.send_message(player.position.to_string);
}