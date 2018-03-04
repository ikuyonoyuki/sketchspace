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

public class gene_02 extends PApplet {


int xgrid = 154;
int ygrid = 3;
float ywide;
float xwide;
int[][] rectalph;
//14*3
int rectNum = xgrid*ygrid;
float angle = 0;

DanceRect[] dRects =  new DanceRect[rectNum];;

public void setup(){
  
  frameRate(60);
  //colorMode(ADD);
  //smooth(32);
  rectMode(CENTER);
  noStroke();
  background(0);
  ywide = floor(height/ygrid);
  xwide = floor(width/xgrid);

  rectNum = xgrid * ygrid;

  for(int k =0 ;k<rectNum;k++){
    dRects[k] = new DanceRect();
    dRects[k].xw = xwide;
    dRects[k].yw = ywide;
  }

  for(int i=0; i<xgrid;i++){
    for(int j=0; j<ygrid;j++){
      dRects[ygrid*i+j].centerx = xwide/2 + xwide * i;
      dRects[ygrid*i+j].centery = ywide/2 + ywide * j;

      dRects[ygrid*i+j].alpha = PApplet.parseInt(map(random(i),0,xgrid,0,255));
    }
  }

  for(int k =0 ;k<rectNum;k++){
    dRects[k].draw();
  }

}



public void draw(){
  background(0);

  for(int k =0 ;k<rectNum;k++){
    dRects[k].update();
    dRects[k].draw();
  }
 //saveFrame("frames/######.tif");

}


class DanceRect{
  float centerx,centery;
  int alpha;
  float xw,yw;
  float dawnspeed;
  float angle;
  DanceRect(){
    centerx = 0;
    centery = 0;
    alpha = 0;
    xw = 0;
    yw = 0;
    dawnspeed = -1;
    angle = 0;
  }


  public void update(){
    if(alpha<=0){
       alpha  = 0;
       dawnspeed *= -1;
    }
    if(alpha >= 255){
      alpha  = 255;
      dawnspeed *= -1;
    }

    alpha += dawnspeed;
  //  println(sin(angle));


  }

  public void draw(){
    noStroke();
    fill(255,alpha);
    rect(centerx,centery,xw,yw);
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
    String[] appletArgs = new String[] { "gene_02" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
