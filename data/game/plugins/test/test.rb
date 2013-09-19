require 'java'

java_import 'net.scapeemulator.game.model.Option'

RuneEmulator::Bootstrap.bind_npc_option(Option::ONE) { |npc, context|
	p 'HURR'
}