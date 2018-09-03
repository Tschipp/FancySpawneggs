package tschipp.fancyspawneggs.client.model;

import java.util.List;

import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import tschipp.fancyspawneggs.client.render.RenderFancyEgg;

public class FancyEggModel implements IBakedModel
{

	private IBakedModel old;
	
	
	public FancyEggModel(IBakedModel old)
	{
		this.old = old;
	}

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand)
	{
		return old.getQuads(state, side, rand);
	}

	@Override
	public boolean isAmbientOcclusion()
	{
		return old.isAmbientOcclusion();
	}

	@Override
	public boolean isGui3d()
	{
		return old.isGui3d();
	}

	@Override
	public boolean isBuiltInRenderer()
	{
		return true;
	}

	public IBakedModel getInternal()
	{
		return old;
	}

	public void setInternal(IBakedModel internal)
	{
		this.old = internal;
	}

	@Override
	public TextureAtlasSprite getParticleTexture()
	{
		return old.getParticleTexture();
	}

	@Override
	public ItemOverrideList getOverrides()
	{
		return ItemOverrideList.NONE;
	}
	
	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType)
	{
		RenderFancyEgg.transformType = cameraTransformType;
		return Pair.of(this, old.handlePerspective(cameraTransformType).getRight());
	}

}
