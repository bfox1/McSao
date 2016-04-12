package co.uk.silvania.rpgcore.exampleskills;

import co.uk.silvania.rpgcore.RPGCore;
import co.uk.silvania.rpgcore.skills.SkillLevelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SkillLevelJump extends SkillLevelBase implements IExtendedEntityProperties {
	
	//Some methods require static, while static breaks others.
	//As such, you should keep this here (it should be in every class that extends SkillLevelBase)
	public static String staticSkillId; 
	
	public SkillLevelJump(EntityPlayer player, String skillID) {
		super(skillID);
		//skillId -must- be unique! I'm not as clever as old item ID's from 1.6 and before.
		//If your skillId clashes with that of another mod, it's crash time.
		//Maybe make it configurable, or prefix your name or modid. It's hidden from users - just used for data storage.
		//(Although, this is also used for the give XP command; so some users might see it.)
		staticSkillId = skillID;
		
		//Leave this alone, it's used for storing your XP. You need it, but you shouldn't change it.
		//Unless, for some weird reason you want them to start with more than 0.
		this.xp = 0;

		
		//The colour of the XP bar, as an RGBA integer. Do not use greys or black as they'll blend with the bar's texture too much.
		//Not currently implemented as XP bars are not added yet; but it will be soon. 
		//xpColor = 0846786;

	}
	
	//This registers your skill with the player.
	//*** REQUIRED ***
	public static final void register(EntityPlayer player) {
		player.registerExtendedProperties(SkillLevelJump.staticSkillId, new SkillLevelJump(player, staticSkillId));
	}

	//This saves data to the player regarding your skill. Usually you'll just save XP
	//However if your skill has a cooldown or something, you may want to save that here too.
	//*** REQUIRED ***
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setFloat(skillId + "xp", xp);
		compound.setTag(skillId, nbt);
	}

	//Loads it back - see save above.
	//*** REQUIRED ***
	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound nbt = (NBTTagCompound) compound.getTag(skillId);
		xp = nbt.getFloat(skillId + "xp");		
	}

	//Called when your constructor is called. You don't need anything here.
	//*** REQUIRED ***
	@Override public void init(Entity entity, World world) {}
	
	//Can either go in this class (Register it as a EventHandler if so!) or in your EventHandler class.
	//This applies the skill to the entity when they are created.
	//*** REQUIRED ***
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer) {
			event.entity.registerExtendedProperties(skillId, new SkillLevelJump((EntityPlayer)event.entity, skillId));
		}
	}
	
	
	//Here, open the GUI which contains details about your skill (eg level unlocks, description and so on)
	//I highly suggest you straight up copy the ones from this example mod. Please, steal the code.
	//You don't have to - but if you do it keeps everything uniform within the mod and looks nice.
	//Plus it has fancy scrolling stuff, and who doesn't want that?
	//It's not TOTALLY required and the mod will function about it but you'll make players cry if you don't have it.
	public void openGui() {
		
	}
	
	//This is an example of a skill in action. In this case, every time the player jumps
	//they are awarded a single XP point. How nice!
	//You can add XP in almost any way you can imagine - All you need is an instance of EntityPlayer.
	//use the get method as shown below, then call addXP(int, player). Number can be anything you like, so long as it's an int.
	//The mod will automatically check if the player has the skill equipped, and will only award XP if they do - so don't worry about that.
	//If you use negative numbers, it will take the XP away instead - there's no negative check, so be careful if you're doing that.
	//(If you take XP, use getXP() > 0 first, if it's false, setXP(0).)
	@SubscribeEvent
	public void onJump(LivingJumpEvent event) {
		if (event.entity instanceof EntityPlayer) {
			//Get the skill in order to modify it
			SkillLevelJump skill = (SkillLevelJump) SkillLevelJump.get((EntityPlayer) event.entity, skillId);
			//XP is a float, so you can use decimals if you really really want to.
			//It's always trimmed to an int for player views though, so if they have 30.99 xp, it'll still show 30.
			skill.addXP(1, (EntityPlayer) event.entity);
			
			//There is a "forceAddXP(xp)" too - This will add XP even if the skill isn't equipped.
			//I dunno why you'd ever want to do that, but you can if you really want to.
			
			//Just tell the console the player gained XP.
			//Example of checking if the skill is equipped. Equipped-ness is checked automatically on addXP.
			if (isSkillEquipped((EntityPlayer) event.entity, skillId)) {
				System.out.println("It jumped! XP: " + skill.getXP());
			}
		}
	}

	//You SHOULD have a gui for your skill, see the examples included (When they're made :))
	//If this is false, it'll turn off the "open GUI" button in the skill list.
	//If it's true, that button will call openGui in this class; so set your GUI stuff there.
	@Override
	public boolean hasGui() {
		return false;
	}

	//Name your skill. This is how it will appear in-game.
	@Override
	public String skillName() {
		return "Jump";
	}

	//Whether your skill is unlocked or not; used for custom unlocking requirements.
	//Will only allow use & equipping of the skill if it's true; so make sure to eventually make it true once requirements are met.
	//You may want to use NBT to save the unlocked status depending on HOW you unlock it.
	//If you want your skill to just rely on Global Level, or be unlocked from the start, just return true.
	//OPTIONAL. DEFAULT: True
	@Override
	public boolean skillUnlocked() {
		return true;
	}

	//The level at which the skill is unlocked and available. IGNORES skillUnlocked!
	//Anything >= 1 means skill is always unlocked as far as level is concerned. Else, you have to reach the level.
	//OPTIONAL. DEFAULT: -1
	@Override
	public int unlockedLevel() {
		return -1;
	}

	//Chat formatting to be applied before the name wherever it's shown.
	//Note sometimes the name is marked as bold automatically, and that boldness takes priority.
	//Formatting is applied with a string such as "\u00A7#" where the # is a character; 0-9 or a-f. Also letters k-o cover formatting.
	//Do not use k though, it's "obfuscated" which gives jumbled letters.
	//OPTIONAL. DEFAULT: "". Never return null, or your skill will be named "nullMy Skill".
	@Override
	public String nameFormat() {
		return "\u00A7b";
	}

	//If your skill requires another skill to function, then list it here. MAKE SURE THE OTHER DOES NOT REQUIRE THIS SKILL.
	//A skill cannot be selected until the one it depends on is already equipped, so if two skills both depend on each other,
	//then you can never equip either. You should treat one as a parent skill, and then a child skill which depends on it.
	//Again, you can require multiple skills.
	//For the first three skills listed, you can also have a level requirement. This is optional; if not included they won't be used.
		//requiredSkills.add("skillId");
		//firstReqSkillLevel = 10; //skill "skillId" has to be level 10 to unlock.
		//secondReqSkillLevel = -1; //Default values, not including this will use -1.
		//thirdReqSkillLevel = -1;
	//OPTIONAL
	@Override
	public void addRequirements() {
		
	}

	//If you want your skill to be incompatable with another skill for whatever reason,
	//use this. You can add as many as you like, and if you have access to the skill's classes I recommend calling Skill.staticSkillId here for simplicity.
	//OPTIONAL
	@Override
	public void addIncompatibilities() {
		
	}

	//Skill's description. Keep it short and sweet; 4-5 lines - you can put details in your skill GUI.
	//This is shown when hovering over the skill icon in the Skill Select screen.
	//Supports formatting same as nameFormat, here "NOTHING!" is in bold.
	//It's a good idea to start off with your skill name, formatted, because it looks nice.
	@Override
	public void addDescription() {
		description.add(nameFormat() + "\u00A7l" + skillName());
		description.add("This is an example skill. Hooray!");
		description.add("Jump up and down to gain XP.");
		description.add("What can you do with that XP?" + "\u00A7lNOTHING!");
	}

	//Higher values mean it takes longer to level up.
	@Override
	public double levelMultiplier() {
		return 1.0;
	}

	//The max level this skill can reach. It'll continue to gain XP after, but won't level up. Optional - leave empty or set to -1 to allow
	//levels right up until xp reaches Float.MAX_VALUE.
	//And if a player does that, they probably cheated.
	@Override
	public int levelCap() {
		return -1;
	}

	//This is your skill icon - shown in the GUI's for skills. Requires two icons; 30x30 and 15x15.
	//See the texture at the location below for an example.
	@Override
	public ResourceLocation skillIcon() {
		return new ResourceLocation(RPGCore.MODID, "textures/gui/skills.png");
	}

	@Override
	public int iconX() {
		return 150;
	}

	@Override
	public int iconZ() {
		return 0;
	}

	//Boolean to toggle XP gain. Setting this to false hides the XP values from the skill.
	//Useful if your skill is in fact just a skill with one use and no progression.
	@Override
	public boolean canGainXP() {
		return true;
	}

	@Override
	public void activateSkill(EntityPlayer player, World world) {
		// TODO Auto-generated method stub
		
	}
}