package tauri.dev.jsg.block.transportrings.controller;

import tauri.dev.jsg.JSG;
import tauri.dev.jsg.creativetabs.JSGCreativeTabsHandler;
import tauri.dev.jsg.util.main.JSGProps;
import tauri.dev.jsg.block.JSGBlock;
import tauri.dev.jsg.tileentity.transportrings.controller.TRControllerAbstractTile;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class TRControllerAbstractBlock extends JSGBlock {
    public TRControllerAbstractBlock(String blockName) {
        super(Material.ROCK);

        setRegistryName(JSG.MOD_ID + ":" + blockName);
        setUnlocalizedName(JSG.MOD_ID + "." + blockName);

        setSoundType(SoundType.STONE);
        setCreativeTab(JSGCreativeTabsHandler.JSG_RINGS_CREATIVE_TAB);

        setDefaultState(blockState.getBaseState().withProperty(JSGProps.FACING_HORIZONTAL, EnumFacing.NORTH));

        setLightOpacity(0);

        setHardness(3.0f);
        setHarvestLevel("pickaxe", 3);
    }

    // ------------------------------------------------------------------------
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, JSGProps.FACING_HORIZONTAL);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(JSGProps.FACING_HORIZONTAL).getHorizontalIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(JSGProps.FACING_HORIZONTAL, EnumFacing.getHorizontal(meta & 0x03));
    }


    // ------------------------------------------------------------------------
    @Override
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
        return super.canPlaceBlockOnSide(worldIn, pos, side) && JSGProps.FACING_HORIZONTAL.getAllowedValues().contains(side);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return getDefaultState().withProperty(JSGProps.FACING_HORIZONTAL, facing);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
        EnumFacing backFacing = state.getValue(JSGProps.FACING_HORIZONTAL).getOpposite();

        if (world.isAirBlock(pos.offset(backFacing))) {
            this.dropBlockAsItem(world, pos, state, 0);
            world.setBlockToAir(pos);
        }
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TRControllerAbstractTile controllerTile = (TRControllerAbstractTile) world.getTileEntity(pos);

        if (!world.isRemote) {
            if (controllerTile != null) {
                controllerTile.updateLinkStatus();
            }
        }
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TRControllerAbstractTile controllerTile = (TRControllerAbstractTile) world.getTileEntity(pos);

        if (!world.isRemote && controllerTile != null && controllerTile.isLinked() && controllerTile.getLinkedRingsTile(world) != null)
            controllerTile.getLinkedRingsTile(world).setLinkedController(null, -1);

        super.breakBlock(world, pos, state);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            if (hand == EnumHand.MAIN_HAND) onRayCasterActivated(world, pos, player);
        }

        return false;
    }

    public abstract void onRayCasterActivated(World world, BlockPos pos, EntityPlayer player);

    // ------------------------------------------------------------------------
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public abstract TRControllerAbstractTile createTileEntity(World world, IBlockState state);

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        switch (state.getValue(JSGProps.FACING_HORIZONTAL)) {
            case NORTH:
                return new AxisAlignedBB(0.15, 0, 1, 0.85, 1, 0.85);

            case SOUTH:
                return new AxisAlignedBB(0.15, 0, 0.15, 0.85, 1, 0);

            case WEST:
                return new AxisAlignedBB(0.85, 0, 0.15, 1, 1, 0.85);

            default:
                return new AxisAlignedBB(0, 0, 0.15, 0.15, 1, 0.85);
        }
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        switch (blockState.getValue(JSGProps.FACING_HORIZONTAL)) {
            case NORTH:
                return new AxisAlignedBB(0.15, 0, 1, 0.85, 1, 0.85);

            case SOUTH:
                return new AxisAlignedBB(0.15, 0, 0.15, 0.85, 1, 0);

            case WEST:
                return new AxisAlignedBB(0.85, 0, 0.15, 1, 1, 0.85);

            default:
                return new AxisAlignedBB(0, 0, 0.15, 0.15, 1, 0.85);
        }
    }

    public boolean renderHighlight(IBlockState blockState) {
        return false;
    }
}
