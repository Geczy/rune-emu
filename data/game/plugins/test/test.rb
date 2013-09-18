require 'java'

java_import 'net.scapeemulator.game.dialogue.DialogueSet'
java_import 'net.scapeemulator.game.dialogue.HeadAnimation'

RuneEmulator::Utilities.build_dialogue("test") { |builder|
	builder.append_start { |stage|
		stage.on_init { |ctx|
			ctx.open_player_conversation_dialogue("Tounge my fartbox pls.", HeadAnimation::STERN, true)
		}
	}
}

RuneEmulator::Bootstrap.bind_cmd('test') { |player, args|
	DialogueSet::get("test").display_to(player)
}