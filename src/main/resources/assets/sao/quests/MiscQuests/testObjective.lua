local step = 0 -- Sets the step location to default which is 0

local questName = "TestQuest" -- Sets our Name of our Test Quest

local questID = 1429 -- Sets the ID of our Quest(Note: If a conflicting Quest was found during validation, it will ignore it!)

local EventType = luajava.bindClass("io.github.bfox1.SwordArtOnline.api.quest.EventType") -- We are telling LuaJ to bind this to our script so it makes the Script aware of our EventTYpe. otherwise it would not know where to get EventType.

local type = EventType.ITEMPICKUP -- our initialization of this quests default and first Step Type.

function getStepLocation()
 return step
end

function getQuestName()
 return questName
end

function getQuestID()
 return questID
end

function getEventType()
 return type
end

function validate(manager)
  if step ~= 0 then
   return false
  end
  if manager:containsQuest(questID) then
   return false
  end
  if questName == "" then
   return false
  end
  return true
end

function onItemPickupEvent(event, cr)
	if step == 0 then
		if event:getItemID() == cr:getItemByName("Elucidator"):getItemID() then
			step = 1
			cr:sendPlayerMessage("This step has been completed")
			return true
		end
	end
	return false
end

