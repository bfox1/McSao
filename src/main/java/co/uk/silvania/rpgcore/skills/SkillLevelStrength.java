package co.uk.silvania.rpgcore.skills;

import co.uk.silvania.rpgcore.RPGCore;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SkillLevelStrength extends SkillLevelBase implements IExtendedEntityProperties {
	
	public static String staticSkillId; 
	
	public SkillLevelStrength(EntityPlayer player, String skillID) {
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
		xp = nbt.getInteger(skillId + "xp");		
	}

	@Override public void init(Entity entity, World world) {}
	
	public static final void register(EntityPlayer player) {
		player.registerExtendedProperties(SkillLevelStrength.staticSkillId, new SkillLevelStrength(player, staticSkillId));
	}
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer) {
			event.entity.registerExtendedProperties(skillId, new SkillLevelStrength((EntityPlayer)event.entity, skillId));
		}
	}
	
	@Override
	public boolean hasGui() {
		return false;
	}
	
	@Override
	public String skillName() {
		return "Strength";
	}

	@Override public void openGui() { }

	@Override
	public void addIncompatibilities() {
		incompatibleSkills.add("skillAgility");
	}

	@Override
	public void addDescription() {
		description.add(nameFormat() + "\u00A7l" + skillName());
		description.add("Base Skill.");
		description.add("Required for most damage skills");
		description.add("Levelled slowly with any act of strength,");
		description.add("such as mining, fighting and so on.");
		description.add("Slowly increases punch damage.");
	}

	@Override
	public double levelMultiplier() {
		return 1.2;
	}

	@Override
	public ResourceLocation skillIcon() {
		return new ResourceLocation(RPGCore.MODID, "textures/gui/skills.png");
	}

	@Override
	public int iconX() {
		return 0;
	}

	@Override
	public int iconZ() {
		return 0;
	}

	@Override
	public void activateSkill(EntityPlayer player, World world) {
		// TODO Auto-generated method stub
		
	}
}
