package io.github.bfox1.SwordArtOnline.playerutilities;

import io.github.bfox1.SwordArtOnline.common.entity.SaoExtendedProperty;
import io.github.bfox1.SwordArtOnline.common.util.LogHelper;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by bfox1 on 4/17/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class WorldFunction
{
    private static final String[] functionList =
            {
              "admin","build","plotBuild","mod"
            };
    private boolean canBuild = true;
    private boolean canPlotBuild;
    private boolean isAdmin = true;
    private boolean isMod;

    private boolean isCanBuild() {
        return canBuild;
    }

    private void setCanBuild(boolean canBuild) {
        this.canBuild = canBuild;
    }

    private boolean isCanPlotBuild() {
        return canPlotBuild;
    }

    private void setCanPlotBuild(boolean canPlotBuild) {
        this.canPlotBuild = canPlotBuild;
    }

    private boolean isAdmin() {
        return isAdmin;
    }

    private void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    private boolean isMod() {
        return isMod;
    }

    private void setMod(boolean mod) {
        isMod = mod;
    }

    public void setFunctionType(String name, Object object)
    {
        switch (name.toLowerCase())
        {
            case "admin":
                if(!checkBooleanObject(object, name)) break;

                setAdmin((Boolean) object);
                break;
            case "mod":
                if(!checkBooleanObject(object, name)) break;
                setMod((Boolean)object);
                break;
            case "build":
                if(!checkBooleanObject(object, name)) break;
                setCanBuild((Boolean)object);
                break;
            case "plotbuild":
                if(!checkBooleanObject(object, name)) break;
                setCanPlotBuild((Boolean)object);
                break;
            default:
                LogHelper.error("Something happened, " + name + " is not a function variable!");
        }
    }

    public Object getFunctionType(String name)
    {
        switch (name.toLowerCase())
        {
            case "admin": return isAdmin();
            case "mod": return isMod();
            case "build": return isCanBuild();
            case "plotbuild": return isCanPlotBuild();
            default:
                LogHelper.error("Something happened, " + name + " is not a function variable! Returning Null");
                return null;
        }
    }

    public void setToOldFunction(WorldFunction function)
    {
        for(String name : functionList)
        {
            this.setFunctionType(name, function.getFunctionType(name));
        }
    }


    public NBTTagCompound compressData(NBTTagCompound compound)
    {
        compound.setBoolean("admin", isAdmin);
        compound.setBoolean("build", isCanBuild());
        compound.setBoolean("plotBuild", isCanPlotBuild());
        compound.setBoolean("mod", isMod);
        return compound;
    }

    public static void uncompressData(NBTTagCompound compound, SaoExtendedProperty property)
    {
        for(String function : functionList)
        {
            property.getFunction().setFunctionType(function, compound.getBoolean(function));
            System.out.println(function + " : "+compound.getBoolean(function));
        }

    }

    private boolean checkBooleanObject(Object object, String name)
    {
        if(!(object instanceof Boolean))
        {
            printErrorMessage(name);
            return false;
        }
        return true;
    }

    private void printErrorMessage(String name)
    {
        LogHelper.error("Setting Fuction type for " + name + " but Object type isn't boolean! this is a problem.");
    }

}
