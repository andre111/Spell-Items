require 'InternalLibrary'


--Generic Custom Object
CustomObject = {}

function CustomObject:new(_internal)
	newObj = {internal = _internal}
	self.__index = self
	return setmetatable(newObj, self)
end

function CustomObject:getInternal()
	return self.internal
end

--Entity
Entity = CustomObject:new()

function Entity:getLocation()
	return internalLib.getLocation(self)
end

function Entity:getSpawn()
	return internalLib.getSpawn(self)
end

function Entity:getLooking(_range, _pathable)
	_range = _range or 50
	_pathable = _pathable or false

	return internalLib.getLooking(self, _range, _pathable)
end

function Entity:getHealth()
	return internalLib.getHealth(self)
end

function Entity:getFoodLevel()
	return internalLib.getFoodLevel(self)
end

function Entity:getSaturation()
	return internalLib.getSaturation(self)
end

function Entity:getGameMode()
	return internalLib.getGameMode(self)
end

function Entity:isPlayer()
	return internalLib.isPlayer(self)
end

--Block
Block = CustomObject:new()

function Block:getLocation()
	return internalLib.getLocation(self)
end

function Block:setType(_type, _data)
	_data = _data or 0

	return internalLib.setType(self, _type, _data)
end

--Location
Location = CustomObject:new()

function Location:getBlock()
	return internalLib.getBlock(self)
end

function Location:getWorld()
	return internalLib.getWorld(self)
end

--World
World = CustomObject:new()

function World:getTime()
	return internalLib.getTime(self)
end

function World:getSpawn()
	return internalLib.getSpawn(self)
end

function World:getPlayerCount()
	return internalLib.getPlayerCount(self)
end

function World:getPlayer(_n)
	return internalLib.getPlayer(self, _n)
end

function World:setTime(_time)
	return internalLib.setTime(self, _time)
end