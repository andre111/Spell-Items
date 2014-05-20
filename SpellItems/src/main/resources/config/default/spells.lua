require 'SpellLibrary'


function firestaff(player, target, block, location)
    spell.ItemLeap(player, 4, 1.5, 1, true)
    utils.CreateEffect(player, "Caster")

    return true
end

function vampire(player, target, block, location)
    if(spell.ItemDamage(player, target, 2)) then
        spell.ItemDamage(player, player, -2)
        
        utils.CreateEffect(player, "Caster")
    end
    
    return true
end

function eatTester(player, target, block, location)
    spell.ItemPotionEffect(player, "5:600:0")
    utils.CreateEffect(player, "Caster")
    
    return true
end

function teleTester(player, target, block, location)
    local success, pPos = spell.ItemVariableSet("playerPos", player)
    local success2, tPos = spell.ItemVariableSet("playerPos", target)
    
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

function poisonEnchant(player, target, block, location, level, damage)
    spell.ItemPotionEffect(target, "19:60:4")
    
    return true
end
--WIP(Enchantments not implemented yet->items.yml)
--http://dev.bukkit.org/bukkit-plugins/custom-enchantments/pages/all-enchantments/
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
    --freeze
    local maxRand = 10-level
    if(maxRand<1) maxRand = 0
    
    if(math.random(0, maxRand)==0) then
        spell.ItemPotionEffect(target, "2:200:7")
        spell.ItemPotionEffect(target, "8:200:128")
        spell.ItemPotionEffect(target, "4:200:7")
    end
    
    return true
end

function thunderingblowEnchant(player, target, block, location, level, damage)
    local maxRand = 10-level
    if(maxRand<1) maxRand = 0
    
    if(math.random(0, maxRand)==0) then
        local success, tPos = spell.ItemVariableSet("playerPos", target)
    
        if(success) then
            effects.CreateLightning(tPos)
            spell.ItemDamage(player, target, 10)
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