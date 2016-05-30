package co.uk.silvania.rpgcore.skills;

import co.uk.silvania.rpgcore.RPGCore;
import io.github.bfox1.SwordArtOnline.skill.ISubSkillTypeIdentifier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SkillLevelAgility extends SkillLevelBase implements IExtendedEntityProperties, ISubSkillTypeIdentifier {
	
	public static String staticSkillId;
	
	public SkillLevelAgility(EntityPlayer player, String skillID) {
		super(skillID);
		staticSkillId = skillID;
		this.xp = 0;
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
	
	public static final void register(EntityPlayer player) {
		player.registerExtendedProperties(SkillLevelAgility.staticSkillId, new SkillLevelAgility(player, staticSkillId));
	}
	
	//RPGCore! This is where we would construct.
	
	@Override
	public boolean hasGui() {
		return false;
	}
	
	@Override
	public String skillName() {
		return "Agility";
	}

	@Override public void openGui() {}

	@Override
	public void addIncompatibilities() {
		incompatibleSkills.add("skillStrength");
	}

	@Override
	public void addDescription() {
		description.add(nameFormat() + "\u00A7l" + skillName());
		description.add("Base Skill");
		description.add("Required for most speed skills");
		description.add("Levelled using skill points gained from");
		description.add("advancing your Global Level.");
	}
	
	@Override
	public void activateSkill(EntityPlayer player, World world) {}
	
	@Override
	public ResourceLocation skillIcon() {
		return new ResourceLocation(RPGCore.MODID, "textures/gui/skills.png");
	}

	@Override
	public int iconX() {
		return 30;
	}

	@Override
	public int iconZ() {
		return 0;
	}
	
	@Override
	public boolean canGainXP() {
		return false;
	}
	
	@Override
	public int xpBarColour() {
		return 252;
	}
	
	@Override
	public String shortName() {
		return "AGI";
	}
	
	@Override
	public String nameFormat() {
		return "\u00A79";
	}
}
