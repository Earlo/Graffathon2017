
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
				this.Points[i][j] = int(noise(i,j)*255);
			}
		}
	}
	void update() {
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