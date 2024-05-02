package net.endermanofdoom.mowithers.render;

import net.endermanofdoom.mowithers.MoWithers;
import net.endermanofdoom.mowithers.entity.monster.EntityCultist;
import net.minecraft.client.model.ModelIllager;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCultist extends RenderLiving<EntityMob>
{
    public RenderCultist(RenderManager p_i47189_1_)
    {
        super(p_i47189_1_, new ModelIllager(0.0F, 0.0F, 64, 64), 0.5F);
        this.addLayer(new LayerHeldItem(this)
        {
            public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
            {
                if (((EntityCultist)entitylivingbaseIn).isAggressive() && !((EntityCultist)entitylivingbaseIn).isSpellcasting())
                {
                    super.doRenderLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
                }
            }
            protected void translateToHand(EnumHandSide p_191361_1_)
            {
                ((ModelIllager)this.livingEntityRenderer.getMainModel()).getArm(p_191361_1_).postRender(0.0625F);
            }
        });
        ((ModelIllager)this.getMainModel()).hat.showModel = true;
    }

    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(EntityMob entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityMob entity)
    {
		switch (((EntityCultist)entity).getVariant())
		{
		case 1:
			return new ResourceLocation(MoWithers.MODID, "textures/entity/mob/cultist_greater.png");
		case 2:
			return new ResourceLocation(MoWithers.MODID, "textures/entity/mob/cardinal.png");
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
			return new ResourceLocation(MoWithers.MODID, "textures/entity/mob/cardinal_victius.png");
		default:
			return new ResourceLocation(MoWithers.MODID, "textures/entity/mob/cultist.png");
		}
    }

    /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    protected void preRenderCallback(EntityMob entitylivingbaseIn, float partialTickTime)
    {
        float f = 0.9375F;
        GlStateManager.scale(f, f, f);
    }
}