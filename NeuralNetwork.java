import java.util.List;

public class NeuralNetwork {




    private List<List<Neuron>> neurons;
    private Weights weights;

    public NeuralNetwork() {

    }

    public NeuralNetwork(List<List<Neuron>> neurons) {
        this.neurons = neurons;
    }

    public List<List<Neuron>> getNeurons() {
        return neurons;
    }

    public void setNeurons(List<List<Neuron>> neurons) {
        this.neurons = neurons;
    }

    public Neuron getNeuron(int layer, int neuron) {
        return neurons.get(layer).get(neuron);
    }
    public void setNeuron(int layer, int index, Neuron neuron) {
        neurons.get(layer).set(index, neuron);
    }
    public void setNeuron(int layer, int index, int val){
            neurons.get(layer).get(index).setActivation(val);
    }
    public void addNeuron(int layer, Neuron neuron) {
        if (layer >= neurons.size()) return;
        neurons.get(layer).add(neuron);
    }

    //No of layers and neurons in each layer
    public NeuralNetwork(int layers) {
       neurons = new java.util.ArrayList<>();
       //The plus one is there for target neurons for testing
       for (int i = 0; i < layers; i++) neurons.add(new java.util.ArrayList<>());
    }

    public Weights getWeights() {
        return weights;
    }

    public void setWeights(Weights weights) {
        this.weights = weights;
    }

    //method to change particular weight
    public void setWeight(int layer, int neuron1, int neuron2, float weight) {
        weights.setWeight(layer, neuron1, neuron2, weight);
    }


    public void clearInput(){
        for(int i = 0; i<neurons.getFirst().size(); ++i){
            neurons.getFirst().get(i).setVal(0);
            neurons.getFirst().get(i).setActivation(0);
        }
    }

//    public void addWeightsBetweenLayers(int layer1,int layer2) {
//        if(layer1<layer2 || layer2 >= neurons.size()) return;
//        int midLayer = (layer1+layer2)/2;
//        for(int i=0;i<neurons.get(layer1).size();++i) {
//           for(int j=0;j<neurons.get(layer2).size();++j) {
//               weights.setWeight(midLayer,i,j,0);
//           }
//        }
//
//    }



    //I have made a list of neurons for each layer

}
