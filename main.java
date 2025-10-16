import java.util.List;

class main {

    public static void main(String[] args) {
        System.out.println("Here comes the NN");
        NeuralEngine engine = new NeuralEngine();
        engine.setInput(new float[]{1,0,0,0,0,1,0,0,0,0});
        List<Neuron> output = engine.forwardPass();
        for(Neuron neuron:output){
            System.out.println("Output: " + neuron.getVal());
            //forward pass works acc to this?
        }
    }
}
