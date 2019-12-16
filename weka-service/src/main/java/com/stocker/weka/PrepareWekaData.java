package com.stocker.weka;

import com.stocker.weka.spring.CompanyRepository;
import com.stocker.yahoo.data.Day;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

import static weka.core.converters.ConverterUtils.*;

@Slf4j
@RestController
public class PrepareWekaData {

    @Autowired
    private CompanyRepository companyRepository;

    private static final String path = "d:\\ruslan_zdor\\3.arff";

    @GetMapping("/prepare")
    public void prepare() {

        log.info("prepare weka datastore");


        File datasetFile = new File(path);
        if (datasetFile.exists() && !datasetFile.delete()) {
            throw new IllegalStateException("Cannot clean weka data set file");
        }

        companyRepository.findByBigCap(2000000000).subscribe(company -> {

            log.info(String.format("processing company %s", company.getName()));

            Instances dataset = null;
            if (datasetFile.exists()) {
                try (
                        FileReader fileReader = new FileReader(datasetFile);
                        BufferedReader buffer = new BufferedReader(fileReader)
                ) {
                    ArffLoader.ArffReader arff = new ArffLoader.ArffReader(buffer);
                    dataset = arff.getData();
                } catch (java.io.IOException e) {
                    log.error("Failed to read weka model file", e);
                }
            } else {
                ArrayList<Attribute> attributes = new ArrayList<>();
                attributes.add(new Attribute("SMA5", Attribute.NUMERIC));
                attributes.add(new Attribute("SMA50", Attribute.NUMERIC));
                attributes.add(new Attribute("SMA200", Attribute.NUMERIC));
                attributes.add(new Attribute("RSI5", Attribute.NUMERIC));
                attributes.add(new Attribute("RSI50", Attribute.NUMERIC));
                attributes.add(new Attribute("RSI200", Attribute.NUMERIC));
                attributes.add(new Attribute("direction", Arrays.asList( "UP" , "DOWN" )));
                dataset = new Instances("Stock", attributes, 0);
            }


            for (Day day : company.getDays()) {
                dataset.add(new DenseInstance(1.0d, new double[]{
                        day.getSMA5() / day.getPrice(),
                        day.getSMA50() / day.getPrice(),
                        day.getSMA200() / day.getPrice(),
                        day.getRSI5(),
                        day.getRSI50(),
                        day.getRSI200(),
                        day.isNextRise() ? 0 : 1
                }));
            }
            String outputFilename = "d:\\ruslan_zdor\\3.arff";
            try {
                DataSink.write(outputFilename, dataset);
            } catch (Exception e) {
                log.error("Failed to save data to: " + outputFilename);
                e.printStackTrace();
            }
        }, throwable -> log.error("Cannot find company for prediction", throwable));
    }

}
