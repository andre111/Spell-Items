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

--Block
Block = CustomObject:new()

function Block:getLocation()
	return internalLib.getLocation(self)
end

--Location
Location = CustomObject:new()

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

function World:getPlayer(_pos)
	return internalLib.getPlayer(self, _pos)
end