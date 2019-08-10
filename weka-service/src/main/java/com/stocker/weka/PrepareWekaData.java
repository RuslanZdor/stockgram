package com.stocker.weka;

import com.stocker.weka.spring.CompanyRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import static weka.core.converters.ConverterUtils.*;

@Log4j2
@RestController
public class PrepareWekaData {

    @Autowired
    private CompanyRepository companyRepository;

    private static final String path = "d:\\ruslan_zdor\\3.arff";

    @GetMapping("/prepare")
    public void prepare() {

        log.info("prepare weka datastore");


        File datasetFile = new File(path);
        if (datasetFile.exists()) {
            datasetFile.delete();
        }

        companyRepository.findAll().subscribe(company -> {

            log.info(String.format("processing company %s", company.getName()));


            Instances dataset = null;
            if (datasetFile.exists()) {
                try (
                        FileReader fileReader = new FileReader(datasetFile);
                        BufferedReader bujffer = new BufferedReader(fileReader)
                ) {
                    ArffLoader.ArffReader arff = new ArffLoader.ArffReader(bujffer);
                    dataset = arff.getData();
                } catch (java.io.IOException e) {
                    log.error(e);
                }
            } else {
                ArrayList<Attribute> attributes = new ArrayList();
                attributes.add(new Attribute("price", Attribute.NUMERIC));
                attributes.add(new Attribute("SMA5", Attribute.NUMERIC));
                attributes.add(new Attribute("SMA50", Attribute.NUMERIC));
                attributes.add(new Attribute("SMA200", Attribute.NUMERIC));
                attributes.add(new Attribute("RSI5", Attribute.NUMERIC));
                attributes.add(new Attribute("RSI50", Attribute.NUMERIC));
                attributes.add(new Attribute("RSI200", Attribute.NUMERIC));
                dataset = new Instances("Stock", attributes, 0);
            }


            double[] values = new double[1];
            values[0] = company.getDays().last().getPrice();
            values[1] = company.getDays().last().getSMA5();
            values[2] = company.getDays().last().getSMA50();
            values[3] = company.getDays().last().getSMA200();
            values[1] = company.getDays().last().getRSI5();
            values[2] = company.getDays().last().getRSI50();
            values[3] = company.getDays().last().getRSI200();
            dataset.add(new DenseInstance(1.0d, values));
            String outputFilename = "d:\\ruslan_zdor\\3.arff";
            try {
                datasetFile.delete();
                DataSink.write(outputFilename, dataset);
            } catch (Exception e) {
                log.error("Failed to save data to: " + outputFilename);
                e.printStackTrace();
            }
        }, throwable -> {
            log.error(throwable);
        });
    }

}
