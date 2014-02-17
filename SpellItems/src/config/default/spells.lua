require 'SpellLibrary'


function vampire(player, target, block, location)
    if(spell.ItemDamage(player, target, 2)) then
        spell.ItemDamage(player, player, -2)
    end
    
    return true
end

function eatTester(player, target, block, location)
    spell.ItemPotionEffect(player, "5:600:0")
    
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

function poisonEnchant(player, target, block, location)
    spell.ItemPotionEffect(target, "19:60:4")
    
    return true
end