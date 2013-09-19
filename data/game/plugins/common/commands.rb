require 'java'

# Common administrator commands

RuneEmulator::Bootstrap.bind_cmd('window') { |player, args|
	player.interface_set.open_window(args[0].to_i)
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