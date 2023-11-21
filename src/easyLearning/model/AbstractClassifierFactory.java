package easyLearning.model;

import net.sf.javaml.classification.Classifier;
public interface AbstractClassifierFactory {
    public Classifier createClassifier(String classifierType);
}
