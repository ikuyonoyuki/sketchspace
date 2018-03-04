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

public class gene_04 extends PApplet {

// Minim \u3092\u4f7f\u7528\u3059\u308b\u6e96\u5099\u3067\u3059


Minim minim;

AudioInput in;
FFT fft;


int NUM = 1000;
ParticleVec2[] particles = new ParticleVec2[NUM];

int wide =4;
float moverote = 0;


public void setup(){
  
  frameRate(30);
  //colorMode(ADD);
  rectMode(CENTER);
  background(0);

  minim = new Minim(this);

  in = minim.getLineIn(Minim.STEREO, 1024*2);
  //bufferSize = 1024 sampleRate = 44100
  fft = new FFT(in.bufferSize(), in.sampleRate());

//  for(int i = 0; i < fft.specSize(); i++){
//    println(i + " = " + fft.getBandWidth()*i + " ~ "+ fft.getBandWidth()*(i+1));
//  }


/*
  for (int i = 0; i < NUM; i++) {
   //\u30d1\u30fc\u30c6\u30a3\u30af\u30eb\u3092\u30a4\u30f3\u30b9\u30bf\u30f3\u30b9\u5316
   particles[i] = new ParticleVec2();
   //\u521d\u671f\u4f4d\u7f6e\u3092\u8a2d\u5b9a
   particles[i].position.set(0, random(height/3));
   //\u6469\u64e6\u529b\u3092\u8a2d\u5b9a
   particles[i].friction = 0.002;
   //\u8cea\u91cf\u3092\u8a2d\u5b9a
    particles[i].mass = 1.0;
   //\u534a\u5f84\u3092\u8a2d\u5b9a
   particles[i].radius = 1.5;
   //\u5186\u5f62\u306b\u30e9\u30f3\u30c0\u30e0\u306b\u306a\u308b\u3088\u3046\u529b\u3092\u52a0\u3048\u308b
   float length = random(1);
   float angle = random(-PI/2,0);
   particles[i].addForce(new PVector(length*cos(angle), length*sin(angle)));
 }
 */
}

public void draw(){
  //background(0);
  fill(0, 15);
  rect(width/2, height/2, width, height);
  // \u4ee5\u4e0b\u63cf\u753b\u51e6\u7406


  //FFT analysis
  fft.forward(in.mix);
  int specSize = fft.specSize()/5;
  int center = height/2;
  strokeWeight(1);
  stroke(250,100,100);
  float max = 0;
  /*
  for (int i = 0; i < specSize; i++){
    // x \u3092\u30b9\u30da\u30af\u30c8\u30e9\u30e0\u5e45\u306b\u5fdc\u3058\u305f\u4f4d\u7f6e\u3068\u3057\u3066\u53d6\u5f97\u3057\u307e\u3059
    float x = map(i, 0, specSize, 0, width);
    line(x, center, x, center - fft.getBand(i) * 4);
    line(x, center, x, center + fft.getBand(i) * 4);
    if(max < fft.getBand(i)){
      max = fft.getBand(i);
    }
  }
  println(max);
  */
  //--------FFT analysis fin ---------

  //println(specSize);

  for (int i = 0; i < specSize; i++){
    float x = map(i, 0, specSize, 0, width);

    //-------light rect-------
    /*
    int alph = 0;
    if(fft.getBand(i)<100){
       alph = int(map(fft.getBand(i),0,100,0,80));
    }else if(fft.getBand(i)>=100){
       alph = 100;
    }
    fill(255,alph);
    noStroke();
    rect(x,height/6,wide,noise(-30,30)+ height/3);
    */
    //*********light rect*******

    //-------light card -------
    pushMatrix();
    float rota = map(i,0,specSize,0,PI*3);
    translate(x,height/6);
    rotate(rota+moverote);


    int alph = 0;
    if(fft.getBand(i)<100){
       alph = PApplet.parseInt(map(fft.getBand(i),0,100,0,80));
    }else if(fft.getBand(i)>=100){
       alph = 100;
    }
    fill(255,alph);
    noStroke();
    rect(0,0,wide,height/3);
    popMatrix();

    //*********light card*******


  //---------Particle ---------
  /*
  fill(255);
  for (int i = 0; i < NUM; i++) {
    //\u30d1\u30fc\u30c6\u30a3\u30af\u30eb\u306e\u4f4d\u7f6e\u3092\u66f4\u65b0
    particles[i].update();
    //\u30d1\u30fc\u30c6\u30a3\u30af\u30eb\u3092\u63cf\u753b
    particles[i].draw();
  }
  drawband();
  saveFrame("frames/######.tif");
  */




}

moverote += 0.003f;
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

  //\u30b3\u30f3\u30b9\u30c8\u30e9\u30af\u30bf\u30fc
  ParticleVec2() {
    //\u521d\u671f\u30d1\u30e9\u30e1\u30fc\u30bf\u30fc\u3092\u8a2d\u5b9a
    radius = 4.0f;
    position = new PVector(width/2.0f, height/2.0f);
    velocity = new PVector(0, 0);
    acceleration = new PVector(0, 0);
    friction = 0.0f;
  }

  public void addForce(PVector force) {
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
  }

  //\u30d1\u30fc\u30c6\u30a3\u30af\u30eb\u3092\u63cf\u753b
  public void draw() {
    ellipse(position.x, position.y, radius * 2, radius * 2);
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
    String[] appletArgs = new String[] { "gene_04" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
