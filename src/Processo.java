
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.PrintStream;

public class Processo {
	private String host;
	public static int numero;
	private int sPort;
	private int cPort;
	public static Socket servidor;


	public Processo() {}

	public Processo(String host, int numero, int sPort, int cPort) {
		this.host = host;
		this.numero = numero;
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


			Socket conexao = server.accept();

			try {
				Scanner in = new Scanner(conexao.getInputStream());
				
				while(in.hasNextLine()) {
					String mensagem = in.nextLine();
					System.out.println(">> " + mensagem);


					if (servidor != null) {
						PrintStream out = new PrintStream(servidor.getOutputStream());

						// A mensagem recebida tem esse formato "origem:ultimoEnviou:mensagem" ex : "P1:P3:Mensagem de Teste"
						String [] arrayBytes = mensagem.split(":");
						char numMensagem = arrayBytes[0].charAt(1);
						arrayBytes[1] = "P"+numero;

						if ( Character.getNumericValue(numMensagem) != numero ) {

							// Caso a mensagem seja numerica, soma com o valor do número do processo.
							if (arrayBytes[2].matches("[0-9]*")) {
								arrayBytes[2] = arrayBytes[2] + numero;
							}else{
								arrayBytes[2] = arrayBytes[2] + "P" + numero;
							}

							out.println(arrayBytes[0]+":"+arrayBytes[1]+":"+arrayBytes[2]);
						}						
					}
				}
				
				in.close();
			}catch(IOException e) {
				System.err.println("\nErro ao receber mensagem");
			}
			server.close();
			conexao.close();
		}catch(IOException e){
			System.err.println("\nServidor não conectado");
		}
	}
}
