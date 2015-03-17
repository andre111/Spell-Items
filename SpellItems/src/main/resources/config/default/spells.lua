require 'SpellLibrary'


function firestaff(player, target, block, location)
    spell.ItemLeap(player, 4, 1.5, 1, true)

	local suc, loc = player:getLocation()
	effects.CreateParticle(loc, "MOBSPAWNER_FLAMES", 0)
	effects.CreateParticle(loc, "MOBSPAWNER_FLAMES", 0)
	effects.CreateParticle(loc, "MOBSPAWNER_FLAMES", 0)
	effects.CreateParticle(loc, "MOBSPAWNER_FLAMES", 0)
	effects.CreateParticle(loc, "MOBSPAWNER_FLAMES", 0)
	effects.CreateSound(loc, "ARROW_HIT", 1, 0.5)

    return true
end

function vampire(player, target, block, location)
    if(spell.ItemDamage(player, target, 2)) then
        spell.ItemDamage(player, player, -2)
        
        local suc, loc = player:getLocation()
		effects.CreateSound(loc, "BAT_IDLE", 1, 0.5)
    end
    
    return true
end

function eatTester(player, target, block, location)
    spell.ItemPotionEffect(player, "5:600:0")
    
	local suc, loc = player:getLocation()
	effects.CreateSound(loc, "BURP", 1, 0.5)
    
    return true
end

function teleTester(player, target, block, location)
    local success, pPos = player:getLocation()
    local success2, tPos = target:getLocation()
    
    if(success and success2) then
        spell.ItemTeleport(player, tPos)
        spell.ItemTeleport(target, pPos)
        
        return true
    end
    
    return false
end

function stackTesterR(player, target, block, location)
    return spell.ItemEntityStack(player, true, target, 1)
end
function stackTesterL(player, target, block, location)
    return spell.ItemEntityStack(player, false)
end

commandLocs = {}
function commandTesterR(player, target, block, location)
    local suc, uuid = player:getUUID()
	local suc2, loc = block:getLocation()
	if(suc and suc2) then
		if(commandLocs[uuid]==nil) then
			commandLocs[uuid] = {locSaved=false, isRightClick=true, x=0, y=0, z=0}
		end
		
		commandTester(player, uuid, loc, false)
	end
	
	return true
end
function commandTesterL(player, target, block, location)
    local suc, uuid = player:getUUID()
	local suc2, loc = block:getLocation()
	if(suc and suc2) then
		if(commandLocs[uuid]==nil) then
			commandLocs[uuid] = {locSaved=false, isRightClick=true, x=0, y=0, z=0}
		end
		
		commandTester(player, uuid, loc, true)
	end
	
	return true
end

function commandTester(player, uuid, loc, leftclick)
	local suc3, x, y, z = loc:getCoordinates()
	if(suc3) then
		x = math.floor(x)
		y = math.floor(y)
		z = math.floor(z)
		
		if(commandLocs[uuid].locSaved and commandLocs[uuid].isRightClick==leftclick) then
			commandLocs[uuid].locSaved = false
		
			local command = "fill "
			command = command..tostring(commandLocs[uuid].x).." "
			command = command..tostring(commandLocs[uuid].y).." "
			command = command..tostring(commandLocs[uuid].z).." "
			command = command..tostring(x).." "
			command = command..tostring(y).." "
			command = command..tostring(z).." "
			command = command.."minecraft:stone"
			
			spell.ItemCommand(player, false, command)
		else
			commandLocs[uuid].locSaved = true
			commandLocs[uuid].isRightClick = not leftclick
			commandLocs[uuid].x = x
			commandLocs[uuid].y = y
			commandLocs[uuid].z = z
		end
	end
end

function poisonEnchant(player, target, block, location, level, damage)
    spell.ItemPotionEffect(target, "19:60:4")
    
    return true
end

function lifestealEnchant(player, target, block, location, level, damage)
    if(damage>0) then
        spell.ItemDamage(player, player, -1)
    end
    
    return true
end
function blindEnchant(player, target, block, location, level, damage)
    spell.ItemPotionEffect(target, "15:200:1")
    
    return true
end
--double damage
function deathbringerEnchant(player, target, block, location, level, damage)
    if(damage>0) then
        spell.ItemDamage(player, target, damage)
    end
    
    return true
end
function iceaspectEnchant(player, target, block, location, level, damage)
    --slow
    spell.ItemPotionEffect(target, "2:200:1")
	
	local suc, loc = target:getLocation()
	effects.CreateParticle(loc, "STEP_SOUND", 79)
    --freeze
    local maxRand = 10-level
    if(maxRand<1) then 
        maxRand = 0
    end
    
    if(math.random(0, maxRand)==0) then
        spell.ItemPotionEffect(target, "2:200:7")
        spell.ItemPotionEffect(target, "8:200:128")
        spell.ItemPotionEffect(target, "4:200:7")
    end
    
    return true
end

function thunderingblowEnchant(player, target, block, location, level, damage)
    local maxRand = 10-level
    if(maxRand<1) then 
        maxRand = 0
    end
    
    if(math.random(0, maxRand)==0) then
        local success, tPos = target:getLocation()
    
        if(success) then
            effects.CreateLightning(tPos)
            spell.ItemDamage(player, target, 20)
        end
    end
    
    return true
end
function vampireEnchant(player, target, block, location, level, damage)
    if(damage>0) then
        spell.ItemDamage(player, player, -(damage/2))
    end
    
    return true
end