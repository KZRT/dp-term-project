import com.holub.database.Database;
import com.holub.database.JTableExporter;
import com.holub.database.Table;
import com.holub.database.TableFactory;
import com.holub.text.ParseFailure;
import com.holub.text.Scanner;
import com.holub.text.TokenSet;
import easyLearning.ClustererFactory;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.classification.evaluation.CrossValidation;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.data.FileHandler;
// Import Abeel java toolkit


import javax.swing.*;
import java.io.*;
import java.util.Map;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        {
            Database theDatabase = new Database();

            // Read a sequence of SQL statements in from the file
            // Database.test.sql and execute them.

            BufferedReader sql = null;
            try {
                sql = new BufferedReader(new FileReader("Database.test.sql"));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            String test;
            while (true) {
                try {
                    if (!((test = sql.readLine()) != null)) break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                test = test.trim();
                if (test.length() == 0) continue;

                while (test.endsWith("\\")) {
                    test = test.substring(0, test.length() - 1);
                    try {
                        test += sql.readLine().trim();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                System.out.println("Parsing: " + test);
                Table result = null;
//                try {
//                    result = theDatabase.execute(test);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                } catch (ParseFailure e) {
//                    throw new RuntimeException(e);
//                }

                if (result != null)    // it was a SELECT of some sort
                    System.out.println(result.toString());
            }

            try {
                try {
                    theDatabase.execute("insert garbage SQL");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Database FAILED");
                System.exit(1);
            } catch (ParseFailure e) {
                System.out.println("Correctly found garbage SQL:\n" + e + "\n" + e.getErrorReport());
            }

            try {
                theDatabase.dump();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Database PASSED");

            TutorialCVSameFolds tutorialCVSameFolds = new TutorialCVSameFolds();
            try {
                tutorialCVSameFolds.main(args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class TutorialCVSameFolds {
        /**
         * Default cross-validation with the same folds for multiple runs.
         */
        public void main(String[] args) throws Exception {
            /* Load data */
            Dataset data = FileHandler.loadDataset(new File("devtools/data/iris.data"), 4, ",");
            /* Construct KNN classifier */
            Classifier knn = new KNearestNeighbors(5);
            /* Construct new cross validation instance with the KNN classifier, */
            CrossValidation cv = new CrossValidation(knn);
            /*
             * Perform 5-fold cross-validation on the data set with a user-defined
             * random generator
             */
            Map<Object, PerformanceMeasure> p = cv.crossValidation(data, 5, new Random(1));

            Map<Object, PerformanceMeasure> q = cv.crossValidation(data, 5, new Random(1));

            Map<Object, PerformanceMeasure> r = cv.crossValidation(data, 5, new Random(25));

            System.out.println("Accuracy=" + p.get("Iris-virginica").getAccuracy());
            System.out.println("Accuracy=" + q.get("Iris-virginica").getAccuracy());
            System.out.println("Accuracy=" + r.get("Iris-virginica").getAccuracy());
            System.out.println(p);
            System.out.println(q);
            System.out.println(r);

            data = FileHandler.loadDataset(new File("devtools/data/iris.data"), 4, ",");
            /*
             * Create a new instance of the KMeans algorithm that will create 3
             * clusters and create one that will make 4 clusters.
             */
            ClustererFactory factory = ClustererFactory.getInstance();
            Clusterer clusterer = factory.createClusterer("KMeans");
            /*
             * Cluster the data, we will create 3 and 4 clusters.
             */
            Dataset[] clusters3 = clusterer.cluster(data);

            double aicScore3 = factory.createClusterEvaluation("AICScore").score(clusters3);
            double bicScore3 = factory.createClusterEvaluation("BICScore").score(clusters3);
            double sseScore3 = factory.createClusterEvaluation("SumOfSquaredErrors").score(clusters3);


            System.out.println("AIC score: " + aicScore3);
            System.out.println("BIC score: " + bicScore3);
            System.out.println("Sum of squared errors: " + sseScore3);

        }
    }
}