package com.deepsea;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class Game extends ApplicationAdapter {

	//controllers
	public PerspectiveCamera cam;
	public FirstPersonCameraController camCon;
	
	//environment
	public Environment lights;
	
	
	//game vars
	public static Vector3 playerStartpos;
	public static Vector3 playerStartlook;
	
	@Override
	public void create () {
		//create the camera
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.near = 0.5f;
		cam.far = 100f;
		playerStartpos = new Vector3(0, 0, 0);
		playerStartlook = new Vector3(10, 0, 10);
		cam.position.set(playerStartpos);
		cam.lookAt(playerStartlook);
		cam.update();
		
		//create the camera controller and input
		camCon = new FirstPersonCameraController(cam);
		Gdx.input.setInputProcessor(camCon);
		
		//create lights
		lights = new Environment();
		lights.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1.f));
		lights.add(new DirectionalLight().set(1, 1, 1, 0, -1, 0));
		
		MathUtils.random.setSeed(0);
		
	}

	@Override
	public void render () {
		//clear screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		cam.update();
		camCon.update();
	}

	@Override
	public void resize(int width, int height) {
		cam.viewportWidth = width;
		cam.viewportHeight = height;
		cam.update();
	}

}
