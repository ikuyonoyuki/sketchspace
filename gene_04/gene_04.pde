// Minim を使用する準備です
import ddf.minim.analysis.*;
import ddf.minim.*;
Minim minim;

AudioInput in;
FFT fft;


int NUM = 1000;
ParticleVec2[] particles = new ParticleVec2[NUM];

int wide =4;
float moverote = 0;


void setup(){
  size(1400,640,P3D);
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
   //パーティクルをインスタンス化
   particles[i] = new ParticleVec2();
   //初期位置を設定
   particles[i].position.set(0, random(height/3));
   //摩擦力を設定
   particles[i].friction = 0.002;
   //質量を設定
    particles[i].mass = 1.0;
   //半径を設定
   particles[i].radius = 1.5;
   //円形にランダムになるよう力を加える
   float length = random(1);
   float angle = random(-PI/2,0);
   particles[i].addForce(new PVector(length*cos(angle), length*sin(angle)));
 }
 */
}

void draw(){
  //background(0);
  fill(0, 15);
  rect(width/2, height/2, width, height);
  // 以下描画処理


  //FFT analysis
  fft.forward(in.mix);
  int specSize = fft.specSize()/5;
  int center = height/2;
  strokeWeight(1);
  stroke(250,100,100);
  float max = 0;
  /*
  for (int i = 0; i < specSize; i++){
    // x をスペクトラム幅に応じた位置として取得します
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
       alph = int(map(fft.getBand(i),0,100,0,80));
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
    //パーティクルの位置を更新
    particles[i].update();
    //パーティクルを描画
    particles[i].draw();
  }
  drawband();
  saveFrame("frames/######.tif");
  */




}

moverote += 0.003;
//saveFrame("frames/######.tif");
}


void stop(){
  minim.stop();
  super.stop();
}

void drawband(){
  float interval = height/3;
  stroke(0,0,255);
  strokeWeight(0.2);
  line(0,interval*0,width,interval*0);
  line(0,interval*1,width,interval*1);
  line(0,interval*2,width,interval*2);
}


//-------------- class ---------------///

//パーティクルクラス
class ParticleVec2 {
  PVector position;        //位置
  PVector velocity;       //速度
  PVector acceleration;   //加速度
  float radius;           //パーティクルの半径
  float friction;         //摩擦
  float mass;             //質量

  //コンストラクター
  ParticleVec2() {
    //初期パラメーターを設定
    radius = 4.0;
    position = new PVector(width/2.0, height/2.0);
    velocity = new PVector(0, 0);
    acceleration = new PVector(0, 0);
    friction = 0.0;
  }

  void addForce(PVector force) {
    //質量から加速度を計算 (a = f/m);
    force.div(mass);
    acceleration.add(force);
  }

  //座標の更新
  void update() {
    velocity.add(acceleration);     //速度に加速度を加算
    velocity.mult(1.0 - friction);  //摩擦力を加味した速度を計算
    position.add(velocity);         //速度から位置を算出
    acceleration.set(0, 0);         //加速度をリセット
  }

  //パーティクルを描画
  void draw() {
    ellipse(position.x, position.y, radius * 2, radius * 2);
  }
}
