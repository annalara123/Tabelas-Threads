import service.ProcessaArquivo;
import java.text.DecimalFormat;
import java.util.concurrent.CyclicBarrier;

public class Main {
    public static void main(String[] args) throws Exception {
        CyclicBarrier barrier = new CyclicBarrier(3, new Runnable() {
            @Override
            public void run() {
                System.out.println("Todos os arquivos foram processados, somando agora os valores...");
            }
        });

        ProcessaArquivo processaReceitas = new ProcessaArquivo("src/resources/receitas.csv", barrier);
        ProcessaArquivo processaDespesas = new ProcessaArquivo("src/resources/despesas.csv", barrier);
        ProcessaArquivo processaProvisoes = new ProcessaArquivo("src/resources/provisao.csv", barrier);

        Thread threadReceitas = new Thread(processaReceitas);
        Thread threadDespesas = new Thread(processaDespesas);
        Thread threadProvisoes = new Thread(processaProvisoes);

        threadReceitas.start();
        threadDespesas.start();
        threadProvisoes.start();

        threadReceitas.join();
        threadDespesas.join();
        threadProvisoes.join();

        double totalReceitas = processaReceitas.getFinanceiroPorData().values().stream().mapToDouble(Double::doubleValue).sum();
        double totalDespesas = processaDespesas.getFinanceiroPorData().values().stream().mapToDouble(Double::doubleValue).sum();
        double totalProvisoes = processaProvisoes.getFinanceiroPorData().values().stream().mapToDouble(Double::doubleValue).sum();

        DecimalFormat df = new DecimalFormat("#,##0.00");

        System.out.println("Total das receitas: " + df.format(totalReceitas));
        System.out.println("Total das despesas: " + df.format(totalDespesas));
        System.out.println("Total das provisões: " + df.format(totalProvisoes));
    }
}
