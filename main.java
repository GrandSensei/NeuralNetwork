import java.util.List;
public class main {



    public static void main(String[] args) throws Exception {
        System.out.println("Starting Neural Network...");

        // 1. INITIALIZE ENGINE
        NeuralEngine engine = new NeuralEngine();

        // 2. LOAD DATA
        // Replace this path with the actual location on your computer!
        String path = "digit-recognizer/train.csv";

        System.out.println("Loading data...");
        // Let's load only 5000 rows first to test if it works quickly
        float[][] data = ExcelParse.loadData(path, 40000);

        float[][] trainData = new float[20000][];
        float[][] testData = new float[20000][];

        // Copy first 20000
        System.arraycopy(data, 0, trainData, 0, 20000);
        // Copy remaining 20000
        System.arraycopy(data, 20000, testData , 0, 20000);

        System.out.println("Data loaded. Rows: " + data.length);

        // 3. FEED DATA TO ENGINE
        engine.setTrainingData(trainData);

        // 4. TRAIN

        System.out.println("Beginning Training...");
        long startTime = System.currentTimeMillis();
        //I went for 50 epochs that took about 425 seconds on my M3 MacBook Pro...
        engine.train(50);

        long endTime = System.currentTimeMillis();
        System.out.println("Training finished in " + (endTime - startTime) / 1000 + " seconds.");

        System.out.println("Starting Testing...");
        engine.test(testData);


    }
}