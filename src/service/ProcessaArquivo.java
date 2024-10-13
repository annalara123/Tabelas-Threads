package service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CyclicBarrier;

public class ProcessaArquivo implements Runnable {
    private String fileName;
    private Map<String, Double> financeiroPorData;
    private CyclicBarrier barrier;

    public ProcessaArquivo(String fileName, CyclicBarrier barrier) {
        this.fileName = fileName;
        this.barrier = barrier;
        this.financeiroPorData = new HashMap<>();
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] parts = line.split(",");
                String data = parts[0].replace("\"", "").trim();
                double valor = Double.parseDouble(parts[1].replace("\"", "").trim());
                financeiroPorData.put(data, financeiroPorData.getOrDefault(data, 0.0) + valor);
            }

            reader.close();
            barrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, Double> getFinanceiroPorData() {
        return financeiroPorData;
    }
}
