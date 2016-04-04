package com.deepsea;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;

public class Game extends ApplicationAdapter {

	//controllers
	public PerspectiveCamera cam;
	public FirstPersonCameraController camCon;
	public AssetLoader assetLoader;
	
	//files and logging
	public static FileHandle logFile;
	
	//environment
	public Environment ambient;
	
	
	//workers
	public ShapeController shapeController;
	
	//game vars
	private int gamePXbits = 32;
	private int xwidth = 1024;
	private int xheight = 768;
	private boolean xfullscreen = false;
	private boolean xvsync = false;
	
	@Override
	public void create () {
		logFile = Gdx.files.local("log.txt");
		writeLogs("BOOT", "Game entry point");
		
		writeLogs("INFO", "resizing screen");
		Gdx.graphics.setWindowedMode(xwidth, xheight);
		Gdx.graphics.setVSync(xvsync);
		
		writeLogs("INFO", "loading assets");
		assetLoader = new AssetLoader(gamePXbits);
		
		writeLogs("INFO", "creating camera");
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(0, 0, 0);
		cam.lookAt(0, 0, 0);
		cam.near = 1f;
		cam.far = 100f;
		cam.update();
		
		writeLogs("INFO", "creating camera controller");
		camCon = new FirstPersonCameraController(cam);
		Gdx.input.setInputProcessor(camCon);
		
		writeLogs("INFO", "creating lights");
		ambient = new Environment();
		ambient.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1.f));
		ambient.add(new DirectionalLight().set(1, 1, 1, 0, -1, 0));
		
		writeLogs("INFO", "creating world");
		shapeController = new ShapeController(cam, ambient);
		shapeController.createSphere();
		
		writeLogs("INFO", "creating environment");
		
		writeLogs("INFO", "creating sounds");
		
		writeLogs("INFO", "creating other");
		
	}

	@Override
	public void render () {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//clear screen
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		//various renderers
		shapeController.render();
		
		cam.update();
		camCon.update();
		
		
	}

	@Override
	public void resize(int width, int height) {
		writeLogs("INFO", "resizing window");
		cam.viewportWidth = width;
		cam.viewportHeight = height;
		cam.update();
	}
	
	@Override
	public void dispose() {
		writeLogs("INFO", "disposing objects");
		
	}
	
	public static void writeLogs(String tag, String log) {
		Gdx.app.log(tag, log);
		logFile.writeString(tag + " : " + log + "\n", true);
	}

}
