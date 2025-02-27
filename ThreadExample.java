class Resource {
    private String data;
    private boolean available = false;

    public synchronized void produce(String value) {
        while (available) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.data = value;
        System.out.println("Produced: " + data);
        available = true;
        notify();
    }

    public synchronized String consume() {
        while (!available) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Consumed: " + data);
        available = false;
        notify();
        return data;
    }
}

class Producer extends Thread {
    private Resource resource;

    public Producer(Resource resource) {
        this.resource = resource;
    }

    public void run() {
        String[] items = {"A", "B", "C", "D"};
        for (String item : items) {
            resource.produce(item);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer extends Thread {
    private Resource resource;

    public Consumer(Resource resource) {
        this.resource = resource;
    }

    public void run() {
        for (int i = 0; i < 4; i++) {
            resource.consume();
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class ThreadExample {
    public static void main(String[] args) {
        Resource resource = new Resource();
        Producer producer = new Producer(resource);
        Consumer consumer = new Consumer(resource);

        producer.start();
        consumer.start();
    }
}
