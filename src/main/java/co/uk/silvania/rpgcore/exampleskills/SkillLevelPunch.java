package co.uk.silvania.rpgcore.exampleskills;

import co.uk.silvania.rpgcore.RPGCore;
import co.uk.silvania.rpgcore.skills.SkillLevelBase;
import co.uk.silvania.rpgcore.skills.SkillLevelStrength;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SkillLevelPunch extends SkillLevelBase implements IExtendedEntityProperties {
	
	//SkillLevelPunch is very similar to Jump but I've not documented it.
	//If you want to see how the mod works and how to create a skill, see SkillLevelJump
	//If you're mad and want to guess, or want to copy-paste a template, use this.	
	
	public static String staticSkillId = "skillPunch";
	
	public SkillLevelPunch(EntityPlayer player, String skillID) {
		super(skillID);
		staticSkillId = skillID;
		this.xp = 0;
	}
	
	public static final void register(EntityPlayer player) {
		player.registerExtendedProperties(SkillLevelPunch.staticSkillId, new SkillLevelPunch(player, staticSkillId));
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setFloat(skillId + "xp", xp);
		compound.setTag(skillId, nbt);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound nbt = (NBTTagCompound) compound.getTag(skillId);
		xp = nbt.getFloat(skillId + "xp");		
	}

	@Override public void init(Entity entity, World world) {}
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer) {
			event.entity.registerExtendedProperties(skillId, new SkillLevelPunch((EntityPlayer)event.entity, skillId));
		}
	}
	
	@SubscribeEvent
	public void onPunch(LivingAttackEvent event) {
		if (event.source.getEntity() instanceof EntityPlayer) {
			System.out.println("onPunch skillId: " + skillId);
			SkillLevelPunch skill = (SkillLevelPunch) SkillLevelPunch.get((EntityPlayer) event.source.getEntity(), skillId);
			skill.addXP(1, (EntityPlayer) event.source.getEntity());
			
			if (isSkillEquipped((EntityPlayer) event.source.getEntity(), skillId)) {
				System.out.println("It Punched! XP: " + skill.getXP());
			}
		}
	}
	
	@Override
	public void addRequirements() {
		requiredSkills.add(SkillLevelStrength.staticSkillId);
	}

	@Override public void openGui() {}
	
	@Override
	public boolean hasGui() {
		return false;
	}

	@Override
	public String skillName() {
		return "Punch";
	}

	@Override
	public void addDescription() {
		description.add(nameFormat() + "\u00A7l" + skillName());
		description.add("PUNCH THING FACE");
		description.add("PUNCH FEEL GOOD");
		description.add("XP GET");
	}

	@Override
	public ResourceLocation skillIcon() {
		return new ResourceLocation(RPGCore.MODID, "textures/gui/skills.png");
	}

	@Override
	public int iconX() {
		return 120;
	}

	@Override
	public int iconZ() {
		return 0;
	}

	@Override
	public void activateSkill(EntityPlayer player, World world) {

	}
}