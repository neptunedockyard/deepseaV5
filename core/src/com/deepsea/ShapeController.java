package com.deepsea;

import java.util.Iterator;

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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.utils.Array;

public class ShapeController {

	public ModelBuilder modelBuilder;
	public MeshPartBuilder meshBuilder;
	public ModelBatch modelBatch;
	public Array<ModelInstance> instances = new Array<ModelInstance>();
	
	//inherited
	public PerspectiveCamera _camera;
	public Environment _lights;
	
	public ShapeController(PerspectiveCamera camera, Environment lights, int world_size_x, int world_size_y, int world_size_z) {
		/*
		 * This method creates the shape controller object that allows the game to 
		 * generate different models, whether they are loaded models or procedurally 
		 * generated models.
		 * It inherits the camera and environment objects, which are used by the renderer.
		 */
		this._camera = camera;
		this._lights = lights;
		this.modelBuilder = new ModelBuilder();
		this.modelBatch = new ModelBatch();
		
		Model grid = modelBuilder.createLineGrid(world_size_x, world_size_y, 5, 5, new Material(ColorAttribute.createDiffuse(Color.RED)), Usage.Position | Usage.Normal | Usage.TextureCoordinates);
		instances.add(new ModelInstance(grid, 0, 0, 0));
		
		Game.writeLogs("INFO", "shapecontroller loaded");
	}
	
	public Model createSphere(Vector3 coords, Vector2 size) {
		Model sphere = modelBuilder.createSphere(coords.x, coords.y, coords.z, (int)size.x, (int)size.y, new Material(ColorAttribute.createDiffuse(Color.BLUE)), Usage.Position | Usage.Normal | Usage.TextureCoordinates);
		instances.add(new ModelInstance(sphere, 10f, 10f, 10f));
		
		Game.writeLogs("INFO", "sphere created");
		
		return sphere;
	}
	
	public void deformModel(Model object) {
		/*
		 * This method creates a sphere and hands that model to a deformer, which deforms the sphere
		 * and then adds it to the instance pile for rendering
		 */
		Array<Node> nodes = object.nodes;
		
		for(Iterator<Node> iterator = nodes.iterator(); iterator.hasNext();) {
			Node node = (Node) iterator.next();
			nodes.add(node);
		}
		Game.writeVars("Nodes size: " + nodes.size);
		
		//return modelBuilder.createSphere(10f, 10f, 10f, 16, 16, new Material(ColorAttribute.createDiffuse(Color.BLUE)), Usage.Position | Usage.Normal | Usage.TextureCoordinates);
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
