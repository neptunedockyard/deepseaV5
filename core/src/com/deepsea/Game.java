package com.deepsea;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class Game extends ApplicationAdapter {

	//controllers
	public PerspectiveCamera cam;
	public FirstPersonCameraController camCon;
	public ModelBuilder modelBuilder;
	public Model model;
	public ModelBatch modelBatch;
	public Array<ModelInstance> instances = new Array<ModelInstance>();
	public ModelInstance modInstance;
	
	//environment
	public Environment lights;
	
	
	//game vars
	
	
	@Override
	public void create () {
		//create the camera
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.near = 0.5f;
		cam.far = 100f;
		cam.position.set(0,0,3);
		cam.lookAt(0,0,0);
		cam.update();
		
		//create the camera controller and input
		camCon = new FirstPersonCameraController(cam);
		Gdx.input.setInputProcessor(camCon);
		
		//create lights
		lights = new Environment();
		lights.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1.f));
		lights.add(new DirectionalLight().set(1, 1, 1, 0, -1, 0));
		
		//create models
		modelBuilder = new ModelBuilder();
		model = modelBuilder.createSphere(2f, 2f, 2f, 20, 20, new Material(ColorAttribute.createDiffuse(Color.BLUE)), Usage.Position | Usage.Normal | Usage.TextureCoordinates);
		
		modInstance = new ModelInstance(model, 10, 10, 10);
		modelBatch = new ModelBatch();
	}

	@Override
	public void render () {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//clear screen
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		cam.rotateAround(Vector3.Zero, new Vector3(0,1,0),1f);
		cam.update();
		//camCon.update();
		
		modelBatch.begin(cam);
		modelBatch.render(modInstance, lights);
		modelBatch.end();
	}

	@Override
	public void resize(int width, int height) {
		cam.viewportWidth = width;
		cam.viewportHeight = height;
		cam.update();
	}
	
	@Override
	public void dispose() {
		model.dispose();
		modelBatch.dispose();
	}

}
