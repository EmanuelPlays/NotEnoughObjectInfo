package com.emanuelplays.notenoughobjectinfo.util;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class FluidInfoProvider {

    public record FluidData(
            String displayName,
            String registryId,
            String modId,
            boolean isSource,
            int level,
            boolean isLava,
            boolean isWater,
            int density,
            int viscosity,
            int temperature,
            List<String> tags
    ) {}

    public static FluidData fromState(Level world, BlockPos pos, BlockState blockState) {
        FluidState fluidState = blockState.getFluidState();
        if (fluidState.isEmpty()) return null;

        var fluid = fluidState.getType();
        ResourceLocation rl = ForgeRegistries.FLUIDS.getKey(fluid);

        String registryId = rl != null ? rl.toString() : "unknown:unknown";
        String modId      = rl != null ? rl.getNamespace() : "unknown";

        // Display name via FluidType
        FluidType fluidType = fluid.getFluidType();
        String displayName = fluidType.getDescription().getString();

        boolean isSource = fluidState.isSource();
        int level        = fluidState.getAmount();

        boolean isLava  = fluid.isSame(net.minecraft.world.level.material.Fluids.LAVA)
                       || fluid.isSame(net.minecraft.world.level.material.Fluids.FLOWING_LAVA);
        boolean isWater = fluid.isSame(net.minecraft.world.level.material.Fluids.WATER)
                       || fluid.isSame(net.minecraft.world.level.material.Fluids.FLOWING_WATER);

        int density     = fluidType.getDensity();
        int viscosity   = fluidType.getViscosity();
        int temperature = fluidType.getTemperature();

        // Tags - empty list for now as FluidType doesn't have getTags() in 1.20.1
        List<String> tags = new ArrayList<>();

        return new FluidData(
                displayName, registryId, modId,
                isSource, level,
                isLava, isWater,
                density, viscosity, temperature,
                tags
        );
    }
}
