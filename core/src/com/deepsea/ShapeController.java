package com.deepsea;

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
		/*
		 * This method creates the shape controller object that allows the game to 
		 * generate different models, whether they are loaded models or procedurally 
		 * generated models.
		 * It inherits the camera and environment objects, which are used by the renderer.
		 */
		this._camera = camera;
		this._lights = lights;
		modelBuilder = new ModelBuilder();
		modelBatch = new ModelBatch();
		
		Model grid = modelBuilder.createLineGrid(10, 10, 500, 500, new Material(ColorAttribute.createDiffuse(Color.RED)), Usage.Position | Usage.Normal | Usage.TextureCoordinates);
		instances.add(new ModelInstance(grid, 0, 0, 0));
		
		Game.writeLogs("INFO", "shapecontroller loaded");
	}
	
	public void createSphere(float x, float y, float z, int lat, int lon) {
		Model sphere = modelBuilder.createSphere(x, y, z, lat, lon, new Material(ColorAttribute.createDiffuse(Color.BLUE)), Usage.Position | Usage.Normal | Usage.TextureCoordinates);
		instances.add(new ModelInstance(sphere, 10f, 10f, 10f));
		
		Game.writeLogs("INFO", "sphere created");
	}
	
	public Model createmodSphere() {
		/*
		 * This method creates a sphere and hands that model to a deformer, which deforms the sphere
		 * and then adds it to the instance pile for rendering
		 */
		return modelBuilder.createSphere(10f, 10f, 10f, 16, 16, new Material(ColorAttribute.createDiffuse(Color.BLUE)), Usage.Position | Usage.Normal | Usage.TextureCoordinates);
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
