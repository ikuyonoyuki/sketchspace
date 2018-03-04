float rl = 430;
float rs = 184;
float p = 45;
float l ,k;
float t ;
float x,y;
int NUM = 200000;

color[] colorpallet = new color[5];

void setup(){
  size(800,700);
  background(28,20,20);
  smooth();
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
  color c = colorpallet[0];


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
    strokeWeight(0.25);
    point(x,y);
    //rect(x,y,100,100);
  }
}