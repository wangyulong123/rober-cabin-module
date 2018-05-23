package group.rober.sql.serialno;

import group.rober.sql.BaseTest;
import group.rober.sql.serialno.cursor.SerialNoCursorDao;
import group.rober.sql.serialno.finder.SerialNoGeneratorFinder;
import group.rober.sql.serialno.generator.SerialNoGenerator;
import group.rober.sql.serialno.model.SerialNoCursor;
import group.rober.sql.serialno.constants.CursorRecordType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;

import java.util.Date;

/**
 * Created by zhulifeng on 17-12-14.
 */
public class SerialNoTest extends BaseTest {

    @Autowired
    @Qualifier(CursorRecordType.DB_TABLE)
    SerialNoCursorDao serialNoCursorDao;

    @Autowired
    SerialNoGeneratorFinder serialNoGeneratorFinder;

    //    @Test
    //    @Rollback
    public void testInsertSerialNoCursor() {
        SerialNoCursor serialNoCursor = new SerialNoCursor();
        serialNoCursor.setId("demo.Person.personId");
        serialNoCursor.setCursorValue(1L);
        serialNoCursor.setTemplate("AS0000");
        serialNoCursor.setUpdatedTime(new Date());

        serialNoCursorDao.insertOne(serialNoCursor);
    }

    //    @Test
    //    @Rollback
    public void testGetSerialNoSingleThread() {
        SerialNoGenerator<String> generator = serialNoGeneratorFinder.find("demo.Person.personId");
        String serialNo = generator.next("demo.Person.personId");
        System.out.println(serialNo);
    }

    //    @Test
    //    @Rollback
    public void testGetSerialNoMultiThread() throws Exception {
        MyThread myThread = new MyThread();
        //        MyThread2 myThread = new MyThread2();

        for (int i = 0; i < 20; i++) {
            new Thread(myThread, String.valueOf(i)).start();
        }

        Thread.sleep(1000 * 60);
    }

    //    @Test
    //    @Rollback
    public void testGetBatchSerialNoSingleThread() {
        SerialNoGenerator<String> generator = serialNoGeneratorFinder.find("demo.Person.personId");
        String[] serialNo = generator.nextBatch("demo.Person.personId", 10, null, null);
        for (int i = 0; i < serialNo.length; i++) {
            System.out.println(serialNo[i]);
        }
    }

    @Test
    @Rollback(true)
    public void testGetSerialNoWithDateYMDSingleThread() {
        String generatorId = "demo.Person.personId";
        SerialNoGenerator<String> generator = serialNoGeneratorFinder.find(generatorId);
        String serialNo = generator.next(generatorId);
        System.out.println(serialNo);
    }

    @Test
    @Rollback(true)
    public void testGetBatchSerialNoWithDateYMDSingleThread() {
        String generatorId = "demo.Customer.customerId";
        SerialNoGenerator<String> generator = serialNoGeneratorFinder.find(generatorId);
        //        String[] serialNo = generator.nextBatch(generatorId, 10);
        String[] serialNo = generator.nextBatch(generatorId, 10, null, null);
        for (int i = 0; i < serialNo.length; i++) {
            System.out.println(serialNo[i]);
        }
    }

    class MyThread implements Runnable {

        @Override
        public void run() {
            String generatorId = "demo.Food.foodId";
            SerialNoGenerator<String> generator = serialNoGeneratorFinder.find(generatorId);
            String serialNo = generator.next(generatorId);
            System.out.println("===================serialNo=" + serialNo);
        }
    }


    class MyThread2 implements Runnable {

        @Override
        public void run() {
            String generatorId = "demo.Person.personId";
            int start = 0;
            while (start < 1000000) {
                SerialNoGenerator<String> generator = serialNoGeneratorFinder.find(generatorId);
                String serialNo = generator.next(generatorId);
                System.out.println("===================serialNo=" + serialNo);
                start++;
            }
        }
    }
}
