# TODO: May not be valid to set the type in the way I have

java_import 'net.scapeemulator.game.model.World'
java_import 'net.scapeemulator.game.model.player.action.OpenDoorAction'
java_import 'net.scapeemulator.game.model.object.ObjectOrientation'
java_import 'net.scapeemulator.game.model.object.GroundObjectListenerAdapter'
java_import 'net.scapeemulator.game.model.object.ObjectType'
java_import 'net.scapeemulator.game.model.Position'
java_import 'net.scapeemulator.game.model.Option'
java_import 'net.scapeemulator.game.model.Door'

module RuneEmulator
	class Doors

		# States for if the door is opened or closed
		OPENED = 0
		CLOSED = 1

		# Each of the orientations
		ORIENTATION_ONE   = 1
		ORIENTATION_TWO   = 2
		ORIENTATION_THREE = 3
		ORIENTATION_FOUR  = 4

		# The mapping for all the single doors
		SINGLE_DOORS = {}

		# The mapping for all the double doors
		DOUBLE_DOORS = {}

		# The mapping for all the gates
		GATES = {}

		# The mapping for all the doors registered
		DOORS = {}

		# The array of objects to add to the object list after a refresh
		OBJECTS = []

		class << self
			def bind_door(opened_obj_id, closed_obj_id)
				DoorData.new(opened_obj_id, closed_obj_id).bind
			end

			def bind_double_door(opened_ldoor_id, opened_rdoor_id, closed_ldoor_id, closed_rdoor_id)
				DoubleDoorData.new(opened_ldoor_id, opened_rdoor_id, closed_ldoor_id, closed_rdoor_id).bind
			end

			def bind_gate(opened_lgate_id, opened_rgate_id, closed_lgate_id, closed_rgate_id)
				GateData.new(opened_lgate_id, opened_rgate_id, closed_lgate_id, closed_rgate_id).bind
			end

			def bind_handlers()
				bind :obj do
					if option.eql?("open")
						if DOORS.include?(door.position)
							player.start_action(OpenDoorAction.new(player, door.position, DOORS[door.position], true))
							context.stop
						end
					end
				end

				bind :obj do
					if option.eql?("close")
						if DOORS.include?(door.position)
							player.start_action(OpenDoorAction.new(player, door.position, DOORS[door.position], false))
							context.stop
						end
					end
				end
			end

			def create_door(object)
				data = SINGLE_DOORS[object.id]

				raise "Data does not exist for door, id: #{object.id}" if data == nil

				state       = data.state(object.id)
				orientation = data.get_orientation(object.rotation, state)
				origin      = data.get_origin(object.position, object.rotation, state)

				objects = {}

				[ :opened, :closed ].each do |type|
					object = objects[type] = data.get_object(origin, orientation, type)
					OBJECTS << object
				end

				SingleTileDoor.new(objects[:opened], objects[:closed]).bind
			end

			def create_double_door(object)
				return if DOORS.include?(object.position)

				data = DOUBLE_DOORS[object.id]

				raise "Data does not exist for double door, id: #{object.id}" if data == nil

				state       = data.state(object.id)
				orientation = data.get_orientation(object.rotation, state)
				origin      = data.get_origin(object.position, object.rotation, state)

				objects = {}

				[ :opened_left, :opened_right, :closed_left, :closed_right ].each { |type|
					object = objects[type] = data.get_object(origin, orientation, type)
					OBJECTS << object
				}

				DoubleTileDoor.new(objects[:opened_left], objects[:opened_right], objects[:closed_left], objects[:closed_right]).bind
			end

			def create_gate(object)
				return if DOORS.include?(object.position)

				data = GATES[object.id]

				raise "Data does not exist for gate, id: #{object.id}" if data == nil

				state       = data.state(object.id)
				orientation = data.get_orientation(object.rotation, state)
				origin      = data.get_origin(object.position, object.rotation, state)

				objects = {}

				[ :opened_left, :opened_right, :closed_left, :closed_right ].each do |type|
					object = objects[type] = data.get_object(origin, orientation, type)
					OBJECTS << object
				end

				DoubleTileDoor.new(objects[:opened_left], objects[:opened_right], objects[:closed_left], objects[:closed_right]).bind
			end

			def refresh()
				OBJECT_LIST.fire_all_events(DoorObjectListener.new)

				OBJECTS.each { |object|
					object.register
				}
			end
		end

		class ObjectProxy
			attr_reader :id, :position, :rotation, :type

			def initialize(args)
				case args.size
				when 2
					raise 'Target cannot be null!' if args[:target] == nil
					@target   = args[:target]
					@position = args[:position]
				when 4
					@id       = args[:id]
					@position = args[:position]
					@rotation = args[:rotation]
					@type     = args[:type]
				else
					raise 'Invalid constructor sent to object proxy'
				end
			end

			def register()
				if @target == nil
					@target = OBJECT_LIST.put(@position, @id, @rotation, @type)
					@target.hide
				end
			end

			def hide()
				@target.hide
			end

			def reveal()
				@target.reveal
			end
		end

		class DoorData

			# Each of the orientations
			# Orientation One: North, Orientation Two: South, Orientation Three: East, Orientation Four: West
			ORIENTATIONS = { 
							 ORIENTATION_ONE   => { :closed => { :x => 0, :y => 0, :rotation => 1 }, :opened => { :x =>  0, :y =>  1, :rotation => 2 } }, 
			                 ORIENTATION_TWO   => { :closed => { :x => 0, :y => 0, :rotation => 3 }, :opened => { :x =>  0, :y => -1, :rotation => 0 } }, 
			                 ORIENTATION_THREE => { :closed => { :x => 0, :y => 0, :rotation => 2 }, :opened => { :x =>  1, :y =>  0, :rotation => 3 } }, 
			                 ORIENTATION_FOUR  => { :closed => { :x => 0, :y => 0, :rotation => 0 }, :opened => { :x => -1, :y =>  0, :rotation => 1 } } 
			               }

			def initialize(opened_obj_id, closed_obj_id)
				@opened_obj_id = opened_obj_id
				@closed_obj_id = closed_obj_id
			end

			def get_origin(position, rotation, state)
				
				delta = { :x => 0, :y => 0 }

				case state
				when OPENED
					case rotation
					when ObjectOrientation::NORTH
						delta[:x] =  1
					when ObjectOrientation::SOUTH
						delta[:x] = -1
					when ObjectOrientation::EAST
						delta[:y] = -1	
					when ObjectOrientation::WEST
						delta[:y] =  1
					end
				end

				Position.new(position.x + delta[:x], position.y + delta[:y], position.height)
			end

			def get_orientation(rotation, state)
				case state
				when OPENED				
					case rotation
					when ObjectOrientation::NORTH
						ORIENTATIONS[ORIENTATION_FOUR]
					when ObjectOrientation::SOUTH
						ORIENTATIONS[ORIENTATION_THREE]			
					when ObjectOrientation::EAST
						ORIENTATIONS[ORIENTATION_ONE]
					when ObjectOrientation::WEST
						ORIENTATIONS[ORIENTATION_TWO]
					end
				when CLOSED
					case rotation
					when ObjectOrientation::NORTH
						ORIENTATIONS[ORIENTATION_ONE]
					when ObjectOrientation::SOUTH
						ORIENTATIONS[ORIENTATION_TWO]					
					when ObjectOrientation::EAST
						ORIENTATIONS[ORIENTATION_THREE]
					when ObjectOrientation::WEST
						ORIENTATIONS[ORIENTATION_FOUR]
					end
				end
			end

			def get_object(origin, orientation, type)
				position  = Position.new(origin.x + orientation[type][:x], origin.y + orientation[type][:y], origin.height)
				object_id = get_id(type)		

				if OBJECT_LIST.contains(object_id, position)
					args = { :target => OBJECT_LIST.get(object_id, position), :position => position }
					ObjectProxy.new(args)
				else
					args = { :id => object_id, :position => position, :rotation => orientation[type][:rotation], :type => ObjectType::TYPE_0 }
					ObjectProxy.new(args)
				end
			end

			def state(object_id)
				{ @opened_obj_id => OPENED, @closed_obj_id => CLOSED }.fetch(object_id)
			end

			def get_id(type)
				case type
				when :opened
					@opened_obj_id
				when :closed
					@closed_obj_id
				end
			end

			def bind()
				SINGLE_DOORS[@opened_obj_id] = self
				SINGLE_DOORS[@closed_obj_id] = self
			end
		end

		class DoubleDoorData

			# Each of the orientations
			# Orientation One: North, Orientation Two: South, Orientation Three: East, Orientation Four: West
			ORIENTATIONS = { 
							 ORIENTATION_ONE   => { 
							 	                    :closed_left  => { :x =>  0, :y => 0, :rotation => 1 }, :opened_left  => { :x =>  0, :y =>  1, :rotation => 0 },
												    :closed_right => { :x =>  1, :y => 0, :rotation => 1 }, :opened_right => { :x =>  1, :y =>  1, :rotation => 2 } 
												  },

			                 ORIENTATION_TWO   => {
			                 	                    :closed_left  => { :x =>  0, :y => 0, :rotation => 3 }, :opened_left  => { :x =>  0, :y =>  -1, :rotation => 2 },
												    :closed_right => { :x => -1, :y => 0, :rotation => 3 }, :opened_right => { :x => -1, :y =>  -1, :rotation => 0 } 
												  },

			                 ORIENTATION_THREE => {
			                 	                    :closed_left  => { :x =>  0, :y =>  0, :rotation => 2 }, :opened_left  => { :x =>  1, :y =>   0, :rotation => 1 },
												    :closed_right => { :x =>  0, :y => -1, :rotation => 2 }, :opened_right => { :x =>  1, :y =>  -1, :rotation => 3 } 
												  },

			                 ORIENTATION_FOUR  => {
			                 	                    :closed_left  => { :x =>  0, :y =>  0, :rotation => 0 }, :opened_left  => { :x => -1, :y =>   0, :rotation => 3 },
												    :closed_right => { :x =>  0, :y =>  1, :rotation => 0 }, :opened_right => { :x => -1, :y =>   1, :rotation => 1 } 
												  } 
			               }

			def initialize(opened_ldoor_obj_id, opened_rdoor_obj_id, closed_ldoor_obj_id, closed_rdoor_obj_id)
				@opened_ldoor_obj_id = opened_ldoor_obj_id
				@opened_rdoor_obj_id = opened_rdoor_obj_id
				@closed_ldoor_obj_id = closed_ldoor_obj_id
				@closed_rdoor_obj_id = closed_rdoor_obj_id
			end

			def get_object(origin, orientation, type)
				position  = Position.new(origin.x + orientation[type][:x], origin.y + orientation[type][:y], origin.height)
				object_id = get_id(type)		

				if OBJECT_LIST.contains(object_id, position)
					args = { :target => OBJECT_LIST.get(object_id, position), :position => position }
					ObjectProxy.new(args)
				else
					args = { :id => object_id, :position => position, :rotation => orientation[type][:rotation], :type => ObjectType::TYPE_0 }
					ObjectProxy.new(args)
				end
			end

			def get_orientation(rotation, state)
				case state
				when OPENED				
					case rotation
					when ObjectOrientation::NORTH
						ORIENTATIONS[ORIENTATION_THREE]
					when ObjectOrientation::SOUTH
						ORIENTATIONS[ORIENTATION_TWO]			
					when ObjectOrientation::EAST
						ORIENTATIONS[ORIENTATION_FOUR]
					when ObjectOrientation::WEST
						ORIENTATIONS[ORIENTATION_ONE]
					end
				when CLOSED
					case rotation
					when ObjectOrientation::NORTH
						ORIENTATIONS[ORIENTATION_ONE]
					when ObjectOrientation::SOUTH
						ORIENTATIONS[ORIENTATION_TWO]					
					when ObjectOrientation::EAST
						ORIENTATIONS[ORIENTATION_THREE]
					when ObjectOrientation::WEST
						ORIENTATIONS[ORIENTATION_FOUR]
					end
				end
			end

			def get_origin(position, rotation, state)
				delta = { :x => 0, :y => 0 }

				case state
				when OPENED
					case rotation
					when ObjectOrientation::NORTH
						delta[:x] = -1
					when ObjectOrientation::SOUTH
						delta[:x] =  1
					when ObjectOrientation::EAST
						delta[:y] =  1	
					when ObjectOrientation::WEST
						delta[:y] = -1
					end
				end

				Position.new(position.x + delta[:x], position.y + delta[:y], position.height)
			end

			def get_id(type)
				case type
				when :opened_left
					@opened_ldoor_obj_id
				when :opened_right
					@opened_rdoor_obj_id
				when :closed_left
					@closed_ldoor_obj_id
				when :closed_right
					@closed_rdoor_obj_id
				end
			end

			def state(object_id)
				{ @opened_ldoor_obj_id => OPENED, @opened_rdoor_obj_id => OPENED, 
			      @closed_ldoor_obj_id => CLOSED, @closed_rdoor_obj_id => CLOSED }.fetch(object_id)
			end

			def bind()
				DOUBLE_DOORS[@opened_ldoor_obj_id] = self
				DOUBLE_DOORS[@closed_ldoor_obj_id] = self
			end
		end

		class GateData

			# Each of the orientations
			# Orientation One: North, Orientation Two: South, Orientation Three: East, Orientation Four: West
			ORIENTATIONS = { 
							 ORIENTATION_ONE   => { 
							 	                    :closed_left  => { :x =>   0, :y => 0, :rotation => 1 }, :opened_left  => { :x =>  0, :y =>  1, :rotation => 0 },
												    :closed_right => { :x =>   1, :y => 0, :rotation => 1 }, :opened_right => { :x =>  0, :y =>  2, :rotation => 0 } 
												  },

			                 ORIENTATION_TWO   => {
			                 	                    :closed_left  => { :x =>   0, :y => 0, :rotation => 3 }, :opened_left  => { :x =>  0, :y =>  -1, :rotation => 2 },
												    :closed_right => { :x =>  -1, :y => 0, :rotation => 3 }, :opened_right => { :x =>  0, :y =>  -2, :rotation => 2 } 
												  },

			                 ORIENTATION_THREE => {
			                 	                    :closed_left  => { :x =>  0, :y =>   0, :rotation => 2 }, :opened_left  => { :x =>  1, :y =>   0, :rotation => 1 },
												    :closed_right => { :x =>  0, :y =>  -1, :rotation => 2 }, :opened_right => { :x =>  2, :y =>   0, :rotation => 1 } 
												  },

			                 ORIENTATION_FOUR  => {
			                 	                    :closed_left  => { :x =>  0, :y =>  0, :rotation => 0 }, :opened_left  => { :x => -1, :y =>   0, :rotation => 3 },
												    :closed_right => { :x =>  0, :y =>  1, :rotation => 0 }, :opened_right => { :x => -2, :y =>   0, :rotation => 3 } 
												  } 
			               }

			def initialize(opened_lgate_obj_id, opened_rgate_obj_id, closed_lgate_obj_id, closed_rgate_obj_id)
				@opened_lgate_obj_id = opened_lgate_obj_id
				@opened_rgate_obj_id = opened_rgate_obj_id
				@closed_lgate_obj_id = closed_lgate_obj_id
				@closed_rgate_obj_id = closed_rgate_obj_id
			end

			def get_object(origin, orientation, type)
				position  = Position.new(origin.x + orientation[type][:x], origin.y + orientation[type][:y], origin.height)
				object_id = get_id(type)		

				if OBJECT_LIST.contains(object_id, position)
					args = { :target => OBJECT_LIST.get(object_id, position), :position => position }
					ObjectProxy.new(args)
				else
					args = { :id => object_id, :position => position, :rotation => orientation[type][:rotation], :type => ObjectType::TYPE_0 }
					ObjectProxy.new(args)
				end
			end

			def get_orientation(rotation, state)
				case state
				when OPENED				
					case rotation
					when ObjectOrientation::NORTH
						ORIENTATIONS[ORIENTATION_THREE]
					when ObjectOrientation::SOUTH
						ORIENTATIONS[ORIENTATION_FOUR]			
					when ObjectOrientation::EAST
						ORIENTATIONS[ORIENTATION_TWO]
					when ObjectOrientation::WEST
						ORIENTATIONS[ORIENTATION_ONE]
					end
				when CLOSED
					case rotation
					when ObjectOrientation::NORTH
						ORIENTATIONS[ORIENTATION_ONE]
					when ObjectOrientation::SOUTH
						ORIENTATIONS[ORIENTATION_TWO]					
					when ObjectOrientation::EAST
						ORIENTATIONS[ORIENTATION_THREE]
					when ObjectOrientation::WEST
						ORIENTATIONS[ORIENTATION_FOUR]
					end
				end
			end

			def get_origin(position, rotation, state)
				delta = { :x => 0, :y => 0 }

				case state
				when OPENED
					case rotation
					when ObjectOrientation::NORTH
						delta[:x] = -1
					when ObjectOrientation::SOUTH
						delta[:x] =  1
					when ObjectOrientation::EAST
						delta[:y] =  1	
					when ObjectOrientation::WEST
						delta[:y] = -1
					end
				end

				Position.new(position.x + delta[:x], position.y + delta[:y], position.height)
			end

			def get_id(type)
				case type
				when :opened_left
					@opened_lgate_obj_id
				when :opened_right
					@opened_rgate_obj_id
				when :closed_left
					@closed_lgate_obj_id
				when :closed_right
					@closed_rgate_obj_id
				end
			end

			def state(object_id)
				{ @opened_lgate_obj_id => OPENED, @opened_rgate_obj_id => OPENED, 
			      @closed_lgate_obj_id => CLOSED, @closed_rgate_obj_id => CLOSED }.fetch(object_id)
			end

			def bind()
				GATES[@opened_lgate_obj_id] = self
				GATES[@closed_lgate_obj_id] = self
			end
		end

		class SingleTileDoor < Door
			def initialize(opened_door, closed_door)
				super()
				@opened_door = opened_door
				@closed_door = closed_door
			end

			def open()
				@opened_door.reveal
				@closed_door.hide
			end

			def close()
				@opened_door.hide
				@closed_door.reveal
			end

			def bind()
				DOORS[@opened_door.position] = self
				DOORS[@closed_door.position] = self
			end
		end

		class DoubleTileDoor < Door
			def initialize(opened_left, opened_right, closed_left, closed_right)
				super()
				@opened_left  = opened_left
				@opened_right = opened_right
				@closed_left  = closed_left
				@closed_right = closed_right
			end

			def open()
				@opened_left.reveal
				@opened_right.reveal
				@closed_left.hide
				@closed_right.hide
			end

			def close()
				@opened_left.hide
				@opened_right.hide
				@closed_left.reveal
				@closed_right.reveal
			end

			def bind()
				DOORS[@opened_left.position]  = self
				DOORS[@opened_right.position] = self
				DOORS[@closed_left.position]  = self
				DOORS[@closed_right.position] = self
			end
		end

		class DoorObjectListener < GroundObjectListenerAdapter
			def groundObjectAdded(object)
				if SINGLE_DOORS.include?(object.id)
					Doors.create_door(object)
				elsif DOUBLE_DOORS.include?(object.id)
					Doors.create_double_door(object)
				elsif GATES.include?(object.id)
					Doors.create_gate(object)
				end
			end
		end
	end
end

RuneEmulator::Doors.bind_door(36848, 36846)
RuneEmulator::Doors.bind_double_door(37000, 37003, 36999, 37002)
RuneEmulator::Doors.bind_gate(36912, 36914, 36913, 36915)
RuneEmulator::Doors.bind_handlers()
RuneEmulator::Doors.refresh()