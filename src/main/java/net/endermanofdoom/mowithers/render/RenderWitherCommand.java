package net.endermanofdoom.mowithers.render;

import net.endermanofdoom.mca.client.render.RenderBaseWither;
import net.endermanofdoom.mca.entity.boss.EntityBaseWither;
import net.endermanofdoom.mowithers.MoWithers;
import net.endermanofdoom.mowithers.entity.wither.EntityWitherCommand;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderWitherCommand extends RenderBaseWither<EntityWitherCommand>
{
    public RenderWitherCommand(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
    }

    protected ResourceLocation getEntityTexture(EntityWitherCommand entity)
    {
    	return entity.getPhasePercent(0.25F) ? new ResourceLocation(MoWithers.MODID, "textures/entity/wither/superboss/wither_command_3.png") : entity.getPhasePercent(0.625F) ? new ResourceLocation(MoWithers.MODID, "textures/entity/wither/superboss/wither_command_2.png") : new ResourceLocation(MoWithers.MODID, "textures/entity/wither/superboss/wither_command_1.png");
    }

    protected ResourceLocation getAuraTexture(EntityBaseWither entity)
    {
        return new ResourceLocation("textures/blocks/beacon.png");
    }
}