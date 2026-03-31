package com.emanuelplays.notenoughobjectinfo.util;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class BlockInfoProvider {

    public record BlockData(
            String displayName, String registryId, String modId,
            float hardness, float blastResistance,
            String harvestTool, int harvestLevel,
            int blockLight, int skyLight, int emittedLight,
            int redstonePower,
            boolean isFlammable, int flammability, int fireSpreadSpeed,
            boolean isReplaceable, boolean isSolid,
            float friction, float speedFactor, float jumpFactor,
            String pushReaction,
            List<String> blockStateProperties,
            List<String> tags,
            String mapColor,
            boolean hasTileEntity, String tileEntityType, String compactNbt
    ) {}

    public static BlockData fromState(Level level, BlockPos pos, BlockState state) {
        Block block = state.getBlock();
        ResourceLocation rl = ForgeRegistries.BLOCKS.getKey(block);

        String registryId  = rl != null ? rl.toString() : "unknown:unknown";
        String modId       = rl != null ? rl.getNamespace() : "unknown";
        String displayName = block.getName().getString();

        float hardness   = state.getDestroySpeed(level, pos);
        float resistance = block.getExplosionResistance();

        String harvestTool  = detectTool(state);
        int    harvestLevel = 0; // Computed via tags in 1.20

        int blockLight = level.getBrightness(net.minecraft.world.level.LightLayer.BLOCK, pos);
        int skyLight   = level.getBrightness(net.minecraft.world.level.LightLayer.SKY,   pos);
        int emitLight  = state.getLightEmission(level, pos);

        int redstonePower = level.getBestNeighborSignal(pos);

        boolean isFlammable    = block.isFlammable(state, level, pos, net.minecraft.core.Direction.UP);
        int     flammability   = block.getFlammability(state, level, pos, net.minecraft.core.Direction.UP);
        int     fireSpread     = block.getFireSpreadSpeed(state, level, pos, net.minecraft.core.Direction.UP);

        boolean isReplaceable  = state.canBeReplaced();
        boolean isSolid        = state.isSolidRender(level, pos);
        float   friction       = block.getFriction(state, level, pos, null);
        float   speedFactor    = block.getSpeedFactor();
        float   jumpFactor     = block.getJumpFactor();
        String  pushReaction   = state.getPistonPushReaction().name();

        List<String> stateProps = new ArrayList<>();
        for (Map.Entry<Property<?>, Comparable<?>> e : state.getValues().entrySet())
            stateProps.add(e.getKey().getName() + "=" + e.getValue());

        List<String> tags = new ArrayList<>();
        state.getTags().map(t -> "#" + t.location()).forEach(tags::add);

        String mapColor = state.getMapColor(level, pos).toString();

        boolean hasTileEntity = state.hasBlockEntity();
        String  teType        = "";
        String  compactNbt    = "";
        if (hasTileEntity) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be != null) {
                ResourceLocation teRl = ForgeRegistries.BLOCK_ENTITY_TYPES.getKey(be.getType());
                teType     = teRl != null ? teRl.toString() : "unknown";
                compactNbt = compactNbt(be.saveWithoutMetadata(), 512);
            }
        }

        return new BlockData(
                displayName, registryId, modId,
                hardness, resistance,
                harvestTool, harvestLevel,
                blockLight, skyLight, emitLight,
                redstonePower,
                isFlammable, flammability, fireSpread,
                isReplaceable, isSolid,
                friction, speedFactor, jumpFactor,
                pushReaction,
                stateProps, tags, mapColor,
                hasTileEntity, teType, compactNbt
        );
    }

    private static String detectTool(BlockState state) {
        if (state.is(BlockTags.MINEABLE_WITH_PICKAXE)) return "Pickaxe";
        if (state.is(BlockTags.MINEABLE_WITH_AXE))     return "Axe";
        if (state.is(BlockTags.MINEABLE_WITH_SHOVEL))  return "Shovel";
        if (state.is(BlockTags.MINEABLE_WITH_HOE))     return "Hoe";
        if (state.is(BlockTags.SWORD_EFFICIENT))        return "Sword";
        if (state.requiresCorrectToolForDrops())        return "Requires Tool";
        return "Any";
    }

    public static String compactNbt(CompoundTag tag, int maxChars) {
        if (tag == null) return "{}";
        StringBuilder sb = new StringBuilder("{");
        List<String> keys = new ArrayList<>(tag.getAllKeys());
        Collections.sort(keys);
        for (String k : keys) {
            if (sb.length() > maxChars) { sb.append("…"); break; }
            sb.append(k).append(":").append(tag.get(k)).append(", ");
        }
        if (sb.length() > 1 && sb.charAt(sb.length() - 2) == ',')
            sb.delete(sb.length() - 2, sb.length());
        return sb.append("}").toString();
    }
}
