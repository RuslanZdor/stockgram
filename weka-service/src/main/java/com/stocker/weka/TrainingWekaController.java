package com.stocker.weka;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import weka.classifiers.functions.Logistic;
import weka.classifiers.trees.RandomForest;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils;

@Log4j2
@RestController
public class TrainingWekaController {

    private static final String DATASTORE_FILE = "d:\\ruslan_zdor\\3.arff";
    private static final String MODEL_FILE = "d:\\ruslan_zdor\\java_model.model";

    @GetMapping("/train")
    public void prepare() throws Exception {

        ConverterUtils.DataSource dataSource = new ConverterUtils.DataSource(DATASTORE_FILE);
        Instances instances = dataSource.getDataSet();
        instances.setClassIndex(instances.numAttributes() - 1);

        RandomForest smOreg = new RandomForest();
        smOreg.buildClassifier(instances);

        SerializationHelper.write(MODEL_FILE, smOreg);
    }

    @GetMapping("/classify")
    public void classify() throws Exception {

        RandomForest randomForest = (RandomForest) SerializationHelper.read(MODEL_FILE);

        ConverterUtils.DataSource dataSource = new ConverterUtils.DataSource(DATASTORE_FILE);
        Instances instances = dataSource.getDataSet();
        instances.setClassIndex(instances.numAttributes() - 1);

        for (int i = 0; i < 20; i++) {
            Instance instance = instances.get(i);
            System.out.println(randomForest.classifyInstance(instance));
        }

        Logistic logistic = (Logistic) SerializationHelper.read("d:\\ruslan_zdor\\model.model");

        for (int i = 0; i < 20; i++) {
            Instance instance = instances.get(i);
            System.out.println(randomForest.classifyInstance(instance));
        }
    }

}
