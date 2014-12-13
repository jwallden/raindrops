package com.joakimwallden.raindrops;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class RaindropsGame extends ApplicationAdapter {
	
	private Texture dropImage;
	private Texture bucketImage;
	private Sound dropSound;
	private Music rainMusic;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Rectangle bucket;
	private Vector3 touchPos;
	private Array<Rectangle> raindrops;
	private long lastDropTime;
	private BitmapFont scoreBoard;
	private BitmapFont missedBoard;
	private int score;
	private int missed;
	
	@Override
	public void create () {
		dropImage = new Texture(Gdx.files.internal("droplet.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));
		
		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
		
		rainMusic.setLooping(true);
		rainMusic.play();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		batch = new SpriteBatch();
		
		bucket = new Rectangle();
		bucket.x = 800/2 - 64/2;
		bucket.y = 20;
		bucket.width = 64;
		bucket.height = 64;
		
		touchPos = new Vector3();
		
		raindrops = new Array<Rectangle>();
		spawnRaindrop();
		
		score = 0;
		missed = 0;
		
		scoreBoard = new BitmapFont();
		scoreBoard.setColor(Color.RED);
		scoreBoard.setScale(1.1f);
		
		missedBoard = new BitmapFont();
		missedBoard.setColor(Color.RED);
		missedBoard.setScale(1.1f);
		
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(bucketImage, bucket.x, bucket.y);
		for (Rectangle raindrop : raindrops){
			batch.draw(dropImage, raindrop.x, raindrop.y);
		}
		scoreBoard.draw(batch, "Drops: " + score, 10, 470);
		missedBoard.draw(batch, "Missed: " + missed, 700, 470);
		batch.end();
		
		if (Gdx.input.isTouched()){
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(),0);
			camera.unproject(touchPos);
			bucket.x = touchPos.x - 64/2;
		}
		
		if (Gdx.input.isKeyPressed(Keys.LEFT)) bucket.x -= 400 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) bucket.x += 400 * Gdx.graphics.getDeltaTime();
		
		if (bucket.x < 0) bucket.x = 0;
		if (bucket.x > 800 - 64) bucket.x = 800 - 64;
		
		if (TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();
		
		//Iterator<Rectangle> iter = raindrops.iterator();
		//while (iter.hasNext()){
			//Rectangle raindrop = iter.next();
		for (Rectangle raindrop : raindrops){	
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if (raindrop.y + 64 < 0){
				missed += 1;
				System.out.println(raindrops.removeValue(raindrop, true));
				//iter.remove();
			}
			if (raindrop.overlaps(bucket)){
				dropSound.play();
				raindrops.removeValue(raindrop, true);
				//iter.remove();
				score += 1;
			}
		}
	}
	
	private void spawnRaindrop(){
		 Rectangle raindrop = new Rectangle();
		 raindrop.x = MathUtils.random(0, 800-64);
		 raindrop.y = 480;
		 raindrop.width = 64;
		 raindrop.height = 64;
		 raindrops.add(raindrop);
		 lastDropTime = TimeUtils.nanoTime();
	}
	
	@Override
	public void dispose(){
		dropImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
		batch.dispose();
	}
}
