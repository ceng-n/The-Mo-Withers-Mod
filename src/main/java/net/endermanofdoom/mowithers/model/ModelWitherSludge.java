package net.endermanofdoom.mowithers.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelWitherSludge extends ModelBase 
{
	private final ModelRenderer Head;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer orbz;
	private final ModelRenderer cube_r3;
	private final ModelRenderer cube_r4;
	private final ModelRenderer cube_r5;

	public ModelWitherSludge(float p_i46302_1_) {
		textureWidth = 128;
		textureHeight = 128;

		Head = new ModelRenderer(this);
		Head.setRotationPoint(0.0F, 24.0F, 0);
		Head.cubeList.add(new ModelBox(Head, 0, 0, -10.0F, -6.0F, -13.0F, 20, 9, 27, p_i46302_1_, false));
		Head.cubeList.add(new ModelBox(Head, 0, 36, -8.0F, -8.0F, -13.0F, 17, 2, 24, p_i46302_1_, false));
		Head.cubeList.add(new ModelBox(Head, 0, 62, -3.0F, -12.0F, -2.0F, 12, 4, 12, p_i46302_1_, false));
		Head.cubeList.add(new ModelBox(Head, 48, 64, 0.0F, 3.0F, -12.0F, 8, 11, 7, p_i46302_1_, false));
		Head.cubeList.add(new ModelBox(Head, 0, 78, -4.0F, 3.0F, 1.0F, 7, 5, 7, p_i46302_1_, false));
		Head.cubeList.add(new ModelBox(Head, 60, 68, -3.0F, 8.0F, 4.0F, 4, 5, 3, p_i46302_1_, false));
		Head.cubeList.add(new ModelBox(Head, 59, 39, -6.0F, 3.0F, -12.0F, 11, 2, 23, p_i46302_1_, false));

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(0.0F, 0.0F, 0);
		Head.addChild(cube_r1);
		setRotationAngle(cube_r1, 3.1416F, 0.0F, 0);
		cube_r1.cubeList.add(new ModelBox(cube_r1, 59, 65, -5.0F, 8.0F, 5.0F, 5, 11, 6, p_i46302_1_, false));

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(0.0F, 0.0F, 0);
		Head.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0F, 0.0F, -3.1416F);
		cube_r2.cubeList.add(new ModelBox(cube_r2, 48, 64, -5.0F, 12.0F, 1.0F, 8, 11, 7, p_i46302_1_, false));

		orbz = new ModelRenderer(this);
		orbz.setRotationPoint(0.0F, 0.0F, 0);
		Head.addChild(orbz);
		orbz.cubeList.add(new ModelBox(orbz, 15, 11, 17.0F, -15.0F, -1.0F, 2, 2, 2, p_i46302_1_, false));
		orbz.cubeList.add(new ModelBox(orbz, 15, 0, -19.0F, -15.0F, 20.0F, 2, 2, 2, p_i46302_1_, false));

		cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(0.0F, 0.0F, 0);
		orbz.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.6626F, 0.0788F, 0.4431F);
		cube_r3.cubeList.add(new ModelBox(cube_r3, 0, 36, -20.0F, 4.0F, -25.0F, 3, 9, 4, p_i46302_1_, false));

		cube_r4 = new ModelRenderer(this);
		cube_r4.setRotationPoint(0.0F, 0.0F, 0);
		orbz.addChild(cube_r4);
		setRotationAngle(cube_r4, 2.4515F, 0.0788F, 0.4431F);
		cube_r4.cubeList.add(new ModelBox(cube_r4, 10, 45, -33.0F, 4.0F, 29.0F, 3, 3, 4, p_i46302_1_, false));
		cube_r4.cubeList.add(new ModelBox(cube_r4, 0, 49, 0.0F, 22.0F, -8.0F, 3, 3, 4, p_i46302_1_, false));
		cube_r4.cubeList.add(new ModelBox(cube_r4, 0, 11, -2.0F, -9.0F, 20.0F, 5, 3, 5, p_i46302_1_, false));

		cube_r5 = new ModelRenderer(this);
		cube_r5.setRotationPoint(0.0F, 0.0F, 0);
		orbz.addChild(cube_r5);
		setRotationAngle(cube_r5, 0.0F, -0.3491F, -0.3054F);
		cube_r5.cubeList.add(new ModelBox(cube_r5, 0, 0, -12.0F, -6.0F, 20.0F, 5, 6, 5, p_i46302_1_, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		Head.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}