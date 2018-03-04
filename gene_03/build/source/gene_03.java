import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.analysis.*; 
import ddf.minim.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class gene_03 extends PApplet {

// Minim \u3092\u4f7f\u7528\u3059\u308b\u6e96\u5099\u3067\u3059


Minim minim;

AudioInput in;
FFT fft;


int NUM = 300;
ParticleVec2[] particles = new ParticleVec2[NUM];
int lengthLimit = 36; // \u8ddd\u96e2\u5236\u9650

int wide =4;
float moverote = 0;


public void setup(){
  
  frameRate(30);

  rectMode(CENTER);
  noStroke();
  background(0);


  for (int i = 0; i < NUM; i++) {
   //\u30d1\u30fc\u30c6\u30a3\u30af\u30eb\u3092\u30a4\u30f3\u30b9\u30bf\u30f3\u30b9\u5316
   particles[i] = new ParticleVec2();
   //\u521d\u671f\u4f4d\u7f6e\u3092\u8a2d\u5b9a
   particles[i].position.set(random(width), random(height));
   //\u6469\u64e6\u529b\u3092\u8a2d\u5b9a
   particles[i].friction = 0.0002f;
   //\u8cea\u91cf\u3092\u8a2d\u5b9a
    particles[i].mass = 1.0f;
   //\u534a\u5f84\u3092\u8a2d\u5b9a
   particles[i].radius = 3.5f;
   particles[i].lifespan = 0.007f + random(0.001f,0.003f);

   //\u5186\u5f62\u306b\u30e9\u30f3\u30c0\u30e0\u306b\u306a\u308b\u3088\u3046\u529b\u3092\u52a0\u3048\u308b
   float length = random(1,2);
   float angle = 0;
   particles[i].force.set(length*cos(angle),length*sin(angle));
   particles[i].addForce();
 }


}




public void draw(){
  background(0);
  //fill(0, 15);
  //rect(width/2, height/2, width, height);


  //---------Particle ---------

  for (int i = 0; i < NUM; i++) {
    //\u30d1\u30fc\u30c6\u30a3\u30af\u30eb\u306e\u4f4d\u7f6e\u3092\u66f4\u65b0
    particles[i].update();
    //\u30d1\u30fc\u30c6\u30a3\u30af\u30eb\u3092\u63cf\u753b
    particles[i].draw();
  }

  for(int k = 0; k < particles.length; k++){
    ParticleVec2 fromP = particles[k];
    // \u8fd1\u304f\u306e\u70b9\u3092\u683c\u7d0d\u3059\u308bArrayList
    ArrayList<ParticleVec2> nearPs = new ArrayList<ParticleVec2>();
    for(int j = 0; j < particles.length; j++){
      ParticleVec2 toP = particles[j];
      // fromP\u3068toP\u306e\u8ddd\u96e2\u3092\u8a08\u7b97
      float dist = dist(fromP.position.x, fromP.position.y, toP.position.x, toP.position.y);
      // lengthLimit\u4ee5\u4e0b\u306a\u3089nearPs\u306b\u8ffd\u52a0
      if(dist < lengthLimit){
        nearPs.add(toP);
      }
    }
    // \u591a\u89d2\u5f62\u3092\u63cf\u753b
    strokeWeight(0.5f);
    stroke(40,30,232);
    noFill();
    beginShape();
    for(ParticleVec2 p : nearPs){
      vertex(p.position.x, p.position.y, p.position.z);
    }
    endShape(CLOSE);
  }


//saveFrame("frames/######.tif");
}


public void stop(){
  minim.stop();
  super.stop();
}

public void drawband(){
  float interval = height/3;
  stroke(0,0,255);
  strokeWeight(0.2f);
  line(0,interval*0,width,interval*0);
  line(0,interval*1,width,interval*1);
  line(0,interval*2,width,interval*2);
}


//-------------- class ---------------///

//\u30d1\u30fc\u30c6\u30a3\u30af\u30eb\u30af\u30e9\u30b9
class ParticleVec2 {
  PVector position;        //\u4f4d\u7f6e
  PVector velocity;       //\u901f\u5ea6
  PVector acceleration;   //\u52a0\u901f\u5ea6
  float radius;           //\u30d1\u30fc\u30c6\u30a3\u30af\u30eb\u306e\u534a\u5f84
  float friction;         //\u6469\u64e6
  float mass;             //\u8cea\u91cf
  PVector force;
  float life;
  float lifespan;

  //\u30b3\u30f3\u30b9\u30c8\u30e9\u30af\u30bf\u30fc
  ParticleVec2() {
    //\u521d\u671f\u30d1\u30e9\u30e1\u30fc\u30bf\u30fc\u3092\u8a2d\u5b9a
    radius = 4.0f;
    position = new PVector(width/2.0f, height/2.0f);
    velocity = new PVector(0, 0);
    acceleration = new PVector(0, 0);
    friction = 0.0f;
    life = 1.0f;
    lifespan = 0.0f;
    force = new PVector(0.0f,0.0f);
  }

  public void addForce() {
    //\u8cea\u91cf\u304b\u3089\u52a0\u901f\u5ea6\u3092\u8a08\u7b97 (a = f/m);
    force.div(mass);
    acceleration.add(force);
  }

  //\u5ea7\u6a19\u306e\u66f4\u65b0
  public void update() {
    velocity.add(acceleration);     //\u901f\u5ea6\u306b\u52a0\u901f\u5ea6\u3092\u52a0\u7b97
    velocity.mult(1.0f - friction);  //\u6469\u64e6\u529b\u3092\u52a0\u5473\u3057\u305f\u901f\u5ea6\u3092\u8a08\u7b97
    position.add(velocity);         //\u901f\u5ea6\u304b\u3089\u4f4d\u7f6e\u3092\u7b97\u51fa
    acceleration.set(0, 0);         //\u52a0\u901f\u5ea6\u3092\u30ea\u30bb\u30c3\u30c8
    if(position.x > width){
      position.x = radius;
    }
    life -= lifespan;
    if(life <= 0){
      position.x = random(width);
      position.y = random(height);
      lifespan *= -1;
      friction *= -1;
    } else if(life >1){
      lifespan *= -1;
      friction *= -1;
    }
  }

  //\u30d1\u30fc\u30c6\u30a3\u30af\u30eb\u3092\u63cf\u753b
  public void draw() {
    int alpha = PApplet.parseInt(map(life,0,1,0,255));
    noStroke();
    fill(255,alpha);
    ellipse(position.x, position.y, radius * 1, radius * 1);
  }
}



//screenshot
int count = 1;

public void keyPressed() {

  // P\u306e\u30ad\u30fc\u304c\u5165\u529b\u3055\u308c\u305f\u6642\u306b\u4fdd\u5b58
  if(key == 'p' || key == 'P') {

    // \u30c7\u30b9\u30af\u30c8\u30c3\u30d7\u306e\u30d1\u30b9\u3092\u53d6\u5f97
    String path  = System.getProperty("user.home") + "/Desktop/screenshot" + count + ".jpg";

    // \u4fdd\u5b58
    save(path);

    // \u756a\u53f7\u3092\u52a0\u7b97
    count++;

    // \u30ed\u30b0\u7528\u9014
    println("screen saved." + path);
  }
}
  public void settings() {  size(300,300,P3D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "gene_03" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
