
int xgrid = 154;
int ygrid = 3;
float ywide;
float xwide;
int[][] rectalph;
//14*3
int rectNum = xgrid*ygrid;
float angle = 0;

DanceRect[] dRects =  new DanceRect[rectNum];;

void setup(){
  size(2220,640,P3D);
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

      dRects[ygrid*i+j].alpha = int(map(random(i),0,xgrid,0,255));
    }
  }

  for(int k =0 ;k<rectNum;k++){
    dRects[k].draw();
  }

}



void draw(){
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


  void update(){
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


  void draw(){
    noStroke();
    fill(255,alpha);
    rect(centerx,centery,xw,yw);
  }


}
