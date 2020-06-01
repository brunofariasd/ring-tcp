
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.io.PrintStream;

public class Conector implements Runnable {
	private String host;
	private int port;

	public Conector(int port) {
		this.port = port;
	}

	public void run() {
		//Laço para que ele tente conexao com o destinatário de uma maneira constante.
		//	O tempo de constância é dada em Thread.sleep().
		while (true) {
			try {
				Socket cliente = new Socket(host, port);		
				System.out.println("\nConectado ao servidor");

				Scanner in = new Scanner(System.in);
				PrintStream out = new PrintStream(cliente.getOutputStream());

				while(in.hasNextLine()) {
					out.println(in.nextLine());
				}
			}catch(UnknownHostException e) {
				System.err.println("\nServidor não encontrado");
			}catch(IOException e) {
				System.err.println("Tentando conectar ao servidor...");
				try {
					Thread.sleep(4000);
				}catch(InterruptedException f) {
					System.err.println("\nErro inesperado de thread");
				}
			}
		}

	}	

}
