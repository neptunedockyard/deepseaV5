package com.deepsea;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.Array;

public class ShapeController {

	public ModelBuilder modelBuilder;
	public MeshPartBuilder meshBuilder;
	public ModelBatch modelBatch;
	public Array<ModelInstance> instances = new Array<ModelInstance>();
	
	//inherited
	public PerspectiveCamera _camera;
	public Environment _lights;
	
	public ShapeController(PerspectiveCamera camera, Environment lights) {
		this._camera = camera;
		this._lights = lights;
		modelBuilder = new ModelBuilder();
		modelBatch = new ModelBatch();
		
		Model grid = modelBuilder.createLineGrid(10, 10, 500, 500, new Material(ColorAttribute.createDiffuse(Color.RED)), Usage.Position | Usage.Normal | Usage.TextureCoordinates);
		instances.add(new ModelInstance(grid, 0, 0, 0));
		
		Gdx.app.log("INFO", "shapecontroller loaded");
	}
	
	public void createSphere() {
		Model sphere = modelBuilder.createSphere(10f, 10f, 10f, 16, 16, new Material(ColorAttribute.createDiffuse(Color.BLUE)), Usage.Position | Usage.Normal | Usage.TextureCoordinates);
		instances.add(new ModelInstance(sphere, 10f, 10f, 10f));
		
		Gdx.app.log("INFO", "sphere created");
	}
	
	public void render() {
		
		modelBatch.begin(_camera);
		modelBatch.render(instances, _lights);
		modelBatch.end();
	}
	
	public void dispose() {
		modelBatch.dispose();
	}
}
