package org.cjoakim.azure.cosmos.dao;

import org.cjoakim.azure.cosmos.models.PostalCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * This class returns US Postal Code information from a csv file loaded
 * from the Java classpath.
 *
 * Chris Joakim, Microsoft, 2018/04/11
 */

public class PostalCodeDao {

    private static final Logger logger = LoggerFactory.getLogger(PostalCodeDao.class);

    private static ArrayList<PostalCode> postalCodesList = new ArrayList<PostalCode>();
    private static HashMap<String, PostalCode> postalCodesMap = new HashMap<String, PostalCode>();


    public static int init() {

        String csvPath = "/postal_codes_us.csv";
        logger.warn(String.format("reading resource %s ...", csvPath));
        int count = -1;

        try {
            InputStreamReader isr = new InputStreamReader(PostalCodeDao.class.getResourceAsStream(csvPath));
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            int lineNum = 0;

            while((line=br.readLine()) != null) {
                lineNum++;
                if (lineNum > 1) {
                    // Skip the csv header row
                    PostalCode pc = new PostalCode(line);
                    if (pc.isValid()) {
                        postalCodesList.add(pc);
                        postalCodesMap.put(pc.getPostalCd(), pc);
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            count = postalCodesList.size();
            logger.warn(String.format("loaded resource %s  count: %s", csvPath, count));
        }
        return count;
    }

    public static PostalCode lookup(String postalCd) {

        return postalCodesMap.get(postalCd);
    }

    public static PostalCode random() {

        Random r = new Random();
        int index = r.nextInt(postalCodesList.size() - 1);
        return postalCodesList.get(index);
    }

    public static void main(String[] args) {

        try {
            logger.warn("main");
            PostalCodeDao.init();

            PostalCode dav = PostalCodeDao.lookup("28036");
            logger.warn(String.format("28036 -> %s", dav.asJson()));

            for (int i = 0; i < 5; i++) {
                PostalCode pc = PostalCodeDao.random();
                logger.warn(String.format("random -> %s", pc.asJson()));
            }

            logger.warn("run complete");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            System.exit(0);
        }
    }

}
