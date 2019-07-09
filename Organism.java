import java.util.Comparator;

public class Organism {
int inputs;
int hiddens;
int outputs;
double fitness;
Matrix inputWeights;
Matrix hiddenWeights;

public Organism(int inputs, int hiddens, int outputs){
        this.inputs = inputs;
        this.hiddens = hiddens;
        this.outputs = outputs;

        fitness = 0.0;
        inputWeights = new Matrix();
        hiddenWeights = new Matrix();
        inputWeights.RandomMatrix(hiddens, inputs);
        hiddenWeights.RandomMatrix(outputs, hiddens);
}

public void CalcFitness(Network net){

        if (fitness != 0.0) {
                return;
        }
        this.fitness = 0.0;
        net.inputWeights = this.inputWeights;
        net.hiddenWeights = this.hiddenWeights;

        double right = 0.0;

        double[] inputone = {0.0,0.0};
        Matrix resultone = net.Predict(inputone);

        this.fitness += resultone.matrix[0][0];
        if (resultone.matrix[0][0] > 0.5) {
                right += 1.0;
        }

        double[] inputtwo = {1.0,0.0};
        Matrix resulttwo = net.Predict(inputtwo);

        this.fitness += (1 - resulttwo.matrix[0][0]);
        if (resulttwo.matrix[0][0] < 0.5) {
                right += 1.0;
        }

        double[] inputthree = {0.0,1.0};
        Matrix resultthree = net.Predict(inputthree);

        this.fitness += (1 - resultthree.matrix[0][0]);
        if (resultthree.matrix[0][0] < 0.5) {
                right += 1.0;
        }

        double[] inputfour = {1.0,1.0};
        Matrix resultfour = net.Predict(inputfour);

        this.fitness += resultfour.matrix[0][0];
        if (resultfour.matrix[0][0] > 0.5) {
                right += 1.0;
        }

        this.fitness += right;

}

public Organism Crossover(Organism b){

        Organism child = new Organism(this.inputs, this.hiddens, this.outputs);

        int midinput = (int)(Math.random() * ( child.inputWeights.Rows() * child.inputWeights.Cols() ));

        for (int i = 0; i< child.inputWeights.Rows(); i++) {
                for(int j=0; j< child.inputWeights.Cols(); j++) {

                        if (i * j < midinput) {
                                child.inputWeights.matrix[i][j] = this.inputWeights.matrix[i][j];
                        }else{
                                child.inputWeights.matrix[i][j] = b.inputWeights.matrix[i][j];
                        }


                }
        }

        int midhidden = (int)(Math.random() * ( child.hiddenWeights.Rows() * child.hiddenWeights.Cols() ));

        for (int i = 0; i< child.hiddenWeights.Rows(); i++) {
                for(int j=0; j< child.hiddenWeights.Cols(); j++) {

                        if (i * j < midhidden) {
                                child.hiddenWeights.matrix[i][j] = this.hiddenWeights.matrix[i][j];
                        }else{
                                child.hiddenWeights.matrix[i][j] = b.hiddenWeights.matrix[i][j];
                        }


                }
        }

        return child;
}

public void Mutate(double mutationrate){

        for (int i = 0; i< this.inputWeights.Rows(); i++) {
                for(int j=0; j< this.inputWeights.Cols(); j++) {

                        if ((Math.random() * 100.0) <  mutationrate) {
                                this.inputWeights.matrix[i][j] = Matrix.RandNorm();
                        }


                }
        }

        for (int i = 0; i< this.hiddenWeights.Rows(); i++) {
                for(int j=0; j< this.hiddenWeights.Cols(); j++) {

                        if ((Math.random() * 100.0) <  mutationrate) {
                                this.hiddenWeights.matrix[i][j] = Matrix.RandNorm();
                        }


                }
        }


}

}
