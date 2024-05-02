package net.endermanofdoom.mowithers.render;

import net.endermanofdoom.mca.client.model.ModelBaseWither;
import net.endermanofdoom.mca.client.render.RenderBaseWither;
import net.endermanofdoom.mca.entity.boss.EntityBaseWither;
import net.endermanofdoom.mowithers.MoWithers;
import net.endermanofdoom.mowithers.entity.wither.EntityWitherDust;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderWitherDust extends RenderBaseWither<EntityWitherDust>
{
    public RenderWitherDust(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
        this.addLayer(new LayerDustOverlay(this));
    }

    protected ResourceLocation getEntityTexture(EntityWitherDust entity)
    {
        int i = entity.getInvulTime();
        return i > 0 && (i > 80 || i / 5 % 2 != 1) ? new ResourceLocation(MoWithers.MODID, "textures/entity/wither/wither_eyes_invulnerable.png") : new ResourceLocation(MoWithers.MODID, "textures/entity/wither/wither_eyes.png");
    }

    protected ResourceLocation getAuraTexture(EntityBaseWither entity)
    {
        return new ResourceLocation("textures/blocks/sand.png");
    }
    
	public class LayerDustOverlay implements LayerRenderer<EntityWitherDust>
    {
        private final RenderWitherDust witherRenderer;
        private final ModelBaseWither witherModel = new ModelBaseWither(-0.001F);

        public LayerDustOverlay(RenderWitherDust witherRendererIn)
        {
            this.witherRenderer = witherRendererIn;
        }

        public void doRenderLayer(EntityWitherDust entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
        {
            if (!entitylivingbaseIn.isInvisible())
            {
                GlStateManager.depthMask(!entitylivingbaseIn.isInvisible());
                this.witherRenderer.bindTexture(new ResourceLocation(MoWithers.MODID, "textures/entity/wither/element/dust.png"));
                GlStateManager.matrixMode(5890);
                GlStateManager.loadIdentity();
                float f = (float)entitylivingbaseIn.ticksExisted + partialTicks;
                GlStateManager.translate(f * 0.025F, f * 0.01F, 0);
                GlStateManager.matrixMode(5888);
                GlStateManager.enableBlend();
                float f3 = 1F;
                GlStateManager.color(f3, f3, f3, 1.0F);
                GlStateManager.disableLighting();
                this.witherModel.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
                this.witherModel.setModelAttributes(this.witherRenderer.getMainModel());
                Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
                this.witherModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
                GlStateManager.matrixMode(5890);
                GlStateManager.loadIdentity();
                GlStateManager.matrixMode(5888);
                GlStateManager.enableLighting();
                GlStateManager.disableBlend();
            }
        }

        public boolean shouldCombineTextures()
        {
            return true;
        }
    }
}