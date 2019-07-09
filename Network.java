import java.util.ArrayList;
import java.util.Arrays;

public class Network {
int inputs;
int hiddens;
int outputs;
Matrix inputWeights = new Matrix();
Matrix hiddenWeights = new Matrix();

public Network(int input, int hidden, int output  ) {
        inputs = input;
        hiddens = hidden;
        outputs = output;

        inputWeights.RandomMatrix(hidden, input);
        hiddenWeights.RandomMatrix(output, hidden);

}

public Matrix Predict(double[] inputData){
        Matrix inputs = new Matrix();
        inputs.EmptyMatrix(inputData.length, 1);
        for (int i = 0; i < inputData.length; i++) {
                inputs.matrix[i][0]= inputData[i];
        }

        Matrix hiddenInputs = inputWeights.DotProduct(inputs);
        Matrix hiddenOutputs = hiddenInputs.Sigmoid();
        Matrix finalInputs = hiddenWeights.DotProduct(hiddenOutputs);
        Matrix finalOutputs = finalInputs.Sigmoid();

        return finalOutputs;


}


public static void main(String[] args) {
        System.out.println("Creating Network");
        Network net = new Network(2,4,1);

        System.out.println("Initialising Trainer");
        Trainer trainer = new Trainer(net, 500, 1, 250, 3, 5);

        System.out.println("Training Network");
        trainer.Train(net, 1000, true);

        double[] inputone = {0.0,0.0};
        System.out.println(Arrays.deepToString(net.Predict(inputone).matrix));

        double[] inputtwo = {1.0,0.0};
        System.out.println(Arrays.deepToString(net.Predict(inputtwo).matrix));

        double[] inputthree = {0.0,1.0};
        System.out.println(Arrays.deepToString(net.Predict(inputthree).matrix));

        double[] inputfour = {1.0,1.0};
        System.out.println(Arrays.deepToString(net.Predict(inputfour).matrix));

        System.out.println("Press 'Enter' key to exit.");
        System.console().readLine();

}

}
