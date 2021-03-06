package com.deepsea;

import java.util.Iterator;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.utils.Array;

public class ShapeController {

	public ModelBuilder modelBuilder;
	public MeshBuilder meshBuilder;
	public MeshPartBuilder meshPartBuilder;
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
		this.meshBuilder = new MeshBuilder();
		
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
	
	public Model createRock(Vector3 coords, Vector2 size) {
		modelBuilder.begin();
		meshPartBuilder = modelBuilder.part("sphere", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal, new Material(ColorAttribute.createDiffuse(Color.RED), ColorAttribute.createSpecular(1, 1, 1, 1), FloatAttribute.createShininess(8f)));
		meshPartBuilder.sphere((float)coords.x, (float)coords.y, (float)coords.z, (int)size.x, (int)size.y);
		System.out.println(meshPartBuilder.getAttributes());
		Model sph = modelBuilder.end();
		
		instances.add(new ModelInstance(sph, (float)coords.x, (float)coords.y, (float)coords.z));
		
		Game.writeLogs("INFO", "rock created");
		
		return sph;
	}
	
	public Model createRock2(Vector3 coords, Vector2 size) {
		modelBuilder.begin();
		
		Mesh rock = new Mesh(true, 8, 8, new VertexAttribute(Usage.Position, 3, "a_position"),
				new VertexAttribute(Usage.ColorPacked, 4, "a_color")
				);
		rock.setVertices(new float[] {
				0, 0, 0, Color.toFloatBits(0, 255, 0, 255),
				0, 1, 0, Color.toFloatBits(0, 255, 0, 255),
				1, 0, 0, Color.toFloatBits(0, 255, 0, 255),
				0, 0, 1, Color.toFloatBits(0, 255, 0, 255),
				1, 1, 0, Color.toFloatBits(0, 255, 0, 255),
				0, 1, 1, Color.toFloatBits(0, 255, 0, 255),
				1, 0, 1, Color.toFloatBits(0, 255, 0, 255),
				1, 1, 1, Color.toFloatBits(0, 255, 0, 255)
		});
		rock.setIndices(new short[] {
				0, 1, 2, 3, 4, 5, 6, 7
		});
		Model mrock = modelBuilder.end();
		
		instances.add(new ModelInstance(mrock, (float)coords.x, (float)coords.y, (float)coords.z));
		
		Game.writeLogs("INFO", "rock2 created");
		
		return mrock;
	}
	
	public void deformModel(Model object) {
		/*
		 * This method creates a sphere and hands that model to a deformer, which deforms the sphere
		 * and then adds it to the instance pile for rendering
		 */
		Array<Node> nodes = object.nodes;
		
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
