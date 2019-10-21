package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.System.out;

/**
 * @description: 数据迁移
 * 数据一致性标准：
 * - RecordCount
 * - Column schema
 * - Column size
 * - data checksum
 * @auther:
 * @date: 2019-09-29 15:51
 */
public class CountDownLatchExample4 {
    private static Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        Event[] events = {new Event(1), new Event(2)};
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (Event event : events) {
            List<Table> tables = capture(event);
            TaskGroup group = new TaskGroup(event, tables.size());

            for (Table table : tables) {

                TaskBatch taskBatch = new TaskBatch(group, 2);

                TrustSourceColumns trustSourceColumns = new TrustSourceColumns(table, taskBatch);
                TrustSourceRecordCount trustSourceRecordCount = new TrustSourceRecordCount(table, taskBatch);

                service.submit(trustSourceColumns);
                service.submit(trustSourceRecordCount);
            }
        }

    }

    private static List<Table> capture(Event event) {
        List<Table> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new Table("table-" + event.id + "-" + i, i * 1000));
        }
        return list;
    }

    interface Watcher {
//        void startWatch();

        void done(Table table);
    }

    static class TaskBatch implements Watcher {
        private CountDownLatch latch;
        private TaskGroup group;

        public TaskBatch(TaskGroup group, int size) {
            this.group = group;
            latch = new CountDownLatch(size);
        }

        public TaskBatch() {

        }

//        @Override
//        public void startWatch() {
//
//        }

        @Override
        public void done(Table table) {
            latch.countDown();

            if (latch.getCount() == 0) {
                out.println("The table " + table.tableName + " finished work, [" + table + "]");
                group.done(table);

            }
        }
    }

    static class TaskGroup implements Watcher {
        private CountDownLatch latch;
        private Event event;

        public TaskGroup(Event event, int size) {
            this.event = event;
            latch = new CountDownLatch(size);
        }

//        @Override
//        public void startWatch() {
//
//        }

        @Override
        public void done(Table table) {
            latch.countDown();

            if (latch.getCount() == 0) {
                out.println("===========All of talbe done in event:" + event.id);
            }
        }
    }

    static class TrustSourceRecordCount implements Runnable {
        private final Table table;
        private final TaskBatch taskBatch;


        public TrustSourceRecordCount(Table table, TaskBatch taskBatch) {
            this.table = table;
            this.taskBatch = taskBatch;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(random.nextInt(10_000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            table.targetRecordCount = table.sourceRecordCount;
            //  out.println("The table " + table.tableName + " target record count capture done and update the data.");
            taskBatch.done(table);

        }
    }

    static class TrustSourceColumns implements Runnable {
        private final Table table;
        private final TaskBatch taskBatch;


        public TrustSourceColumns(Table table, TaskBatch taskBatch) {
            this.table = table;
            this.taskBatch = taskBatch;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(random.nextInt(10_000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            table.targetColumnSchema = table.sourceColumnSchema;
            //     out.println("The table " + table.tableName + " target column capture done and update the data.");
            taskBatch.done(table);

        }
    }

    static class Event {
        int id;

        public Event(int id) {
            this.id = id;
        }
    }

    static class Table {
        String tableName;
        long sourceRecordCount;
        long targetRecordCount;
        String sourceColumnSchema = "<table name='a'><column name='col1' type='varchar2'></column></table>";
        String targetColumnSchema = "";

        public Table(String tableName, long sourceRecordCount) {
            this.tableName = tableName;
            this.sourceRecordCount = sourceRecordCount;
        }

        public Table(String tableName, long sourceRecordCount, long targetRecordCount, String sourceColumnSchema, String targetColumnSchema) {
            this.tableName = tableName;
            this.sourceRecordCount = sourceRecordCount;
            this.targetRecordCount = targetRecordCount;
            this.sourceColumnSchema = sourceColumnSchema;
            this.targetColumnSchema = targetColumnSchema;
        }

        @Override
        public String toString() {
            return "Table{" +
                    "tableName='" + tableName + '\'' +
                    ", sourceRecordCount=" + sourceRecordCount +
                    ", targetRecordCount=" + targetRecordCount +
                    ", sourceColumnSchema='" + sourceColumnSchema + '\'' +
                    ", targetColumnSchema='" + targetColumnSchema + '\'' +
                    '}';
        }
    }
}
