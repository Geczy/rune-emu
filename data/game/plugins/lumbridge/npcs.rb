require 'java'

java_import 'net.scapeemulator.game.dialogue.HeadAnimation'
java_import 'net.scapeemulator.game.dialogue.DialogueContext'
java_import 'net.scapeemulator.game.model.player.action.StartDialogueAction'
java_import 'net.scapeemulator.game.model.Position'

MEN_AND_WOMEN_TYPES = [ 1 ]

RuneEmulator::Utilities.create_npc(:normal, { :type => 1 }) { |npc|
	npc.set_position Position.new(3222, 3222)
}

# Bind the conversation option for all of the lumbridge NPCs
RuneEmulator::Bootstrap.bind_npc_option(Option::ONE) { |player, npc, option, context|
	if option.eql?('talk-to')
		if MEN_AND_WOMEN_TYPES.include?(npc.type)
			player.start_action StartDialogueAction.new(player, npc, RuneEmulator::Utilities.get_dialogue(:lumbridge_man_woman))
			context.stop
		end
	end
}

# Build the man and women dialogue
RuneEmulator::Utilities.build_dialogue(:lumbridge_man_woman) { |builder|

	# Append the start of the dialogue
	builder.append_start { |stage|

		# On initialization send the player with a greeting
		stage.on_init { |context|
			context.open_player_conversation_dialogue([ "Salutations!", "Hello there.", "Greetings." ].sample, HeadAnimation::HAPPY, true)
		}

		# On input just send the dialogue to the next stage
		stage.on_option { |context|
			context.set_stage('response')
		}
	}

	# Append the response of the dialogue	
	builder.append_stage('response') { |stage|

		# On initialization send the player the response text
		stage.on_init { |context|
			context.open_npc_conversation_dialogue("Sorry, I prefer not to talk to strangers.", DialogueContext::CURRENT_TARGET, HeadAnimation::HAPPY, true)
		}

		# Stop the dialogue on any sort of input
		stage.on_option { |context|
			context.stop
		}
	}
}