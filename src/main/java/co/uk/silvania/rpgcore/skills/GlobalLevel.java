package co.uk.silvania.rpgcore.skills;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GlobalLevel extends SkillLevelBase implements IExtendedEntityProperties {
	
	public static String staticSkillId;
	
	public String skillName;
	public String skillId;
	
	public float xpGlobal;
	
	public GlobalLevel(EntityPlayer player, String skillID) {
		super(skillID);
		skillName = "Global Level";
		skillId = skillID;
		staticSkillId = skillID;
		this.xpGlobal = 0;		
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setFloat(skillId + "xp", xpGlobal);
		compound.setTag(skillId, nbt);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound nbt = (NBTTagCompound) compound.getTag(skillId);
		xpGlobal = nbt.getFloat(skillId + "xp");
	}
	
	public static final void register(EntityPlayer player) {
		player.registerExtendedProperties(GlobalLevel.staticSkillId, new GlobalLevel(player, staticSkillId));
	}

	@Override public void init(Entity entity, World world) {}
	
	public float getXPGlobal() {
		return xpGlobal;
	}
	
	public String getXPForPrint() {
		return (int) getXPGlobal() + " / " + getXpForLevel(getLevel()+1);
	}
	
	public int getLevel() {
		int base = 83;
		int previousXp = 83;
		int level = 1;
		
		while (xpGlobal >= previousXp) {
			previousXp += base + ((base / 100.0) * ((level) * (35 + ((level/10)*10))));
			level++;
		}
		return level;
		
	}
	
	public int getXpForLevel(int level) {
		int base = 83;
		int xpForLevel = 83;
		
		for (int i = 1; i < level; i++) {
			xpForLevel += base + ((base / 100.0) * ((i) * (35 + ((i/10)*10))));
		}
		return xpForLevel;
	}
	
	public float xpToNextLevel() {		
		return (getXpForLevel(getLevel())) - (int)getXPGlobal();
	}
	
	public void setLevel() {
		level = (int) Math.round(levelMultiplier() * Math.sqrt(xp));
		System.out.println("Level: " + level);
	}

	public void setXP(float xpSet) {
		xpGlobal = xpSet;
	}
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer) {
			event.entity.registerExtendedProperties(skillId, new GlobalLevel((EntityPlayer)event.entity, skillId));
		}
	}
	
	public static IExtendedEntityProperties get(EntityPlayer player) {
		return player.getExtendedProperties(staticSkillId);
	}
	
	@Override
	public boolean hasGui() {
		return false;
	}

	@Override
	public String skillName() {
		return skillName;
	}

	@Override public void openGui() {}
	@Override public void addDescription() {}

	@Override
	public double levelMultiplier() {
		return 2.0;
	}

	@Override
	public ResourceLocation skillIcon() {
		return null;
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
