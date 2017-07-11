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
	void set(int x, int y, int val) {
		Points[(this.stepper+1) % this.phases][x][y] = val;
	}

	void update() {
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
	void draw() {
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
