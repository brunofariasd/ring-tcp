
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Processo {
	private String host;
	private int sPort;
	private int cPort;

	public Processo(String host, int sPort, int cPort) {
		this.host = host;
		this.sPort = sPort;
		this.cPort = cPort;
	}

	public void execute() {
		try{
			ServerSocket server = new ServerSocket(sPort);
			System.out.println("\nServidor em execucao: " + sPort + "\n");

			//É necessário que ele se conecte com o processo destinatário de forma paralela
			//	para evitar empasse.
			ExecutorService executorService = Executors.newCachedThreadPool();
			executorService.execute(new Conector(host, cPort));
			executorService.shutdown();

			//A conexao é de apenas um processo remetente
			Socket conexao = server.accept();

			try {
				Scanner in = new Scanner(conexao.getInputStream());
				while(in.hasNextLine()) {
					System.out.println(">> : " + in.nextLine());
				}
			}catch(IOException e) {
				System.err.println("\nErro ao receber mensagem");
			}
		}catch(IOException e){
			System.err.println("\nServidor não conectado");
		}
	}
}
