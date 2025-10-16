import java.util.ArrayList;
import java.util.List;

public class NeuralEngine {



    private NeuralNetwork neuralNetwork;


    private final int LAYERS = 4;
    private final int INPUT_SIZE =784;
    private final int OUTPUT_SIZE =10;

    private final int STANDARD =0;





    public NeuralEngine() {
        neuralNetwork = new NeuralNetwork(LAYERS);
        setLayer(0,INPUT_SIZE);
        setLayer(1,16);
        setLayer(2,16);
        setLayer(3,OUTPUT_SIZE);
        setLayer(4,OUTPUT_SIZE); //The testing layer
        System.out.println("The neural network has been set");
        setWeights();
    }

    public NeuralEngine(int layers) {
        if(layers == STANDARD) setNeuralNetwork(3);
        else  setNeuralNetwork(layers);
    }


    //works imo
    public void setNeuralNetwork(int layers) {
        neuralNetwork = new NeuralNetwork(layers);
        if(layers>0) setLayer(0,INPUT_SIZE);
        if(layers>1) setLayer(layers-1,OUTPUT_SIZE);
        if(layers==STANDARD){
            setLayer(1,16);
            setLayer(2,16);

        }
        System.out.println("The neural network has been set");
        setWeights();
        System.out.println("The weights have been set");
        System.out.println("The neural network is ready");
        System.out.println("Number of layers: " +neuralNetwork.getNeurons().size());
    }


//    public void addLayer() {
//        neuralNetwork.getNeurons().add(new ArrayList<>());
//    }

    //works imo
    public void setLayer(int layer, int neurons) {
        if(layer>=neuralNetwork.getNeurons().size()) return;
        for(int z=0; z<neurons;++z) {
            Neuron neuron = new Neuron();
            neuralNetwork.addNeuron(layer,neuron);
            System.out.println("Adding neuron with bias: "+neuron.getBias() + " to layer "  + layer);
        }
        System.out.println("number of neurons in layer " + layer + " is " + neurons);
    }

    //We have a neural network that can make 2 hidden layers along with an input and output layer.
    //the next step is weights

    //this should initialize the weights and set them to 0. right??
    public void setWeights(){
        neuralNetwork.setWeights(new Weights(new java.util.ArrayList<>()));
        int layers  = neuralNetwork.getNeurons().size();
        for(int midLayer = 0 ; midLayer<layers-1;++midLayer){
            neuralNetwork.getWeights().addMidLayer();
            int neuronsInLayer = neuralNetwork.getNeurons().get(midLayer).size();
            int neuronsInNextLayer = neuralNetwork.getNeurons().get(midLayer+1).size();
            for(int neuron1=0;neuron1<neuronsInLayer;++neuron1){
                List<Float> neuron1Weights = new ArrayList<>();
                for(int neuron2=0;neuron2<neuronsInNextLayer;++neuron2){
                    neuron1Weights.add(0f);
                }
                neuralNetwork.getWeights().getWeightsOfLayer(midLayer).add(neuron1Weights);
            }
        }

        //after this we randomize the weights
        neuralNetwork.getWeights().randomizeWeights();
    }



    // forward propagation
    public List<Neuron> forwardPass() {
        //we do -2 to skip the testing layer for the time being`
        System.out.println(neuralNetwork.getNeurons().size());
        for(int prevLayer=0;prevLayer<neuralNetwork.getNeurons().size()-2;++prevLayer){
            for (int neuron2 = 0; neuron2 < neuralNetwork.getNeurons().get(prevLayer+1).size(); ++neuron2) {
                float sum = neuralNetwork.getNeurons().get(prevLayer+1).get(neuron2).getBias();
                for (int neuron1 = 0; neuron1 < neuralNetwork.getNeurons().get(prevLayer).size(); ++neuron1) {
                    sum += neuralNetwork.getWeights().getWeight(prevLayer, neuron1, neuron2) * neuralNetwork.getNeurons().get(prevLayer).get(neuron1).getVal();
                }
                neuralNetwork.getNeurons().get(prevLayer+1).get(neuron2).activate(sum);
            }
        }

        //make it return the output neurons
        return neuralNetwork.getNeurons().get(neuralNetwork.getNeurons().size()-2);
    }


    //populate the inputs!
    public void setInput(float[] inputs){
        if(inputs.length!=INPUT_SIZE) return;
        for(int i=0;i<INPUT_SIZE;++i){
            neuralNetwork.getNeuron(0,i).activate(inputs[i]);
        }
        System.out.println("Inputs have been set");
        for(int x= 0; x<INPUT_SIZE;++x){
            System.out.println("Input " + x + " is " + neuralNetwork.getNeuron(0,x).getVal());
        }
    }


    public void train(){
        int iterations = trainingData.length/BATCH_SIZE;
        //no of the partitions you made of the data
        for(int i=0;i<iterations;++i){
            //method to clean the input layer
            neuralNetwork.clearInput();
            float cost = 0;
            // the data in the given partition
            int start = i*BATCH_SIZE;
            //first populate the inputs
            for(int j=start;j<start+BATCH_SIZE;++j){
               //The input in each data point
                // k = 1 as the 0th index is the label
                for(int k=1;k<trainingData[j].length;++k){
                    //set the input layer of neurons
                    neuralNetwork.getNeuron(0,k-1).activate(trainingData[j][k]);
                }
                // do the forward pass once you have set the inputs
                forwardPass();
                cost+=calculateCostFunction(j);
            }

            /// /TODO: You will be missing out on a few data points so try to get those somehow
            cost/=BATCH_SIZE;
            System.out.println("The cost is " + cost);

            //now that we have the cost, we can do the backpropagation


            //backpropagation
            // the idea is to change the weights-= learning rate * derivative of cost function.

            /// /TODO: Backpropation methods
            //backpropagation
            backpropagation();

        }
        // for a bunch of training data

        // do forward passes

        //calculate the cost function which is MSE


    }

    public void backpropagation(){
        ;
    }

    //methods to gather training data>

    private final int BATCH_SIZE= 1000;


    //the inputs range is not -1,1 so be careful to parse that later

    //This is for the MNIST dataset
    private float calculateCostFunction(int trainingDataIndex){
        float cost = 0;
        float[] trainingData = this.trainingData[trainingDataIndex];
        //Make an array of the output neurons
        float[] target = new float[OUTPUT_SIZE];
        for(int i=0;i<OUTPUT_SIZE;++i){
            if(i==trainingData[0]) target[i]=1;
            else target[i]=0;
        }
        for(int x = 0; x<neuralNetwork.getNeurons().getLast().size();++x){
            float output = neuralNetwork.getNeurons().getLast().get(x).getVal();
            // we subtract the output from the target,
            // we square it
            cost += (output-target[x])*(output-target[x]);
            // we sum it up

        }
        // we divide by the batch size
        return cost;
    }






    private float[][] trainingData;
    private List<List<Float>> testData;

    public void setTrainingData(float[][] trainingData) {
        this.trainingData = trainingData;
    }

    public void setTestData(List<List<Float>> testData) {
        this.testData = testData;
    }

    public float[][] getTrainingData() {
        return trainingData;
    }
    public List<List<Float>> getTestData() {
        return testData;
    }




}
