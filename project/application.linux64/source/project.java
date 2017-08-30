import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import moonlander.library.*; 
import ddf.minim.*; 
import java.util.Map; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class project extends PApplet {

/* 
 * Code for starting a demo project that
 * uses GNU Rocket and Moonlander for
 * syncing.
GLE *
 * You must install Moonlander as a library
 * into Processing before starting development.
 */


// Minim is needed for the music playback
// (even when using Moonlander)






// These control how big the opened window is.
// Before you release your demo, set these to 
// full HD resolution (1920x1080).
//static final int CANVAS_WIDTH = 1200;
//static final int CANVAS_HEIGHT = 800;

static final int CANVAS_WIDTH = 1920;
static final int CANVAS_HEIGHT = 1080;






// Our public Moonlander instance
Moonlander moonlander;




int xPlane = 300;
int yPlane = 300;
int xStart = 480;
int yStart = 270;

int state = 0;

int xS = 40;
int yS = 40;
int zS = 40;

Cylinder cylinde = new Cylinder(xPlane,yPlane);
Plane startPlane = new Plane(xStart,yStart,4);
Plane plane2d = new Plane(xPlane,yPlane,4);
Space space3d = new Space(xS,yS,zS);

//Creature[] Creatures = {player, gobbo, plump0, plump1, plump2, plump3, plump4, plump5, plump6};
//Creature[] Important = {player};

/*
 * Processing's setup method.
 *
 * Do all your one-time setup routines in here.
 */
public void setup() {
  // Set up the drawing area size and renderer (P2D / P3D).
  
  //surface.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
  frameRate(60);
  lights();
  // Parameters: 
  // - PApplet
  // - soundtrack filename (relative to sketch's folder)
  // - beats per minute in the song
  // - how many rows in Rocket correspond to one beat
  moonlander = Moonlander.initWithSoundtrack(this, "../graffa17_2.wav", 140, 16);
  
  //PC = loadImage("../textures/at0.png");
  // Last thing in setup; start Moonlander. This either
  // connects to Rocket (development mode) or loads data 
  // from 'syncdata.rocket' (player mode).
  // Also, in player mode the music playback starts immediately.
  moonlander.start();
  plane2d.set(xPlane/4,yPlane/4,5000);
  cylinde.set(xPlane/4,yPlane/4,5000); 
  space3d.set(xS/2,yS/2,zS/2,100);
  //camera();
}


/*
 * Processing's drawing method
 */
public void draw() {
  moonlander.update();

  // Draw something
  // Get values from Rocket using 
  // moonlander.getValue("track_name") or
  int r = moonlander.getIntValue("r");
  int g = moonlander.getIntValue("g");
  int b = moonlander.getIntValue("b");
  
  int beat = moonlander.getIntValue("beat");
  int phase = moonlander.getIntValue("phase");

  int x = moonlander.getIntValue("x");
  int y = moonlander.getIntValue("y");
  int z = moonlander.getIntValue("z");
  float s = PI * ( moonlander.getIntValue("spin") / 100.0f);

  int cDis = moonlander.getIntValue("cameraDis");
  int cSpi = moonlander.getIntValue("cameraSpin");

  float cameraAngle = PI * ( ( moonlander.getIntValue("angle") + cSpi ) / 100.0f);
  float cameraAngleY = PI * ( ( moonlander.getIntValue("angleY") + cSpi ) / 100.0f);

  //
  //camera( x, height/2 + y, (height/2) / tan(PI/6) + z, width/2, height/2, 0, 0, 1, 0);
  //camera( 0, height/2 , (height/2) / tan(PI/6) , width/2 + x , height/2 + y, z, 0, 1, 0);
  //camera( x, height/2 + y, (height/2) / tan(PI/6) + z, width/2 + x, height/2 + y, z, 0, 1, 0);

  //camera( 0, height/2 , (height/2) / tan(PI/6) , width/2, height/2, 0, 0, 1, 0);
  
  //camera( x, y-10, z, x-cameraPOS[1] * cDis, y-10+0.2, z-cameraPOS[0] * cDis, 0, 1, 0);
  float[] CPOS = { x +sin(cameraAngle) * cDis, y-cDis/2 + sin(cameraAngleY) * cDis, z +cos(cameraAngle) * cDis +cos(cameraAngleY) * cDis, };
  
  camera( CPOS[0], CPOS[1], CPOS[2], x, y, z, 0, 1, 0);
  
  /* 
  print(CPOS[0], CPOS[1], CPOS[2], x, y, z, 0, 1, 0,"->\n");
  print(mouseX*4- CANVAS_WIDTH, 
          mouseY*4- CANVAS_HEIGHT + height/2 , 
          (height/2) / tan(PI/6), 
          width/4, 
          height/4, 
          0, 0, 1, 0,"\n____\n");
  */
  /*
    camera( mouseX*4- CANVAS_WIDTH, 
          mouseY*4- CANVAS_HEIGHT + height/2 , 
          (height/2) / tan(PI/6), 
          width/4, 
          height/4, 
          0, 0, 1, 0);
  //*/


  //println(  0, height/2 , (height/2) / tan(PI/6) , width/2, height/2, 0, 0, 1, 0 );
  //camera( x, y, z, x, y, z, 0, 1, 0);
  //camera( 0, height/2 , (height/2) , width/2, height/2, 0, 0, 1, 0);  
  background(r,g,b);
  
  switch(phase) {
    case 0:
      if (beat > 0){
        if( addLetter()){
          state = 1;
        }
      }
      if (state == 0){
        startPlane.draw();
      }
      else{
        if (beat > 0){
          startPlane.set(PApplet.parseInt(random(xStart)),PApplet.parseInt(random(yStart)),beat*100);
        }
        startPlane.update();
      }
      break;
    case 1:
      if (beat > 0){
        startPlane.set(PApplet.parseInt(random(xStart)),PApplet.parseInt(random(yStart)),beat*200);
      }
      startPlane.update();
      break;
    case 2:
      if (beat > 0){
        startPlane.set(PApplet.parseInt(random(xStart)),PApplet.parseInt(random(yStart)),beat*200);
      }
      cylinde.update();
      break;
  }
  /*
  HashMap<Integer,Creature> hm = new HashMap<Integer,Creature>();
  for (int i = 0; i < Creatures.length; i++) {
    hm.put( int(dist(CPOS[0], CPOS[1], CPOS[2], Creatures[i].x, Creatures[i].y, Creatures[i].z)), Creatures[i]);
    }
  
  int[] sorted = {};
  for(Integer i : hm.keySet())
    sorted = append( sorted, int(i) );
  sorted = reverse(sort(sorted));
  
  for (int i = 0; i < sorted.length; i++) {
    int key = sorted[i];
    hm.get(key).update();
  }

  //sort(hm.keySet().toArray());   
  //Creatures[i].update();
  */

}
int[] cursor = {30,30,0,0};

int[][] w = {
{1,0,0,0,0,0,0,0,0,0,1},
{1,0,0,0,0,0,0,0,0,0,1},
{0,1,0,0,0,1,0,0,0,1,0},
{0,1,0,0,0,1,0,0,0,1,0},
{0,0,1,0,1,0,1,0,1,0,0},
{0,0,1,0,1,0,1,0,1,0,0},
{0,0,0,1,0,0,0,1,0,0,0},
};

int[][] a = {
{0,0,0,0,0,1,0,0,0,0,0},
{0,0,0,0,1,0,1,0,0,0,0},
{0,0,0,0,1,0,1,0,0,0,0},
{0,0,0,1,0,0,0,1,0,0,0},
{0,0,0,1,1,1,1,1,0,0,0},
{0,0,0,1,0,0,0,1,0,0,0},
{0,0,0,1,0,0,0,1,0,0,0},
};

int[][] e = {
{0,0,0,1,1,1,1,0,0,0},
{0,0,0,1,0,0,0,0,0,0},
{0,0,0,1,0,0,0,0,0,0},
{0,0,0,1,1,1,0,0,0,0},
{0,0,0,1,0,0,0,0,0,0},
{0,0,0,1,0,0,0,0,0,0},
{0,0,0,1,1,1,1,0,0,0},
};

int[][] s = {
{0,0,0,1,1,1,1,0,0,0},
{0,0,0,1,0,0,0,0,0,0},
{0,0,0,1,0,0,0,0,0,0},
{0,0,0,1,1,1,1,0,0,0},
{0,0,0,0,0,0,1,0,0,0},
{0,0,0,0,0,0,1,0,0,0},
{0,0,0,1,1,1,1,0,0,0},
};

int[][] dot = {
{0,0,0,0,0,0,0,0,0,0,0},
{0,0,0,0,0,0,0,0,0,0,0},
{0,0,0,0,0,0,0,0,0,0,0},
{0,0,0,0,0,0,0,0,0,0,0},
{0,0,0,0,0,0,0,0,0,0,0},
{0,0,1,0,0,0,0,0,0,0,0},
{0,0,1,0,0,0,0,0,0,0,0},
{0,1,0,0,0,0,0,0,0,0,0},
};

int[][] spc = {
{0,0,0,0,0,0,0,0,0,0,0},
{0,0,0,0,0,0,0,0,0,0,0},
{0,0,0,0,0,0,0,0,0,0,0},
{0,0,0,0,0,0,0,0,0,0,0},
{0,0,0,0,0,0,0,0,0,0,0},
{0,0,0,0,0,0,0,0,0,0,0},
{0,0,0,0,0,0,0,0,0,0,0},
};

int[][] i = {
{0,0,0,1,1,1,0,0,0},
{0,0,0,0,1,0,0,0,0},
{0,0,0,0,1,0,0,0,0},
{0,0,0,0,1,0,0,0,0},
{0,0,0,0,1,0,0,0,0},
{0,0,0,0,1,0,0,0,0},
{0,0,0,1,1,1,0,0,0},
};

int[][] g = {
{0,0,1,1,1,1,1,0,0},
{0,0,1,0,0,0,1,0,0,0},
{0,0,1,0,0,0,0,0,0,0},
{0,0,1,0,0,0,0,0,0,0},
{0,0,1,0,0,1,1,0,0,0},
{0,0,1,0,0,0,1,0,0,0},
{0,0,1,1,1,1,1,0,0,0},
};

int[][] u = {
{0,0,0,1,0,0,0,1,0,0,0},
{0,0,0,1,0,0,0,1,0,0,0},
{0,0,0,1,0,0,0,1,0,0,0},
{0,0,0,1,0,0,0,1,0,0,0},
{0,0,0,1,0,0,0,1,0,0,0},
{0,0,0,1,0,0,0,1,0,0,0},
{0,0,0,0,1,1,1,1,0,0,0},
};

int[][] r = {
{0,0,1,1,1,0,0,0},
{0,0,1,0,0,1,0,0,0,0,0},
{0,0,1,0,0,1,0,0,0,0,0},
{0,0,1,1,1,0,0,0,0,0,0},
{0,0,1,1,0,0,0,0,0,0,0},
{0,0,1,0,1,0,0,0,0,0,0},
{0,0,1,0,0,1,0,0,0,0,0},
};

int[][] f = {
{0,0,1,1,1,1,0,0,0,0,0},
{0,0,1,0,0,0,0,0,0,0,0},
{0,0,1,0,0,0,0,0,0,0,0},
{0,0,1,1,1,0,0,0,0,0,0},
{0,0,1,0,0,0,0,0,0,0,0},
{0,0,1,0,0,0,0,0,0,0,0},
{0,0,1,0,0,0,0,0,0,0,0},
};

int[][] t = {
{0,1,1,1,1,1,0,0,0,0,0},
{0,0,0,1,0,0,0,0,0,0,0},
{0,0,0,1,0,0,0,0,0,0,0},
{0,0,0,1,0,0,0,0,0,0,0},
{0,0,0,1,0,0,0,0,0,0,0},
{0,0,0,1,0,0,0,0,0,0,0},
{0,0,0,1,0,0,0,0,0,0,0},
};

int[][] h = {
{0,0,1,0,0,0,1,0,0,0,0},
{0,0,1,0,0,0,1,0,0,0,0},
{0,0,1,0,0,0,1,0,0,0,0},
{0,0,1,1,1,1,1,0,0,0,0},
{0,0,1,0,0,0,1,0,0,0,0},
{0,0,1,0,0,0,1,0,0,0,0},
{0,0,1,0,0,0,1,0,0,0,0},
};

int[][] n = {
{0,0,1,0,0,0,1,0,0,0,0},
{0,0,1,0,0,0,1,0,0,0,0},
{0,0,1,1,0,0,1,0,0,0,0},
{0,0,1,0,1,0,1,0,0,0,0},
{0,0,1,0,0,1,1,0,0,0,0},
{0,0,1,0,0,0,1,0,0,0,0},
{0,0,1,0,0,0,1,0,0,0,0},
};
int[][] o = {
{0,0,1,1,1,1,0,0,0,0,0},
{0,0,1,0,0,0,1,0,0,0,0},
{0,0,1,0,0,0,1,0,0,0,0},
{0,0,1,0,0,0,1,0,0,0,0},
{0,0,1,0,0,0,1,0,0,0,0},
{0,0,1,0,0,0,1,0,0,0,0},
{0,0,0,1,1,1,0,0,0,0,0},
};
int[][] n1 = {
{0,0,0,1,0,0,0},
{0,0,1,1,0,0,0,0,0,0,0},
{0,0,0,1,0,0,0,0,0,0,0},
{0,0,0,1,0,0,0,0,0,0,0},
{0,0,0,1,0,0,0,0,0,0,0},
{0,0,0,1,0,0,0,0,0,0,0},
{0,0,1,1,1,0,0,0,0,0,0},
};

int[][] n7 = {
{0,0,1,1,1,1,1,0,0,0,0},
{0,0,0,0,0,1,0,0,0,0,0},
{0,0,0,0,1,0,0,0,0,0,0},
{0,0,0,0,1,0,0,0,0,0,0},
{0,0,0,1,0,0,0,0,0,0,0},
{0,0,0,1,0,0,0,0,0,0,0},
{0,0,0,1,0,0,0,0,0,0,0},
};

int[][] l = {
{0,0,0,1,0,0,0,0},
{0,0,0,1,0,0,0,0},
{0,0,0,1,0,0,0,0},
{0,0,0,1,0,0,0,0},
{0,0,0,1,0,0,0,0},
{0,0,0,1,0,0,0,0},
{0,0,0,1,1,1,1,0},
};


int[][][][] text = {{w,a,w,e,s,dot,i,spc,g,u,e,s,s},
                    {g,r,a,f,f,a,t,h,o,n,n1,n7},
                    {e,a,r,l,o}};

public boolean addLetter() {
  if (cursor[3] < text.length){
    if (cursor[2] < text[cursor[3]].length){
      int[][] letter = text[cursor[3]][cursor[2]];
      //text = shorten(text);
      for (int j = 0; j < letter.length; j++) {
        for (int i = 0; i < letter[0].length; i++) {
          if (letter[j][i] == 1){
            try {
              startPlane.set(cursor[0]+i*3,cursor[1]+j*3,200);
            }
            catch (Exception e) {
              cursor[0] = 30;
              cursor[1] += letter.length;
            }
          }
        }
      }
      cursor[0] += letter[0].length + 20;
      cursor[2] += 1;
    }
    else{
      cursor[0] = 30;
      cursor[1] += 40;
      cursor[3] += 1;
      cursor[2] = 0;
    }
    return false;   
  }
  else{
    return true;
  }
}
class Cylinder {
	int[][][] Points;
	int rows;
	int cols;
	int stepper;
	int phases;
	Cylinder (int cols, int rows){
		this.rows = rows;
		this.cols = cols;
		this.stepper = 0;
		this.phases = 2;
		this.Points = new int[this.phases][cols][rows];
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				this.Points[0][i][j] = 0;
				this.Points[1][i][j] = 0;
				//this.Points[2][i][j] = 0;
			}
		}
	}
	public void set(int x, int y, int val) {
		Points[(this.stepper+1) % this.phases][x][y] = val;
	}

	public void update() {
		int old = this.stepper;
		this.stepper = (this.stepper+1) % this.phases;
		int cur = this.stepper;
		for (int i = 0; i < this.cols; i++) {
			pushMatrix();
			beginShape(POINTS);

			for (int j = 0; j < this.rows; j++) {
				//point(i, j, this.Points[cur][i][j]/100);
				stroke(0,0,0+this.Points[cur][i][j]/4);

				strokeWeight(1 + this.Points[cur][i][j]/12);
				int size = 200 + this.Points[cur][i][j];
				vertex(6*i, size*cos(j), size*sin(j));


				this.Points[old][i][j] = ((	
						this.Points[cur][i][(this.rows+j-1)%this.rows] + this.Points[cur][i][(j+1)%this.rows] +
						this.Points[cur][(this.cols+i-1)%this.cols][j] + this.Points[cur][(i+1)%this.cols][j] ) / 2) - this.Points[old][i][j];
			}
			endShape();
			popMatrix();
		}
	}
	public void draw() {
		int cur = (this.stepper+1) % this.phases;
		for (int i = 0; i < this.cols; i++) {
			pushMatrix();
			beginShape(POINTS);
			for (int j = 0; j < this.rows; j++) {
				//point(i, j, this.Points[cur][i][j]/100);
				strokeWeight(1 + this.Points[cur][i][j]/12);
				int size = 400 + this.Points[cur][i][j];
				vertex(6*i, size*cos(j), size*sin(j));
				//if (this.Points[old][i][j] !=0){
				//	this.Points[old][i][j] -= this.Points[old][i][j]/abs(this.Points[old][i][j]);
				//}
			}
			endShape();
			popMatrix();
		}
	}
}
class Plane {
	int[][][] Points;
	int rows;
	int cols;
	int stepper;
	int phases;
	int dis;
	Plane (int cols, int rows, int distance){
		this.dis = distance;
		this.rows = rows;
		this.cols = cols;
		this.stepper = 0;
		this.phases = 2;
		this.Points = new int[this.phases][cols+2][rows+2];
		for (int i = 0; i < cols+2; i++) {
			for (int j = 0; j < rows+2; j++) {
				this.Points[0][i][j] = 0;
				this.Points[1][i][j] = 0;
				//this.Points[2][i][j] = 0;
			}
		}
	}
	public void set(int x, int y, int val) {
		Points[(this.stepper+1) % this.phases][x][y] = val;
	}

	public void update() {
		int old = this.stepper;
		this.stepper = (this.stepper+1) % this.phases;
		int cur = this.stepper;

		//int next = (this.stepper+1) % this.phases;
		//stroke(150);
		//noStroke();
		//fill(100);
		strokeWeight(1);

		for (int i = 1; i < this.cols; i++) {
			pushMatrix();
			beginShape(LINES);
			for (int j = 1; j < this.rows; j++) {
				stroke(0+this.Points[cur][i][j]/4,0,0);
				strokeWeight(1 + this.Points[cur][i][j]/12);
				vertex(this.dis*i, this.dis*j, this.Points[cur][i][j]/10);
				this.Points[old][i][j] = ((	
						this.Points[cur][i][j-1] + this.Points[cur][i][j+1] +
						this.Points[cur][i-1][j] + this.Points[cur][i+1][j] ) / 2) - this.Points[old][i][j];
			}
			endShape();
			popMatrix();
		}
	}
	public void draw() {
		int cur = (this.stepper+1) % this.phases;
		for (int i = 0; i < this.cols; i++) {
			pushMatrix();
			beginShape(LINES);
			for (int j = 0; j < this.rows; j++) {
				strokeWeight(1 + this.Points[cur][i][j]/12);
				vertex(this.dis*i, this.dis*j, this.Points[cur][i][j]/10);
			}
			endShape();
			popMatrix();
		}

	}

}

class Space {
	int[][][][] Points;
	int rows;
	int cols;
	int lrs;
	int stepper;
	Space (int cols,int rows, int lrs){
		this.rows = rows;
		this.cols = cols;
		this.lrs = lrs;

		this.stepper = 0;
		this.Points = new int[2][cols+2][rows+2][lrs+2];
		for (int i = 0; i < cols+2; i++) {
			for (int j = 0; j < rows+2; j++) {
				for (int k = 0; k < lrs+2; k++) {
					this.Points[0][i][j][k] = 0;
					this.Points[1][i][j][k] = 0;
				}
			}
		}
	}
	public void set(int x, int y, int z, int val) {
		Points[this.stepper][x][y][z] = val;
	}

	public void update() {
		int cur = this.stepper;
		this.stepper = (this.stepper+1) % 2;
		int old = this.stepper;
		//stroke(150);
		//fill(100);
		for (int i = 1; i < this.cols; i++) {
			for (int j = 1; j < this.rows; j++) {
				pushMatrix();
				beginShape(POINTS);
				for (int k = 1; k < this.lrs; k++) {
					int val = this.Points[old][i][j][k];
					strokeWeight(val/5);

					vertex(i+val, j+val, k+val);
					this.Points[old][i][j][k] = (
					((	this.Points[cur][i][j-1][k] + this.Points[cur][i][j+1][k] +
						this.Points[cur][i-1][j][k] + this.Points[cur][i+1][j][k] +
						this.Points[cur][i][j][k-1] + this.Points[cur][i][j][k+1]) / 4)
					- this.Points[old][i][j][k]);
				}
				endShape();
				popMatrix();
			}
		}
	}
}

class Terrain {
	int[][] Points;
	int cols;
	int rows;
	Terrain (int cols,int rows){
		this.cols = cols;
		this.rows = rows;
		this.Points = new int[cols+2][rows+2];
		for (int i = 0; i < cols+2; i++) {
			for (int j = 0; j < rows+2; j++) {
				this.Points[i][j] = PApplet.parseInt(noise(i,j)*255);
			}
		}
	}
	public void update() {
		for (int i = 0; i < this.cols; i++) {
			pushMatrix();
			beginShape(LINES);
			for (int j = 0; j < this.rows; j++) {
				strokeWeight(this.Points[i][j]/5);
				vertex(i, j, this.Points[i][j]/100);
			}
			endShape();
			popMatrix();
		}
	}
}
  public void settings() {  size(1920,1080,P3D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--stop-color=#cccccc", "project" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
