package com.emanuelplays.notenoughobjectinfo.util;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class FluidInfoProvider {

    public record FluidData(
            String displayName, String registryId, String modId,
            boolean isSource, int level,
            boolean isLava, boolean isWater,
            int density, int viscosity, int temperature,
            float motionScale,
            List<String> tags
    ) {}

    public static FluidData fromState(Level level, BlockPos pos, BlockState blockState) {
        FluidState fs = blockState.getFluidState();
        if (fs.isEmpty()) return null;

        Fluid fluid = fs.getType();
        ResourceLocation rl = ForgeRegistries.FLUIDS.getKey(fluid);

        String registryId = rl != null ? rl.toString() : "unknown:unknown";
        String modId      = rl != null ? rl.getNamespace() : "unknown";

        FluidType ft = fluid.getFluidType();
        String displayName = ft.getDescription().getString();

        boolean isSource = fs.isSource();
        int fluidLevel   = fs.getAmount();

        boolean isLava  = fluid.isSame(Fluids.LAVA)          || fluid.isSame(Fluids.FLOWING_LAVA);
        boolean isWater = fluid.isSame(Fluids.WATER)         || fluid.isSame(Fluids.FLOWING_WATER);

        int  density     = ft.getDensity();
        int  viscosity   = ft.getViscosity();
        int  temperature = ft.getTemperature();
        float motionScale= (float) ft.motionScale(null);

        List<String> tags = new ArrayList<>();
        // FluidType doesn't expose tags directly; use fluid registry tags
        fluid.builtInRegistryHolder().tags().map(t -> "#" + t.location()).forEach(tags::add);

        return new FluidData(
                displayName, registryId, modId,
                isSource, fluidLevel,
                isLava, isWater,
                density, viscosity, temperature,
                motionScale,
                tags
        );
    }
}
