package org.kuali.ext.mm.service.fixture;

import java.io.IOException;
import java.util.Properties;

import org.kuali.ext.mm.TestDataPreparator;
import org.kuali.ext.mm.businessobject.StockCount;


/*
 * Copyright 2008 The Kuali Foundation.
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public enum StockItemCountFixture {
    STOCKITEMCOUNT(1), STOCKITEMCOUNT1(2), STOCKITEMCOUNT2(3), STOCKITEMCOUNT3(4);
    private int testDataPos;

    private static Properties properties;
    static {

    	String propertiesFileName = "org/kuali/ext/mm/service/data/stock_item_count_service.properties";
        properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemResourceAsStream(propertiesFileName));
        }
        catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private StockItemCountFixture(int dataPos) {
        this.testDataPos = dataPos;
    }

    @SuppressWarnings("deprecation")
    public StockCount newStockCount() {

    	String propertyKey = "stockCount.testData" + testDataPos;
        String deliminator = properties.getProperty("deliminator");

        String fieldNames = properties.getProperty("stockCount.fieldNames");
        StockCount stockCountData = TestDataPreparator.buildTestDataObject(StockCount.class, properties, propertyKey, fieldNames, deliminator);
        return stockCountData;
    }

    public static void main(String s[]){
    	StockCount ss = StockItemCountFixture.STOCKITEMCOUNT.newStockCount();
    	StockCount ss1 = StockItemCountFixture.STOCKITEMCOUNT1.newStockCount();
    	StockCount ss2 = StockItemCountFixture.STOCKITEMCOUNT2.newStockCount();
    	StockCount ss3  = StockItemCountFixture.STOCKITEMCOUNT3.newStockCount();
    	System.out.println(ss);
    	System.out.println(ss1);
    	System.out.println(ss2);
    	System.out.println(ss3);
    }


}
