require 'java'

java_import 'net.scapeemulator.game.dialogue.DialogueSet'
java_import 'net.scapeemulator.game.dialogue.HeadAnimation'

RuneEmulator::Utilities.build_dialogue("test") { |builder|
	builder.append_start { |stage|
		stage.on_init { |ctx|
			ctx.open_npc_conversation_dialogue("I hate being an NPC stuck in this world, its insane if you know what I mean right?", 1, HeadAnimation::STERN, true)
		}
	}
}

RuneEmulator::Bootstrap.bind_cmd('test') { |player, args|
	DialogueSet::get("test").display_to(player)
}