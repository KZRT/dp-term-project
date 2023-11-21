package easyLearning;

import net.sf.javaml.classification.*;
import net.sf.javaml.classification.bayes.KDependentBayesClassifier;
import net.sf.javaml.classification.bayes.NaiveBayesClassifier;

public class ClassifierFactory implements AbstractClassifierFactory{
    @Override
    public Classifier createClassifier(String classifierType) {
        switch (classifierType){
            case "KNN":
                return new KNearestNeighbors();
            case "NaiveBayes":
                return new NaiveBayesClassifier();
            case "KDTree":
                return new KDtreeKNN();
            case "MeanFeatureVoting":
                return new MeanFeatureVotingClassifier();
            case "NearestMean":
                return new NearestMeanClassifier();

            default:
                return null;
        }
    }

    public Classifier createBayesClassifier(String classifierType) {
        switch (classifierType){
            case "NaiveBayes":
                return new NaiveBayesClassifier();
            case "K-DependentBayes":
                return new KDependentBayesClassifier();
            default:
                return null;
        }
    }
}
