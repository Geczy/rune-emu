#  _____                         ______                 
# |  __ \                       |  ____|                
# | |__) |   _ _ __   ___ ______| |__   _ __ ___  _   _ 
# |  _  / | | | '_ \ / _ \______|  __| | '_ ` _ \| | | |
# | | \ \ |_| | | | |  __/      | |____| | | | | | |_| |
# |_|  \_\__,_|_| |_|\___|      |______|_| |_| |_|\__,_|
#  
# Written by SiniSoul, Credits to Evelus Team
# Don't touch this script, kiddies.                                                    

require 'java'

java_import 'net.scapeemulator.game.model.Option'
java_import 'net.scapeemulator.game.model.ExtendedOption'
java_import 'net.scapeemulator.game.model.World'

# The constants for each permission type
ADMINISTRATOR = 2
MODERATOR     = 1
PLAYER        = 0

# The world object list constant.
OBJECT_LIST = World::getWorld().getGroundObjects()

# The mapping of all the options and the respected symbol to access the option.
OPTION = { :one => Option::ONE, :two => Option::TWO, :three => Option::THREE, :four => Option::FOUR, :five => Option::FIVE }

# The mapping of all the extended options and the respected symbol to access the option.
EXTENDED_OPTION = { :one => ExtendedOption::ONE,   :two => ExtendedOption::TWO, :three => ExtendedOption::THREE, :four => ExtendedOption::FOUR, 
		    :five => ExtendedOption::FIVE, :six => ExtendedOption::SIX, :seven => ExtendedOption::SEVEN, :eight => ExtendedOption::EIGHT, 
		    :nine => ExtendedOption::NINE }

# The mapping of all the defined handler types.
HANDLER_TYPES = {}

class HandlerType
	attr_accessor :name, :constructor_params, :dispatcher_params, :local_args, :class_name, :package_prefix, :context_method

	def initialize(name, block)
		@name               = name
		@constructor_params = []
		@dispatcher_params  = []
		@local_args         = []
		@class_name         = ''
		@package_prefix     = ''
		@context_method     = ''
		instance_eval(&block)
		setup
	end

	def formatted_name
		name.to_s.capitalize
	end

	def setup

		# Import the java class for the specified target handler.
		java_import "net.scapeemulator.game.#{@package_prefix}.#{@class_name}"

		# Get the target class to inherit.
		inherit = Class.new(Object.const_get(class_name))

		# Define each of the generated class names.		
		Object.const_set("Generated#{formatted_name}Handler", inherit)
		Object.const_set("#{formatted_name}Context", Class.new)
	end
end

# Public: Helps define a new handler type.
#
# name   - The name of the handler type to define.
# block  - The block to use to assist in initializing the handler type.
#
# Returns nil.
def define_handler_type(name, &block)
	HANDLER_TYPES.store(name, HandlerType.new(name, block))
end

# Public: Binds a handler of the specified type with the parameters and block.
#
# type   - The HandlerType of the handler to bind to the script context.
# params - The parameters for the handler to bind to the script context.
# block  - The block for the handler to use with the instance of the generated handler.
#
# Returns nil.
def bind(type, params={}, &block)

	raise "Unknown type #{type}" if !HANDLER_TYPES.include? type

	handler_type = HANDLER_TYPES[type]

	add_handler(handler_type, create_handler(handler_type, params, block))
end

# Public: Generates a new handler of the specified type with the parameters and block.
#
# type   - The HandlerType of the handler to generate.
# params - The parameters for the handler to generate.
# block  - The block for the handler to use with the instance of the generated handler.
#
# Returns the generated handler.
def create_handler(type, params, block)
	
	klass = Object.const_get("Generated#{type.formatted_name}Handler")
	name  = type.name

	# Fix all the parameters if required.
	case name
	when :btn, :obj

		option_set = name.eql?(:btn) ? EXTENDED_OPTION : OPTION

		# Bind the option to one if the parameter wasnt included
		params.store(:option, :one) if !params.include? :option

		# Update all the parameters to their true value if required.
		params.update(params) { |key, value| if key.eql? :option then option_set[value] else value end }
	when :cmd

		# Set the permission to be administrator if it doesnt already exist in the parameters.
		params.store(:permission, ADMINISTRATOR) if !params.include? :permission
	end

	klass.class_eval do

		define_method(:initialize) do |block, args, *params|
		   	super(*params)
		   	@args  = args
		   	@block = block
		end

		context = Object.const_get("#{type.formatted_name}Context")

		context.class_eval do
			attr_reader *(type.dispatcher_params + type.local_args)

			define_method(:initialize) do |block, values|

				params = type.dispatcher_params + type.local_args

				params.each do |param|
					instance_variable_set("@#{param.to_s}", values[param])
				end		 

				instance_variable_set(:@block, block)					
		 	end

		 	define_method(:dispatch) do
		 		instance_eval &@block
		 	end
		end

		# Lambda to generate a string for all the regular dispatcher parameters.
		args  = -> values { values.map { |value| ":#{value.to_s} => #{value.to_s}" }.join(", ") }

		# Lambda to generate a string for all the local arguments if needed.
		extra = -> values { !values.empty? ? ", #{ values.map { |value| ":#{value.to_s} => @args[:#{value.to_s}]" }.join(", ") }"  : '' }

		# Lambda to generate a string for the condition of dispatching information
		cond  = -> name   { name.eql?(:cmd) ? 'if player.rights >= @args[:permission]' : '' }

		# Define the target abstract method.
		define_method(:handle, eval("-> #{type.dispatcher_params.join(', ')} do
						context.new(@block, #{args[type.dispatcher_params]}#{extra[type.local_args]}).dispatch #{cond[type.name]}
					    end"))
	end

	# Generate the flattened array of parameters that are in the correct order.
	flattened = []

	type.constructor_params.each { |key| flattened << params[key] }

	# Generate the extra arguments from the parameter list.
	keys = case name
		   when :btn then [:id, :component]
		   when :cmd then [:permission]
		   else []
		   end
	
	args = !keys.empty? ? params.select { |key, value| keys.include? key } : {}

	# Create the new handler.
	klass.new(block, args, *flattened)
end

# Public: Adds a handler to the script context.
#
# type    - The HandlerType of the handler to add.
# handler - The generated handler to add.
#
# Returns nil.
def add_handler(type, handler)
	$ctx.send(type.context_method, handler)
end

# Define all of the handler types.

define_handler_type(:btn) do

	# Append each of the dispatcher params
	self.dispatcher_params << :player
	self.dispatcher_params << :dynamic_id

	# Append each of the local arguments.
	self.local_args << :id
	self.local_args << :component

	# Append each of the construction params
	self.constructor_params << :option
	self.constructor_params << :id
	self.constructor_params << :component

	# Set the class name, context method, and package prefix
	self.class_name     = 'ButtonHandler'
	self.context_method = 'add_button_handler'
	self.package_prefix = 'button'
end

define_handler_type(:cmd) do

	# Append each of the dispatcher params
	self.dispatcher_params << :player
	self.dispatcher_params << :args

	# Append each of the construction params
	self.constructor_params << :name

	# Set the class name, context method, and package prefix
	self.class_name     = 'CommandHandler'
	self.context_method = 'add_command_handler'
	self.package_prefix = 'command'
end

define_handler_type(:item_on_item) do

	# Append each of the dispatcher params
	self.dispatcher_params << :player
	self.dispatcher_params << :inventory
	self.dispatcher_params << :to
	self.dispatcher_params << :first
	self.dispatcher_params << :second

	# Append each of the construction params
	self.constructor_params << :first
	self.constructor_params << :second

	# Set the class name, context method, and package prefix
	self.class_name     = 'ItemOnItemHandler'
	self.context_method = 'add_item_on_item_handler'
	self.package_prefix = 'item'
end

define_handler_type(:item) do

	# Append each of the dispatcher params
	self.dispatcher_params << :player
	self.dispatcher_params << :inventory
	self.dispatcher_params << :item
	self.dispatcher_params << :option
	self.dispatcher_params << :context

	# Append each of the construction params
	self.constructor_params << :option

	# Set the class name, context method, and package prefix
	self.class_name     = 'ItemHandler'
	self.context_method = 'add_item_handler'
	self.package_prefix = 'item'
end

define_handler_type(:item_on_obj) do

	# Append each of the dispatcher params
	self.dispatcher_params << :player
	self.dispatcher_params << :inventory
	self.dispatcher_params << :item
	self.dispatcher_params << :obj

	# Append each of the construction params
	self.constructor_params << :item_id
	self.constructor_params << :obj_id

	# Set the class name, context method, and package prefix
	self.class_name     = 'ItemOnObjectHandler'
	self.context_method = 'add_item_on_object_handler'
	self.package_prefix = 'item'
end

define_handler_type(:obj) do

	# Append each of the dispatcher params
	self.dispatcher_params << :player
	self.dispatcher_params << :obj
	self.dispatcher_params << :option
	self.dispatcher_params << :ctx

	# Append each of the construction params
	self.constructor_params << :option

	# Set the class name, context method, and package prefix
	self.class_name     = 'ObjectHandler'
	self.context_method = 'add_object_handler'
	self.package_prefix = 'object'
end

define_handler_type(:player) do

	# Append each of the dispatcher params
	self.dispatcher_params << :player
	self.dispatcher_params << :selected

	# Append each of the construction params
	self.constructor_params << :option

	# Set the class name, context method, and package prefix
	self.class_name     = 'PlayerHandler'
	self.context_method = 'add_player_handler'
	self.package_prefix = 'player'
end

define_handler_type(:npc) do

	# Append each of the dispatcher params
	self.dispatcher_params << :player
	self.dispatcher_params << :npc
	self.dispatcher_params << :option
	self.dispatcher_params << :context

	# Append each of the construction params
	self.constructor_params << :option

	# Set the class name, context method, and package prefix
	self.class_name     = 'NPCHandler'
	self.context_method = 'add_npc_handler'
	self.package_prefix = 'npc'
end