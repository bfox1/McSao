package io.github.bfox1.SwordArtOnline.client.creativetabs;

import io.github.bfox1.SwordArtOnline.init.BlockInit;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by bfox1 on 4/2/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class SaoTabsManager extends CreativeTabs
{
    private Item itemIcon;
    public SaoTabsManager(String label)
    {
        super(label);
    }

    public static final SaoTabsManager SaoBlocks = new SaoTabsManager("Sao Blocks").setItem(Item.getItemFromBlock(BlockInit.aincradCobbleVariation));
    public static final SaoTabsManager SaoItems = new SaoTabsManager("Sao Items").setItem(null);

    /**
     * This method is used for setting TabIcon for creative Tab for Sao.
     * If you add Null into its parameters the Creative tab will return Barrier block
     * From minecraft.
     * @param item
     * @return
     */
    public final SaoTabsManager setItem(Item item)
    {
        this.itemIcon = item;
        return this;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final Item getTabIconItem()
    {
        return itemIcon != null ? itemIcon : Item.getItemFromBlock(Blocks.BARRIER);
    }
}
