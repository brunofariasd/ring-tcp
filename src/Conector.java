
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.io.PrintStream;

public class Conector implements Runnable {
	private String host;
	private int port;

	public Conector(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void run() {
		//Laço para que ele tente conexao com o destinatário de uma maneira constante.
		//	O tempo de constância é dada em Thread.sleep().
		while (true) {
			try {
				Socket servidor = new Socket(host, port);

				Processo processo = new Processo();
				processo.servidor = servidor;

				System.out.println("\n(Cliente)Conectado ao servidor");

				Scanner in = new Scanner(System.in);
				PrintStream out = new PrintStream(servidor.getOutputStream());


				while(in.hasNextLine()) {
					out.println("P"+processo.numero+":P"+processo.numero+":"+in.nextLine());
				}
				
				servidor.close();
				in.close();
				out.close();
			}catch(UnknownHostException e) {
				System.err.println("\n(Cliente)Servidor não encontrado");
			}catch(IOException e) {
				System.err.println("(Cliente)Tentando conectar ao servidor...");
				try {
					Thread.sleep(4000);
				}catch(InterruptedException f) {
					System.err.println("\n(Cliente)Erro inesperado de thread");
				}
			}
		}

	}	

}
