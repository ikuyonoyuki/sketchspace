// Minim を使用する準備です
import ddf.minim.analysis.*;
import ddf.minim.*;
Minim minim;

AudioInput in;
FFT fft;


int NUM = 300;
ParticleVec2[] particles = new ParticleVec2[NUM];
int lengthLimit = 36; // 距離制限

int wide =4;
float moverote = 0;


void setup(){
  size(2220,213,P3D);
  frameRate(30);

  rectMode(CENTER);
  noStroke();
  background(0);


  for (int i = 0; i < NUM; i++) {
   //パーティクルをインスタンス化
   particles[i] = new ParticleVec2();
   //初期位置を設定
   particles[i].position.set(random(width), random(height));
   //摩擦力を設定
   particles[i].friction = 0.0002;
   //質量を設定
    particles[i].mass = 1.0;
   //半径を設定
   particles[i].radius = 3.5;
   particles[i].lifespan = 0.007 + random(0.001,0.003);

   //円形にランダムになるよう力を加える
   float length = random(1,2);
   float angle = 0;
   particles[i].force.set(length*cos(angle),length*sin(angle));
   particles[i].addForce();
 }


}




void draw(){
  background(0);
  //fill(0, 15);
  //rect(width/2, height/2, width, height);


  //---------Particle ---------

  for (int i = 0; i < NUM; i++) {
    //パーティクルの位置を更新
    particles[i].update();
    //パーティクルを描画
    particles[i].draw();
  }

  for(int k = 0; k < particles.length; k++){
    ParticleVec2 fromP = particles[k];
    // 近くの点を格納するArrayList
    ArrayList<ParticleVec2> nearPs = new ArrayList<ParticleVec2>();
    for(int j = 0; j < particles.length; j++){
      ParticleVec2 toP = particles[j];
      // fromPとtoPの距離を計算
      float dist = dist(fromP.position.x, fromP.position.y, toP.position.x, toP.position.y);
      // lengthLimit以下ならnearPsに追加
      if(dist < lengthLimit){
        nearPs.add(toP);
      }
    }
    // 多角形を描画
    strokeWeight(0.5);
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
  PVector force;
  float life;
  float lifespan;

  //コンストラクター
  ParticleVec2() {
    //初期パラメーターを設定
    radius = 4.0;
    position = new PVector(width/2.0, height/2.0);
    velocity = new PVector(0, 0);
    acceleration = new PVector(0, 0);
    friction = 0.0;
    life = 1.0;
    lifespan = 0.0;
    force = new PVector(0.0,0.0);
  }

  void addForce() {
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

  //パーティクルを描画
  void draw() {
    int alpha = int(map(life,0,1,0,255));
    noStroke();
    fill(255,alpha);
    ellipse(position.x, position.y, radius * 1, radius * 1);
  }
}
