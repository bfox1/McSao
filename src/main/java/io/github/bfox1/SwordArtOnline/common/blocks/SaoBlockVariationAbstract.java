package io.github.bfox1.SwordArtOnline.common.blocks;

import io.github.bfox1.SwordArtOnline.common.blocks.itemblock.ISaoBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bfox1 on 4/2/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public abstract class SaoBlockVariationAbstract extends SaoBlockAbstract
{
    public static final PropertyEnum TYPE = PropertyEnum.create("type", SaoBlockVariationAbstract.EnumType.class);
    public final int subTypes;
    public final List<String> subTypeNamList;
    public SaoBlockVariationAbstract(Material p_i46399_1_, int subTypes, float hardness)
    {
        super(p_i46399_1_, hardness);
        this.subTypes = subTypes;
        this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, EnumType.BLOCK_ONE));
        this.subTypeNamList = new ArrayList<String>();
    }


    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {TYPE});
    }

    @Override
    public String getSaoMetaBlockName(ItemStack stack)
    {

        return EnumType.getTypeById(stack.getItemDamage()).getName();
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState state;
        switch(meta)
        {
            case 0 : state = getStateType(EnumType.BLOCK_ONE); break;
            case 1 : state = getStateType(EnumType.BLOCK_TWO); break;
            case 2 : state = getStateType(EnumType.BLOCK_THREE); break;
            case 3 : state = getStateType(EnumType.BLOCK_FOUR); break;
            case 4 : state = getStateType(EnumType.BLOCK_FIVE); break;
            default: state = getStateType(EnumType.BLOCK_ONE); break;
        }
        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        EnumType type = (EnumType) state.getValue(TYPE);
        return type.getID();
    }

    private IBlockState getStateType(EnumType type)
    {
        return getDefaultState().withProperty(TYPE, type);
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return getMetaFromState(state);
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tabs, List list)
    {
        for(EnumType type : EnumType.values())
        {
            if(type.getID() <= subTypes)
            list.add(new ItemStack(itemIn, 1, type.getID()));
        }
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(world.getBlockState(pos)));
    }

    @Override
    public SaoBlockVariationAbstract setUnlocalizedName(String name)
    {
        return (SaoBlockVariationAbstract)super.setUnlocalizedName(name);
    }

    public ISaoBlock setSubTypeFullNameList()
    {
        for(int i = 0; i <= subTypes; i++)
        {
            //this.subTypeNamList.add(i, this.getUnlocalizedName().replaceAll("tile.", "") + "_" + EnumType.getTypeById(i).getName());
            this.subTypeNamList.add(i, this.getRegistryName().toString() + "_" + EnumType.getTypeById(i).getName());
            System.out.println(this.subTypeNamList.get(i));
        }
        return this;
    }

    public String[] getSubtypeArray()
    {
        String[] subtype = new String[this.subTypeNamList.size()];
        subtype = subTypeNamList.toArray(subtype);
        return subtype;
    }



    public enum EnumType implements IStringSerializable
    {
        BLOCK_ONE(0, "block_one"),
        BLOCK_TWO(1, "block_two"),
        BLOCK_THREE(2, "block_three"),
        BLOCK_FOUR(3, "block_four"),
        BLOCK_FIVE(4, "block_five")
        ;
        private int ID;
        private String name;
        private EnumType(int ID, String name)
        {
            this.ID = ID;
            this.name = name;
        }
        @Override
        public String getName() {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public int getID()
        {
            return ID;
        }

        public static EnumType getTypeById(int id)
        {
            for(EnumType type : EnumType.values())
            {
                if(type.getID() == id)
                {
                    return type;
                }
            }
            return EnumType.BLOCK_ONE;
        }

        @Override
        public String toString()
        {
            return getName();
        }


    }
}
