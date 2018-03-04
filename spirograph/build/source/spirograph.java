import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class spirograph extends PApplet {

float rl = 430;
float rs = 184;
float p = 45;
float l ,k;
float t ;
float x,y;
int NUM = 200000;

int[] colorpallet = new int[5];


public void setup(){
  
  background(28,20,20);
  
  translate(width/2,height/2);
  rectMode(CENTER);
  //blendMode(ADD);
  colorpallet[0] = color(49,253,184);
  colorpallet[1] = color(26,167,227);
  colorpallet[2] = color(42,60,250);
  colorpallet[3] = color(136,26,227);
  colorpallet[4] = color(253,51,185);

  t = 0;
  l = p /rs;
  k = rs / rl;
  int cnum = 0;
  int c = colorpallet[0];


  for(int i=0;i<NUM;i++){
    float tt1 = map(i,0,NUM,0,100*PI);
    float tt2 = tt1*(1-k)/k;
    x = rl * ((1-k)*cos(tt1) + l*k*cos(tt2));
    y = rl * ((1-k)*sin(tt1) - l*k*sin(tt2));
    noFill();
    if(i%(NUM/5)==0){
       c = colorpallet[cnum];
      cnum ++;
    }

    stroke(c);
    strokeWeight(0.25f);
    point(x,y);
    //rect(x,y,100,100);
  }


}

public void draw(){
  /*
  background(20,20,28);

  pushMatrix();
  translate(width/2,height/2);
  float nn = 2000;
  for(int i=0;i<nn;i++){
    float td = map(i,0,nn,0,PI*4);
    float tstart = t + td ;
    float tt2 = tstart*(1-k)/k;
    x = rl * ((1-k)*cos(tstart) + l*k*cos(tt2));
    y = rl * ((1-k)*sin(tstart) - l*k*sin(tt2));
    noFill();
    stroke(63,204,176);
    strokeWeight(0.2);
    //point(x,y);
    rect(x,y,100,100);
  }


  t += 0.01;
  popMatrix();
  */

}
  public void settings() {  size(800,700);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "spirograph" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
