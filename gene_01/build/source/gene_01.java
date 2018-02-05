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

public class gene_01 extends PApplet {


Line line1,line2;

LineSagment s1 ,s2;

public void setup(){
  
  background(22,22,28);
  
  line1 = fromPoints(0,1,6,4);
  line2 = fromPoints(3,6,5,0);
  PVector p = line1.getIntersectionPoint(line2);
  line1.viewConsole();
  line2.viewConsole();
  println("intersection = " + p);

  s1 = new LineSagment(random(width),random(height),random(width),random(height));
  s2 = new LineSagment(random(width),random(height),random(width),random(height));
}

public void draw(){
  background(22,22,28);
  s1.draw();
  s2.draw();
  PVector p = s1.getIntersectionSegmentPoint(s2);
  if(s1.intersectsSegment(s2)){
  fill(200,30,70);
  noStroke();
  ellipse(p.x,p.y,6,6);
  }

  if(mousePressed == true){
    if(dist(s1.x1,s1.y1,mouseX,mouseY)<=15){
      s1.x1 = mouseX;
      s1.y1 = mouseY;
    }
    if(dist(s1.x2,s1.y2,mouseX,mouseY)<=15){
      s1.x2 = mouseX;
      s1.y2 = mouseY;
    }
    if(dist(s2.x1,s2.y1,mouseX,mouseY)<=15){
      s2.x1 = mouseX;
      s2.y1 = mouseY;
    }
    if(dist(s2.x2,s2.y2,mouseX,mouseY)<=15){
      s2.x2 = mouseX;
      s2.y2 = mouseY;
    }
  }
}


public Line fromPoints(float x1,float y1,float x2,float y2){
  float dx = x2 - x1;
  float dy = y2 - y1;
  return new Line(dy,-dx,dx*y1 - dy*x1);


}




/****************************
     LineSegment Class
*****************************/

class LineSagment{
  float x1,y1,x2,y2;

  LineSagment(float _x1,float _y1,float _x2,float _y2){
    x1 = _x1;
    y1 = _y1;
    x2 = _x2;
    y2 = _y2;
  }

  public void draw(){
    stroke(255);
    strokeWeight(0.5f);
    line(x1,y1,x2,y2);
  }

  public void viewConsole(){
    println("(" + x1 + "," + y1 + ") - (" + x1 + "," + y1 + ")");
  }

  public Line toLine() {
    return  fromPoints(x1, y1, x2, y2);
  }

  public boolean intersectsLine(Line l){
    float t1 = l.a*x1 + l.b*y1 + l.c;
    float t2 = l.a*x2 + l.b*y2 + l.c;
    return t1*t2 <= 0;
  }
  public boolean intersectsSegment(LineSagment s){
    return intersectsLine(s.toLine()) && s.intersectsLine(toLine());
  }

  public PVector getIntersectionLinePoint(Line l){
    if(!intersectsLine(l)){
      return null;
    }
    return l.getIntersectionPoint(toLine());
  }

  public PVector getIntersectionSegmentPoint(LineSagment s){
    if(!intersectsSegment(s)){
      return null;
    }
    return s.toLine().getIntersectionPoint(toLine());
  }


}


/****************************
         Line Class
*****************************/

class Line{
  float a,b,c;

  Line(float _a,float _b, float _c){
    a = _a;
    b = _b;
    c = _c;
  }

  public PVector getIntersectionPoint(Line l){
    float d = a*l.b - l.a * b;
    if(d == 0.0f){
      return null;
    }
    float x = (b * l.c - l.b * c) / d;
    float y = (l.a * c - a * l.c) / d;
    return new PVector(x, y);
  }

  public void viewConsole(){
    println("a= " + a + ", b= " + b + ", c= " + c);
  }
}
  public void settings() {  size(600,400);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "gene_01" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
