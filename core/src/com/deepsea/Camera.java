package com.deepsea;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;

public class Camera {

	public PerspectiveCamera cam;
	
	public Camera(int i, int j, int k) {
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(i, j, k);
		cam.lookAt(10, 0, 10);
		cam.near = 1f;
		cam.far = 100f;
		cam.update();
	}
	
	
	
}
