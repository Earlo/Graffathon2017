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
	void set(int x, int y, int val) {
		Points[(this.stepper+1) % this.phases][x][y] = val;
	}

	void update() {
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
	void draw() {
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
	void set(int x, int y, int z, int val) {
		Points[this.stepper][x][y][z] = val;
	}

	void update() {
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